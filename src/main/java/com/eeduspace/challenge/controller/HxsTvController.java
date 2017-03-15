package com.eeduspace.challenge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.request.ChallengeRequestModel;
import com.eeduspace.challenge.model.weeklyrank.WeekRankingModel;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.google.gson.Gson;

/**
 * @author liuxin
 * Date: 2016/9/8
 * 好学生TV接口
 */
@Controller
@RequestMapping("/hxsTV")
public class HxsTvController {
	
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;
	
	@Autowired
	private UserService userServiceImpl;
	
	private Gson gson = new Gson();
	
	private final Logger logger = LoggerFactory.getLogger(HxsTvController.class);
	
	private static final Integer showNum = 12;
	
	/**
	 * 好学生TV全国排行榜 前十二名
	 * @param requestId
	 * @param challengeRequestModel
	 */
	@RequestMapping(value = "/getNationalTop", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getNationalTop(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("getNationalTop:{}", gson.toJson(challengeRequestModel));
		String subjectCode = challengeRequestModel.getSubjectCode();
		String gradeCode = challengeRequestModel.getGradeCode();
		String mobile = challengeRequestModel.getMobile();
		if (StringUtils.isEmpty(subjectCode)) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (StringUtils.isEmpty(gradeCode)) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subjectCode", subjectCode);
		params.put("gradeCode", gradeCode);
		params.put("showNum", showNum);
		List<WeekRankingModel> rankingList = weeklyRankingServiceImpl.getHxsTvNationalRank(params);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("mobile", mobile);
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		WeekRankingModel userRanking = weeklyRankingServiceImpl.findUserNationalRankingTv(queryMap);
		if(!StringUtils.isEmpty(mobile)){
			UserModel user = userServiceImpl.findUserInfoByMobile(mobile);
			if(userRanking != null){
				Long countCurrentRank = weeklyRankingServiceImpl.countCurrentRank(user.getUserCode(), subjectCode, gradeCode);
				userRanking.setRanking(countCurrentRank);
			}else{
				userRanking = new WeekRankingModel();
				userRanking.setHeadImgUrl(user.getHeadImgUrl());
				userRanking.setIsVip(user.getIsVip());
				userRanking.setNickName(user.getNickName());
				userRanking.setUserCode(user.getUserCode());
				userRanking.setMobile(user.getMobile());
			}
		}
		BaseResponse baseResponse = new BaseResponse(requestId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("user", userRanking);
		resultMap.put("rankingList", rankingList);
		baseResponse.setResult(resultMap);
		return baseResponse;
	}
	
	/**
	 * 好学生TV好友排行榜 前十二名
	 * @param requestId
	 * @param challengeRequestModel
	 */
	@RequestMapping(value = "/getFriendTop", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse getFriendTop(@RequestParam(required=false) String requestId,@RequestBody ChallengeRequestModel challengeRequestModel){
		logger.debug("getFriendTop:{}", gson.toJson(challengeRequestModel));
		String subjectCode = challengeRequestModel.getSubjectCode();
		String gradeCode = challengeRequestModel.getGradeCode();
		String mobile = challengeRequestModel.getMobile();
		if (StringUtils.isEmpty(subjectCode)) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
		}
		if (StringUtils.isEmpty(gradeCode)) {
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subjectCode", subjectCode);
		params.put("gradeCode", gradeCode);
		params.put("showNum", showNum);
		params.put("mobile", mobile);
		List<WeekRankingModel> rankingList = weeklyRankingServiceImpl.getHxsTvFriendRank(params);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("mobile", mobile);
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		WeekRankingModel userRanking = weeklyRankingServiceImpl.findUserNationalRankingTv(queryMap);
		if(!StringUtils.isEmpty(mobile)){
			UserModel user = userServiceImpl.findUserInfoByMobile(mobile);
			if(userRanking != null){
				Long countCurrentRank = weeklyRankingServiceImpl.countCurrentRankInFriend(user.getUserCode(), subjectCode, gradeCode);
				userRanking.setRanking(countCurrentRank);
			}else{
				userRanking = new WeekRankingModel();
				userRanking.setHeadImgUrl(user.getHeadImgUrl());
				userRanking.setIsVip(user.getIsVip());
				userRanking.setNickName(user.getNickName());
				userRanking.setUserCode(user.getUserCode());
				userRanking.setMobile(user.getMobile());
			}
		}
		BaseResponse baseResponse = new BaseResponse();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("user", userRanking);
		resultMap.put("friendRankList", rankingList);
		baseResponse.setResult(resultMap);
		return baseResponse;
	}
}
