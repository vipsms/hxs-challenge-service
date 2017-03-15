package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/13
 * 任务奖励领取记录
 */
public class TaskRewardReceive {
    //主键
    private Long id;
    //唯一标识
    private String uuid;
    //用户code
    private String userCode;
    //任务类型
    private String taskType;//TaskEnum.TaskInfo
    //任务UUID
    private String taskUuid;
    //任务名称
    private String taskName;
    //具体任务名称
    private String concreteTaskCode;//TaskEnum.SpecificTasks
    //领取状态 0 未领取 1 已领取
    private Integer receiveState;
    //领取积分值
    private long rewardPoint;
    //领取时间
    private Date receiveDate;
    //更新时间
    private Date updateDate;

    //是否完成  1 已完成  0  未完成
    private Integer finishState;

	public Integer getFinishState() {
		return finishState;
	}

	public void setFinishState(Integer finishState) {
		this.finishState = finishState;
	}

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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskUuid() {
        return taskUuid;
    }

    public void setTaskUuid(String taskUuid) {
        this.taskUuid = taskUuid;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(Integer receiveState) {
        this.receiveState = receiveState;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getConcreteTaskCode() {
        return concreteTaskCode;
    }

    public void setConcreteTaskCode(String concreteTaskCode) {
        this.concreteTaskCode = concreteTaskCode;
    }

    public long getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(long rewardPoint) {
        this.rewardPoint = rewardPoint;
    }
}