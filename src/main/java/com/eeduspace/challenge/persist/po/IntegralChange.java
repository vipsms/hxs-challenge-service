package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/13
 * 积分兑换记录
 */
public class IntegralChange {
    private Long id;
    //唯一标识
    private String uuid;
    //消耗积分
    private Long consumeIntegral;
    //用户code
    private String userCode;
    //兑换类型 RewardEnum.RewardType  体力值、签到、任务、对战、挑战
    private String changeType;
    //兑换名称
    private String changeName;
    //创建时间
    private Date createDate;
    //修改时间
    private Date updateDate;

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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
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

	public Long getConsumeIntegral() {
		return consumeIntegral;
	}

	public void setConsumeIntegral(Long consumeIntegral) {
		this.consumeIntegral = consumeIntegral;
	}
}