package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * 任务规则表
 */
public class TaskRule {
    //主键
    private Long id;
    //唯一标识
    private String uuid;
    //任务信息UUID
    private String concreteUuid;
    //奖励积分
    private Double rewardPoint;
    //完成次数
    private Double completionTimes;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;
    //VIP额外奖励积分
    private Double vipRewardPoint;

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

    public String getConcreteUuid() {
        return concreteUuid;
    }

    public void setConcreteUuid(String concreteUuid) {
        this.concreteUuid = concreteUuid;
    }

    public Double getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Double rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public Double getCompletionTimes() {
        return completionTimes;
    }

    public void setCompletionTimes(Double completionTimes) {
        this.completionTimes = completionTimes;
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

    public Double getVipRewardPoint() {
        return vipRewardPoint;
    }

    public void setVipRewardPoint(Double vipRewardPoint) {
        this.vipRewardPoint = vipRewardPoint;
    }
}