package com.eeduspace.challenge.model.request;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:
 */
public class LoginRequestModel {
    //扫码登录时所需的公钥验证码
    private String accessKey;
    //设备标识
    private String televisionCode;
    //用户code
    private String userCode;
    //密码
    private String password;
    //手机号
    private String mobile;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getTelevisionCode() {
        return televisionCode;
    }

    public void setTelevisionCode(String televisionCode) {
        this.televisionCode = televisionCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
