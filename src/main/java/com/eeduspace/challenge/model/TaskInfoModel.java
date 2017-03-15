package com.eeduspace.challenge.model;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/8/2
 * Description:
 */
public class TaskInfoModel {

    private Long id;

    private String uuid;
    //任务名称
    private String taskName;
    //任务类型 TaskEnum.TaskInfo 新手任务 每日任务 成长任务
    private String taskType;
    //任务描述
    private String taskDescribe;
    //具体任务UUID  	表concrete_task 的UUID
    private String concreteTaskUuid;
    //任务创建者
    private String taskCreator;
    //任务创建者code
    private String taskCreatorCode;
    //具体任务code 	表concrete_task 的concreteTaskCode
    private String concreteTaskCode;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;
    //奖励积分
    private long rewardPoint;
    //完成次数
    private long completionTimes;
    //VIP额外奖励积分
    private long vipRewardPoint;

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe;
    }

    public String getConcreteTaskUuid() {
        return concreteTaskUuid;
    }

    public void setConcreteTaskUuid(String concreteTaskUuid) {
        this.concreteTaskUuid = concreteTaskUuid;
    }

    public String getTaskCreator() {
        return taskCreator;
    }

    public void setTaskCreator(String taskCreator) {
        this.taskCreator = taskCreator;
    }

    public String getTaskCreatorCode() {
        return taskCreatorCode;
    }

    public void setTaskCreatorCode(String taskCreatorCode) {
        this.taskCreatorCode = taskCreatorCode;
    }

    public String getConcreteTaskCode() {
        return concreteTaskCode;
    }

    public void setConcreteTaskCode(String concreteTaskCode) {
        this.concreteTaskCode = concreteTaskCode;
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

    public long getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(long rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public long getCompletionTimes() {
        return completionTimes;
    }

    public void setCompletionTimes(long completionTimes) {
        this.completionTimes = completionTimes;
    }

    public long getVipRewardPoint() {
        return vipRewardPoint;
    }

    public void setVipRewardPoint(long vipRewardPoint) {
        this.vipRewardPoint = vipRewardPoint;
    }
}
