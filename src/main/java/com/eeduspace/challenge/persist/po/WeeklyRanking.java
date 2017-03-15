package com.eeduspace.challenge.persist.po;

import java.util.Date;
import java.util.UUID;

public class WeeklyRanking {
    private Long id;

    private String uuid=UUID.randomUUID().toString().replace("-", "");

    private String userCode;
    //排名
    private Double rank;
    //昵称
    private String nickName;
    //本周获得战斗力 
    private Long weekFightValue=0l;
    //本周获得积分
    private Long weekIntegral=0l;
    //学科code
    private String subjectCode;
    //学年code
    private String gradeCode;
    //单元code
    private String unitCode;
    //上下册code
    private String volumeCode;

    private Date createDate=new Date();

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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public Long getWeekFightValue() {
		return weekFightValue;
	}

	public void setWeekFightValue(Long weekFightValue) {
		this.weekFightValue = weekFightValue;
	}

	public Long getWeekIntegral() {
		return weekIntegral;
	}

	public void setWeekIntegral(Long weekIntegral) {
		this.weekIntegral = weekIntegral;
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(String volumeCode) {
        this.volumeCode = volumeCode;
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