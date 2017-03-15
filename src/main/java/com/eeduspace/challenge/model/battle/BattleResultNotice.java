package com.eeduspace.challenge.model.battle;

public class BattleResultNotice {
	/**
	 * 对战记录UUID
	 */
	private String fightUUID;
	/**
	 * 通知接收者code
	 */
	private String receiver;
	/**
	 * 对站结果      0 失败  1 胜利  2 平局
	 */
	private Integer fightResult;
	/**
	 * 对战日期
	 */
	private String fightDate;
	/**
	 * 获得战斗力值
	 */
	private Long fightValue;
	/**
	 * 获得积分
	 */
	private Long integral;
	/**
	 * 对手昵称
	 */
	private String opponentNickName;
	/**
	 * 对手用户code
	 */
	private String opponentUserCode;
	
	/**
	 * 单元Code
	 * @return
	 */
	private String unitCode;
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getFightUUID() {
		return fightUUID;
	}
	public void setFightUUID(String fightUUID) {
		this.fightUUID = fightUUID;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Integer getFightResult() {
		return fightResult;
	}
	public void setFightResult(Integer fightResult) {
		this.fightResult = fightResult;
	}
	public String getFightDate() {
		return fightDate;
	}
	public void setFightDate(String fightDate) {
		this.fightDate = fightDate;
	}
	public Long getFightValue() {
		return fightValue;
	}
	public void setFightValue(Long fightValue) {
		this.fightValue = fightValue;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public String getOpponentNickName() {
		return opponentNickName;
	}
	public void setOpponentNickName(String opponentNickName) {
		this.opponentNickName = opponentNickName;
	}
	public String getOpponentUserCode() {
		return opponentUserCode;
	}
	public void setOpponentUserCode(String opponentUserCode) {
		this.opponentUserCode = opponentUserCode;
	}
	
}
