package com.eeduspace.challenge.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeduspace.challenge.convert.QuestionConvert;
import com.eeduspace.challenge.enumeration.BattleEnum;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.enumeration.UserEnum.OnlineStatus;
import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.BattleApplyModel;
import com.eeduspace.challenge.model.battle.BattlePushModel;
import com.eeduspace.challenge.model.battle.BattleResultModel;
import com.eeduspace.challenge.model.battle.BattleResultSubmitModel;
import com.eeduspace.challenge.model.battle.BattleUserAnswerModel;
import com.eeduspace.challenge.model.battle.BattleUserModel;
import com.eeduspace.challenge.model.battle.FriendBattleRankModel;
import com.eeduspace.challenge.model.challenge.ChallengeQuestionModel;
import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.model.request.BattleRequestModel;
import com.eeduspace.challenge.model.weeklyrank.RankModel;
import com.eeduspace.challenge.persist.dao.TaskRewardReceiveMapper;
import com.eeduspace.challenge.persist.po.Question;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserAnswer;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.QuestionService;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.eeduspace.challenge.service.UserAnswerService;
import com.eeduspace.challenge.service.UserFightService;
import com.eeduspace.challenge.service.UserFriendService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

/**
 * @author zhuchaowei
 * 2016年7月18日
 * Description 对战控制类
 */
