package com.eeduspace.challenge.mina.model;

import java.util.List;

public class ClientModel {
	/**
	 * 发送者userCode
	 */
	private String fromUserCode;
	/**
	 * 接收者UserCode
	 */
	private String toUserCode;
	/**
	 * 群发 接收者组群
	 */
	private List<String> toUserCodes;
	/**
	 * 消息内容
	 */
	private Object message;
	/**
	 * 消息类型
	 */
	private String messageType;
	public String getFromUserCode() {
		return fromUserCode;
	}
	public void setFromUserCode(String fromUserCode) {
		this.fromUserCode = fromUserCode;
	}
	public String getToUserCode() {
		return toUserCode;
	}
	public void setToUserCode(String toUserCode) {
		this.toUserCode = toUserCode;
	}
	public List<String> getToUserCodes() {
		return toUserCodes;
	}
	public void setToUserCodes(List<String> toUserCodes) {
		this.toUserCodes = toUserCodes;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}

}
