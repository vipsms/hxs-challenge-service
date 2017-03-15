package com.eeduspace.challenge.model.request;

public class UserAnswerRequestModel {
	private Boolean isBattleWithRobot;
    private String userCode;
    private String fightRecordUuid;
    private String fightType;
    private Boolean isRight;
    private Double score;
    private Long useTime;
    private String questionUuid;
    private String userOption;
    private String rightOption;
    private String paperUuid;
    private String questionSn;
    private String otherUserCode;
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getFightRecordUuid() {
		return fightRecordUuid;
	}

	public void setFightRecordUuid(String fightRecordUuid) {
		this.fightRecordUuid = fightRecordUuid;
	}

	public String getFightType() {
		return fightType;
	}

	public void setFightType(String fightType) {
		this.fightType = fightType;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	public String getQuestionUuid() {
		return questionUuid;
	}

	public void setQuestionUuid(String questionUuid) {
		this.questionUuid = questionUuid;
	}

	public String getUserOption() {
		return userOption;
	}

	public void setUserOption(String userOption) {
		this.userOption = userOption;
	}

	public String getRightOption() {
		return rightOption;
	}

	public void setRightOption(String rightOption) {
		this.rightOption = rightOption;
	}

	public String getPaperUuid() {
		return paperUuid;
	}

	public void setPaperUuid(String paperUuid) {
		this.paperUuid = paperUuid;
	}

	public String getQuestionSn() {
		return questionSn;
	}

	public void setQuestionSn(String questionSn) {
		this.questionSn = questionSn;
	}

	public String getOtherUserCode() {
		return otherUserCode;
	}

	public void setOtherUserCode(String otherUserCode) {
		this.otherUserCode = otherUserCode;
	}

	public Boolean getIsBattleWithRobot() {
		return isBattleWithRobot;
	}

	public void setIsBattleWithRobot(Boolean isBattleWithRobot) {
		this.isBattleWithRobot = isBattleWithRobot;
	}
    
}
