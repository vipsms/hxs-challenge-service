package com.eeduspace.challenge.model;

/**
 * 用户答题详情model
 * @author zhuchaowei
 * 2016年7月14日
 * Description
 */
public class UserAnswerDeliaModel {
	private String userCode;
	/**
	 * 是否正确
	 */
    private Boolean isRight;
    /**
     * 得分
     */
    private Double score;
    /**
     * 单题用时
     */
    private Double useTime;
    /**
     * 试题uuid
     */
    private String questionUuid;
    /**
     * 用户选项
     */
    private String userOption;
    /**
     * 正确选项
     */
    private String rightOption;
    /**
     * 卷子uuid
     */
    private String paperUuid;
    /**
     * 试题序号
     */
    private String questionSn;
    
    /**
     * 题干信息
     */
    private String questionStem;
    /**
     * 选项信息
     */
    private String quesionOption;

    /**
     * 解析内容
     */
    private String questionAnalysis;

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

	public Double getUseTime() {
		return useTime;
	}

	public void setUseTime(Double useTime) {
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

	public String getQuestionStem() {
		return questionStem;
	}

	public void setQuestionStem(String questionStem) {
		this.questionStem = questionStem;
	}

	public String getQuesionOption() {
		return quesionOption;
	}

	public void setQuesionOption(String quesionOption) {
		this.quesionOption = quesionOption;
	}

	public String getQuestionAnalysis() {
		return questionAnalysis;
	}

	public void setQuestionAnalysis(String questionAnalysis) {
		this.questionAnalysis = questionAnalysis;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
