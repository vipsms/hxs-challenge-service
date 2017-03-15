package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/13
 * 用户成就表
 */
public class UserAchievement {
    private Long id;

    private String userCode;
    //第一名次数
    private Long first=0l;
    //第二名次数
    private Long second=0l;
    //第三名次数
    private Long third=0l;

    private Date createDate;

    private Date updateDate;

    private String subjectCode;

    private String gradeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public Long getThird() {
        return third;
    }

    public void setThird(Long third) {
        this.third = third;
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }
}