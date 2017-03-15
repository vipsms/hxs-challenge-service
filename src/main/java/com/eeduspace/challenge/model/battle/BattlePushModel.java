package com.eeduspace.challenge.model.battle;

import com.eeduspace.challenge.model.BaseResourceModel;

public class BattlePushModel extends BaseResourceModel{
	private String paperUUID;
	private Long userCurrentRank;
	private String fightUUID;
	/**
	 * 是否匹配到对手  true 是 false 否
	 */
	private Boolean isMatch;
	/**
	 * 对战另一方用户信息
	 */
	private BattleUserModel battleUserModel;
	public String getPaperUUID() {
		return paperUUID;
	}
	public void setPaperUUID(String paperUUID) {
		this.paperUUID = paperUUID;
	}
	public String getFightUUID() {
		return fightUUID;
	}
	public void setFightUUID(String fightUUID) {
		this.fightUUID = fightUUID;
	}
	public Long getUserCurrentRank() {
		return userCurrentRank;
	}
	public void setUserCurrentRank(Long userCurrentRank) {
		this.userCurrentRank = userCurrentRank;
	}
	public BattleUserModel getBattleUserModel() {
		return battleUserModel;
	}
	public void setBattleUserModel(BattleUserModel battleUserModel) {
		this.battleUserModel = battleUserModel;
	}
	public Boolean getIsMatch() {
		return isMatch;
	}
	public void setIsMatch(Boolean isMatch) {
		this.isMatch = isMatch;
	}
}
