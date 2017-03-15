package com.eeduspace.challenge.persist.po;

import java.util.Date;

public class UserFeedBack {
	//主键
    private Long id;
    //唯一标识
    private String uuid;
    //用户标识
    private String userCode;
    //意见内容 
    private String message;
    //用户联系方式 
    private String contactInformation;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;
    //回复管理员 
    private String replyManagerid;
    //回复时间 
    private Date replyDate;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
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

    public String getReplyManagerid() {
        return replyManagerid;
    }

    public void setReplyManagerid(String replyManagerid) {
        this.replyManagerid = replyManagerid;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
}