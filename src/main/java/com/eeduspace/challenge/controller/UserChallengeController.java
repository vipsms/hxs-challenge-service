package com.eeduspace.challenge.controller;

import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.enumeration.UserEnum.OnlineStatus;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.challenge.ChallengeQuestionModel;
import com.eeduspace.challenge.model.challenge.ChallengeResultModel;
import com.eeduspace.challenge.model.challenge.ChallengeUserAnswerModel;
import com.eeduspace.challenge.model.request.ChallengeRequestModel;
import com.eeduspace.challenge.model.weeklyrank.RankModel;
import com.eeduspace.challenge.model.weeklyrank.WeekListMasterModel;
import com.eeduspace.challenge.model.weeklyrank.WeekRankingModel;
import com.eeduspace.challenge.persist.dao.TaskRewardReceiveMapper;
import com.eeduspace.challenge.persist.po.FightResult;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.WeeklyChart;
import com.eeduspace.challenge.persist.po.WeeklyRanked;
import com.eeduspace.challenge.persist.po.WeeklyRanking;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.QuestionService;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.service.UserFriendService;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyChartService;
import com.eeduspace.challenge.service.WeeklyRankedService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.eeduspace.challenge.util.DateUtils;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

/**
 * @author zhuchaowei 2016年7月18日 Description 用户挑战控制层
 */
@RestController
@RequestMapping(value = "/user_challenge")
public class UserChallengeController {
	private Gson gson = new Gson();
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;
	@Autowired
	private WeeklyChartService weeklyChartServiceImpl;
	@Autowired
	private WeeklyRankedService weeklyRankedServiceImpl;
	@Autowired
	private FightResultService fightResultServiceImpl;
	@Inject
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private QuestionService questionServiceImpl;
	@Resource
	private SystemDictionaryService systemDictionaryService;
	@Autowired
	private UserFriendService userFriendServiceImpl;
	@Autowired
	private UserService userService;
	@Autowired
    private TaskRewardReceiveService taskRewardReceiveServiceImpl;
	@Autowired
    private UserConvert userConvert;
	
    private final Logger logger = LoggerFactory.getLogger(UserChallengeController.class);

	/**
	 * 获取挑战全国排行榜 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年7月18日
	 * 下午12:28:38
	 * 
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value = "get_national_rank", method = RequestMethod.POST)
	private BaseResponse getNationalRank(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel) {
		logger.debug("getNationalRank:{}",gson.toJson(challengeRequestModel));
		BaseResourceModel baseResourceModel = new BaseResourceModel();
		BaseResponse baseResponse = new BaseResponse(requestId);
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");

		}
		if (challengeRequestModel.getOrder() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Order");

		}
		if (challengeRequestModel.getStart() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Start");
		}
		if (challengeRequestModel.getItem() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Item");

		}
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		if(!fightResultServiceImpl.verifyEligibility(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode())){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.FORBIDDEN_NOPERMISSION.toString(), "verifyEligibility error");
		}
		try {
			baseResourceModel.setGradeCode(challengeRequestModel.getGradeCode());
			baseResourceModel.setSubjectCode(challengeRequestModel.getSubjectCode());
			Long userCurrentRank=weeklyRankingServiceImpl.countCurrentRank(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			List<WeekRankingModel> canChallengeList=new ArrayList<>();;
			if(userCurrentRank-10<=0){
				canChallengeList=weeklyRankingServiceImpl.findNationalRank(0l, userCurrentRank, baseResourceModel);
			}else{
				canChallengeList=weeklyRankingServiceImpl.findNationalRank(userCurrentRank-10, 10l, baseResourceModel);
			}
			List<WeekRankingModel> lists=	weeklyRankingServiceImpl.findNationalRank(challengeRequestModel.getOrder(), challengeRequestModel.getStart(), challengeRequestModel.getItem(), baseResourceModel);
			Long rank=challengeRequestModel.getStart();
			if(challengeRequestModel.getOrder().equals("down")){
				/**
				 * 挑战前十个人    
				 */
				if(rank-10<=0){
					rank=0l;
				}else{
					rank=rank-10;
				}
			}
			UserModel userModel = null;
			for (WeekRankingModel weekRankingModel : lists) {
				userModel = userService.findDetailAllByUserCode(weekRankingModel.getUserCode());
				rank=rank+1;
				weekRankingModel.setIsChallenge(isChallenge(canChallengeList, weekRankingModel));
				weekRankingModel.setCurrentRank(rank+"");
				weekRankingModel.setMobile(weekRankingModel.getMobile());
				weekRankingModel.setIsVip(userModel.getIsVip());
				if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
					weekRankingModel.setOverdue(false);
				}else {
					weekRankingModel.setOverdue(true);
				}
