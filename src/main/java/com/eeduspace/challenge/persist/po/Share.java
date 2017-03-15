package com.eeduspace.challenge.persist.po;

import java.util.Date;


public class Share {
    private Long id;
    //创建时间
	private Date creatDate;
	//唯一标识
	private String uuid;
    //事件类型：对战挑战
	private String eventType;
    //分享类型：QQ,朋友圈
	private String shareType;
    //设备来源
	private String equipmentsource;
	//用户标识
	private String userCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getEquipmentsource() {
		return equipmentsource;
	}

	public void setEquipmentsource(String equipmentsource) {
		this.equipmentsource = equipmentsource;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	
}