package com.eeduspace.challenge.model;

import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.persist.po.User;

/**
 * Author: dingran
 * Date: 2016/7/21
 * Description:
 */
public class IntegralChangeModel {
    private User user;
    private Long rewardIntegral;
    private RewardEnum.ChangeType changeType;
    private RewardEnum.RewardType rewardType;
    private String rewardSourceUuid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public RewardEnum.ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(RewardEnum.ChangeType changeType) {
        this.changeType = changeType;
    }

    public RewardEnum.RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardEnum.RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardSourceUuid() {
        return rewardSourceUuid;
    }

    public void setRewardSourceUuid(String rewardSourceUuid) {
        this.rewardSourceUuid = rewardSourceUuid;
    }

	public Long getRewardIntegral() {
		return rewardIntegral;
	}

	public void setRewardIntegral(Long rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}
}
