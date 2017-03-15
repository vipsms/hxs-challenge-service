package com.eeduspace.challenge.model;

import com.eeduspace.challenge.persist.po.User;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/8/2
 * Description:
 */
public class TaskRewardReceiveModel {

    //唯一标识
    private String uuid;
    //用户code
    private String userCode;
    //任务类型
    private String taskType;//TaskEnum.TaskInfo
    // 具体任务类型
    private String concreteTaskCode;//TaskEnum.SpecificTasks
    //任务UUID
    private Date taskUuid;
    //任务名称
    private String taskName;
    //领取状态 0 未领取 1 已领取
    private Integer receiveState;
    //领取时间
    private Date receiveDate;
    //更新时间
    private Date updateDate;
    //用户
    private User user;

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

    public String getConcreteTaskCode() {
        return concreteTaskCode;
    }

    public void setConcreteTaskCode(String concreteTaskCode) {
        this.concreteTaskCode = concreteTaskCode;
    }

    public Date getTaskUuid() {
        return taskUuid;
    }

    public void setTaskUuid(Date taskUuid) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
