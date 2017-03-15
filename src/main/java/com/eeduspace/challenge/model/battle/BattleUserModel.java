package com.eeduspace.challenge.model.battle;
/**
 * 对战用户model
 * @author zhuchaowei
 * 2016年7月19日
 * Description
 */
public class BattleUserModel {
	private String nickName;
	private String userCode;
	private Integer isVip;
	private String headImgUrl;
	private Long combatValue;
    private boolean overdue;//用户会员是否过期  true 已过期  false 未过期
    private Integer sex;
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
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
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Long getCombatValue() {
		return combatValue;
	}
	public void setCombatValue(Long combatValue) {
		this.combatValue = combatValue;
	}
}
