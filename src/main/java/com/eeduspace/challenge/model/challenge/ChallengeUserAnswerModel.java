package com.eeduspace.challenge.model.challenge;

import java.util.List;

import com.eeduspace.challenge.model.UserAnswerDeliaModel;

/**
 * 挑战用户答题信息详情
 * @author zhuchaowei
 * 2016年7月18日
 * Description
 */
public class ChallengeUserAnswerModel {
	List<UserAnswerDeliaModel> myAnswer;
	List<UserAnswerDeliaModel> otherAnswer;
	public List<UserAnswerDeliaModel> getMyAnswer() {
		return myAnswer;
	}
	public void setMyAnswer(List<UserAnswerDeliaModel> myAnswer) {
		this.myAnswer = myAnswer;
	}
	public List<UserAnswerDeliaModel> getOtherAnswer() {
		return otherAnswer;
	}
	public void setOtherAnswer(List<UserAnswerDeliaModel> otherAnswer) {
		this.otherAnswer = otherAnswer;
	}
	
}