@RestController
@RequestMapping("/battle")
public class BattleController {
	@Inject
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private UserFightService userFightServiceImpl;
	@Autowired
	private FightResultService fightResultServiceImpl;
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;
	@Autowired
	private QuestionService questionServiceImpl;
	@Autowired
	private UserFriendService userFriendService;
	@Inject
	private MessageUtil messageUtil;
	@Autowired
	private UserAnswerService userAnswerServiceImpl;
	@Autowired
	private UserService userService;
	@Autowired
    private TaskRewardReceiveService taskRewardReceiveServiceImpl;
	
	
    private final Logger logger = LoggerFactory.getLogger(BattleController.class);
    private Gson gson=new Gson();
	/**
	 * 匹配对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午5:09:08
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="math_battle",method=RequestMethod.POST)
	public BaseResponse mathBattle(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("math_battle:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getStageCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "StageCode");
		}
		if (battleRequestModel.getBookTypeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BookTypeCode");
		}
		if (battleRequestModel.getBattleType() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BattleType");
		}
		BattlePushModel pushModel=new BattlePushModel();
		try {
			baseResourceModel.setSubjectCode(battleRequestModel.getSubjectCode());
			baseResourceModel.setGradeCode(battleRequestModel.getGradeCode());
			baseResourceModel.setBookTypeCode(battleRequestModel.getBookTypeCode());
			baseResourceModel.setStageCode(battleRequestModel.getStageCode());
//			String stringStatus = redisClientTemplate.get("status"+battleRequestModel.getUserCode());
//			if("1".equals(stringStatus)){
				pushModel=userFightServiceImpl.matchBattle(baseResourceModel, battleRequestModel.getUserCode(), battleRequestModel.getBattleType());
				baseResponse.setResult(pushModel);
				
//			}
			
			
		} catch (Exception e) {
			logger.error("mathBattle exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"mathBattle Exception");
		}
		logger.debug("math_battle 匹配对战 返回数据 ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 学科学年列表
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午5:21:47
	 * @return
	 */
	@RequestMapping(value="battle_subject_list",method=RequestMethod.POST)
	public BaseResponse battleSubjectList(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("battle_subject_list:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		try {
			
			/**
			 * 根据subjectCode查找gradeCode
			 */
			List<String> gradeCodes = weeklyRankingServiceImpl.findAllMaxGrade(battleRequestModel.getSubjectCode());
			List<RankModel> rankModels = new ArrayList<>();
			for (int i=0;i<gradeCodes.size();i++) {
				
				rankModels=weeklyRankingServiceImpl.findSubjectRank(battleRequestModel.getSubjectCode(),gradeCodes.get(i));
				
			}
			UserModel userModel = null;
			if(rankModels.size()>0){
				for (RankModel rankModel : rankModels) {
					userModel = userService.findDetailAllByUserCode(rankModel.getUserCode());
					if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
						rankModel.setOverdue(false);
					}else {
						rankModel.setOverdue(true);
					}
				}
			}
			
			baseResponse.setResult(rankModels);
			
		} catch (Exception e) {
			logger.error("battleSubjectList exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"battleSubjectList Exception");
		}
		logger.debug("battle_subject_list 学年学科列表 ： "+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 获取对战试题
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午5:27:36
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="get_battle_question",method=RequestMethod.POST)
	public BaseResponse getBattleQuestion(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("get_battle_question:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getPaperUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUUID");
		}
		if (battleRequestModel.getIsWithRobot() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "IsWithRobot");
		}
		if (battleRequestModel.getFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightUUID");
		}
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		try {
			List<Question> questions=questionServiceImpl.findByPaperUUID(battleRequestModel.getPaperUUID());
			List<QuestionEntity> lists=new ArrayList<>();
			List<UserAnswer> robotAnswers=new ArrayList<>();
			int rightOption=0;
			if(battleRequestModel.getIsWithRobot()){
				int i=0;
				for (Question question : questions) {
					i++;
					QuestionEntity questionEntity=QuestionConvert.toQuestionEctity(question);
					lists.add(questionEntity);
						//如果是与机器人对战 模拟机器人做题
						UserAnswer answer=userAnswerServiceImpl.saveRobotAnswer(questionEntity,battleRequestModel.getFightUUID(),i+"");
						if(answer.getIsRight()){
							rightOption=rightOption+1;
						}
						robotAnswers.add(answer);
				}
				BaseResourceModel baseResourceModel=new BaseResourceModel();
				baseResourceModel.setSubjectCode(battleRequestModel.getSubjectCode());
				baseResourceModel.setGradeCode(battleRequestModel.getGradeCode());
				//异步提交提交机器人答题结果
				Long useTime=60l;//需要随机
				Double score=(double) (10*rightOption);
//				logger.debug("get_battle_question=====================================");
				fightResultServiceImpl.submitResult("eeduspacerobot", useTime, score, baseResourceModel, battleRequestModel.getFightUUID(), battleRequestModel.getPaperUUID(), BattleEnum.QUICKBATTLE, true,battleRequestModel.getUnitCode());
			}else{
				for (Question question : questions) {
					QuestionEntity questionEntity=QuestionConvert.toQuestionEctity(question);
					lists.add(questionEntity);
				}
			}
			
			ChallengeQuestionModel challengeQuestionModel=new ChallengeQuestionModel();
			challengeQuestionModel.setQuestions(lists);
			challengeQuestionModel.setRobotAnswers(robotAnswers);
			baseResponse.setResult(challengeQuestionModel);
		} catch (Exception e) {
			logger.error("getBattleQuestion exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getBattleQuestion Exception");
		}
		logger.debug("get_battle_question 获取对战试题 返回数据 ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	
	/**
	 * 提交对战答题结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午5:31:43
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="save_battle_fight_result",method=RequestMethod.POST)
	public BaseResponse saveBattleFightResult(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("save_battle_fight_result:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		BattleResultSubmitModel battleResultSubmitModel=new BattleResultSubmitModel();
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getIsWithRobot() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "IsWithRobot");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getUseTime() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UseTime");
		}
		if (battleRequestModel.getFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightUUID");
		}
		if (battleRequestModel.getPaperUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUUID");
		}
		if (battleRequestModel.getBattleType() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BattleType");
		}
		if(BattleEnum.getEnum(battleRequestModel.getBattleType())==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_INVALID.toString(), "BattleType error");
		}
		try {
			baseResourceModel.setSubjectCode(battleRequestModel.getSubjectCode());
			baseResourceModel.setGradeCode(battleRequestModel.getGradeCode());
			Map<String, Object> queryUserFightResult=new HashMap<String, Object>();
			queryUserFightResult.put("fightUuid",battleRequestModel.getFightUUID());
			queryUserFightResult.put("userCode", battleRequestModel.getUserCode());
			if(fightResultServiceImpl.findByCondition(queryUserFightResult).size()>0){
				return BaseResponse.setResponse(new BaseResponse(requestId),
						ResponseCode.RESOURCE_DUPLICATE.toString(), "答题结果已提交,不能重复提交111"); 
			}
//			logger.debug("save_battle_fight_result=======================================");
			battleResultSubmitModel= fightResultServiceImpl.submitResult(battleRequestModel.getUserCode(), battleRequestModel.getUseTime(),
															battleRequestModel.getScore(), baseResourceModel, battleRequestModel.getFightUUID(),
															battleRequestModel.getPaperUUID(),BattleEnum.getEnum(battleRequestModel.getBattleType()),
															battleRequestModel.getIsWithRobot(),battleRequestModel.getUnitCode());
		} catch (Exception e) {
			logger.error("saveBattleFightResult exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"saveBattleFightResult Exception");
		}
		baseResponse.setResult(battleResultSubmitModel);
		logger.debug("save_battle_fight_result 提交对战答题结果:"+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 获取对战结果信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午6:49:18
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="get_battle_result",method=RequestMethod.POST)
	public BaseResponse getBattleReulst(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("get_battle_result:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightUUID");
		}
		try {
			List<BattleResultModel> baResultModels=fightResultServiceImpl.getBattleDetail(battleRequestModel.getFightUUID());
			for (BattleResultModel battleResultModel : baResultModels) {
				UserModel userModel = userService.findDetailAllByUserCode(battleResultModel.getUserCode());
//				battleResultModel.setOverDue(userModel.isOverdue());
				//清除缓存中的    redis对战中的uuid  等数据
				String stringStatus = redisClientTemplate.get("status"+userModel.getUserCode());
				if("2".equals(stringStatus)){
//					redisClientTemplate.del("battle"+userModel.getUserCode());
					redisClientTemplate.del("status"+userModel.getUserCode());
					redisClientTemplate.del("rank"+userModel.getUserCode());
				}
				
				if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
					battleResultModel.setOverdue(false);
				}else {
					battleResultModel.setOverdue(true);
				}
				/**
				 * 	新手任务
				 * 完成一个对战并且对战结果胜利，修改任务状态信息 finishState
				 */
				if(baseResponse.getHttpCode().equals("200") ){
					//正常调用接口  并且比赛是	赢状态
					if(battleResultModel.getBattleResult() !=null && battleResultModel.getBattleResult()==1){
						//根据task_uuid 查找这个任务信息对象
						TaskRewardReceive uuidReceive = taskRewardReceiveServiceImpl.findByTaskUuidAndUserCode("17b804869f1a4500aebe0f8518272254",userModel.getUserCode());
						if(uuidReceive != null && uuidReceive.getFinishState()==TaskEnum.FinishState.NoFinish.getValue()){
//							logger.debug("uuidReceive.getFinishState()  :"+uuidReceive.getFinishState());
							//修改完成状态
//							logger.debug("++++++++++++++++uuidReceive.getConcreteTaskCode():"+uuidReceive.getConcreteTaskCode());
							if(("battle_victory").equals(uuidReceive.getConcreteTaskCode())){
								taskRewardReceiveServiceImpl.updateByUuidAndUserCode("17b804869f1a4500aebe0f8518272254", userModel.getUserCode());
//								logger.debug("完成对战胜利任务  userModel.getUserCode() ："+userModel.getUserCode());
							}
						}
					}
				}
				
			}
			
