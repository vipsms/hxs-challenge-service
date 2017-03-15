package com.eeduspace.challenge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey.On;
import com.eeduspace.challenge.asynchronous.EventOperationService;
import com.eeduspace.challenge.controller.BattleController;
import com.eeduspace.challenge.enumeration.BattleEnum;
import com.eeduspace.challenge.enumeration.FightEnum;
import com.eeduspace.challenge.enumeration.FightResultEnum;
import com.eeduspace.challenge.enumeration.NoticeEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.enumeration.RewardEnum.ChangeType;
import com.eeduspace.challenge.enumeration.RewardEnum.RewardType;
import com.eeduspace.challenge.enumeration.UserEnum.OnlineStatus;
import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.IntegralChangeModel;
import com.eeduspace.challenge.model.NoticeModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.BattlePushResultModel;
import com.eeduspace.challenge.model.battle.BattleResultModel;
import com.eeduspace.challenge.model.battle.BattleResultNotice;
import com.eeduspace.challenge.model.battle.BattleResultSubmitModel;
import com.eeduspace.challenge.model.challenge.ChallengeResultModel;
import com.eeduspace.challenge.persist.dao.FightResultMapper;
import com.eeduspace.challenge.persist.po.FightResult;
import com.eeduspace.challenge.persist.po.Notice;
import com.eeduspace.challenge.persist.po.Question;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserAnswer;
import com.eeduspace.challenge.persist.po.UserFight;
import com.eeduspace.challenge.persist.po.UserTaskLog;
import com.eeduspace.challenge.persist.po.WeeklyRanking;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.NoticeService;
import com.eeduspace.challenge.service.QuestionService;
import com.eeduspace.challenge.service.UserAnswerService;
import com.eeduspace.challenge.service.UserFightService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.eeduspace.challenge.util.FightComputeUtil;
import com.eeduspace.challenge.util.JPushUtil;
import com.eeduspace.challenge.util.Order;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;


