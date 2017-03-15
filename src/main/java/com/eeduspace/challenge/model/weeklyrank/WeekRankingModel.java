package com.eeduspace.challenge.model.weeklyrank;
/**
 * 挑战排行榜榜单信息
 * @author zhuchaowei
 * 2016年7月25日
 * Description
 */
public class WeekRankingModel {
	private Long championTimes;
	private Long weekFightValueFloat;
	private String headImgUrl;
	private String nickName;
	private String userCode;
	private String currentRank;
	/**
	 * 是否可挑战 false 不可挑战  true 可挑战
	 */
	private Boolean isChallenge=false;
	private String mobile;
	private Integer isVip;
	private Boolean overdue;
	
	private Long ranking;//排名
	private Integer stamina;
	public Integer getStamina() {
		return stamina;
	}
	public void setStamina(Integer stamina) {
		this.stamina = stamina;
	}
	public Long getRanking() {
		return ranking;
	}
	public void setRanking(Long ranking) {
		this.ranking = ranking;
	}
	public Boolean getOverdue() {
		return overdue;
	}
	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getWeekFightValueFloat() {
		return weekFightValueFloat;
	}
	public void setWeekFightValueFloat(Long weekFightValueFloat) {
		this.weekFightValueFloat = weekFightValueFloat;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCurrentRank() {
		return currentRank;
	}
	public void setCurrentRank(String currentRank) {
		this.currentRank = currentRank;
	}
	public Boolean getIsChallenge() {
		return isChallenge;
	}
	public void setIsChallenge(Boolean isChallenge) {
		this.isChallenge = isChallenge;
	}
	public Long getChampionTimes() {
		return championTimes;
	}
	public void setChampionTimes(Long championTimes) {
		this.championTimes = championTimes;
	}
	
}