			baseResponse.setResult(baResultModels);
			//baseResponse.setUserCode(battleRequestModel.getUserCode());
		} catch (Exception e) {
			logger.error("getBattleReulst exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString(),"getBattleReulst Exception");
		}
		logger.debug("get_battle_result 获取对战结果信息 ："+gson.toJson(baseResponse));
		
		return baseResponse;
	}
	/**
	 * 获取对战答题信息详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午7:02:12
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="get_battle_user_answer",method=RequestMethod.POST)
	public BaseResponse getBattleUserAnswer(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("get_battle_user_answer:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getOtherUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "OtherCode");
		}
		if (battleRequestModel.getPaperUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUUID");
		}
		if (battleRequestModel.getFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightUUID");
		}
		try {
			BattleUserAnswerModel battleUserAnswerModel=questionServiceImpl.getBattleAnswer(battleRequestModel.getUserCode(), battleRequestModel.getOtherUserCode(), battleRequestModel.getFightUUID(), battleRequestModel.getPaperUUID());
			baseResponse.setResult(battleUserAnswerModel);	
		} catch (Exception e) {
			logger.error("getBattleUserAnswer exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString(),"getBattleUserAnswer Exception");
		}
		logger.debug("get_battle_user_answer 获取对战答题信息详情 ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 与机器人对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月19日 上午10:47:42
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="battle_with_robot",method=RequestMethod.POST)
	public BaseResponse battleWithRobot(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("battle_with_robot:{}",gson.toJson(battleRequestModel));
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getStageCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "StageCode");
		}
		if (battleRequestModel.getBookTypeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BookTypeCode");
		}
		/**
		 * 置一操作
		 */
		User findByUserCode = userService.findByUserCode(battleRequestModel.getUserCode());
		findByUserCode.setUserOnlineState(OnlineStatus.On_line.getValue());
		userService.update(findByUserCode);
		//置一操作结束
		BaseResponse baseResponse=new BaseResponse(requestId);
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		BattlePushModel battlePushModel=new BattlePushModel();
		try {
			baseResourceModel.setGradeCode(battleRequestModel.getGradeCode());
			baseResourceModel.setSubjectCode(battleRequestModel.getSubjectCode());
			baseResourceModel.setBookTypeCode(battleRequestModel.getBookTypeCode());
			baseResourceModel.setStageCode(battleRequestModel.getStageCode());
			if(!redisClientTemplate.sismember(baseResourceModel.getSubjectCode()+baseResourceModel.getGradeCode(), battleRequestModel.getUserCode())){
				//匹配池中没有当前用户
				return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString(),"no user");
			}
			redisClientTemplate.srem(baseResourceModel.getSubjectCode()+baseResourceModel.getGradeCode(), battleRequestModel.getUserCode());
			redisClientTemplate.del("battle"+battleRequestModel.getUserCode());
			redisClientTemplate.set("status"+battleRequestModel.getUserCode(),"2");
			battlePushModel=userFightServiceImpl.battleWithRobot(baseResourceModel, battleRequestModel.getUserCode());
			baseResponse.setResult(battlePushModel);
		} catch (Exception e) {
			logger.error("battleWithRobot exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString(),"battleWithRobot Exception");
		}
		logger.debug("battle_with_robot 与机器人对战  ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	
	/**
	 * 与好友对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月29日 上午10:59:23
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="battle_with_friend",method=RequestMethod.POST)
	public BaseResponse battleWithFriend(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		BaseResponse baseResponse=new BaseResponse(requestId);
		return baseResponse;
	}
	/**
	 * 取消匹配  清除redis中的对战匹配信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月19日 下午5:51:48
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="cancel_math",method=RequestMethod.POST)
	public BaseResponse  cancelMatch(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("cancel_math:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
	    redisClientTemplate.srem(battleRequestModel.getSubjectCode()+battleRequestModel.getGradeCode(), battleRequestModel.getUserCode());
	    redisClientTemplate.set("status"+battleRequestModel.getUserCode(),"2");
		redisClientTemplate.del("battle"+battleRequestModel.getUserCode());
		return baseResponse;
	}
	/**
	 * 退出对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月19日 下午5:59:21
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="quit_battle",method=RequestMethod.POST)
	public BaseResponse quitBattle(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("quit_battle:{}",gson.toJson(battleRequestModel));
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightUUID");
		}
		Map<String, Object> queryUserFightResult=new HashMap<String, Object>();
		queryUserFightResult.put("uuid",battleRequestModel.getFightUUID());
		queryUserFightResult.put("userCode", battleRequestModel.getUserCode());
		if(fightResultServiceImpl.findByCondition(queryUserFightResult).size()>0){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.RESOURCE_DUPLICATE.toString(), "答题结果已提交,不能重复提交"); 
		}
		if(BattleEnum.getEnum(battleRequestModel.getBattleType())==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_INVALID.toString(), "BattleType error");
		}
		BattleResultSubmitModel battleResultSubmitModel=new BattleResultSubmitModel();
		BaseResponse baseResponse=new BaseResponse(requestId);
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		baseResourceModel.setGradeCode(battleRequestModel.getGradeCode());
		baseResourceModel.setSubjectCode(battleRequestModel.getSubjectCode());
//		logger.debug("quit_battle:=========================================");
		battleResultSubmitModel=fightResultServiceImpl.submitResult(battleRequestModel.getUserCode(), battleRequestModel.getUseTime(), battleRequestModel.getScore(), baseResourceModel, 
				battleRequestModel.getFightUUID(), battleRequestModel.getPaperUUID(),BattleEnum.getEnum(battleRequestModel.getBattleType()),battleRequestModel.getIsWithRobot(),baseResourceModel.getUnitCode());
		baseResponse.setResult(battleResultSubmitModel);
		return baseResponse;
	}
	/**
	 * 获取好友对战好友排行榜
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月1日 上午10:04:03
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="friend_battle_rank",method=RequestMethod.POST)
	public BaseResponse friendBattleRank(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("friend_battle_rank:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getStart() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Start");
		}
		if(Integer.valueOf(battleRequestModel.getItem())==null){
			battleRequestModel.setItem(10);
		}
		List<FriendBattleRankModel> listsBattleRankModels=userFriendService.findFriendBattleRank(battleRequestModel.getUserCode(),battleRequestModel.getStart(),battleRequestModel.getItem());
		Long rank=battleRequestModel.getStart()==0l?1l:battleRequestModel.getStart()+1;
		for (FriendBattleRankModel friendBattleRankModel : listsBattleRankModels) {
			if(!StringUtils.isEmpty(friendBattleRankModel.getVipEndDate())){
                Date dateNow=new Date();
                if(friendBattleRankModel.getVipEndDate().after(dateNow)){
                	friendBattleRankModel.setIsExpired(false);
                }else{
                	friendBattleRankModel.setIsExpired(true);
                }
            }
			friendBattleRankModel.setRank(rank);
			rank++;
		}
		baseResponse.setResult(listsBattleRankModels);
		logger.debug("friend_battle_rank 获取好友对战好友排行榜  :"+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 好友对战申请
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月1日 上午10:29:34
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="friend_battle_apply")
	public BaseResponse friendBattleApply(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("friend_battle_apply:{}",gson.toJson(battleRequestModel));
		if (battleRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (battleRequestModel.getBattleUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BattleUserCode");
		}
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		if (battleRequestModel.getStageCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "StageCode");
		}
		if (battleRequestModel.getBookTypeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BookTypeCode");
		}
		if (battleRequestModel.getNickName() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "NickName");
		}
		BaseResponse baseResponse=new BaseResponse(requestId);
		/**
		 * 置一操作
		 */
		User findByUserCode = userService.findByUserCode(battleRequestModel.getUserCode());
		findByUserCode.setUserOnlineState(OnlineStatus.On_line.getValue());
		userService.update(findByUserCode);
		//置一操作结束
		try {
			User user=userService.findByUserCode(battleRequestModel.getBattleUserCode());
			if(user==null){
				return BaseResponse.setResponse(new BaseResponse(requestId),
						ResponseCode.RESOURCE_NOTFOUND.toString(), "user not found");
			}else if (user.getUserOnlineState()==UserEnum.OnlineStatus.On_line.getValue()) {
				ClientModel clientModel=new ClientModel();
				BattleApplyModel battleApplyModel=new BattleApplyModel();
				battleApplyModel.setGradeCode(battleRequestModel.getGradeCode());
				battleApplyModel.setSubjectCode(battleRequestModel.getSubjectCode());
				battleApplyModel.setStageCode(battleRequestModel.getStageCode());
				battleApplyModel.setBookTypeCode(battleRequestModel.getBookTypeCode());
				battleApplyModel.setDesc("请求对战");
//				UserModel userModel = userService.findDetailAllByUserCode(battleRequestModel.getOtherUserCode());
				battleApplyModel.setNickName(battleRequestModel.getNickName());
				clientModel.setFromUserCode(battleRequestModel.getUserCode());
				clientModel.setMessageType("hxs_friend_battle_apply");
				clientModel.setMessage(battleApplyModel);
				clientModel.setToUserCode(battleRequestModel.getBattleUserCode());
				messageUtil.sendMessageToOne(clientModel);
			}else{
				return BaseResponse.setResponse(new BaseResponse(requestId),
						ResponseCode.RESOURCE_INUSE.toString(), "user is fight");
			}
				
		} catch (Exception e) {
			logger.error("friendBattleApply Exception:{}",e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"friendBattleApply Exception");
		}
		return baseResponse;
	}
	/**
	 * 同意好友申请对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月1日 下午12:52:44
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="agree_battle")
	public BaseResponse agreeBattle(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("agree_battle:{}",gson.toJson(battleRequestModel));
		if (StringUtils.isEmpty(battleRequestModel.getUserCode())) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (StringUtils.isEmpty(battleRequestModel.getBattleUserCode())) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BattleUserCode");
		}
		if (battleRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (battleRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		if (battleRequestModel.getStageCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "StageCode");
		}
		if (battleRequestModel.getBookTypeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "BookTypeCode");
		}
		
		
		BaseResponse baseResponse=new BaseResponse(requestId);
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		BattlePushModel battlePushModel=new BattlePushModel();
		try {
			//校验对战申请方是否在线状态
			User user=userService.findByUserCode(battleRequestModel.getBattleUserCode());
			if(user.getUserOnlineState()==OnlineStatus.On_Challenge.getValue()){
				return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.USER_FIGHT.toString());
			}
			if(user.getUserOnlineState()==OnlineStatus.Off_line.getValue()){
				return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.USER_OFF_LINE.toString());
			}
			
			baseResourceModel.setSubjectCode(battleRequestModel.getSubjectCode());
			baseResourceModel.setStageCode(battleRequestModel.getStageCode());
			baseResourceModel.setGradeCode(battleRequestModel.getGradeCode());
			baseResourceModel.setBookTypeCode(battleRequestModel.getBookTypeCode());
			battlePushModel=userFightServiceImpl.agreeBattleWithFriend(baseResourceModel, battleRequestModel.getUserCode(), battleRequestModel.getBattleUserCode());
		
			//推送给对战申请方 start
			UserModel userModel=userService.findDetailAllByUserCode(battleRequestModel.getUserCode());
			BattlePushModel otherUser=new BattlePushModel();
			BattleUserModel battleUserModel=new BattleUserModel();
			battleUserModel.setCombatValue(userModel.getFightValue());
			battleUserModel.setHeadImgUrl(userModel.getHeadImgUrl());
			battleUserModel.setIsVip(userModel.getIsVip());
			battleUserModel.setNickName(userModel.getNickName());
			battleUserModel.setUserCode(userModel.getUserCode());
			battleUserModel.setOverdue(userModel.isOverdue());
			otherUser.setFightUUID(battlePushModel.getFightUUID());
			otherUser.setPaperUUID(battlePushModel.getPaperUUID());
			otherUser.setBattleUserModel(battleUserModel);
			otherUser.setBookTypeCode(battleRequestModel.getBookTypeCode());
			otherUser.setStageCode(battleRequestModel.getStageCode());
			otherUser.setSubjectCode(battleRequestModel.getSubjectCode());
			otherUser.setGradeCode(battleRequestModel.getGradeCode());
			ClientModel clientModel=new ClientModel();
			clientModel.setFromUserCode(battleRequestModel.getUserCode());
			clientModel.setMessageType("hxs_agree_friend_battle");
			clientModel.setMessage(otherUser);
			clientModel.setToUserCode(battleRequestModel.getBattleUserCode());
			messageUtil.sendMessageToOne(clientModel);
			//end
		} catch (Exception e) {
			logger.error("agreeBattle Exception:{}",e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString());
		}
		baseResponse.setResult(battlePushModel);
		return baseResponse;
	}
	
	/**
	 * 分享战斗结果信息
	 * @param requestId
	 * @param battleRequestModel
	 * @return
	 */
	@RequestMapping(value="share/get_battle_result",method=RequestMethod.POST)
	public BaseResponse shareBattleReulst(@RequestParam(required=false) String requestId,@RequestBody BattleRequestModel battleRequestModel){
		logger.debug("get_battle_result:{}",gson.toJson(battleRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (battleRequestModel.getFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightUUID");
		}
		try {
			List<BattleResultModel> baResultModels=fightResultServiceImpl.getBattleDetail(battleRequestModel.getFightUUID());
			for (BattleResultModel battleResultModel : baResultModels) {
				UserModel userModel = userService.findDetailAllByUserCode(battleResultModel.getUserCode());
//				battleResultModel.setOverDue(userModel.isOverdue());
				if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
					battleResultModel.setOverdue(false);
				}else {
					battleResultModel.setOverdue(true);
				}
//				System.out.println(userModel.getVipEndDate()+"---------------"+battleResultModel.getOverdue());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resultList", baResultModels);
			map.put("userCode", battleRequestModel.getUserCode());
			baseResponse.setResult(map);
			//baseResponse.setUserCode(battleRequestModel.getUserCode());
		} catch (Exception e) {
			logger.error("getBattleReulst exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString(),"getBattleReulst Exception");
		}
		logger.debug("share/get_battle_result 获取分享结果信息 ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	
}
