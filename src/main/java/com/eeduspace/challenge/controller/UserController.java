package com.eeduspace.challenge.controller;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eeduspace.challenge.client.service.UserClient;
import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.ShareModel;
import com.eeduspace.challenge.model.UserFeedBackModel;
import com.eeduspace.challenge.model.UserInnerModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.VerifyCodeModel;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.persist.dao.TaskRewardReceiveMapper;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserFriend;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.ShareService;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.eeduspace.challenge.service.UserFeedBackService;
import com.eeduspace.challenge.service.UserFriendService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.UserTaskLogService;
import com.eeduspace.challenge.util.DateUtils;
import com.eeduspace.challenge.util.SMSUtil;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.eeduspace.uuims.comm.util.base.ValidateUtils;
import com.google.gson.Gson;

/**
 * Author: dingran
 * Date: 2016/7/12
 * Description:
 */

@RestController
@RequestMapping(value="/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Gson gson=new Gson();

    @Autowired
    private UserClient userClient;
    @Autowired
    private SMSUtil SMSUtil;

    @Autowired
    private UserService userService;
 
    @Inject
	private MessageUtil messageUtil;
    @Autowired
    private UserConvert userConvert;

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    
    @Autowired
    private UserFeedBackService userFeedBackService;
    @Autowired
    private SystemDictionaryService systemDictionaryService;
    
    @Autowired
    private ShareService shareService;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private UserTaskLogService userTaskLogService;
    @Autowired
	private TaskRewardReceiveMapper taskRewardReceiveMapperImpl;
    @Autowired
    private TaskRewardReceiveService taskRewardReceiveServiceImpl;
    
    public static ConcurrentMap<Long, IoSession> sessionsMap = new ConcurrentHashMap<Long, IoSession>();
    
    /**
     * 用户添加与注册
     * 向 uuims 注册用户
     * 1.获取参数  验证
     * 2.调用client入库uuims
     * 3.成功  执行入库 hxs_challenge 否则 return
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse registerOrCreate(@RequestParam("requestId") String requestId,
                                         @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            /**
             * TODO 需要注册来源、注册渠道、注册设备类型、产品类型
             * registerSource、channelSource、equipmentType、productType
             */
            logger.debug("userMode:{}",requestBody);
            UserRequestModel userModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userModel.getPassword())){
                logger.error("register Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "password");
            }
            if (StringUtils.isBlank(userModel.getMobile())) {
                logger.error("register Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "mobile");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userModel.getMobile())) {
                logger.error("register ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "mobile");
            }
            //todo 验证格式
            if(StringUtils.isNotBlank(userModel.getEquipmentType())){
                userModel.setEquipmentType(userConvert.converterSourceEquipmentType(userModel.getEquipmentType()).getValue());
            }
            //验证手机号唯一 去UUIMS 验证 当该手机号未被使用时则返回200
            BaseResponse clientBaseResponse = userClient.validateByMobile(userModel);
            if (clientBaseResponse == null || !"200".equals(clientBaseResponse.getHttpCode())) {
                logger.error("register  ValidateMobile Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "mobile");
            }

            User user = userService.register(userModel);
            
            if(user==null){
                logger.error("register Resource pool return  clientResponse exception.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_INUSE.toString());
            }
            /**
             * 初始化新手任务
             */
            if(baseResponse.getHttpCode().equals("200")){
            	
            	/**
                 * 新手任务
                 * w完成对战并且胜利
                 */
            	TaskRewardReceive taskRewardReceive = new TaskRewardReceive();
            	User userNew =userService.findByMobile(user.getMobile());
	            taskRewardReceive.setUserCode(userNew.getUserCode());
	            taskRewardReceive.setTaskType(TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode());
	            taskRewardReceive.setConcreteTaskCode(TaskEnum.SpecificTasks.BATTLE_VICTORY.getTaskCode());
	            taskRewardReceive.setUuid(UUID.randomUUID().toString().replace("-", ""));
	            taskRewardReceive.setTaskUuid("17b804869f1a4500aebe0f8518272254");
	            taskRewardReceive.setTaskName(TaskEnum.SpecificTasks.BATTLE_VICTORY.getDesc());
	            taskRewardReceive.setReceiveState(TaskEnum.ReceiveState.NoReceive.getValue());
	            taskRewardReceive.setUpdateDate(new Date());
	            taskRewardReceive.setReceiveDate(new Date());
	            taskRewardReceive.setRewardPoint(10);
	            taskRewardReceive.setFinishState(TaskEnum.FinishState.NoFinish.getValue());
	            
	            taskRewardReceiveMapperImpl.save(taskRewardReceive);
	            
	            /**
				 * 	新手任务
				 * 挑战任务
				 */
				TaskRewardReceive taskRewardReceive2 = new TaskRewardReceive();
				taskRewardReceive2.setUserCode(userNew.getUserCode());
	            taskRewardReceive2.setTaskType(TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode());
	            taskRewardReceive2.setConcreteTaskCode(TaskEnum.SpecificTasks.CHALLENGE.getTaskCode());
	            taskRewardReceive2.setUuid(UUID.randomUUID().toString().replace("-", ""));
	            taskRewardReceive2.setTaskUuid("63b804869f1a4500aebe0f8518272255");
	            taskRewardReceive2.setTaskName(TaskEnum.SpecificTasks.CHALLENGE.getDesc());
	            taskRewardReceive2.setReceiveState(TaskEnum.ReceiveState.NoReceive.getValue());
	            taskRewardReceive2.setUpdateDate(new Date());
	            taskRewardReceive2.setReceiveDate(new Date());
	            taskRewardReceive2.setRewardPoint(25);
	            taskRewardReceive2.setFinishState(TaskEnum.FinishState.NoFinish.getValue());
	            
	            taskRewardReceiveMapperImpl.save(taskRewardReceive2);
	            
	            /**
				 * 	新手任务
				 * 分享战报任务
				 */
				TaskRewardReceive taskRewardReceive3 = new TaskRewardReceive();
				taskRewardReceive3.setUserCode(userNew.getUserCode());
				taskRewardReceive3.setTaskType(TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode());
				taskRewardReceive3.setConcreteTaskCode(TaskEnum.SpecificTasks.STAND_SHARE.getTaskCode());
				taskRewardReceive3.setUuid(UUID.randomUUID().toString().replace("-", ""));
				taskRewardReceive3.setTaskUuid("etb804869f1a4500aebe0f85182722op");
				taskRewardReceive3.setTaskName(TaskEnum.SpecificTasks.STAND_SHARE.getDesc());
				taskRewardReceive3.setReceiveState(TaskEnum.ReceiveState.NoReceive.getValue());
				taskRewardReceive3.setUpdateDate(new Date());
	            taskRewardReceive3.setReceiveDate(new Date());
	            taskRewardReceive3.setRewardPoint(20);
	            taskRewardReceive3.setFinishState(TaskEnum.FinishState.NoFinish.getValue());
	            
	            taskRewardReceiveMapperImpl.save(taskRewardReceive3);
	            
	            
	            /**
				 * 	新手任务
				 * 完善信息
				 */
				TaskRewardReceive taskRewardReceive4 = new TaskRewardReceive();
				taskRewardReceive4.setUserCode(userNew.getUserCode());
				taskRewardReceive4.setTaskType(TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode());
				taskRewardReceive4.setConcreteTaskCode(TaskEnum.SpecificTasks.PERFECT_INFORMATION.getTaskCode());
				taskRewardReceive4.setUuid(UUID.randomUUID().toString().replace("-", ""));
				taskRewardReceive4.setTaskUuid("80b804869f1a4500aebe0f8518272251");
				taskRewardReceive4.setTaskName(TaskEnum.SpecificTasks.PERFECT_INFORMATION.getDesc());
				taskRewardReceive4.setReceiveState(TaskEnum.ReceiveState.NoReceive.getValue());
				taskRewardReceive4.setUpdateDate(new Date());
				taskRewardReceive4.setReceiveDate(new Date());
				taskRewardReceive4.setRewardPoint(500);
				taskRewardReceive4.setFinishState(TaskEnum.FinishState.NoFinish.getValue());
	            
	            taskRewardReceiveMapperImpl.save(taskRewardReceive4);
            }
            return baseResponse;
        }catch (Exception e){
            logger.error("register  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 根据刷新令牌获取新令牌
     *
     * @return
     */
    @RequestMapping(value = "/refresh",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse refresh( @RequestParam("requestId") String requestId,
                                    @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);

            if(StringUtils.isBlank(userRequestModel.getRefreshToken())){
                logger.error("token refresh Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".refreshToken");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "refreshToken");
            }
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("token refresh Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            baseResponse= userService.refreshToken(userRequestModel);
            return baseResponse;

        } catch (Exception e) {
            logger.error("token refresh error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }//{"mobile":"13311657870","password":"test1234561","registerSource":"011","productType":"HaoXusSheng","channelSource":"000","equipmentType":"Test"}
    }
    /**
     * 根据用户code查询用户
     *
     * @return
     */
    @RequestMapping(value = "/inner/info",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse findByCodeInner( @RequestParam("requestId") String requestId,
                                         @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);

            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("findByCodeInner Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            //TODO 进行实体与MODEL的转换
            User user =userService.findByUserCode(userRequestModel.getUserCode());
            if (user ==null) {
                logger.error("findByCodeInner Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString());
            }
            UserInnerModel userInnerModel=new UserInnerModel();
            userInnerModel.setUserCode(userRequestModel.getUserCode());
            if(!org.springframework.util.StringUtils.isEmpty(user.getVipEndDate())){
                Date dateNow=new Date();
                if(user.getVipEndDate().after(dateNow)){
                    userInnerModel.setIsVip(1);
                }
            }
            baseResponse.setResult(userInnerModel);
            logger.debug("/inner/info 根据用户code查询用户"+gson.toJson(baseResponse));
            return baseResponse;

        } catch (Exception e) {
            logger.error("findByCodeInner error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 根据用户code查询用户
     *
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse findByCode( @RequestParam("requestId") String requestId,
                                    @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);

            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("findByCode Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            //TODO 进行实体与MODEL的转换
            User userInfoPo =userService.findByUserCode(userRequestModel.getUserCode());
            if (userInfoPo ==null) {
                logger.error("findByCode Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString());
            }
            UserModel userModel= userConvert.fromUserPo(userInfoPo, true);
            if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
            	userModel.setOverdue(false);
			}else {
				userModel.setOverdue(true);
			}
            if(StringUtils.isNotBlank(userRequestModel.getFriendCode())){
                UserFriend userFriend= userFriendService.findByMyUserCodeAndFriendCode(userRequestModel.getUserCode(), userRequestModel.getFriendCode());
                if(userFriend!=null){
                    userModel.setFriend(true);
                }else{
                	userModel.setFriend(false);
                }
            }
            /**
             * 完成信息完善	触发   修改任务表信息  finishState
             */
            //根据任务的task_uuid查询完成状态
            TaskRewardReceive uuidReceive = taskRewardReceiveServiceImpl.findByTaskUuidAndUserCode("80b804869f1a4500aebe0f8518272251",userModel.getUserCode());
			if(uuidReceive != null && uuidReceive.getFinishState()==TaskEnum.FinishState.NoFinish.getValue()){
				//修改完成状态
				if(uuidReceive.getConcreteTaskCode().equals("perfect_information")){
					updateReceiveState(userModel);
				}
				
			}
           
            
            baseResponse.setResult(userModel);
            logger.debug("/info 根据用户code查询用户  : "+gson.toJson(baseResponse));
            return baseResponse;

        } catch (Exception e) {
            logger.error("findByCode error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 用户完善信息  监听  
     * @param userModel
     */
	public void updateReceiveState(UserModel userModel){
		logger.debug("updateReceiveState  userCode"+userModel.getUserCode());
    	if(StringUtils.isBlank(userModel.getHeadImgUrl())){
    		return ;
      	}
    	if(StringUtils.isBlank(userModel.getNickName())){
    		return ;
      	}
    	if(StringUtils.isBlank(userModel.getSex()+"")){
    		return ;
      	}
    	if(StringUtils.isBlank(userModel.getBirthday())){
    		return ;
      	}
    	if(StringUtils.isBlank(userModel.getArea())){
    		return ;
      	}
    	if(StringUtils.isBlank(userModel.getSchool())){
    		return ;
      	}
    	if(StringUtils.isBlank(userModel.getGrade())){
    		return ;
      	}
    	
    	taskRewardReceiveServiceImpl.updateByUuidAndUserCode("80b804869f1a4500aebe0f8518272251", userModel.getUserCode());;
    }
    /**
     * 根据用户手机号查询用户
     *
     * @return
     */
    @RequestMapping(value = "/info/mobile",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse findByMobile( @RequestParam("requestId") String requestId,
                                      @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);

            if(StringUtils.isBlank(userRequestModel.getMobile())){
                logger.error("findByMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            //TODO 进行实体与MODEL的转换
            User userInfoPo =userService.findByMobile(userRequestModel.getMobile());
            if (userInfoPo ==null) {
                logger.error("findByCode Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString());
            }
            UserModel userModel= userConvert.fromUserPo(userInfoPo, true);
            if(StringUtils.isNotBlank(userRequestModel.getFriendCode())){
                UserFriend userFriend= userFriendService.findByMyUserCodeAndFriendCode(userInfoPo.getUserCode(), userRequestModel.getFriendCode());
                if(userFriend!=null){
                    userModel.setFriend(true);
                }else{
                	userModel.setFriend(false);
                }
            }
            baseResponse.setResult(userModel);
            return baseResponse;
           
        } catch (Exception e) {
            logger.error("findByMobile error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login( @RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getPassword())){
                logger.error("login Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "password");
            }
            if (StringUtils.isBlank(userRequestModel.getMobile())) {
                logger.error("login Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "mobile");
            }
            if (StringUtils.isBlank(userRequestModel.getMacAddress())) {
                logger.error("login Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".macAddress");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "macAddress");
            }
            // 验证数据格式
            if (!ValidateUtils.isMobile(userRequestModel.getMobile())) {
                logger.error("login ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "mobile");
            }
           BaseResponse loginResponse= userService.login(userRequestModel,false);
           loginResponse.setRequestId(baseResponse.getRequestId());
           return loginResponse;
        } catch (Exception e) {
        	
        	/**/
			
            logger.error("login error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 登出系统
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse logOut(@RequestParam("requestId") String requestId,
                               @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("logOut Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            User user= userService.findByUserCode(userRequestModel.getUserCode());
            if(user==null){
                logger.error("logOut Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            }
            user.setUserOnlineState(UserEnum.OnlineStatus.Off_line.getValue());
            userService.update(user);
            //TODO  删除 该用户下的token 并向uuims 请求退出
            String userToken =  redisClientTemplate.get("hxs_challenge_"+userRequestModel.getUserCode());
            if(StringUtils.isNotBlank(userToken)){
                redisClientTemplate.del(userToken);
            }
            redisClientTemplate.del(userRequestModel.getUserCode());
            //TODO 记录日志
            return baseResponse;
        } catch (Exception e) {
            logger.error("logOut error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 修改密码
     */
    @RequestMapping(value = "/pwd/edit",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse editPwd(@RequestParam("requestId") String requestId,
                                @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            BaseResponse editPwdResponse= userService.editPwd(userRequestModel);
            editPwdResponse.setRequestId(baseResponse.getRequestId());
            return editPwdResponse;
        } catch (Exception e) {
            logger.error("editPwd error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 重置密码
     */
    @RequestMapping(value = "/pwd/reset",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse resetPwd(@RequestParam("requestId") String requestId,
                                 @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            //忘记密码的时候  把这个人对应的token清除掉
            /**发送一条消息*/
            
            /*  User findByMobile = userService.findByMobile(userRequestModel.getMobile());
            if(findByMobile==null){
            	BaseResponse resetPwdResponse= userService.resetPwd(userRequestModel);
                resetPwdResponse.setRequestId(baseResponse.getRequestId());
                
                return resetPwdResponse;
            }
            Integer userOnlineState = findByMobile.getUserOnlineState();
          if(userOnlineState != null && userOnlineState != UserEnum.OnlineStatus.Off_line.getValue()){
            	*
            	 * 发送消息
            	 *
            	ClientModel clientModel=new ClientModel();
            	clientModel.setFromUserCode("eeduspacerobot");
				clientModel.setMessageType("hxs_login_repeat");
				clientModel.setMessage("您的账号密码被修改，如非本人操作请修改密码，重新登录！");
				clientModel.setToUserCode(findByMobile.getUserCode());
				messageUtil.sendMessageToOne(clientModel);
				logger.debug("重复登录发送消息："+gson.toJson(clientModel));
            }
           
            String userToken =  redisClientTemplate.get("hxs_challenge_"+findByMobile.getUserCode());
            if(StringUtils.isNotBlank(userToken)){
                redisClientTemplate.del(userToken);
            }
             */
            /**
             * 激活用户的条件是：1，本人用户名还有密码匹配并且是正确的
             * 				2，忘记密码携带上本地库表中的ak，sk去uuims修改密码
             * 
             * 先去本地库表查找用户，如果没有直接弹出友情提示，无法修改密码
             */
            User findByUserCode = userService.findByUserCode(userRequestModel.getMobile());
            if(findByUserCode==null){
            	return BaseResponse.setResponse(ResponseCode.SUCCESS.toString(),"此用户不是在挑战好学生平台注册的，无法在这个平台修改密码！");
            }
            /**发送一条消息*/
            
            BaseResponse resetPwdResponse= userService.resetPwd(userRequestModel);
            resetPwdResponse.setRequestId(baseResponse.getRequestId());
            
            return resetPwdResponse;
        } catch (Exception e) {
            logger.error("resetPwd error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 修改基本信息
     */
    @RequestMapping(value = "/complete/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse completeUserInfo(@RequestParam("requestId") String requestId,
                                         @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("completeUserInfo Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            User user= userService.findByUserCode(userRequestModel.getUserCode());
            if(user==null){
                logger.error("completeUserInfo Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            }
            userService.update(userRequestModel);
            //获取用户详情
            baseResponse.setResult(userConvert.fromUserPo(user, true));
/*            UserInfo userInfo= userService.getDetailByUserCode(userRequestModel.getUserCode());
            if(userInfo==null){
                userInfo=new UserInfo();
                userInfo.setUserCode(userRequestModel.getUserCode());
                userInfo = userService.saveUserInfo(userInfo);
             }
            int userInfoDegree=user.getUserInfoDegree();
            userInfo = userService.computeUserInfoDegree(userInfoDegree,userRequestModel,userInfo);
            user.setUserInfoDegree(userInfo.getUserInfoDegree());
            userService.updateUserAndUserInfo(user,userInfo);*/
            return baseResponse;
        } catch (Exception e) {
            logger.error("completeUserInfo error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 
     * 购买体力值
     * @param requestId
     * @return
     */

    @RequestMapping(value = "/buy/stamina",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse buyStamina(@RequestParam("requestId") String requestId,
                                   @RequestBody String requestBody) {

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if (StringUtils.isBlank(userRequestModel.getUserCode())) {
                logger.error("buyStamina Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            User user=userService.findByUserCode(userRequestModel.getUserCode());
            if (null==user) {
                logger.error("buyStamina Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            }
            //验证积分够不够
            //TODO 设置体力值
            String stamina=  redisClientTemplate.get("hxs_challenge_stamina_"+userRequestModel.getUserCode());
            if(org.apache.commons.lang.StringUtils.isBlank(stamina)){
                logger.error("buyStamina ("+userRequestModel.getUserCode()+")  hxs_challenge_stamina is null.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(),"hxs_challenge_stamina_"+userRequestModel.getUserCode());
            }
            String[] staminaStrings=stamina.split(",");
            if(Integer.parseInt(staminaStrings[1])<1){
                logger.error("buyStamina ("+userRequestModel.getUserCode()+")  hxs_challenge_stamina is null.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_NOPERMISSION.toString());
            }
            SystemDictionary staminaSystemD=systemDictionaryService.findByName(SystemDictionaryEnum.USER_STRENGTH.getCode());
            Long sv=   Long.parseLong(staminaSystemD.getValue());
            logger.debug("================sv=================sv========================:"+sv+";userpoints"+user.getUserPoints());
            //验证积分够不够
            if(user.getUserPoints()<sv){
                logger.error("buyStamina ("+userRequestModel.getUserCode()+")  user.getUserPoints() is small.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.INTEGRAL_INSUFFICIENT.toString());
            }
            userService.updateUserIntegralChange(userRequestModel.getUserCode(),sv , RewardEnum.ChangeType.EXCHANGE, RewardEnum.RewardType.STAMINA, null);
            int sx= Integer.parseInt(staminaStrings[1])-1;
            redisClientTemplate.setex("hxs_challenge_stamina_"+userRequestModel.getUserCode(), DateUtils.getTadaySurplusTime(),Integer.parseInt(staminaStrings[0])+1+","+sx);

            return baseResponse;
        }catch (Exception e){
            logger.error("buyStamina  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 获取验证码
     * @param requestId
     * @return
     */

    @RequestMapping(value = "/verifyCode",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse verifyCode(@RequestParam("requestId") String requestId,
                                   @RequestBody String requestBody) {

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            VerifyCodeModel verifyCodeModel = gson.fromJson(requestBody, VerifyCodeModel.class);
            String mobile = verifyCodeModel.getMobile();
            if (StringUtils.isBlank(verifyCodeModel.getMobile())) {
                logger.error("verifyCode Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".mobile");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "mobile");
            }
            if (StringUtils.isBlank(verifyCodeModel.getType())) {
                logger.error("verifyCode Exception：requestId："+baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".type");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "type");
            }
            //TODO 验证类型
            //TODO 验证手机号 是否已经存在
            String code= SMSUtil.send(verifyCodeModel.getMobile(), verifyCodeModel.getType());
            verifyCodeModel=new VerifyCodeModel();
            verifyCodeModel.setCode(code);
            verifyCodeModel.setMobile(mobile);
            baseResponse.setResult(verifyCodeModel);
            return baseResponse;
        }catch (Exception e){
            logger.error("verifyCode  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 用户反馈
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/feedBack",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse UserFeedBack(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
    	UserFeedBackModel userFeedBackModel = gson.fromJson(requestBody, UserFeedBackModel.class);
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
//            if (StringUtils.isBlank(userFeedBackModel.getUserCode())) {
//                logger.error("feedBack Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
//            }
            if (StringUtils.isBlank(userFeedBackModel.getMessage())) {
                logger.error("feedBack Exception：requestId："+baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".message");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "message");
            } 	
            if (userFeedBackModel.getMessage().length()>=1000) {
                logger.error("feedBack Exception：requestId："+baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_LIMITEXCEEDED.toString()+".message");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_LIMITEXCEEDED.toString(), "message");
            }
            User user=userService.findByUserCode(userFeedBackModel.getUserCode());
//            if (null==user) {
//                logger.error("feedBack Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
//                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
//            } 
            userFeedBackService.saveUserFeedBack(user, userFeedBackModel);
            userTaskLogService.UserTaskLogSave(userFeedBackModel.getUserCode(), "user_center", null, null, "user_feed_back", null);
            return baseResponse;
        }catch (Exception e){
            logger.error("feedBack  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 保存用户分享记录
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/share",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse UserShare(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
    	ShareModel shareModel = gson.fromJson(requestBody, ShareModel.class);
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            if (StringUtils.isBlank(shareModel.getUserCode())) {
                logger.error("share Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            User users=userService.findByUserCode(shareModel.getUserCode());
            if (null==users) {
                logger.error("share Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            } 
            String  eventType= userConvert.converterSourceEventtType(shareModel.getEventType());
            if (StringUtils.isBlank(eventType)) {
            	 logger.error("share Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".eventType");
                 return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "eventType");
			}
            String shareType = UserEnum.ShareType.toEnumValue(shareModel.getShareType());
            if (StringUtils.isBlank(shareType)) {
           	 logger.error("share Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".shareType");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "shareType");
			}
            shareService.saveUserShare(shareModel);
            userTaskLogService.UserTaskLogSave(shareModel.getUserCode(), "share", null, null, eventType, eventType);
            logger.debug("share 保存分享记录返回数据"+gson.toJson(baseResponse));
            /**
			 * 	新手任务
			 * 完成一个分享任务，修改任务状态信息 finishState
			 */
			if(baseResponse.getHttpCode().equals("200")){
				//根据 task_uuid 查找任务对象
				TaskRewardReceive uuidReceive = taskRewardReceiveServiceImpl.findByTaskUuidAndUserCode("etb804869f1a4500aebe0f85182722op",shareModel.getUserCode());
				if(uuidReceive != null && uuidReceive.getFinishState()==TaskEnum.FinishState.NoFinish.getValue()){
					//修改完成状态
					if(("stand_share").equals(uuidReceive.getConcreteTaskCode())){
						taskRewardReceiveServiceImpl.updateByUuidAndUserCode("etb804869f1a4500aebe0f85182722op", shareModel.getUserCode());
					}
				}
			}
			logger.debug("shareModel.getUserCode():"+shareModel.getUserCode()+",baseResponse:"+gson.toJson(baseResponse));
            return baseResponse;
        }catch (Exception e){
            logger.error("share  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
}
