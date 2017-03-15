package com.eeduspace.challenge.persist.po;

import java.util.Date;
/**
 * Author: dingran
 * Date: 2016/7/13
 * APP 更新记录
 */
public class AppUpdate {
    private Long id;
    //app 描述
    private String appDescribe;
    //app 名称
    private String appName;
    //app版本
    private Integer appVersion;
    //是否可用
    private Boolean available;
    //创建时间
    private Date createTime;
    //下载地址
    private String downUrl;
    //是否强制更新
    private Boolean necessary;
    //唯一标识
    private String uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppDescribe() {
        return appDescribe;
    }

    public void setAppDescribe(String appDescribe) {
        this.appDescribe = appDescribe;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public Boolean getNecessary() {
        return necessary;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}