package com.eeduspace.challenge.model;

/**
 * Author: dingran
 * Date: 2016/7/29
 * Description:
 */
public class UserInnerModel {
    private String userCode;
    private int isVip=0;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }
}
