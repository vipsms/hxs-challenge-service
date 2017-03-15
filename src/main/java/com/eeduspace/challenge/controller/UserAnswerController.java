package com.eeduspace.challenge.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeduspace.challenge.convert.UserAnswerConvert;
import com.eeduspace.challenge.enumeration.FightEnum;
import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.request.UserAnswerRequestModel;
import com.eeduspace.challenge.persist.po.UserAnswer;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.UserAnswerService;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

/**
 * @author zhuchaowei 2016年7月18日 Description 用户答题记录控制
 */
@RestController
@RequestMapping("/user_answer")
public class UserAnswerController {
	@Autowired
	private UserAnswerService userAnswerServiceImpl;
	@Autowired
	private MessageUtil messageUtil;
	@Inject
	private RedisClientTemplate redisClientTemplate;
	private Gson gson=new Gson();
	private final Logger logger = LoggerFactory
			.getLogger(UserAnswerController.class);
	/**
	 * 保存答题记录 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年7月18日
	 * 下午3:30:21
	 * 
	 * @param userAnswerRequestModel
	 * @return
	 */
	@RequestMapping(value = "save_user_answer",method=RequestMethod.POST)
	public BaseResponse saveUserAnswer(@RequestBody UserAnswerRequestModel userAnswerRequestModel,@RequestParam(required=false) String requestId) {
		BaseResponse baseResponse = new BaseResponse();
		if(userAnswerRequestModel.getIsBattleWithRobot()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "IsBattleWithRobot"); 
		}
		if(userAnswerRequestModel.getUserCode()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode"); 
		}
		if(userAnswerRequestModel.getFightRecordUuid()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightRecordUuid"); 
		}
		if(userAnswerRequestModel.getFightType()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightType"); 
		}
		if(FightEnum.getEnum(userAnswerRequestModel.getFightType())==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_INVALID.toString(), "FightType error");
		}
		if(userAnswerRequestModel.getOtherUserCode()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "OtherUserCode"); 
		}
		if(userAnswerRequestModel.getIsRight()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "IsRight"); 
		}
		if(userAnswerRequestModel.getScore()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "Score"); 
		}
		if(userAnswerRequestModel.getUseTime()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UseTime"); 
		}
		if(userAnswerRequestModel.getQuestionUuid()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "QuestionUuid"); 
		}
		if(userAnswerRequestModel.getRightOption()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "RightOption"); 
		}
		if(userAnswerRequestModel.getPaperUuid()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUuid"); 
		}
		if(userAnswerRequestModel.getQuestionSn()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "QuestionSn"); 
		}
		logger.debug("userAnswerRequestModel{}:",gson.toJson(userAnswerRequestModel));
		
		try {
			UserAnswer userAnswer = UserAnswerConvert.toUserAnswer(userAnswerRequestModel);
			
			userAnswerServiceImpl.save(userAnswer);
			//给另一方发送消息 非机器人对战时发送消息
			if(!userAnswerRequestModel.getIsBattleWithRobot()){
				ClientModel clientModel=new ClientModel();
				clientModel.setFromUserCode(userAnswerRequestModel.getUserCode());
				clientModel.setMessage(userAnswerRequestModel);
				clientModel.setMessageType("hxs_answer_message");
				clientModel.setToUserCode(userAnswerRequestModel.getOtherUserCode());
				messageUtil.sendMessageToOne(clientModel);
			}
		} catch (Exception e) {
			logger.error("saveUserAnswer exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(),	ResponseCode.SERVICE_ERROR.toString(),"saveUserAnswer Exception");
		}
		logger.debug("save_user_answer 每道题答完返回："+gson.toJson(baseResponse));
		return baseResponse;
	}
	
	
	/**
	 * 保存挑战答题过程信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月27日 下午12:49:40
	 * @param userAnswerRequestModel
	 * @param requestId
	 * @return
	 */
	@RequestMapping(value = "save_challenge_answer",method=RequestMethod.POST)
	public BaseResponse saveChallengeAnswer(@RequestBody UserAnswerRequestModel userAnswerRequestModel,@RequestParam(required=false) String requestId) {
		BaseResponse baseResponse = new BaseResponse();
		if(userAnswerRequestModel.getUserCode()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "UserCode"); 
		}
		if(userAnswerRequestModel.getFightRecordUuid()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "IsBattleWithRobot"); 
		}
		if(userAnswerRequestModel.getFightType()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "FightType"); 
		}
		if(FightEnum.getEnum(userAnswerRequestModel.getFightType())==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_INVALID.toString(), "FightType error");
		}
		
		if(userAnswerRequestModel.getIsRight()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "IsRight"); 
		}
//		if(userAnswerRequestModel.getScore()==null){
//			return BaseResponse.setResponse(new BaseResponse(requestId),
//					ResponseCode.PARAMETER_MISS.toString(), "Score"); 
//		}
//		if(userAnswerRequestModel.getUseTime()==null){
//			return BaseResponse.setResponse(new BaseResponse(requestId),
//					ResponseCode.PARAMETER_MISS.toString(), "UseTime"); 
//		}
		if(userAnswerRequestModel.getQuestionUuid()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "QuestionUuid"); 
		}
		if(userAnswerRequestModel.getRightOption()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "RightOption"); 
		}
		if(userAnswerRequestModel.getPaperUuid()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "PaperUuid"); 
		}
		if(userAnswerRequestModel.getQuestionSn()==null){
			return BaseResponse.setResponse(new BaseResponse(requestId),
					ResponseCode.PARAMETER_MISS.toString(), "QuestionSn"); 
		}
		logger.debug("userAnswerRequestModel{}:",gson.toJson(userAnswerRequestModel));
		try {
			UserAnswer userAnswer = UserAnswerConvert.toUserAnswer(userAnswerRequestModel);
			userAnswerServiceImpl.save(userAnswer);
		} catch (Exception e) {
			logger.error("saveUserAnswer exception:{}", e);
			return BaseResponse.setResponse(new BaseResponse(),	ResponseCode.SERVICE_ERROR.toString(),"saveUserAnswer Exception");
		}
		logger.debug("save_challenge_answer 保存挑战答题过程信息  ："+gson.toJson(baseResponse));
		return baseResponse;
	}
}