//				System.out.println(userModel.getVipEndDate()+"----------------"+weekRankingModel.getOverdue());
				
			}
			baseResponse.setResult(lists);
		} catch (Exception e) {
			logger.error("getNationalRank exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getNationalRank Exception");
		}
		logger.debug("get_national_rank 获取挑战全国排行榜   ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	
	public Boolean isChallenge(List<WeekRankingModel> canChall,WeekRankingModel weekRankingModel){
		for (WeekRankingModel w : canChall) {
			if(w.getUserCode().equals(weekRankingModel.getUserCode())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取自己在好友中的排名情况
	 * @author  作者 : gaofengming
			E-mail : gaofengming@e-eduspace.com
	 * @date 创建时间   ：2016年9月20日下午4:48:27  
	 * @param  enclosing_method_arguments
	 * @return  return_type
	 */
	@RequestMapping(value = "get_own_friend_rank", method = RequestMethod.POST)
	private BaseResponse getOwnFriendRank(@RequestParam(required=false) String requestId,@RequestBody RankModel rankModel) {
		logger.debug("getOwnFriendRank:{}",gson.toJson(rankModel));
		BaseResponse baseResponse = new BaseResponse(requestId);
		if (rankModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (rankModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		if (rankModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		try {
			Long userCurrentRank=weeklyRankingServiceImpl.countCurrentRankInFriend(rankModel.getUserCode(), rankModel.getSubjectCode(), rankModel.getGradeCode());
			
			User userInfoPo =userService.findByUserCode(rankModel.getUserCode());
			UserModel userModel= userConvert.fromUserPo(userInfoPo, true);
			WeekRankingModel rankingModel = new WeekRankingModel();
			rankingModel.setRanking(userCurrentRank);
			rankingModel.setHeadImgUrl(userModel.getHeadImgUrl());
			rankingModel.setNickName(userModel.getNickName());
			if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
				rankingModel.setOverdue(false);
			}else {
				rankingModel.setOverdue(true);
			}
			/**
			 * 获取全国榜中的战斗力值
			 */
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("userCode", rankModel.getUserCode());
			queryMap.put("subjectCode", rankModel.getSubjectCode());
			queryMap.put("gradeCode", rankModel.getGradeCode());
			WeekRankingModel userRanking = weeklyRankingServiceImpl.findUserNationalRanking(queryMap);
			
			/**
			 * 这个人周榜中没有数据的时候，直接返回一个null
			 * 
			 * 进入榜之后战斗力为自己榜中的数据
			 */
			if(userRanking==null){
				baseResponse.setResult(null);
			}else{
				rankingModel.setWeekFightValueFloat(userRanking.getWeekFightValueFloat());
				rankingModel.setIsVip(userModel.getIsVip());
				//体力值
				String stamina=redisClientTemplate.get("hxs_challenge_stamina_"+rankModel.getUserCode());
				Integer times=Integer.valueOf(stamina.split(",")[0]);
				rankingModel.setStamina(times);
				
				baseResponse.setResult(rankingModel);
			}
			
		} catch (Exception e) {
			logger.error("getOwnFriendRank exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getOwnFriendRank Exception");
		}
		logger.debug("get_own_friend_rank 获取自己在好友排行榜中的位置："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 获取挑战好友排行榜 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年7月18日
	 * 下午12:28:38
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value = "get_friend_rank", method = RequestMethod.POST)
	private BaseResponse getFriendRank(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel) {
		logger.debug("getFriendRank:{}",gson.toJson(challengeRequestModel));
		BaseResourceModel baseResourceModel = new BaseResourceModel();
		BaseResponse baseResponse = new BaseResponse(requestId);
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");

		}
		if (challengeRequestModel.getOrder() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Order");

		}
		if (challengeRequestModel.getStart() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Start");
		}
		if (challengeRequestModel.getItem() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Item");

		}
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		if(!fightResultServiceImpl.verifyEligibility(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode())){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.FORBIDDEN_NOPERMISSION.toString(), "verifyEligibility error");
		}
		try {
			baseResourceModel.setGradeCode(challengeRequestModel.getGradeCode());
			baseResourceModel.setSubjectCode(challengeRequestModel.getSubjectCode());
			Long userCurrentRank=weeklyRankingServiceImpl.countCurrentRankInFriend(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			List<WeekRankingModel> canChallengeList=new ArrayList<>();;
			if(userCurrentRank-10<0){
				canChallengeList=weeklyRankingServiceImpl.findFriendRank(challengeRequestModel.getUserCode(), 0l, userCurrentRank, baseResourceModel);//(0l, userCurrentRank, baseResourceModel);
			}else{
				canChallengeList=weeklyRankingServiceImpl.findFriendRank(challengeRequestModel.getUserCode(),userCurrentRank-10, 10l, baseResourceModel);
			}
			List<WeekRankingModel> lists=	weeklyRankingServiceImpl.findFriendRank(challengeRequestModel.getUserCode(),challengeRequestModel.getOrder(), challengeRequestModel.getStart(), challengeRequestModel.getItem(), baseResourceModel);
			Long rank=challengeRequestModel.getStart();
			if(challengeRequestModel.getOrder().equals("down")){
				if(rank-10<0){
					rank=0l;
				}else{
					rank=rank-10;
				}
			}
			UserModel userModel = null;
			for (WeekRankingModel weekRankingModel : lists) {
				userModel = userService.findDetailAllByUserCode(weekRankingModel.getUserCode());
				rank=rank+1;
				weekRankingModel.setIsChallenge(isChallenge(canChallengeList, weekRankingModel));
				weekRankingModel.setCurrentRank(rank+"");
				weekRankingModel.setMobile(weekRankingModel.getMobile());
				weekRankingModel.setIsVip(userModel.getIsVip());
//				weekRankingModel.setOverdue(userModel.isOverdue());
				if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
					weekRankingModel.setOverdue(false);
				}else {
					weekRankingModel.setOverdue(true);
				}
			}
			baseResponse.setResult(lists);
		} catch (Exception e) {
			logger.error("getNationalRank exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getNationalRank Exception");
		}
		logger.debug("get_friend_rank 获取挑战好友排行榜"+gson.toJson(baseResponse));
		return baseResponse;
	}
	
	/**
	 * 获取上周榜主信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午1:19:16
	 * @return
	 */
	@RequestMapping(value="get_last_week_championship",method=RequestMethod.POST)
	public BaseResponse getLastWeekChampionship(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");

		}
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		BaseResponse baseResponse=new BaseResponse(requestId);
		try {
			WeeklyChart weeklyChart=weeklyChartServiceImpl.findMaxWeeklyChart(challengeRequestModel.getSubjectCode(),challengeRequestModel.getGradeCode());
			if(weeklyChart==null){
				baseResponse.setResult(null);
			}else{
				logger.debug("weeklyChart{}",gson.toJson(weeklyChart));
				WeekListMasterModel weekListMasterModel=new WeekListMasterModel();
				logger.debug("userCode-----------weeklyRanked:{}"+challengeRequestModel.getUserCode()+","+challengeRequestModel.getSubjectCode()+","+challengeRequestModel.getGradeCode());
				
				WeeklyRanked weeklyRanked=weeklyRankedServiceImpl.findMasterInFriend(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
				logger.debug("周冠军的用户code，weeklyChart:"+weeklyChart.getChampionUserCode());
				UserModel userModel=userService.findDetailAllByUserCode(weeklyChart.getChampionUserCode());
				if(weeklyRanked!=null){
					UserModel friendMaster=userService.findDetailAllByUserCode(weeklyRanked.getUserCode());
					weekListMasterModel.setFriendMaster(friendMaster);
				}
				weekListMasterModel.setNationwideMaster(userModel);
				logger.debug("user{}",gson.toJson(userModel));
				baseResponse.setResult(weekListMasterModel);
			}
		} catch (Exception e) {
			logger.error("getLastWeekChampionship exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString(),"getLastWeekChampionship Exception");
		}
		logger.debug("get_last_week_championship 获取上周榜主信息   ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 获取被挑战者卷子
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午1:49:29
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="get_challenger_paper",method=RequestMethod.POST)
	public BaseResponse getChallengerPaper(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel,HttpServletRequest request){
		logger.debug("challengeRequestModel:{}",gson.toJson(challengeRequestModel));
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");

		}if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		if (challengeRequestModel.getChallengerCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerCode");
		}
		
		BaseResponse baseResponse=new BaseResponse(requestId);
		/**
		 * 置一操作
		 */
		User findByUserCode = userService.findByUserCode(challengeRequestModel.getUserCode());
		findByUserCode.setUserOnlineState(OnlineStatus.On_line.getValue());
		userService.update(findByUserCode);
		//置一操作结束
		//校验用户在线状态
		User user=userService.findByUserCode(challengeRequestModel.getUserCode());
		if(user.getUserOnlineState()==OnlineStatus.On_Challenge.getValue()){
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.USER_FIGHT.toString());
		}else if(user.getUserOnlineState()==OnlineStatus.Off_line.getValue()){
			return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.USER_OFF_LINE.toString());

		}
		try {
			//TODO 进行挑战 去redis里 更新体力值和过期时间
			//更新用户战斗状态为对战中
			updateUserOnLineState(challengeRequestModel.getUserCode(), UserEnum.OnlineStatus.On_Challenge);
			String stamina=redisClientTemplate.get("hxs_challenge_stamina_"+challengeRequestModel.getUserCode());
			Integer times=Integer.valueOf(stamina.split(",")[0]);
			if(times<=0){
				return BaseResponse.setResponse(new BaseResponse(requestId),
						ResponseCode.STAMINA_INSUFFICIENT.toString(), "体力不足");
			}else{
				redisClientTemplate.setex("hxs_challenge_stamina_"+challengeRequestModel.getUserCode(), DateUtils.getTadaySurplusTime(), (times-1)+","+stamina.split(",")[1]);
			}
			List<FightResult> fightResults=fightResultServiceImpl.findUserHighestPaper(challengeRequestModel.getChallengerCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			baseResponse.setResult(fightResults);
		} catch (Exception e) {
			logger.error("getChallengerPaper:{}",e);
		}
		return baseResponse;
		
	}
	
	public void updateUserOnLineState(String userCode,UserEnum.OnlineStatus userEnum){
		//更新用户战斗状态为对战中
		User user=userService.findByUserCode(userCode);
		user.setUserOnlineState(userEnum.getValue());
		user.setUpdateDate(new Date());
		userService.update(user);
	}
	/**
	 * 获取用户当前榜排名信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午2:35:29
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="get_user_current_rank",method=RequestMethod.POST)
	public BaseResponse getUserCurrentRank(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("challengeRequestModel:{}",gson.toJson(challengeRequestModel));
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if(!fightResultServiceImpl.verifyEligibility(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode())){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.FORBIDDEN_NOPERMISSION.toString(), "verifyEligibility error");
		}
		BaseResponse baseResponse=new BaseResponse(requestId);
		Long userRank=0l;
		Long friendRank=0l;
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
			
		}
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
			
		}
			friendRank=weeklyRankingServiceImpl.countCurrentRankInFriend(challengeRequestModel.getUserCode(),challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			userRank=weeklyRankingServiceImpl.countCurrentRank(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			
			Long allFriend = userFriendServiceImpl.getUserFriendCount(challengeRequestModel.getUserCode());
			Long allUser = weeklyRankingServiceImpl.countCurrentRankTotal(challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			double d = (allUser - userRank) / (double)allUser;
			BigDecimal temp = new BigDecimal(d);
			double userPercent = (temp.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue())*100;
			
		//TODO 体力值信息需要从redis里面取
		UserModel userModel=userService.findDetailAllByUserCode(challengeRequestModel.getUserCode());
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userCode", challengeRequestModel.getUserCode());
		queryMap.put("subjectCode", challengeRequestModel.getSubjectCode());
		queryMap.put("gradeCode", challengeRequestModel.getGradeCode());
		List<WeeklyRanking> weeklyRankings=weeklyRankingServiceImpl.findByCondition(queryMap);
		if(weeklyRankings.size()>0){
			WeeklyRanking weeklyRanking=weeklyRankingServiceImpl.findByCondition(queryMap).get(0);
			userModel.setFightValue(weeklyRanking.getWeekFightValue());
		}
		List<Long> usRankList=new ArrayList<>();
		usRankList.add(userRank);
		usRankList.add(friendRank);
		userModel.setRank(usRankList);
		String stamina=  redisClientTemplate.get("hxs_challenge_stamina_"+userModel.getUserCode());
		  if(StringUtils.isBlank(stamina)){
              SystemDictionary systemDictionary=new SystemDictionary();
              if(userModel.getIsVip()==0){
                   systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA.getCode());
              }else{
            	  systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA_VIP.getCode());
              }
                   
              redisClientTemplate.setex("hxs_challenge_stamina_"+userModel.getUserCode(), DateUtils.getTadaySurplusTime(), systemDictionary.getValue());
          }
		if(StringUtils.isNotBlank(stamina)){
			userModel.setStamina(Integer.valueOf(stamina.split(",")[0]));
		}else{
			userModel.setStamina(0);
		}
		
		userModel.setAllFriend(allFriend);
		userModel.setUserPercent(userPercent);
		baseResponse.setResult(userModel);
		logger.debug("get_user_current_rank 获取用户当前榜排名信息   ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 获取试题信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午4:00:20
	 * @param challengeRequestModel
	 * @return
	 */
	@RequestMapping(value="get_questions",method=RequestMethod.POST)
	public BaseResponse getQuestions(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("challengeRequestModel:{}",gson.toJson(challengeRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");

		}
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");

		}
		if (challengeRequestModel.getPaperUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUUID");

		}
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		if (challengeRequestModel.getChallengerCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerCode");

		}
		try {
			baseResourceModel.setSubjectCode(challengeRequestModel.getSubjectCode());
			baseResourceModel.setGradeCode(challengeRequestModel.getGradeCode());
			ChallengeQuestionModel challengeQuestionModel= questionServiceImpl.getChallengeQuestion(challengeRequestModel.getPaperUUID(), challengeRequestModel.getUserCode(), challengeRequestModel.getChallengerCode(), challengeRequestModel.getChallengeType(), baseResourceModel);
			
			baseResponse.setResult(challengeQuestionModel);
		} catch (Exception e) {
			logger.error("getQuestions exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getQuestions Exception");
		}
		logger.debug("get_questions 获取试题信息  ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 计算挑战结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午4:17:19
	 * @param challengeRequestModel
	 * @return
	 */
	@RequestMapping(value="compute_challenge_result",method=RequestMethod.POST)
	public BaseResponse computeChallengeResult(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("challengeRequestModel:{}",gson.toJson(challengeRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");

		}
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");

		}
		if (challengeRequestModel.getChallengerCombatValue() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerCombatValue");

		}
		if (challengeRequestModel.getChallengerFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerFightUUID");

		}
		if (challengeRequestModel.getUserRank() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserRank");

		}
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		if (challengeRequestModel.getGetScore() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GetScore");

		}
		if (challengeRequestModel.getUserUseTime() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserUseTime");

		}
		if (challengeRequestModel.getUserFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserFightUUID");

		}
		if (challengeRequestModel.getChallengerCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerCode");

		}
		if (challengeRequestModel.getChallengeType() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengeType");

		}
		Map<String, Object> queryUserFightResult=new HashMap<String, Object>();
		queryUserFightResult.put("fightUuid",challengeRequestModel.getUserFightUUID());
		queryUserFightResult.put("userCode", challengeRequestModel.getUserCode());
		if(fightResultServiceImpl.findByCondition(queryUserFightResult).size()>0){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.RESOURCE_DUPLICATE.toString(), "答题结果已提交,不能重复提交"); 
		}
		try {
			BaseResourceModel baseResourceModel=new BaseResourceModel();
			baseResourceModel.setSubjectCode(challengeRequestModel.getSubjectCode());
			baseResourceModel.setGradeCode(challengeRequestModel.getGradeCode());
			//挑战者（自己）
			UserModel userModel=userService.findDetailAllByUserCode(challengeRequestModel.getUserCode());
			//被挑战者
			UserModel userModel2=userService.findDetailAllByUserCode(challengeRequestModel.getChallengerCode());
			ChallengeResultModel challengeResultModel=fightResultServiceImpl.computeChallengeFightResult(challengeRequestModel.getChallengerCombatValue(), challengeRequestModel.getChallengerFightUUID(), challengeRequestModel.getUserCode(), challengeRequestModel.getChallengerCode(), challengeRequestModel.getChallengerName(), challengeRequestModel.getGetScore(), challengeRequestModel.getUserUseTime(), challengeRequestModel.getUserRank(), challengeRequestModel.getUserFightUUID(), baseResourceModel,challengeRequestModel.getChallengeType());
			logger.debug("challengeRequestModel.getChallengerCombatValue()挑战完之后获取到的战斗力值  ："+gson.toJson(challengeRequestModel.getChallengerCombatValue()));
			//获取挑战者的当前排名
			Long userCurrentRankFriend=weeklyRankingServiceImpl.countCurrentRankInFriend(userModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			Long userCurrentRank=weeklyRankingServiceImpl.countCurrentRank(userModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode());
			if(challengeRequestModel.getChallengeType().equals("friend")){
				//好友榜中  自己战斗之后的当前排名
				challengeResultModel.setFightedRank(userCurrentRankFriend);
			}else if(challengeRequestModel.getChallengeType().equals("other")){
				challengeResultModel.setFightedRank(userCurrentRank);
			}
			
			challengeResultModel.setNickName(userModel.getNickName());
			if(userModel.getVipEndDate()!=null && userModel.getVipEndDate().after(new Date())){
				challengeResultModel.setOverdue(false);
			}else {
				challengeResultModel.setOverdue(true);
			}
			challengeResultModel.setUserIsVip(userModel.getIsVip());
			challengeResultModel.setChallengeNickName(userModel2.getNickName());
			if(userModel2.getVipEndDate()!=null && userModel2.getVipEndDate().after(new Date())){
				challengeResultModel.setChallengeOverdue(false);
			}else {
				challengeResultModel.setChallengeOverdue(true);
			}
			/**
			 * 完成  挑战任务的时候更新 finishState
			 */
			if(baseResponse.getHttpCode()=="200"){
				//根据任务的   task_uuid 查找任务对象
				TaskRewardReceive uuidReceive = taskRewardReceiveServiceImpl.findByTaskUuidAndUserCode("63b804869f1a4500aebe0f8518272255",userModel.getUserCode());
				if(uuidReceive != null && uuidReceive.getFinishState()==TaskEnum.FinishState.NoFinish.getValue()){
					//修改任务  完成状态
					if(uuidReceive.getConcreteTaskCode().equals("challenge_task")){
						taskRewardReceiveServiceImpl.updateByUuidAndUserCode("63b804869f1a4500aebe0f8518272255", userModel.getUserCode());
					}
					
				}
			}
			baseResponse.setResult(challengeResultModel);
		} catch (Exception e) {
			logger.error("computeChallengeResult exception:{}", e);
			
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"computeChallengeResult Exception");
		}
		logger.debug("compute_challenge_result 计算挑战结果  ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	/**
	 * 获取挑战答题详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午4:36:56
	 * @param challengeRequestModel
	 * @return
	 */
	@RequestMapping(value="get_challenge_user_answer",method=RequestMethod.POST)
	public BaseResponse getChallengeUserAnswerDetail(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("challengeRequestModel:{}",gson.toJson(challengeRequestModel));
		BaseResponse baseResponse=new BaseResponse(requestId);
		if (challengeRequestModel.getUserFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserFightUUID");

		}
		if (challengeRequestModel.getChallengerCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerCode");

		}
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");

		}
		if (challengeRequestModel.getPaperUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUUID");

		}
		if (challengeRequestModel.getChallengerFightUUID() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "ChallengerFightUUID");

		}
		try {
			ChallengeUserAnswerModel challengeUserAnswerModel=questionServiceImpl.getChallengeAnswer(challengeRequestModel.getUserCode(), challengeRequestModel.getUserFightUUID(), challengeRequestModel.getChallengerCode(), challengeRequestModel.getChallengerFightUUID(), challengeRequestModel.getPaperUUID());
			baseResponse.setResult(challengeUserAnswerModel);
		} catch (Exception e) {
			logger.error("getChallengeUserAnswerDetail exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getChallengeUserAnswerDetail Exception");
		}
		logger.debug("get_challenge_user_answer 获取挑战答题详情  ："+gson.toJson(baseResponse));
		return baseResponse;
	}
	
	/**
	 * 校验挑战资格
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月4日 上午11:27:37
	 * @param requestId
	 * @param challengeRequestModel
	 * @return
	 */
	@RequestMapping(value="verify_eligibility",method=RequestMethod.POST)
	public BaseResponse verifyEligibility(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("challengeRequestModel:{}",gson.toJson(challengeRequestModel));
		if (challengeRequestModel.getUserCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode");
		}
		if (challengeRequestModel.getSubjectCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (challengeRequestModel.getGradeCode() == null) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		BaseResponse baseResponse=new BaseResponse();
		try {
			baseResponse.setResult(fightResultServiceImpl.verifyEligibility(challengeRequestModel.getUserCode(), challengeRequestModel.getSubjectCode(), challengeRequestModel.getGradeCode()));
		} catch (Exception e) {
			logger.error("verifyEligibility exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(requestId),	ResponseCode.SERVICE_ERROR.toString(),"getChallengeUserAnswerDetail Exception");
		}
		logger.debug("verify_eligibility 校验挑战资格返回："+gson.toJson(baseResponse));
		return baseResponse;
	}
}
