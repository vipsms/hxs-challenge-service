package com.eeduspace.challenge.model.request;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
public class TaskRequestModel {
    private String userCode;
    private String taskRewardReceiveUuid;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTaskRewardReceiveUuid() {
        return taskRewardReceiveUuid;
    }

    public void setTaskRewardReceiveUuid(String taskRewardReceiveUuid) {
        this.taskRewardReceiveUuid = taskRewardReceiveUuid;
    }
}
