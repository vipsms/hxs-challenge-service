package com.eeduspace.challenge.model;

import java.util.Date;

public class UserSignModel {
	//主键
    private Long id;
    //唯一标识
    private String uuid;
    //用户标识
    private String userCode;
    //签到时间
    private Date signDate;
    //获取积分
    private Long integral;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;
    //签到次数
    private long signTimes;

    private boolean isTodeaySign=false ;
        
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getSignTimes() {
		return signTimes;
	}

	public void setSignTimes(long signTimes) {
		this.signTimes = signTimes;
	}

	public boolean getIsTodeaySign() {
		return isTodeaySign;
	}

	public void setIsTodeaySign(boolean isTodeaySign) {
		this.isTodeaySign = isTodeaySign;
	}


    
    
    
    
	
}
