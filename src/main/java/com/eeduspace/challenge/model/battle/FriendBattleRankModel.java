package com.eeduspace.challenge.model.battle;

import java.util.Date;

/**
 * 好友对战排行榜返回实体
 * @author zhuchaowei
 * 2016年8月1日
 * Description
 */
public class FriendBattleRankModel {
	private String nickName;
	private String headImgUrl;
	private Integer userOnlineState;
	private Long fightValue;
	private String userCode;
	private Long rank;
	private Integer isVip;
	private Date vipStartDate;
	private Date vipEndDate;
	private Long weekFightValue;//需要显示动态的数据
	
	/**
	 * VIP是否过期
	 */
	private Boolean isExpired=true;
	
	public Long getWeekFightValue() {
		return weekFightValue;
	}
	public void setWeekFightValue(Long weekFightValue) {
		this.weekFightValue = weekFightValue;
	}
	public Date getVipStartDate() {
		return vipStartDate;
	}
	public void setVipStartDate(Date vipStartDate) {
		this.vipStartDate = vipStartDate;
	}
	public Date getVipEndDate() {
		return vipEndDate;
	}
	public void setVipEndDate(Date vipEndDate) {
		this.vipEndDate = vipEndDate;
	}
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	public Integer getUserOnlineState() {
		return userOnlineState;
	}
	public void setUserOnlineState(Integer userOnlineState) {
		this.userOnlineState = userOnlineState;
	}
	public Long getFightValue() {
		return fightValue;
	}
	public void setFightValue(Long fightValue) {
		this.fightValue = fightValue;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public Boolean getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}
	
}
