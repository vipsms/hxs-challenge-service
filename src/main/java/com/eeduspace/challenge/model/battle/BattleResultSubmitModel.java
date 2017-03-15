package com.eeduspace.challenge.model.battle;
/**
 * 对战结果提交返回类
 * @author zhuchaowei
 * 2016年7月18日
 * Description
 */
public class BattleResultSubmitModel {
	/**
	 * 对战记录UUID
	 */
	private String fightUUID;
	/**
	 * 是否双方完成答题
	 */
	private Boolean isFinish;
	/**
	 * 对战是否结束 true 结束 false 未结束
	 */
	private Boolean isEnd=false;
	public String getFightUUID() {
		return fightUUID;
	}
	public void setFightUUID(String fightUUID) {
		this.fightUUID = fightUUID;
	}
	public Boolean getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}
	public Boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
}
