package com.eeduspace.challenge.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeduspace.challenge.client.model.response.UserInfoResponse;
import com.eeduspace.challenge.client.model.response.UserLoginResponse;
import com.eeduspace.challenge.client.service.UserClient;
import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.IntegralChangeModel;
import com.eeduspace.challenge.model.TokenModel;
import com.eeduspace.challenge.model.UserBuyVipResponseModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.request.UserBuyVipRequestModel;
import com.eeduspace.challenge.model.request.UserBuyVipSuccessModel;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.persist.dao.UserInfoMapper;
import com.eeduspace.challenge.persist.dao.UserMapper;
import com.eeduspace.challenge.persist.po.IntegralChange;
import com.eeduspace.challenge.persist.po.IntegralGet;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserFriend;
import com.eeduspace.challenge.persist.po.UserInfo;
import com.eeduspace.challenge.persist.po.VipPack;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.IntegralChangeService;
import com.eeduspace.challenge.service.IntegralGetService;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.eeduspace.challenge.service.UserFriendService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.VipPackService;
import com.eeduspace.challenge.util.DateUtils;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;

/**
 * Author: dingran
 * Date: 2016/7/12
 * Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private Gson gson=new Gson();
    @Value("${userInfoDegree.headImgUrl}")
    private String headImgUrl;
    @Value("${userInfoDegree.nickName}")
    private String nickName;
    @Value("${userInfoDegree.sex}")
    private String sex;
    @Value("${userInfoDegree.birthday}")
    private String birthday;
    @Value("${userInfoDegree.area}")
    private String area;
    @Value("${userInfoDegree.city}")
    private String city;
    @Value("${userInfoDegree.province}")
    private String province;
    @Value("${userInfoDegree.school}")
    private String school;
    @Value("${userInfoDegree.grade}")
    private String grade;
    @Value("${user.weixin.buy.vipPack}")
    private String weixinBuyVipPack;
    @Value("${user.zhifubao.buy.vipPack}")
    private String zhifubaoBuyVipPack;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private IntegralGetService integralGetService;
    @Resource
    private IntegralChangeService integralChangeService;
    @Resource
    private SystemDictionaryService systemDictionaryService;
    @Resource
    private UserFriendService userFriendService;
    @Inject
    private UserClient userClient;
    @Inject
  	private MessageUtil messageUtil;
    @Inject
    private UserConvert userConvert;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private VipPackService vipPackService;
    @Autowired
    private TaskRewardReceiveService taskRewardReceiveService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.baseDao =userMapper;
    }

    @Override
    public List<UserModel> findFriendByUserCode(String userCode) {
        return userMapper.findFriendByUserCode(userCode);
    }

    @Override
    public UserModel findDetailAllByUserCode(String userCode) {
        UserModel userModel= userMapper.findDetailByUserCode(userCode);
        
        //TODO 设置体力值
        String stamina=  redisClientTemplate.get("hxs_challenge_stamina_" + userCode);
        if(StringUtils.isBlank(stamina)){
            SystemDictionary systemDictionary=new SystemDictionary();
            systemDictionary.setValue("3,1");
            if(userModel != null && userModel.getIsVip()==0){
                systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA.getCode());
            }else{
            	systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA_VIP.getCode());
            }
                

            stamina=systemDictionary.getValue();
            redisClientTemplate.setex("hxs_challenge_stamina_"+userCode, DateUtils.getTadaySurplusTime(), systemDictionary.getValue());
        }
        String[] staminaStrings=stamina.split(",");
        if(staminaStrings.length>0){
        	userModel.setStamina(Integer.parseInt(staminaStrings[0]));
        }
        return userModel;
    }

    @Override
    public User findByUserCode(String userCode){
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        List<User> list=this.findByCondition(queryMap);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public User findByMobile(String mobile) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("mobile", mobile);
        List<User> list=this.findByCondition(queryMap);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    @Transactional
    public User register(UserRequestModel userModel) throws IOException {
        User user=null;
        BaseResponse baseResponse= userClient.userRegister(userModel);
        //创建成功
        if("Success".equals(baseResponse.getCode())){
            UserInfoResponse userInfoResponse = GsonUtil.fromObjectJson(gson.toJson(baseResponse), "result", "userModel", UserInfoResponse.class);
            User userInfoPo = new User();
            userInfoPo.setMobile(userInfoResponse.getPhone());
            userInfoPo.setPassword(userModel.getPassword());//TODO 可以不进行记录了
            userInfoPo.setOpenId(userInfoResponse.getOpenId());
            userInfoPo.setAccessKey(userInfoResponse.getAccessKey());
            userInfoPo.setSecretKey(userInfoResponse.getSecretKey());
            userInfoPo.setUserCode(UUID.randomUUID().toString().replace("-", ""));
            String userName="iwrong" + userInfoResponse.getPhone().substring(3);
            userInfoPo.setUserName(userName);

            userInfoPo.setEquipmentType(userModel.getEquipmentType());
            userInfoPo.setRegisterSource(userModel.getRegisterSource());
            if(StringUtils.isBlank(userModel.getChannelSource())){
                userInfoPo.setChannelSource("000");
            }
            userInfoPo.setChannelSource(userModel.getChannelSource());
            userInfoPo.setUserInfoDegree(0);
            userInfoPo.setFirst(0l);
            userInfoPo.setSecond(0l);
            userInfoPo.setThird(0l);
            userInfoPo.setUserPoints(0l);
            userInfoPo.setFightValue(0l);
            userInfoPo.setIsVip(UserEnum.Vip.Ordinary_user.getValue());
            userInfoPo.setUserOnlineState(UserEnum.OnlineStatus.Off_line.getValue());
            userInfoPo.setCreateDate(new Date());
            userInfoPo.setUpdateDate(new Date());
            userInfoPo.setUserInfoDegree(Integer.parseInt(nickName));
            userInfoPo.setWeekStatus(0d);
            //TODO
            user= userMapper.insert(userInfoPo);
            //TODO 添加 我是我的好友
            UserFriend userFriend=new UserFriend();
            userFriend.setMyUserCode(user.getUserCode());
            userFriend.setFriendCode(user.getUserCode());
            userFriend.setCreateDate(new Date());
            userFriend.setUpdateDate(new Date());
            userFriendService.save(userFriend);
            //TOOD 保存用户详情
            UserInfo userInfo=new UserInfo();
            userInfo.setUserCode(user.getUserCode());
            userInfo.setNickName(userName);
            userInfoMapper.save(userInfo);
        }else
            logger.error("register reponse:{}",gson.toJson(baseResponse));

        return user;
    }

    @Override
    public BaseResponse refreshToken(UserRequestModel userRequestModel) throws IOException {
        BaseResponse clientBaseResponse =  userClient.refreshToken(userRequestModel);
        if("Success".equals(clientBaseResponse.getCode())){
            UserLoginResponse userLoginResponse=GsonUtil.fromObjectJson(gson.toJson(clientBaseResponse), "result", "tokenModel", UserLoginResponse.class);
            UserModel  userModel=new UserModel();
            userModel.setUserCode(userRequestModel.getUserCode());
            userModel.setToken(userLoginResponse.getToken());
            userModel.setRefreshToken(userLoginResponse.getRefreshToken());
            userModel.setExpires(userLoginResponse.getExpires());

            redisClientTemplate.setex(userLoginResponse.getToken(), Integer.parseInt(userLoginResponse.getExpires()), gson.toJson(userModel));
            redisClientTemplate.setex("hxs_challenge_"+userRequestModel.getUserCode(), Integer.parseInt(userLoginResponse.getExpires()), userLoginResponse.getToken());
        }
        return clientBaseResponse;
    }

    @Override
    @Transactional
    public BaseResponse login(UserRequestModel userRequestModel,boolean isScan) throws IOException {
        userRequestModel.setPhone(userRequestModel.getMobile());
        TokenModel tokenModel=null;
        BaseResponse loginResponse=userClient.userLogin(userRequestModel);
        logger.debug("===================loginResponse==================="+gson.toJson(loginResponse));
        if(loginResponse.getHttpCode().equals("200")){
            UserLoginResponse userLoginResponse= GsonUtil.fromObjectJson(gson.toJson(loginResponse), "result", "userModel", UserLoginResponse.class);
            User user = this.findByMobile(userRequestModel.getPhone());
            String userCode="";
            if(user ==null){
               //如果本地不存在用户，去UUIMS激活用户
                user =new User();
                BaseResponse activationResponse=userClient.userActivation(userRequestModel);
                UserLoginResponse userActivationResponse=GsonUtil.fromObjectJson(gson.toJson(activationResponse), "result", "userModel", UserLoginResponse.class);
                user.setAccessKey(userActivationResponse.getAccessKey());
                user.setSecretKey(userActivationResponse.getSecretKey());
                user.setOpenId(userActivationResponse.getOpenId());
                user.setMobile(userActivationResponse.getPhone());
                logger.debug("===============password===================="+userActivationResponse.getPassword()+userLoginResponse.getPassword()+",");
                user.setPassword(userActivationResponse.getPassword());
                userCode=UUID.randomUUID().toString().replace("-", "");
                user.setUserCode(userCode);
                String userName="iwrong" + userRequestModel.getPhone().substring(3);
                user.setUserName(userName);
                
                user.setMacAddress(userRequestModel.getMacAddress());
                
                user.setEquipmentType("Web");
                user.setRegisterSource(userActivationResponse.getProductType());
                user.setChannelSource("000");

                user.setUserInfoDegree(0);
                user.setFirst(0l);
                user.setSecond(0l);
                user.setThird(0l);
                user.setUserPoints(0l);
                user.setFightValue(0l);
                user.setIsVip(UserEnum.Vip.Ordinary_user.getValue());
                user.setUserInfoDegree(Integer.parseInt(nickName));
                user.setUserOnlineState(UserEnum.OnlineStatus.On_line.getValue());
                user.setWeekStatus(0d);
                UserInfo userInfo=new UserInfo();
                userInfo.setNickName(userName);
                userInfo.setUserCode(userCode);
                userInfoMapper.save(userInfo);
                //TODO 添加 我是我的好友
                UserFriend userFriend=new UserFriend();
                userFriend.setMyUserCode(userCode);
                userFriend.setFriendCode(userCode);
                userFriend.setCreateDate(new Date());
                userFriend.setUpdateDate(new Date());
                userFriendService.save(userFriend);

                this.save(user);
            }else {
            	//验证用户替换设备，发送给被替换设备异地登录警告信息
            	if((!user.getMacAddress().equals(userRequestModel.getMacAddress())) && user.getUserOnlineState()==1 ){
            		ClientModel clientModel=new ClientModel();
                	clientModel.setFromUserCode("eeduspacerobot");
        			clientModel.setMessageType("hxs_login_repeat");
        			clientModel.setMessage("您的账号已在另一台"+userRequestModel.getMacAddress().split("-")[0]+"设备上登录，如非本人操作请修改密码，重新登录！");
        			clientModel.setToUserCode(user.getUserCode());
        			messageUtil.sendMessageToOne(clientModel);
        			logger.debug("重复登录发送消息："+gson.toJson(clientModel));
            	}
                userCode=user.getUserCode();
                //避免用户的密码在其它的应用中做了修改
                user.setPassword(userRequestModel.getPassword());
                user.setUpdateDate(new Date());
                user.setUserOnlineState(UserEnum.OnlineStatus.On_line.getValue());
                user.setMacAddress(userRequestModel.getMacAddress());
                this.update(user);
            }


            //TODO 删除用户之前存在的TOKEN
            logger.debug("获取redis中的token的键是："+"hxs_challenge_"+userCode);
            String userCodeToken= redisClientTemplate.get("hxs_challenge_"+userCode);
            logger.debug("根据键获取到的token值   ："+userCodeToken);
            if(StringUtils.isNotBlank(userCodeToken)){
                redisClientTemplate.del(userCodeToken);
            }
            logger.debug("删除完之后根据键获取到的token值   ："+userCodeToken);
            //todo 将token保存在redis中
            tokenModel=new TokenModel();
            tokenModel.setUserCode(userCode);
            tokenModel.setToken(userLoginResponse.getToken());
            tokenModel.setRefreshToken(userLoginResponse.getRefreshToken());
            tokenModel.setExpires(userLoginResponse.getExpires());
            logger.debug("token有效时间token------------------:"+Integer.parseInt(userLoginResponse.getExpires()));
            redisClientTemplate.setex(userLoginResponse.getToken(), Integer.parseInt(userLoginResponse.getExpires()), gson.toJson(tokenModel));
            logger.debug("redis设置到的token----key-userLoginResponse.getToken()-------------------："+redisClientTemplate.get(userLoginResponse.getToken()));
            redisClientTemplate.setex("hxs_challenge_"+userCode, Integer.parseInt(userLoginResponse.getExpires()), userLoginResponse.getToken());
            logger.debug("redis设置到的token----key-hxs_challenge_+userCode---------------："+redisClientTemplate.get("hxs_challenge_"+userCode));
            //TODO 设置体力值
            String stamina=  redisClientTemplate.get("hxs_challenge_stamina_"+userCode);
            if(StringUtils.isBlank(stamina)){
                SystemDictionary systemDictionary=new SystemDictionary();
                if(user.getIsVip()==0){
                	systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA.getCode());
                }else{
                	systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA_VIP.getCode());
                }

                redisClientTemplate.setex("hxs_challenge_stamina_"+userCode, DateUtils.getTadaySurplusTime(), systemDictionary.getValue());
            }

            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setResult(tokenModel);
            return baseResponse;
            //todo 添加用户登录日志
        }
        return loginResponse;
    }

    @Override
    public BaseResponse editPwd(UserRequestModel userModel) throws IOException {
        User user = this.findByUserCode(userModel.getUserCode());
//        System.out.println(user.getOpenId()+"---------------------------------------");
        userModel.setOpenId(user.getOpenId());
        userModel.setUserAccessKey(user.getAccessKey());
        userModel.setUserSecretKey(user.getSecretKey());
        userModel.setToken(userModel.getToken());
        BaseResponse clientBaseResponse =  userClient.updatePassword(userModel);
        if("Success".equals(clientBaseResponse.getCode())){
            user.setPassword(userModel.getPassword());
            this.update(user);
//            userMapper.updateByPrimaryKeySelective(user);
        }
        return clientBaseResponse;
    }

    @Override
    public BaseResponse resetPwd(UserRequestModel userModel) throws IOException {
//        User user = this.findByMobile(userModel.getMobile());
//        String userCode="";
//        if(user==null){
//        	//原来的代码逻辑是本地没有了  去uuims根据电话号码查找
//        	
//        	BaseResponse byPhone = userClient.describeByPhone(userModel);
//        	logger.debug("resetPwd user_findByMobile:"+user+",byPhone:"+byPhone);
//        	if(byPhone.getCode().equals("Success")){
//        		user =new User();
//                BaseResponse activationResponse=userClient.userActivation(userModel);
//                UserLoginResponse userActivationResponse=GsonUtil.fromObjectJson(gson.toJson(activationResponse), "result", "userModel", UserLoginResponse.class);
//                user.setAccessKey(userActivationResponse.getAccessKey());
//                user.setSecretKey(userActivationResponse.getSecretKey());
//                user.setOpenId(userActivationResponse.getOpenId());
//                user.setMobile(userActivationResponse.getPhone());
//                user.setPassword(userActivationResponse.getPassword());
//                userCode=UUID.randomUUID().toString().replace("-", "");
//                user.setUserCode(userCode);
//                String userName="iwrong" + userModel.getPhone().substring(3);
//                user.setUserName(userName);
//
//                user.setEquipmentType("Web");
//                user.setRegisterSource(userActivationResponse.getProductType());
//                user.setChannelSource("000");
//
//                user.setUserInfoDegree(0);
//                user.setFirst(0l);
//                user.setSecond(0l);
//                user.setThird(0l);
//                user.setUserPoints(0l);
//                user.setFightValue(0l);
//                user.setIsVip(UserEnum.Vip.Ordinary_user.getValue());
//                user.setUserInfoDegree(Integer.parseInt(nickName));
//                user.setUserOnlineState(UserEnum.OnlineStatus.On_line.getValue());
//                user.setWeekStatus(0d);
//
//                UserInfo userInfo=new UserInfo();
//                userInfo.setNickName(userName);
//                userInfo.setUserCode(userCode);
//                userInfoMapper.save(userInfo);
//                //TODO 添加 我是我的好友
//                UserFriend userFriend=new UserFriend();
//                userFriend.setMyUserCode(userCode);
//                userFriend.setFriendCode(userCode);
//                userFriend.setCreateDate(new Date());
//                userFriend.setUpdateDate(new Date());
//                userFriendService.save(userFriend);
//
//                this.save(user);
//        	}else {
//        		return BaseResponse.setResponse(new BaseResponse(),ResponseCode.RESOURCE_NOTFOUND.toString());
//        	}
//        	
//        }
//        userModel.setOpenId(user.getOpenId());
//        BaseResponse clientBaseResponse =  userClient.resetPwd(userModel);
//        if("Success".equals(clientBaseResponse.getCode())){
//            user.setPassword(userModel.getPassword());
//            this.update(user);
//        }
//        return clientBaseResponse;
    	
    	
    	
    	//友情提示版本
    	User user = this.findByMobile(userModel.getMobile());
    	userModel.setPhone(userModel.getMobile());
        String userCode="";
        if(user==null){
        	//原来的代码逻辑是本地没有了  去uuims根据电话号码查找
        	
//        	BaseResponse byPhone = userClient.describeByPhone(userModel);
//        	logger.debug("resetPwd user_findByMobile:"+user+",byPhone:"+byPhone);
//        	if(byPhone.getCode().equals("Success")){
        		user =new User();
                BaseResponse activationResponse=userClient.userActivation(userModel);
                UserLoginResponse userActivationResponse=GsonUtil.fromObjectJson(gson.toJson(activationResponse), "result", "userModel", UserLoginResponse.class);
                user.setAccessKey(userActivationResponse.getAccessKey());
                user.setSecretKey(userActivationResponse.getSecretKey());
                user.setOpenId(userActivationResponse.getOpenId());
                user.setMobile(userActivationResponse.getPhone());
                user.setPassword(userActivationResponse.getPassword());
                userCode=UUID.randomUUID().toString().replace("-", "");
                user.setUserCode(userCode);
                String userName="iwrong" + userModel.getPhone().substring(3);
                user.setUserName(userName);

                user.setEquipmentType("Web");
                user.setRegisterSource(userActivationResponse.getProductType());
                user.setChannelSource("000");

                user.setUserInfoDegree(0);
                user.setFirst(0l);
                user.setSecond(0l);
                user.setThird(0l);
                user.setUserPoints(0l);
                user.setFightValue(0l);
                user.setIsVip(UserEnum.Vip.Ordinary_user.getValue());
                user.setUserInfoDegree(Integer.parseInt(nickName));
                user.setUserOnlineState(UserEnum.OnlineStatus.On_line.getValue());
                user.setWeekStatus(0d);

                UserInfo userInfo=new UserInfo();
                userInfo.setNickName(userName);
                userInfo.setUserCode(userCode);
                userInfoMapper.save(userInfo);
                //TODO 添加 我是我的好友
                UserFriend userFriend=new UserFriend();
                userFriend.setMyUserCode(userCode);
                userFriend.setFriendCode(userCode);
                userFriend.setCreateDate(new Date());
                userFriend.setUpdateDate(new Date());
                userFriendService.save(userFriend);

                this.save(user);
//        	}else {
//        		return BaseResponse.setResponse(new BaseResponse(),ResponseCode.RESOURCE_NOTFOUND.toString());
//        	}
        	
        }
        userModel.setOpenId(user.getOpenId());
        BaseResponse clientBaseResponse =  userClient.resetPwd(userModel);
        if("Success".equals(clientBaseResponse.getCode())){
            user.setPassword(userModel.getPassword());
            this.update(user);
        }
        return clientBaseResponse;
      
    }

    @Override
    @Transactional
    public void update(UserRequestModel userRequestModel) {
        User user= this.findByUserCode(userRequestModel.getUserCode());
        logger.debug("user.getId" + user.getId());
        logger.debug("userRequestModel.getUserCode()" + userRequestModel.getUserCode());
        UserInfo userInfo= this.getDetailByUserCode(userRequestModel.getUserCode());
        logger.debug("userInfo" + gson.toJson(userInfo));
        if(userInfo==null){
            logger.debug("--------------------------------------null");
            userInfo=new UserInfo();
            userInfo.setUserCode(userRequestModel.getUserCode());
            userInfo.setCreateDate(new Date());
            userInfo.setUpdateDate(new Date());
            this.saveUserInfo(userInfo);
            userInfo=this.getDetailByUserCode(userRequestModel.getUserCode());
            logger.debug("--------------------------------------null",gson.toJson(userInfo));
        }
        int userInfoDegree=user.getUserInfoDegree();
        userInfo = this.computeUserInfoDegree(userInfoDegree,userRequestModel,userInfo);
        user.setUserInfoDegree(userInfo.getUserInfoDegree());
        this.updateUserAndUserInfo(user, userInfo);
        //TODO 当userInfoDegree 为100时 则添加完善任务
        if(userInfo.getUserInfoDegree()==100){
            TaskRewardReceive taskRewardReceive=new TaskRewardReceive();
            taskRewardReceive.setUserCode(userInfo.getUserCode());
            taskRewardReceive.setTaskType(TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode());
            taskRewardReceive.setConcreteTaskCode(TaskEnum.SpecificTasks.PERFECT_INFORMATION.getTaskCode());
            taskRewardReceiveService.saveByUser(user,taskRewardReceive);
        }
    }

    /****************************************用户详情相关*******************************************/
    @Override
    public UserInfo getDetailByUserCode(String userCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        List<UserInfo> list=userInfoMapper.findByCondition(queryMap, null);
        if(list==null || list.isEmpty()){
            return null;
        }
        logger.debug("userInfo.getId-----------------------()"+gson.toJson(list.get(0)));
        return list.get(0);
    }
    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        userInfoMapper.save(userInfo);
        return this.getDetailByUserCode(userInfo.getUserCode());
    }

    @Override
    @Transactional
    public void updateUserAndUserInfo(User user, UserInfo userInfo) {
        userInfoMapper.update(userInfo);
        this.update(user);
    }
    /****************************************用户积分相关*******************************************/

    @Override
    public void updateUserIntegralChange(String userCode, Long rewardIntegral, RewardEnum.ChangeType changeType, RewardEnum.RewardType rewardType, String rewardSourceUuid) {
        //获取用户
        User user= userMapper.get(userCode,"userCode");
        if(user==null){
            return;
        }
        IntegralChangeModel integralChangeModel=new IntegralChangeModel();
        integralChangeModel.setUser(user);
        integralChangeModel.setChangeType(changeType);
        integralChangeModel.setRewardSourceUuid(rewardSourceUuid);
        integralChangeModel.setRewardIntegral(rewardIntegral);
        integralChangeModel.setRewardType(rewardType);
        this.updateUserIntegralChange(integralChangeModel);
    }

    @Override
    @Transactional
    public void updateUserIntegralChange(IntegralChangeModel integralChangeModel) {
        User user=integralChangeModel.getUser();
        if(user==null){
            logger.error("updateUserIntegralChange user is null.");
            return;
        }
        //兑换
        if(RewardEnum.ChangeType.EXCHANGE==integralChangeModel.getChangeType()){
            //todo 验证积分是否够兑换
            if(user.getUserPoints()<integralChangeModel.getRewardIntegral()){
                return;
            }
            user.setUserPoints(new BigDecimal(user.getUserPoints()).subtract(new BigDecimal(integralChangeModel.getRewardIntegral())).longValue());

            IntegralChange integralChange=new IntegralChange();
            integralChange.setUuid(UUID.randomUUID().toString().replace("-", ""));
            integralChange.setChangeName(integralChangeModel.getRewardType().getDesc());
            integralChange.setConsumeIntegral(integralChangeModel.getRewardIntegral());
            integralChange.setChangeType(integralChangeModel.getRewardType().getTaskCode());
            integralChange.setUserCode(user.getUserCode());
            integralChange.setCreateDate(new Date());
            integralChange.setUpdateDate(new Date());
            integralChangeService.save(integralChange);
        }else {
            //奖励
            user.setUserPoints(new BigDecimal(user.getUserPoints()).add(new BigDecimal(integralChangeModel.getRewardIntegral())).longValue());
            IntegralGet integralGet=new IntegralGet();
            integralGet.setUserCode(user.getUserCode());
            integralGet.setUuid(UUID.randomUUID().toString().replace("-", ""));
            integralGet.setRewardName(integralChangeModel.getRewardType().getDesc());
            integralGet.setRewardIntegral(integralChangeModel.getRewardIntegral());
            integralGet.setRewardType(integralChangeModel.getRewardType().getTaskCode());
            integralGet.setRewardSourceUuid(integralChangeModel.getRewardSourceUuid());
            integralGet.setCreateDate(new Date());
            integralGet.setUpdateDate(new Date());
            integralGetService.save(integralGet);
        }
        user.setUpdateDate(new Date());
        this.update(user);
    }


    public UserInfo computeUserInfoDegree(int userInfoDegree,UserRequestModel userRequestModel,UserInfo userInfo){
        if(StringUtils.isNotBlank(userRequestModel.getHeadImgUrl())){
            if(StringUtils.isNotBlank(userInfo.getHeadImgUrl())){
                userInfoDegree+=Integer.parseInt(headImgUrl);
            }
            userInfo.setHeadImgUrl(userRequestModel.getHeadImgUrl());
        }
        if(StringUtils.isNotBlank(userRequestModel.getNickName())){
            if(StringUtils.isNotBlank(userInfo.getNickName())){
                userInfoDegree+=Integer.parseInt(nickName);
            }
            userInfo.setNickName(userRequestModel.getNickName());
        }
        if(userRequestModel.getSex() != null && userRequestModel.getSex()>=0){
            userInfo.setSex(userConvert.converterUserSexType(userRequestModel.getSex()));
            if(null != userInfo.getSex()){
                userInfoDegree+=Integer.parseInt(sex);
            }
        }
        if(StringUtils.isNotBlank(userRequestModel.getBirthday())){
            if(StringUtils.isNotBlank(userInfo.getBirthday())){
                userInfoDegree+=Integer.parseInt(birthday);
            }
            userInfo.setBirthday(userRequestModel.getBirthday());
        }
        if(StringUtils.isNotBlank(userRequestModel.getArea())){
            userInfo.setArea(userRequestModel.getArea());
            if(StringUtils.isNotBlank(userInfo.getArea())){
                userInfoDegree+=Integer.parseInt(area);
            }
        }
        if(StringUtils.isNotBlank(userRequestModel.getProvince())){
            userInfo.setProvince(userRequestModel.getProvince());
            if(StringUtils.isNotBlank(userInfo.getProvince())){
                userInfoDegree+=Integer.parseInt(province);
            }
        }
        if(StringUtils.isNotBlank(userRequestModel.getCity())){
            userInfo.setCity(userRequestModel.getCity());
            if(StringUtils.isNotBlank(userInfo.getCity())){
                userInfoDegree+=Integer.parseInt(city);
            }
        }
        if(StringUtils.isNotBlank(userRequestModel.getSchool())){
            userInfo.setSchool(userRequestModel.getSchool());
            if(StringUtils.isNotBlank(userInfo.getSchool())){
                userInfoDegree+=Integer.parseInt(school);
            }
        }
        if(StringUtils.isNotBlank(userRequestModel.getGrade())){
            userInfo.setGrade(userRequestModel.getGrade());
            if(StringUtils.isNotBlank(userInfo.getGrade())){
                userInfoDegree+=Integer.parseInt(grade);
            }
        }
        userInfo.setUserInfoDegree(userInfoDegree);
        return userInfo;
    }
	/**
     * 用户请求购买vip包   
     * weiXin
     */
	@Override
	public String weiXinBuyVipPack(UserBuyVipRequestModel model) throws Exception {
		String gsonResponse = "";
		UserBuyVipResponseModel vipResponseModel = new UserBuyVipResponseModel();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userUniqueId", model.getUserCode());
		map.put("vipProductCode", model.getVipCode());
		map.put("type", model.getGradeType());
		map.put("body", model.getVipDesc()  );
		map.put("totalFee", model.getVipPrice());
		map.put("spbillCreateIp", model.getIp());
		String data = gson.toJson(map);
		logger.debug("用户购买VIP请求参数：" + data);
	    gsonResponse= HTTPClientUtils.httpPostRequestJson(weixinBuyVipPack, data);
//	    if(StringUtils.isNotBlank(gsonResponse)){
//			vipResponseModel =   gson.fromJson(gsonResponse, UserBuyVipResponseModel.class);
//		}
		return gsonResponse;
	}
	/**
     * 用户请求购买vip包
     * zhiFuBao
     */
	@Override
	public String zhiFuBaoBuyVipPack(UserBuyVipRequestModel model) throws Exception {
		String gsonResponse = "";
		UserBuyVipResponseModel vipResponseModel = new UserBuyVipResponseModel();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userUniqueId", model.getUserCode());
		map.put("vipProductCode", model.getVipCode()  );
		map.put("type", model.getGradeType());
		map.put("body", model.getVipDesc()  );
		map.put("totalFee", model.getVipPrice());
		String data = gson.toJson(map);
		logger.debug("用户购买VIP请求参数：" + data);
	    gsonResponse= HTTPClientUtils.httpPostRequestJson(zhifubaoBuyVipPack, data);
//	    if(StringUtils.isNotBlank(gsonResponse)){
//			vipResponseModel =   gson.fromJson(gsonResponse, UserBuyVipResponseModel.class);
//		}
		return gsonResponse;
		
	}
    /**
     * 用户请求购买vip包成功 修改用户信息
     */
    @Override
    public void buyVipPackSuccess(UserBuyVipSuccessModel model, User user, VipPack vipPack) throws Exception  {
    	SystemDictionary systemDictionary = systemDictionaryService.findByName(SystemDictionaryEnum.toEnumCode(vipPack.getVipType()));
    	IntegralChangeModel integralChangeModel= new IntegralChangeModel();
    	if (user.getIsVip()==1 && user.getVipEndDate().getTime()>new Date().getTime()) {
            user.setVipEndDate(DateUtils.addDay(user.getVipEndDate(), vipPack.getVipDay()));
        }else {
            user.setVipEndDate(DateUtils.addDay(new Date(), vipPack.getVipDay()));
            user.setVipStartDate(new Date());
        }
    	user.setIsVip(1);
    	if (null != systemDictionary) {
    		integralChangeModel.setUser(user);
        	integralChangeModel.setRewardSourceUuid(vipPack.getUuid());
        	integralChangeModel.setRewardType(RewardEnum.RewardType.BUYVIPPACK);
        	integralChangeModel.setChangeType(RewardEnum.ChangeType.REWARD);
        	integralChangeModel.setRewardIntegral(Long.valueOf(systemDictionary.getValue()));
    	    this.updateUserIntegralChange(integralChangeModel);
        }else {
        	user.setUpdateDate(new Date());
		}
    	this.update(user);
    }

	@Override
	public UserModel findUserInfoByMobile(String mobile) {
		return userMapper.findUserInfoByMobile(mobile);
	}
	
	@Override
	public void updateWeekStatus() {
		userMapper.updateWeekStatus();
	}

}
