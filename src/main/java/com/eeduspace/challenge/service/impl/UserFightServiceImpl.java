package com.eeduspace.challenge.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeduspace.challenge.asynchronous.EventOperationService;
import com.eeduspace.challenge.client.model.response.QuestionResponseModel;
import com.eeduspace.challenge.client.service.BaseResourceClient;
import com.eeduspace.challenge.controller.BattleController;
import com.eeduspace.challenge.convert.QuestionConvert;
import com.eeduspace.challenge.enumeration.BattleEnum;
import com.eeduspace.challenge.enumeration.ChallengeEnum;
import com.eeduspace.challenge.enumeration.FightEnum;
import com.eeduspace.challenge.enumeration.FightStateEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.enumeration.UserEnum.OnlineStatus;
import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.BattlePushModel;
import com.eeduspace.challenge.model.battle.BattleUserModel;
import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.dao.UserFightMapper;
import com.eeduspace.challenge.persist.po.Paper;
import com.eeduspace.challenge.persist.po.Question;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserFight;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.PaperService;
import com.eeduspace.challenge.service.QuestionService;
import com.eeduspace.challenge.service.UserFightService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.eeduspace.challenge.util.Constant;
import com.eeduspace.challenge.util.RandomUtils;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

@Service("userFightServiceImpl")
public class UserFightServiceImpl extends BaseServiceImpl<UserFight>
		implements UserFightService {
	@Autowired
	private MessageUtil messageUtil;
	@Resource
	private UserFightMapper userFightMapperImpl;
	@Inject
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;
	@Autowired
	private BaseResourceClient baseResourceClientImpl;
	@Autowired
	private PaperService paperServiceImpl;
	@Autowired
	private QuestionService questionServiceImpl;
	@Autowired
	private FightResultService fightResultServiceImpl;
	@Autowired
	private UserService userService;
	@Inject
	private EventOperationService eventOperationService;
	private final Logger logger = LoggerFactory.getLogger(BattleController.class);
	private Gson gson=new Gson();
	@Autowired
	public UserFightServiceImpl(UserFightMapper userFightMapper) {
		this.baseDao = userFightMapper;
	}
	@Override
	public String saveChallenge(String userCode, String challenger,String paperUUID,String challengeType,BaseResourceModel baseResourceModel) {
		UserFight userFight=new UserFight();
		userFight.setFightType(FightEnum.CHALLENGE.getValue());
		if(challengeType.equals("friend")){
			userFight.setBattleType(ChallengeEnum.FRIENDCHALLENGE.getValue());
		}else{
			userFight.setBattleType(ChallengeEnum.RANKCHALLENGE.getValue());
		}
		userFight.setGradeCode(baseResourceModel.getGradeCode());
		userFight.setPlayerA(userCode);
		userFight.setPlaeryB(challenger);
		userFight.setQuestionsCode(paperUUID);
		userFight.setSubjectCode(baseResourceModel.getSubjectCode());
		userFight.setUnitCode(baseResourceModel.getUnitCode());
		userFight.setIsOver(FightStateEnum.FIGHTING.getValue());
		this.save(userFight);
		return userFight.getUuid();
	}
	@Transactional
	@Override
	public BattlePushModel saveBattle(String userACode, String userBCode,
			String battleType, BaseResourceModel baseResourceModel) throws ClientProtocolException, IOException {
		BattlePushModel battlePushModel=new BattlePushModel();
		//TODO step 1 去请求试题信息  返回试卷UUID 及学科学年等信息 
		Paper paper=new Paper();
		ansySavePaper(baseResourceModel, paper);
		UserFight userFight=new UserFight();
		//userFight.setBattleType(BattleEnum.QUICKBATTLE.getValue());
		userFight.setBattleType(BattleEnum.getEnum(battleType).getValue());
		userFight.setFightType(FightEnum.BATTLE.getValue());
		userFight.setGradeCode(baseResourceModel.getGradeCode());
		userFight.setSubjectCode(baseResourceModel.getSubjectCode());
		userFight.setPlayerA(userACode);
		userFight.setPlaeryB(userBCode);
		userFight.setQuestionsCode(paper.getUuid());
		this.save(userFight);
		battlePushModel.setPaperUUID(paper.getUuid());
		battlePushModel.setFightUUID(userFight.getUuid());
		return battlePushModel;
	}
	/**
	 * 保存试卷信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月22日 下午2:45:37
	 * @param baseResourceModel
	 * @param paper
	 */
	public void ansySavePaper(final BaseResourceModel baseResourceModel,final Paper paper) {
		QuestionResponseModel questionResponseModel=new QuestionResponseModel();
		try {
			questionResponseModel=baseResourceClientImpl.getPapper(baseResourceModel);
			paper.setSubjectCode(baseResourceModel.getSubjectCode());
			paper.setGradeCode(baseResourceModel.getGradeCode());
			saveQuestions(paper, questionResponseModel);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		new Thread(){
//			@Override
//			public void run() {
//			}
//		}.start();
	}
	/**
	 * 保存试题信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月16日 下午2:01:08
	 * @param paper
	 * @param baseResponse
	 * @throws IOException 
	 */
	public void saveQuestions(Paper paper,QuestionResponseModel questionResponseModel) throws IOException{
		//TODO 保存试卷 和试题  baseResponse 自选返回试题信息
		List<Question> questions=new ArrayList<>();
		for (QuestionEntity questionEntity : questionResponseModel.getDatas()) {
			Question question=QuestionConvert.toQuestion(questionEntity, paper.getUuid());
			questions.add(question);
		}
		paperServiceImpl.save(paper);
		questionServiceImpl.saveList(questions);
	}
	@Transactional
	@Override
	public BattlePushModel matchBattle(BaseResourceModel baseResourceModel,String userACode,String battleType) throws ClientProtocolException, IOException {
		/**
		 * 置一操作
		 */
		User findByUserCode = userService.findByUserCode(userACode);
		findByUserCode.setUserOnlineState(OnlineStatus.On_line.getValue());
		userService.update(findByUserCode);
		//查询学年学科下匹配者数量
		Long ss=redisClientTemplate.scard(baseResourceModel.getSubjectCode()+baseResourceModel.getGradeCode());
		if(ss<=0){
			//如果set集合中不存在匹配者 将用户存储到set集合 key为学年学科组成  
			redisClientTemplate.sadd(baseResourceModel.getSubjectCode()+baseResourceModel.getGradeCode(), userACode);
			//表示未匹配到
			BattlePushModel b= new BattlePushModel();
			b.setIsMatch(false);
			return b;
		}else{
			
			String userBCode="";
			//获取学年学科下待匹配用户集合
			Set<String> userSets=redisClientTemplate.smembers(baseResourceModel.getSubjectCode()+baseResourceModel.getGradeCode());
			Iterator<String> it=userSets.iterator() ;   
		    while(it.hasNext()){
		    	 userBCode=it.next();
		         break;
		    } 
		    
		    
		    //匹配到对手后 移除匹配集合
		    redisClientTemplate.srem(baseResourceModel.getSubjectCode()+baseResourceModel.getGradeCode(), userBCode);
		    BattlePushModel pushModel=this.saveBattle(userACode, userBCode, battleType, baseResourceModel);
		    pushModel.setIsMatch(true);
		    //TODO 匹配到对手以后将对战信息推送给对手 待添加推送业务信息
		    pushModel.setBattleUserModel(getBattleUserModel(userBCode));//设置匹配到对手的信息
		   logger.debug("=================pushModel================"+gson.toJson(pushModel));
		    //TODO step 2  获取用户当前排名  设置排名
			Long userARank=weeklyRankingServiceImpl.countCurrentRank(userACode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode());
			Long userBRank=weeklyRankingServiceImpl.countCurrentRank(userBCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode());
			/**
			 * 匹配对战的时候把之前排名信息保存到缓存 之中
			 */
			if(userACode!=null){
				logger.debug("userACode=========================:"+userACode);
				redisClientTemplate.set("rank"+userACode, String.valueOf(userARank));
				logger.debug("userACode===============redis========"+redisClientTemplate.get("rank"+userACode));
			}
			if(userBCode!=null){
				logger.debug("userBCode=========================:"+userBCode);
				redisClientTemplate.set("rank"+userBCode, String.valueOf(userBRank));
				logger.debug("userBCode===============redis========"+redisClientTemplate.get("rank"+userBCode));
			}
			
			pushModel.setUserCurrentRank(userARank);
			//向Redis里面存储用户对战信息  断线的时候需要去清除redis信息
			logger.debug("===================向redis中插入fightuuid为：================"+pushModel.getFightUUID());
			logger.debug("battle"+userACode+"===================向redis中插入userCode为：================"+"battle"+userBCode);
			String a = "1";
			redisClientTemplate.set("battle"+userACode, pushModel.getFightUUID());
			redisClientTemplate.set("battle"+userBCode, pushModel.getFightUUID());
			redisClientTemplate.set("status"+userACode, a);
			redisClientTemplate.set("status"+userBCode, a);
			String fightUUIDA = redisClientTemplate.get("battle" + userACode);
			String fightUUIDB = redisClientTemplate.get("battle" + userBCode);
			logger.debug("设置到redis中的用户对应的fightUUIDA ："+fightUUIDA+"============fightUUIDB========="+fightUUIDB);
			//向B用户推送匹配到的信息
			UserModel userModel1=userService.findDetailAllByUserCode(userACode);
			BattleUserModel userModelB=getBattleUserModel(userACode);
			if(userModel1.getVipEndDate()!=null && userModel1.getVipEndDate().after(new Date())){
				userModelB.setOverdue(false);
			}else {
				userModelB.setOverdue(true);
			}
//			userModelB.setOverdue(!userModel1.isOverdue());
			BattlePushModel pushB=new BattlePushModel();
			pushB.setUserCurrentRank(userBRank);
			pushB.setPaperUUID(pushModel.getPaperUUID());
			pushB.setFightUUID(pushModel.getFightUUID());
			pushB.setBattleUserModel(userModelB);
			pushB.setIsMatch(true);
			//TODO 进行推送
			ClientModel clientModel=new ClientModel();
			clientModel.setMessageType("hxs_battle_message");//匹配对战消息
			clientModel.setMessage(pushB);
			clientModel.setToUserCode(userBCode);
			clientModel.setFromUserCode(userACode);
			logger.debug("------------clientModel----------"+gson.toJson(clientModel));
			messageUtil.sendMessageToOne(clientModel);
			
			
			//更改在线状态
			UserModel myUserModel=new UserModel();
			UserModel otherUserModel=new UserModel();

			myUserModel.setUserCode(userACode);
			myUserModel.setUserOnlineState(OnlineStatus.On_Challenge.getValue());
		
			otherUserModel.setUserCode(userBCode);
			otherUserModel.setUserOnlineState(OnlineStatus.On_Challenge.getValue());
			eventOperationService.userOnLineState(myUserModel);
			eventOperationService.userOnLineState(otherUserModel);

			return pushModel;
		}
	}
	public BattleUserModel getBattleUserModel(String userCode){
			UserModel userModel=userService.findDetailAllByUserCode(userCode);
		    BattleUserModel battleUserModel=new BattleUserModel();
		    battleUserModel.setCombatValue(userModel.getFightValue());
		    battleUserModel.setHeadImgUrl(userModel.getHeadImgUrl());
		    battleUserModel.setIsVip(userModel.getIsVip());
		    battleUserModel.setUserCode(userModel.getUserCode());
		    battleUserModel.setOverdue(userModel.isOverdue());
		    battleUserModel.setNickName(userModel.getNickName());
		    return battleUserModel;
	}
	@Override
	public BattlePushModel battleWithRobot(BaseResourceModel baseResourceModel,
			String userCode) throws ClientProtocolException, IOException {
		
		BattlePushModel battlePushModel=this.saveBattle(userCode, Constant.ROBOTCODE, BattleEnum.QUICKBATTLE.toString(), baseResourceModel);
		BattleUserModel userModel=new BattleUserModel();
		UserModel userModel1=userService.findDetailAllByUserCode(userCode);
		/**
		 * 与机器人对战   显示对战结果nickName  常量
		 */
		String randomNumber = RandomUtils.getRandomNumber(6);
//		userModel.setNickName("用户"+RandomUtils.getRandomNumber(6));
//		userModel.setNickName("用户"+randomNumber);
		userModel.setNickName("tom");
//		userModel.setUserCode(Constant.ROBOTCODE+randomNumber);
		userModel.setUserCode(Constant.ROBOTCODE);
		userModel.setOverdue(userModel1.isOverdue());
		/**
		 * 增加性别
		 */
		Integer sex = userModel1.getSex();
		if (sex!=null && sex==UserEnum.Sex.Woman.getValue()) {
			userModel.setSex(UserEnum.Sex.Man.getValue());
		}else {
			userModel.setSex(UserEnum.Sex.Woman.getValue());
		}
		
		battlePushModel.setBattleUserModel(userModel);
		
		UserModel myuModel=new UserModel();
		myuModel.setUserCode(userCode);
		myuModel.setUserOnlineState(OnlineStatus.On_Challenge.getValue());
		eventOperationService.userOnLineState(myuModel);
		return battlePushModel;
	}
	@Override
	public BattlePushModel agreeBattleWithFriend(
			BaseResourceModel baseResourceModel, String userCode,
			String friendCode) throws ClientProtocolException, IOException {
		/**
		 * 添加对战之前排名信息
		 */
		Long userARank=weeklyRankingServiceImpl.countCurrentRank(userCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode());
		Long userBRank=weeklyRankingServiceImpl.countCurrentRank(friendCode, baseResourceModel.getSubjectCode(), baseResourceModel.getGradeCode());
		if(userCode!=null){
			logger.debug("userCode=========================:"+userCode);
			redisClientTemplate.set("rank"+userCode, String.valueOf(userARank));
			logger.debug("userACode===============redis========"+redisClientTemplate.get("rank"+userCode));
		}
		if(friendCode!=null){
			logger.debug("userBCode=========================:"+friendCode);
			redisClientTemplate.set("rank"+friendCode, String.valueOf(userBRank));
			logger.debug("userBCode===============redis========"+redisClientTemplate.get("rank"+friendCode));
		}
		//更改在线状态
		UserModel myuModel=new UserModel();
		UserModel friendUserModel=new UserModel();
		myuModel.setUserCode(userCode);
		myuModel.setUserOnlineState(OnlineStatus.On_Challenge.getValue());
		friendUserModel.setUserCode(friendCode);
		friendUserModel.setUserOnlineState(OnlineStatus.On_Challenge.getValue());
		eventOperationService.userOnLineState(myuModel);
		eventOperationService.userOnLineState(friendUserModel);
		
		
		BattlePushModel battlePushModel=new BattlePushModel();
		UserModel userModel=userService.findDetailAllByUserCode(friendCode);
		BattleUserModel battleUserModel=new BattleUserModel();
		battleUserModel.setCombatValue(userModel.getFightValue());
		battleUserModel.setHeadImgUrl(userModel.getHeadImgUrl());
		battleUserModel.setIsVip(userModel.getIsVip());
		battleUserModel.setNickName(userModel.getNickName());
		battleUserModel.setUserCode(userModel.getUserCode());
		battlePushModel=this.saveBattle(userCode, friendCode, BattleEnum.FRIENDBATTLE.toString(), baseResourceModel);
		battlePushModel.setBattleUserModel(battleUserModel);
		return battlePushModel;
	}
}
