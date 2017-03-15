package com.eeduspace.challenge.persist.po;

import java.util.Date;
/**
 * Author: dingran
 * Date: 2016/7/13
 * 具体任务表
 */
public class ConcreteTask {
    private Long id;
    //唯一标识
    private String uuid;
    //具体任务名称
    private String concreteTaskName;
    //具体任务code TaskEnum.SpecificTasks
    private String concreteTaskCode;
    //具体任务描述
    private String concreteTaskDesc;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;

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

    public String getConcreteTaskName() {
        return concreteTaskName;
    }

    public void setConcreteTaskName(String concreteTaskName) {
        this.concreteTaskName = concreteTaskName;
    }

    public String getConcreteTaskCode() {
        return concreteTaskCode;
    }

    public void setConcreteTaskCode(String concreteTaskCode) {
        this.concreteTaskCode = concreteTaskCode;
    }

    public String getConcreteTaskDesc() {
        return concreteTaskDesc;
    }

    public void setConcreteTaskDesc(String concreteTaskDesc) {
        this.concreteTaskDesc = concreteTaskDesc;
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
}