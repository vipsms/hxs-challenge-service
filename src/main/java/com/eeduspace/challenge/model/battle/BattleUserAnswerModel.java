package com.eeduspace.challenge.model.battle;

import java.util.List;

import com.eeduspace.challenge.model.UserAnswerDeliaModel;

public class BattleUserAnswerModel {
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
