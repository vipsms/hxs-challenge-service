package com.eeduspace.challenge.model.battle;
/**
 * 对战结果model
 * @author zhuchaowei
 * 2016年7月13日
 * Description
 */
public class BattleResultModel {
	/**
	 * 获取战斗力值
	 */
	private String obtainFightValue;
	/**
	 * 用户Acode
	 */
	private String userCode;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	private String currentRank;
	
	/**
	 * 对战结果       0 失败
				1 胜利
				2平局
	 */
	private Integer battleResult;

	/**
	 * 获得积分
	 */
	private Long integral;
	/**
	 * 用户答题用时
	 */
	private Long userUseTime;
	/**
	 * 用户得分
	 */
	private Double userGetScore;
	/**
	 * 战后排名
	 */
	private Long fightedRank;
	private String headImgUrl;
	private Boolean isVip;
	/**
	 * 获得战斗力值
	 */
	private Long fightValue;
	private Boolean overdue;
	
	
	/**
	 * 应app需求，添加四个字段：
	 * 
	 * 	gradeCode subjectCode  unitCode pagerCode
	 * 
	 * @author  作者 : gaofengming
			E-mail : gaofengming@e-eduspace.com
	 * @date 创建时间   ：2016年8月19日上午11:46:11  
	 */
	private String gradeCode;
	private String subjectCode;
	private String unitCode;
//	private String paperUUID;
	private String pagerCode;//对应的库表之中的是  paperUUID 字段
	
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getPagerCode() {
		return pagerCode;
	}
	public void setPagerCode(String pagerCode) {
		this.pagerCode = pagerCode;
	}
	public Boolean getOverdue() {
		return overdue;
	}
	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public Long getUserUseTime() {
		return userUseTime;
	}
	public void setUserUseTime(Long userUseTime) {
		this.userUseTime = userUseTime;
	}
	public Double getUserGetScore() {
		return userGetScore;
	}
	public void setUserGetScore(Double userGetScore) {
		this.userGetScore = userGetScore;
	}
	public Long getFightedRank() {
		return fightedRank;
	}
	public void setFightedRank(Long fightedRank) {
		this.fightedRank = fightedRank;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCurrentRank() {
		return currentRank;
	}
	public void setCurrentRank(String currentRank) {
		this.currentRank = currentRank;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Boolean getIsVip() {
		return isVip;
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}
	
	public Long getFightValue() {
		return fightValue;
	}
	public void setFightValue(Long fightValue) {
		this.fightValue = fightValue;
	}
	public Integer getBattleResult() {
		return battleResult;
	}
	public void setBattleResult(Integer battleResult) {
		this.battleResult = battleResult;
	}
	public String getObtainFightValue() {
		return obtainFightValue;
	}
	public void setObtainFightValue(String obtainFightValue) {
		this.obtainFightValue = obtainFightValue;
	}
	
	
}
