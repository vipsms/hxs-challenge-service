package com.eeduspace.challenge.model.request;

import com.eeduspace.challenge.model.BaseResourceModel;

public class BattleRequestModel extends BaseResourceModel{
	private String battleType;
	private String userCode;
	private String paperUUID;
	private String fightUUID;
	private Long useTime;
	private Double score;
	private String otherUserCode;
	
	private String nickName;
	/**
	 * 对战方userCode
	 */
	private String battleUserCode;
	/**
	 * 是否与机器人对战  true是  false 否
	 */
	private Boolean isWithRobot;
	/**
	 * 分页开始位置
	 */
	private Long start;
	/**
	 * 分页显示条数
	 */
	private int item;
	
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public String getBattleType() {
		return battleType;
	}
	public void setBattleType(String battleType) {
		this.battleType = battleType;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
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
	public Long getUseTime() {
		return useTime;
	}
	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getOtherUserCode() {
		return otherUserCode;
	}
	public void setOtherUserCode(String otherUserCode) {
		this.otherUserCode = otherUserCode;
	}
	public Boolean getIsWithRobot() {
		return isWithRobot;
	}
	public void setIsWithRobot(Boolean isWithRobot) {
		this.isWithRobot = isWithRobot;
	}
	public String getBattleUserCode() {
		return battleUserCode;
	}
	public void setBattleUserCode(String battleUserCode) {
		this.battleUserCode = battleUserCode;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
