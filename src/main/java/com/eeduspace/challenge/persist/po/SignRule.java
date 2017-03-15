package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/13
 * 用户签到规则
 */
public class SignRule {
    private Long id;

    private String uuid;
    //连续签到次数
    private Long signTimes;
    //用户奖励积分值
    private Long rewardPoints;
    //VIP用户奖励积分值
    private Long vipRewardPoints;

//    private boolean isRandom;//是否随机
//
//    private Double minRewardPoint;//随机最小值
//
//    private Double maxRewardPoint;//随机最大值

    private Date createDate;

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

	public Long getSignTimes() {
		return signTimes;
	}

	public void setSignTimes(Long signTimes) {
		this.signTimes = signTimes;
	}

	public Long getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Long rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Long getVipRewardPoints() {
		return vipRewardPoints;
	}

	public void setVipRewardPoints(Long vipRewardPoints) {
		this.vipRewardPoints = vipRewardPoints;
	}
    

}