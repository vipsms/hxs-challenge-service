package com.eeduspace.challenge.model.request;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
public class UserNoticeRequestModel {
    private String userCode;
    private String noticeUuid;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNoticeUuid() {
        return noticeUuid;
    }

    public void setNoticeUuid(String noticeUuid) {
        this.noticeUuid = noticeUuid;
    }
}
