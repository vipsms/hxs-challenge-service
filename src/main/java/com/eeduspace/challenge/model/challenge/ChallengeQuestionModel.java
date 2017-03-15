package com.eeduspace.challenge.model.challenge;

import java.util.List;

import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.po.UserAnswer;

public class ChallengeQuestionModel {
	private List<QuestionEntity> questions;
	private String fightUUID;
	private List<UserAnswer> robotAnswers;
	public String getFightUUID() {
		return fightUUID;
	}
	public void setFightUUID(String fightUUID) {
		this.fightUUID = fightUUID;
	}
	public List<QuestionEntity> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionEntity> questions) {
		this.questions = questions;
	}
	public List<UserAnswer> getRobotAnswers() {
		return robotAnswers;
	}
	public void setRobotAnswers(List<UserAnswer> robotAnswers) {
		this.robotAnswers = robotAnswers;
	}
}
