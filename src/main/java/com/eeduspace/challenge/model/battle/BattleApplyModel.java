package com.eeduspace.challenge.model.battle;

import com.eeduspace.challenge.model.BaseResourceModel;

/**
 * 对战申请model
 * @author zhuchaowei
 * 2016年8月1日
 * Description
 */
public class BattleApplyModel extends BaseResourceModel{
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 是否同意
	 */
	private Boolean isAgree;
	
	private String nickName;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Boolean getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
