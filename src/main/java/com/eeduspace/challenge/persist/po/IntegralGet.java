package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/13
 * 表积分获取记录表
 */
public class IntegralGet {
    private Long id;
    //唯一标识
    private String uuid;
    //用户code
    private String userCode;
    //奖励名称
    private String rewardName;
    //奖励积分
    private Long rewardIntegral;
    //奖励类型  RewardEnum.RewardType  体力值、签到、任务、对战、挑战
    private String rewardType;
    //奖励来源UUID
    private String rewardSourceUuid;
    //创建时间
    private Date createDate;
    //更新时间
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

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }


    public Long getRewardIntegral() {
		return rewardIntegral;
	}

	public void setRewardIntegral(Long rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardSourceUuid() {
        return rewardSourceUuid;
    }

    public void setRewardSourceUuid(String rewardSourceUuid) {
        this.rewardSourceUuid = rewardSourceUuid;
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
}