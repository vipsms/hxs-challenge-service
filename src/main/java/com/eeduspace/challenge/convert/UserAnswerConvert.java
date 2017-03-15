package com.eeduspace.challenge.convert;

import com.eeduspace.challenge.enumeration.FightEnum;
import com.eeduspace.challenge.model.request.UserAnswerRequestModel;
import com.eeduspace.challenge.persist.po.UserAnswer;

public class UserAnswerConvert {
	public static UserAnswer toUserAnswer(UserAnswerRequestModel answerRequestModel){
		UserAnswer userAnswer=new UserAnswer();
		userAnswer.setFightRecordUuid(answerRequestModel.getFightRecordUuid());
		userAnswer.setIsRight(answerRequestModel.getIsRight());
		userAnswer.setPaperUuid(answerRequestModel.getPaperUuid());
		userAnswer.setQuestionSn(answerRequestModel.getQuestionSn());
		userAnswer.setFightType(FightEnum.getValue(answerRequestModel.getFightType()));
		userAnswer.setQuestionUuid(answerRequestModel.getQuestionUuid());
		userAnswer.setRightOption(answerRequestModel.getRightOption());
		userAnswer.setScore(answerRequestModel.getScore());
		userAnswer.setUserCode(answerRequestModel.getUserCode());
		userAnswer.setUserOption(answerRequestModel.getUserOption());
		userAnswer.setUseTime(answerRequestModel.getUseTime());
		return userAnswer;
	}
}
