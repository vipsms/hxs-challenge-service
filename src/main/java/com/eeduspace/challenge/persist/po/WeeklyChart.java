package com.eeduspace.challenge.persist.po;

import java.util.Date;
import java.util.UUID;
/**
 * 周榜信息
 */
public class WeeklyChart {
    private Long id;

    private String uuid=UUID.randomUUID().toString().replace("-", "");
    //周冠军code
    private String championUserCode;
    //周冠军昵称
    private String championNickName;
    //总周数
    private Long allWeek;
    //第二名
    private String secondUserCode;
    //第三名
    private String thirdUserCode;
    //学科
    private String subjectCode;
    //学年
    private String gradeCode;
    //上下册
    private String volumeCode;
    //单元
    private String unitCode;
    //月周数
    private Double fewWeek;

    private Date createDate;

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

    public String getChampionUserCode() {
        return championUserCode;
    }

    public void setChampionUserCode(String championUserCode) {
        this.championUserCode = championUserCode;
    }

    public String getChampionNickName() {
        return championNickName;
    }

    public void setChampionNickName(String championNickName) {
        this.championNickName = championNickName;
    }

    public Long getAllWeek() {
        return allWeek;
    }

    public void setAllWeek(Long allWeek) {
        this.allWeek = allWeek;
    }

    public String getSecondUserCode() {
        return secondUserCode;
    }

    public void setSecondUserCode(String secondUserCode) {
        this.secondUserCode = secondUserCode;
    }

    public String getThirdUserCode() {
        return thirdUserCode;
    }

    public void setThirdUserCode(String thirdUserCode) {
        this.thirdUserCode = thirdUserCode;
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

    public String getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(String volumeCode) {
        this.volumeCode = volumeCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Double getFewWeek() {
        return fewWeek;
    }

    public void setFewWeek(Double fewWeek) {
        this.fewWeek = fewWeek;
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