@Service("fightResultServiceImpl")
public class FightResultServiceImpl extends BaseServiceImpl<FightResult>
		implements FightResultService {
	@Resource
	private FightResultMapper fightResultMapperImpl;
	@Autowired
	private UserFightService userFightServiceImpl;
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;
	@Autowired
	private UserAnswerService userAnswerServiceImpl;
	@Autowired
	private UserService userService;
	@Inject
	private RedisClientTemplate redisClientTemplate;
	@Inject
	private EventOperationService eventOperationService;
	@Inject
	private MessageUtil messageUtil;
	@Autowired
	private QuestionService questionServiceImpl;
	private final Logger logger = LoggerFactory.getLogger(BattleController.class);
    private Gson gson=new Gson();
    
	@Autowired
	public FightResultServiceImpl(FightResultMapper fightResultMapper) {
		this.baseDao = fightResultMapper;
	}
	@Override
	public List<FightResult> findUserHighestPaper(String userCode,Long paperSize,String subjectCode,String gradeCode) {
		return fightResultMapperImpl.findUserHighestScorePaper(userCode,paperSize,subjectCode,gradeCode);
	}
	@Override
	public List<FightResult> findUserHighestPaper(String userCode,String subjectCode,String gradeCode) {
		UserModel userModel=new UserModel();
		userModel.setUserCode(userCode);
		userModel.setUserOnlineState(OnlineStatus.On_Challenge.getValue());
		eventOperationService.userOnLineState(userModel);
		return this.findUserHighestPaper(userCode,null,subjectCode,gradeCode);
	}
	@Override
	public void saveUserPaper(FightResult fightResult) {
		
	}
	@Transactional
	@Override
	public ChallengeResultModel computeChallengeFightResult(String challengerCombatValue,
			String challengerFightUUID, String userCode, String challengerCode,String challengerName,
			String getScore,Long useTime,Long userRank,String userFightUUID,BaseResourceModel baseResourceModel,String fightType) {
		Map<String, Object> queryChallengerMap=new HashMap<>();
		queryChallengerMap.put("fightUuid", challengerFightUUID);
		queryChallengerMap.put("userCode", challengerCode);
		FightResult fightResult=this.findByCondition(queryChallengerMap).get(0);
		
		ChallengeResultModel challengeResultModel=new ChallengeResultModel();
		challengeResultModel.setChallengerCode(challengerCode);
		challengeResultModel.setChallengerName(challengerName);
		challengeResultModel.setChallengerUseTime(fightResult.getUseTime());
		challengeResultModel.setChallerGetScore(fightResult.getGetScore());
		challengeResultModel.setUserCode(userCode);
		challengeResultModel.setUserGetScore(Double.valueOf(getScore));
		challengeResultModel.setUserUseTime(useTime);
		
		
		//校验答题是否完整
		List<Question> questions=questionServiceImpl.getNotDoneQuestions(fightResult.getPaperUuid(), userCode, userFightUUID);
		if(questions.size()>0){
			//处理未做试题
			Long maxLong=questionServiceImpl.getMaxQuestionSn(userFightUUID);
			if(maxLong==null){
				maxLong=1l;
			}
			//模拟答题
			for (Question question : questions) {
				UserAnswer userAnswer=new UserAnswer();
				userAnswer.setFightRecordUuid(userFightUUID);
				userAnswer.setIsRight(false);
				userAnswer.setPaperUuid(fightResult.getPaperUuid());
				userAnswer.setQuestionSn(maxLong+"");
				userAnswer.setFightType(FightEnum.CHALLENGE.getValue());
				userAnswer.setQuestionUuid(question.getQuestionUuid());
				userAnswer.setRightOption(question.getRightOption());
				userAnswer.setScore(question.getScore());
				userAnswer.setUserCode(userCode);
				maxLong++;
				userAnswerServiceImpl.save(userAnswer);
			}
		}
		
		//保存挑战日志
		UserTaskLog userLog=new UserTaskLog();
//		userLog.setConcreteTaskCode(concreteTaskCode);
//		userLog.setCreateDate(new Date());
//		userLog.setModule(module);
//		userLog.setOperate(operate);
//		userLog.setUserCode(userCode);
//		eventOperationService.handleUserLog(log);
		
		
		//保存对战记录
		FightResult userFightResult=new FightResult();//用户的答题结果
		Long getCombatValue=FightComputeUtil.challengeCombatValueCompute(Long.valueOf(challengerCombatValue));
		if(Double.valueOf(getScore)>fightResult.getGetScore()){//挑战胜利
			userFightResult.setIntegral(15l);//胜利得15分
			challengeResultModel.setChallengeResult(FightResultEnum.VICTORY.getValue());
		
			challengeResultModel.setCombatValue(getCombatValue);
			//去周榜信息中增加战斗力
			Map<String, Object> userQuerMap=new HashMap<>();
			userQuerMap.put("userCode", userCode);
			userQuerMap.put("subjectCode", fightResult.getSubjectCode());
			userQuerMap.put("gradeCode", fightResult.getGradeCode());
			WeeklyRanking weeklyRanking=weeklyRankingServiceImpl.findByCondition(userQuerMap).get(0);
			weeklyRanking.setWeekFightValue(weeklyRanking.getWeekFightValue()+getCombatValue);
			weeklyRanking.setUpdateDate(new Date());
			weeklyRankingServiceImpl.update(weeklyRanking);
			Long userCurrentRank=weeklyRankingServiceImpl.countCurrentRank(userCode,fightResult.getSubjectCode(),fightResult.getGradeCode());
			challengeResultModel.setFightedRank(userCurrentRank);
		}else if(Double.valueOf(getScore)==fightResult.getGetScore()){
			if(useTime==fightResult.getUseTime()){
				//挑战平局
				userFightResult.setIntegral(10l);//平局得10分
				challengeResultModel.setChallengeResult(FightResultEnum.DRAW.getValue());
			}else if (useTime<fightResult.getUseTime()) {
				userFightResult.setIntegral(15l);//胜利得15分
				challengeResultModel.setChallengeResult(FightResultEnum.VICTORY.getValue());
			}else{
				userFightResult.setIntegral(5l);//失败得5分
				challengeResultModel.setChallengeResult(FightResultEnum.FAILURE.getValue());
			}
		}else{//挑战失败
			userFightResult.setIntegral(5l);//失败得5分
			challengeResultModel.setChallengeResult(FightResultEnum.FAILURE.getValue());
		}
		challengeResultModel.setIntegral(userFightResult.getIntegral());//获取积分
		userFightResult.setUserCode(challengeResultModel.getUserCode());
//		userFightResult.setCurrentRank(userRank);//当前排名需要计算
		userFightResult.setCurrentRank(weeklyRankingServiceImpl.countCurrentRank(challengeResultModel.getUserCode(), baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode()));//当前排名需要计算
		userFightResult.setGetScore(Double.valueOf(getScore));
		userFightResult.setFightedRank(challengeResultModel.getFightedRank());
		userFightResult.setFightResult(challengeResultModel.getChallengeResult());
		userFightResult.setGradeCode(baseResourceModel.getGradeCode());
		userFightResult.setFightUuid(userFightUUID);
		userFightResult.setSubjectCode(baseResourceModel.getSubjectCode());
		userFightResult.setPaperUuid(fightResult.getPaperUuid());
		userFightResult.setFightValue(challengeResultModel.getCombatValue());
		userFightResult.setUseTime(useTime);
		this.save(userFightResult);
		//TODO 处理用户积分 用户基础信息
		User winUser=userService.findByUserCode(userFightResult.getUserCode());
		winUser.setFightValue(winUser.getFightValue()+userFightResult.getFightValue());
		userService.update(winUser);
		IntegralChangeModel winUserChangeModel=new IntegralChangeModel();
		winUserChangeModel.setChangeType(ChangeType.REWARD);
		winUserChangeModel.setRewardType(RewardType.CHALLENGE);
		winUserChangeModel.setUser(winUser);
		winUserChangeModel.setRewardIntegral(userFightResult.getIntegral());
		winUserChangeModel.setRewardSourceUuid(userFightResult.getFightUuid());
		userService.updateUserIntegralChange(winUserChangeModel);
		
		UserModel userModel=userService.findDetailAllByUserCode(userCode);
		UserModel challengerModel=userService.findDetailAllByUserCode(challengerCode);
		challengeResultModel.setUserTotalCombatValue(userModel.getFightValue());
		challengeResultModel.setChallengerTotalCombatCalue(challengerModel.getFightValue());
		challengeResultModel.setUserIsVip(userModel.getIsVip());
		challengeResultModel.setChallengerIsVip(challengerModel.getIsVip());
		challengeResultModel.setMyHeadImgUrl(userModel.getHeadImgUrl());
		challengeResultModel.setChallengerHeadImgUrl(challengerModel.getHeadImgUrl());

		
		
		
		//更新用户状态
		userModel.setUserOnlineState(OnlineStatus.On_line.getValue());
		eventOperationService.userOnLineState(userModel);
		
		return  challengeResultModel;
	}
	@Override
	public void computeBattleFightResult(String fightUUID,BattleEnum battleEnum) {
		//TODO step 1 根据 fightuuid 获取 对战用户
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("uuid", fightUUID);
		UserFight userFight=userFightServiceImpl.findByCondition(queryMap).get(0);
		logger.debug("userFight所对应的值是什么  ："+userFight.getPlayerA(),userFight.getUuid()+"----------"+userFight.getPlaeryB(),userFight.getUuid());
		FightResult userA= getByUserCode(userFight.getPlayerA(),userFight.getUuid());
		FightResult userB= getByUserCode(userFight.getPlaeryB(),userFight.getUuid());
		logger.debug("computeBattleFightResult 计算对战输赢方法"+"userB:"+gson.toJson(userB)+",userA:"+gson.toJson(userA)+",battleEnum:"+battleEnum);
		if(userA.getGetScore().doubleValue()>userB.getGetScore().doubleValue())
		{//userA 获胜
			win(userA, userB,false,battleEnum);
		}else if(userA.getGetScore().doubleValue()==userB.getGetScore().doubleValue()){
			if(userA.getUseTime()>userB.getUseTime()){
				//userB获胜
				win(userB, userA,false,battleEnum);
			}else if(userA.getUseTime()==userB.getUseTime()){
				// 平局
				win(userA, userB,true,battleEnum);
			}else{
				//userA获胜
				win(userA, userB,false,battleEnum);
			}
		}else{
			//userB获胜
			win(userB, userA,false,battleEnum);
		}
	}
	/**
	 * 根据用户code 获取个人答题结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 下午4:53:24
	 * @param userCode
	 * @return
	 */
	public FightResult getByUserCode(String userCode,String fightUUID){
		logger.debug("好友对战保存对战结果  参数  ："+gson.toJson(fightUUID+"-----------"+userCode));
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("userCode", userCode);
		queryMap.put("fightUuid", fightUUID);
		return this.findByCondition(queryMap).get(0);
	}
	/**
	 * 胜负处理
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 下午5:40:36
	 * @param winUserResult  胜利者信息  
	 * @param loseFightResult 失败者信息
	 */
	public void win(FightResult winUserResult,FightResult loseFightResult,Boolean  isDraw,BattleEnum battleEnum){
		List<FightResult> lists=new ArrayList<>();
		// step 1 更新获胜者 信息 增加战斗力 和积分   将战斗结果修改为 成功
		Long getFightValue=FightComputeUtil.battleCombatValueCompute(userAnswerServiceImpl.countRight(winUserResult.getPaperUuid(), winUserResult.getUserCode(), winUserResult.getFightUuid()), userAnswerServiceImpl.countRight(loseFightResult.getPaperUuid(), loseFightResult.getUserCode(), loseFightResult.getFightUuid()), winUserResult.getUseTime(),isDraw);
		// 更新用户积分及用户战斗力处理
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("userCode", winUserResult.getUserCode());
		List<WeeklyRanking>  winUserRanking=weeklyRankingServiceImpl.findByCondition(queryMap);
	
		IntegralChangeModel winUserChangeModel=new IntegralChangeModel();
		IntegralChangeModel loseUserChangeModel=new IntegralChangeModel();
		
		User winUser=userService.findByUserCode(winUserResult.getUserCode());
		logger.debug("winUser winUserResult.getUserCode()"+winUserResult.getUserCode());
		User loseUser=userService.findByUserCode(loseFightResult.getUserCode());
		if(loseUser!=null && winUser!=null){
			logger.debug("获取每一个用户数据1111   ："+gson.toJson("loseUser:"+loseUser+",winUser:"+winUser.getFightValue()+",getFightValue:"+getFightValue));
		}
		
		winUser.setFightValue(winUser.getFightValue()+getFightValue);
		//胜利一次加一次
		Double d = winUser.getWeekStatus()+1d;
		logger.debug("===============数据库之中查出来的胜利次数week_status==================="+winUser.getWeekStatus()+"=======d===="+d);
		winUser.setWeekStatus(d);
		userService.update(winUser);
		//初始化榜单表
		if(winUser.getWeekStatus()>=5d){
			weeklyRankingServiceImpl.saveWeekRanking(winUser.getUserCode(),loseFightResult.getSubjectCode(), loseFightResult.getGradeCode());
		}
		winUserChangeModel.setChangeType(ChangeType.REWARD);
		winUserChangeModel.setRewardType(RewardType.BATTLE);
		winUserChangeModel.setUser(winUser);
		winUserChangeModel.setRewardSourceUuid(winUserResult.getFightUuid());
		if(winUserRanking.size()>0){
			winUserRanking.get(0).setWeekFightValue(winUserRanking.get(0).getWeekFightValue()+getFightValue);
			winUserRanking.get(0).setUpdateDate(new Date());
			//更新周榜用户战斗力值
			weeklyRankingServiceImpl.update(winUserRanking.get(0));
		}
		if(isDraw){
			loseFightResult.setFightValue(getFightValue);
			winUserResult.setFightResult(FightResultEnum.DRAW.getValue());
			loseFightResult.setFightResult(FightResultEnum.DRAW.getValue());
			queryMap.put("userCode", loseFightResult.getUserCode());
			List<WeeklyRanking>  loseRanking=weeklyRankingServiceImpl.findByCondition(queryMap);
			if(loseRanking.size()>0){
				loseRanking.get(0).setWeekFightValue(loseRanking.get(0).getWeekFightValue()+getFightValue);
				loseRanking.get(0).setUpdateDate(new Date());
				//更新周榜用户战斗力值
				weeklyRankingServiceImpl.update(loseRanking.get(0));
			}
			loseUser.setFightValue(loseUser.getFightValue()+getFightValue);
			userService.update(loseUser);
			loseUserChangeModel.setChangeType(ChangeType.REWARD);
			loseUserChangeModel.setRewardType(RewardType.BATTLE);
			loseUserChangeModel.setUser(loseUser);
			loseUserChangeModel.setRewardSourceUuid(loseFightResult.getFightUuid());
			if(battleEnum.equals(BattleEnum.FRIENDBATTLE)){
				winUserChangeModel.setRewardIntegral(10l);
				loseUserChangeModel.setRewardIntegral(10l);
			}else{
				winUserChangeModel.setRewardIntegral(5l);
				loseUserChangeModel.setRewardIntegral(5l);
			}
			loseFightResult.setIntegral(loseUserChangeModel.getRewardIntegral());
			userService.updateUserIntegralChange(loseUserChangeModel);
		}else{
			loseFightResult.setIntegral(0l);
			loseFightResult.setFightValue(0l);
			winUserResult.setFightResult(FightResultEnum.VICTORY.getValue());
			loseFightResult.setFightResult(FightResultEnum.FAILURE.getValue());
			if(battleEnum.equals(BattleEnum.FRIENDBATTLE)){
				winUserChangeModel.setRewardIntegral(15l);
			}else{
				winUserChangeModel.setRewardIntegral(10l);
			}
		}
		//更新胜利者
		userService.updateUserIntegralChange(winUserChangeModel);
//		logger.debug("俩方的输赢值  ："+gson.toJson(winUserResult.getFightResult()+"----"+loseFightResult.getFightResult()));
		// step 3 获取用户实时排行榜排名
		Long userCurrentRank=weeklyRankingServiceImpl.countCurrentRank(winUserResult.getUserCode(), winUserResult.getSubjectCode(), winUserResult.getGradeCode());
		// step 3 更新fightresult 信息  最终将排名 战斗力获取值 结果 更新
		winUserResult.setFightedRank(userCurrentRank);
		winUserResult.setFightValue(getFightValue);
		winUserResult.setIntegral(winUserChangeModel.getRewardIntegral());//积分
		loseFightResult.setIntegral(loseFightResult.getIntegral());
		loseFightResult.setFightedRank(weeklyRankingServiceImpl.countCurrentRank(loseFightResult.getUserCode(), loseFightResult.getSubjectCode(), loseFightResult.getGradeCode()));
		lists.add(winUserResult);
		lists.add(loseFightResult);
		//对战结果有一个是FightResult=null值的时候，不进行更新操作
		if(winUserResult.getFightResult()!=null && loseFightResult.getFightResult()!=null){
			this.updateList(lists);
		}
	}
	@Transactional
	@Override
	public BattleResultSubmitModel submitResult(String userCode, Long useTime, Double score,
			BaseResourceModel baseResourceModel,String fightUUID,String paperUUID,BattleEnum battleEnum,Boolean isWithRobot,String unitCode) {
		// TODO Auto-generated method stub
		BattleResultSubmitModel battleResultSubmitModel=new BattleResultSubmitModel();
		
		//根据fight uuid 和paperUUID查询有无记录  根据结果判断 是否需要进行对战结果计算流程
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("fightUuid", fightUUID);
		List<FightResult> lists=new ArrayList<>();
//		if(!isWithRobot){
			lists=	this.findByCondition(queryMap);
//		}
		FightResult fightResult=new FightResult();
		
		/**
		 * 根据用户code还有fightuuid查询一个对战记录
		 * 
		 * 在这里当前排名只是从数据库之中取出来的 ，对战之后又的排名才是计算出来的
		 * 
		 * 从缓存之中取出来
		 */
		if(!isWithRobot){
			logger.debug("=========submitResult==============================userCode==:"+userCode);
			String stringRank = redisClientTemplate.get("rank"+userCode);
			logger.debug("=========submitResult=======redis中获取===========stringRank==:"+stringRank);
//			long parseLong = Long.parseLong(stringRank);
//			fightResult.setCurrentRank(Long.parseLong(stringRank));
			fightResult.setCurrentRank(new BigDecimal(stringRank).longValue());
//			fightResult.setCurrentRank(weeklyRankingServiceImpl.countCurrentRank(userCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode()));//原来的方法
		}else{
			fightResult.setCurrentRank(weeklyRankingServiceImpl.countCurrentRank(userCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode()));//原来的方法
		}
		fightResult.setFightedRank(weeklyRankingServiceImpl.countCurrentRank(userCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode()));
		fightResult.setFightType(FightEnum.BATTLE.getValue());
		fightResult.setFightUuid(fightUUID);
		fightResult.setGetScore(score);
		fightResult.setGradeCode(baseResourceModel.getGradeCode());
		fightResult.setSubjectCode(baseResourceModel.getSubjectCode());
		fightResult.setPaperUuid(paperUUID);
		fightResult.setUserCode(userCode);
		fightResult.setUseTime(useTime);
		fightResult.setUnitCode(unitCode);
		logger.debug("保存对战结果fightResult======================:"+fightResult.getFightResult());
		this.save(fightResult);
		//redis删除本次对战信息
		redisClientTemplate.set("status"+userCode,"2");
		redisClientTemplate.del("battle"+userCode);
		//保存用户信息到周实时榜
		if(!userCode.equals("eeduspacerobot")){
			weeklyRankingServiceImpl.saveWeekRanking(userCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode());
		}
		if(lists.size()>0){
			logger.debug("集合中元素的个数是多少  ："+gson.toJson(lists.size()));
		}
		if(lists.size()>=1 ){//走对战结果计算流程   返回结果信息给当前提交人   推送信息给一个用户 信息为 答题结果已出 请查看
			logger.debug("进入计算方法："+gson.toJson(fightUUID+"-----------"+battleEnum));
			computeBattleThread(fightUUID, battleEnum);
			
			if(!isWithRobot){
				//推送对战结果消息
				BattleResultNotice battleResultNotice=new BattleResultNotice();
				battleResultNotice.setFightUUID(fightUUID);
				battleResultNotice.setReceiver(lists.get(0).getUserCode());
				battleResultNotice.setOpponentUserCode(userCode);
				battleResultNotice.setUnitCode(unitCode);
				logger.debug("==========================================submitResult方法中：battleResultNotice："+battleResultNotice);
				eventOperationService.userFightResultNotice(battleResultNotice);
//				eventOperationService.userFightResultNotice(battleResultSubmitModel);
			
				ClientModel clientModel=new ClientModel();
				clientModel.setFromUserCode(userCode);
				clientModel.setMessageType("hxs_battle_result_message");
				clientModel.setToUserCode(lists.get(0).getUserCode());
				logger.debug("===============================对战结束之后，双方的userCode是：userCode："+userCode+"----lists.get(0).getUserCode()"+lists.get(0).getUserCode());
				BattlePushResultModel battlePushResultModel=new BattlePushResultModel();
				battlePushResultModel.setFightUUID(fightUUID);
				battlePushResultModel.setUnitCode(unitCode);
				clientModel.setMessage(battlePushResultModel);
				messageUtil.sendMessageToOne(clientModel);
			}
			
			battleResultSubmitModel.setFightUUID(fightUUID);
			
			battleResultSubmitModel.setIsFinish(true);
			battleResultSubmitModel.setIsEnd(true);
		}else{
			logger.debug("掉线之后会不会走这个方法啊。。。。。。。。。。。。。。。。。。。。。");
			battleResultSubmitModel.setIsFinish(false);
			battleResultSubmitModel.setIsEnd(true);
			/*//更新用户状态
			UserModel userModel=new UserModel();
			userModel.setUserCode(userCode);
			userModel.setUserOnlineState(OnlineStatus.On_line.getValue());
			eventOperationService.userOnLineState(userModel);*/
			
		}
		logger.debug("保存试题的时候获取 unitCode值为："+gson.toJson(unitCode));
		//更新用户在线状态
		//UserModel userModel=new UserModel();
		
		User user=userService.findByUserCode(userCode);
		//userModel.setUserCode(userCode);
		user.setUserOnlineState(OnlineStatus.On_line.getValue());
		userService.update(user);
		//eventOperationService.userOnLineState(userModel);
		return battleResultSubmitModel;
	}
	/**
	 * 开启线程处理对战结果计算
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月21日 下午3:25:36
	 * @param fightUUID
	 * @param battleEnum
	 */
	public void computeBattleThread(final String fightUUID,final BattleEnum battleEnum){
				computeBattleFightResult(fightUUID, battleEnum);
//		new Thread(){
//			public void run() {
//			};
//		}.start();
	}
	@Override
	public List<BattleResultModel> getBattleDetail(String fightUUID) {
		
		return fightResultMapperImpl.findBattleResultModel(fightUUID);
	}
	@Override
	public void disconnectionFight(String userCode, String fightUUID) {
		Map<String, Object> queryUserFight=new HashMap<>();
		queryUserFight.put("uuid", fightUUID);
		
		// step1 获取用户对战信息
		// step2 获取用户答对题的数量
		// step3按照试卷总时间为答题时间
		// step4得分按照用户做对的题 每题10来计算
		List<UserFight> userFights=userFightServiceImpl.findByCondition(queryUserFight);
		if(userFights.size()>0){
			UserFight userFight=userFights.get(0);
			Long rightLong=userAnswerServiceImpl.countRight(userFight.getQuestionsCode(), userCode, fightUUID);
			Double score=new BigDecimal(rightLong).multiply(new BigDecimal(10)).doubleValue();
			BaseResourceModel baseResourceModel=new BaseResourceModel();
			baseResourceModel.setSubjectCode(userFight.getSubjectCode());
			baseResourceModel.setGradeCode(userFight.getGradeCode());
			Boolean isWithRobot=false;
			if(userFight.getPlaeryB().equals("eeduspacerobot")){
				isWithRobot=true;
			}
//			logger.debug("disconnectionFight(:--------------------------------======================================");
			this.submitResult(userCode, 90l, score, baseResourceModel, fightUUID, userFight.getQuestionsCode(), BattleEnum.getEnum(userFight.getBattleType()),isWithRobot,userFight.getUnitCode());
		}
	}
	@Override
	public Boolean verifyEligibility(String userCode, String subjectCode,
			String gradeCode) {
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("userCode", userCode);
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		queryMap.put("start", 0);
		queryMap.put("limit", 10);
		List<FightResult> list=this.findByCondition(queryMap);
		User userCode2 = userService.findByUserCode(userCode);
		if(userCode2!=null && userCode2.getWeekStatus()>=5d){
			return true;
		} else {
			return false;
		}
		
//		if(list.size()>=5){
//			return true;
//		}else{
//			return false;
//		}
	}
}
