package com.eeduspace.challenge.model.challenge;


/**
 * 挑战结果model
 * @author zhuchaowei
 * 2016年7月13日
 * Description
 */
public class ChallengeResultModel {
	/**
	 * 用户code
	 */
	private String userCode;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 被挑战者code
	 */
	private String challengerCode;
	/**
	 * 被挑战者名称
	 */
	private String challengerName;
	/**
	 * 挑战结果       0 失败
				1 胜利
				2平局
	 */
	private Integer challengeResult;
	/**
	 * 获得战斗力值
	 */
	private Long combatValue=0l;
	/**
	 * 获得积分
	 */
	private Long integral=0l;
	/**
	 * 用户答题用时
	 */
	private Long userUseTime;
	/**
	 * 被挑战者用时
	 */
	private Long challengerUseTime;
	/**
	 * 用户得分
	 */
	private Double userGetScore;
	/**
	 * 被挑战者得分
	 */
	private Double challerGetScore;
	/**
	 * 战后排名
	 */
	private Long fightedRank;
	/**
	 * 自己头像
	 */
	private String myHeadImgUrl;
	/**
	 * 被挑战者头像
	 */
	private String challengerHeadImgUrl;
	/**
	 * 自己是否VIP
	 */
	private Integer userIsVip;
	/**
	 * 被挑战者是否VIP
	 */
	private Integer challengerIsVip;
	/**
	 * 用户总战斗力
	 */
	private Long userTotalCombatValue;
	/**
	 * 被挑战者总战斗力
	 */
	private Long challengerTotalCombatCalue;
	private String nickName;
	private boolean overdue;
	private String challengeNickName;
	private boolean challengeOverdue;
	
	public String getChallengeNickName() {
		return challengeNickName;
	}
	public void setChallengeNickName(String challengeNickName) {
		this.challengeNickName = challengeNickName;
	}
	public boolean isChallengeOverdue() {
		return challengeOverdue;
	}
	public void setChallengeOverdue(boolean challengeOverdue) {
		this.challengeOverdue = challengeOverdue;
	}
	public boolean isOverdue() {
		return overdue;
	}
	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
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
	public Long getUserTotalCombatValue() {
		return userTotalCombatValue;
	}
	public void setUserTotalCombatValue(Long userTotalCombatValue) {
		this.userTotalCombatValue = userTotalCombatValue;
	}
	public Long getChallengerTotalCombatCalue() {
		return challengerTotalCombatCalue;
	}
	public void setChallengerTotalCombatCalue(Long challengerTotalCombatCalue) {
		this.challengerTotalCombatCalue = challengerTotalCombatCalue;
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
	public String getChallengerCode() {
		return challengerCode;
	}
	public void setChallengerCode(String challengerCode) {
		this.challengerCode = challengerCode;
	}
	public String getChallengerName() {
		return challengerName;
	}
	public void setChallengerName(String challengerName) {
		this.challengerName = challengerName;
	}
	public Integer getChallengeResult() {
		return challengeResult;
	}
	public void setChallengeResult(Integer challengeResult) {
		this.challengeResult = challengeResult;
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
	public Long getChallengerUseTime() {
		return challengerUseTime;
	}
	public void setChallengerUseTime(Long challengerUseTime) {
		this.challengerUseTime = challengerUseTime;
	}
	public Double getUserGetScore() {
		return userGetScore;
	}
	public void setUserGetScore(Double userGetScore) {
		this.userGetScore = userGetScore;
	}
	public Double getChallerGetScore() {
		return challerGetScore;
	}
	public void setChallerGetScore(Double challerGetScore) {
		this.challerGetScore = challerGetScore;
	}
	public Long getFightedRank() {
		return fightedRank;
	}
	public void setFightedRank(Long fightedRank) {
		this.fightedRank = fightedRank;
	}
	public Long getCombatValue() {
		return combatValue;
	}
	public void setCombatValue(Long combatValue) {
		this.combatValue = combatValue;
	}
	public String getMyHeadImgUrl() {
		return myHeadImgUrl;
	}
	public void setMyHeadImgUrl(String myHeadImgUrl) {
		this.myHeadImgUrl = myHeadImgUrl;
	}
	public String getChallengerHeadImgUrl() {
		return challengerHeadImgUrl;
	}
	public void setChallengerHeadImgUrl(String challengerHeadImgUrl) {
		this.challengerHeadImgUrl = challengerHeadImgUrl;
	}
	public Integer getUserIsVip() {
		return userIsVip;
	}
	public void setUserIsVip(Integer userIsVip) {
		this.userIsVip = userIsVip;
	}
	public Integer getChallengerIsVip() {
		return challengerIsVip;
	}
	public void setChallengerIsVip(Integer challengerIsVip) {
		this.challengerIsVip = challengerIsVip;
	}
	
}
