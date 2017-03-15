package com.eeduspace.challenge.model.request;

import java.io.Serializable;


public class BaseRequestBody implements Serializable{
	private static final long serialVersionUID = 1L;
	private String access_key;	
	private String action;	
	private long timestamp=System.currentTimeMillis();	
	private String bodyMD5;
	private String signature;
	private String token;
	private String requestId;
	
	
	public BaseRequestBody(String access_key, String action,
			String bodyMD5, String signature, String token, String requestId) {
		super();
		this.access_key = access_key;
		this.action = action;
		this.bodyMD5 = bodyMD5;
		this.signature = signature;
		this.token = token;
		this.requestId = requestId;
	}
	
	public BaseRequestBody(String action, String bodyMD5,
			String requestId) {
		super();
		this.action = action;
		this.bodyMD5 = bodyMD5;
		this.requestId = requestId;
	}

	public String getAccess_key() {
		return access_key;
	}
	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getBodyMD5() {
		return bodyMD5;
	}
	public void setBodyMD5(String bodyMD5) {
		this.bodyMD5 = bodyMD5;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
}
