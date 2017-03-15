package com.eeduspace.challenge.persist.po;

import java.util.Date;
import java.util.UUID;

public class FightResult {
    private Long id;

    private String uuid=UUID.randomUUID().toString().replace("-", "");

    private String userCode;
    //用时
    private Long useTime;
    //得分
    private Double getScore;
    //答题结果
    private String result;
    //积分
    private Long integral=0l;
    //增加战斗力
    private Long fightValue=0l;
    //当前排名
    private Long currentRank;
    //战后排名
    private Long fightedRank;
    //对战结果
    private Integer fightResult;
    //对战记录标识
    private String fightUuid;
    //战斗类型
    private Integer fightType;
    //试卷uuid
    private String paperUuid;
    //试卷标识
    private String paperTitle;

    private String subjectCode;

    private String gradeCode;

    private String unitCode;

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

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public Double getGetScore() {
        return getScore;
    }

    public void setGetScore(Double getScore) {
        this.getScore = getScore;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    
    public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Long getFightValue() {
		return fightValue;
	}

	public void setFightValue(Long fightValue) {
		this.fightValue = fightValue;
	}

	public Long getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(Long currentRank) {
        this.currentRank = currentRank;
    }

    public Long getFightedRank() {
        return fightedRank;
    }

    public void setFightedRank(Long fightedRank) {
        this.fightedRank = fightedRank;
    }

    public Integer getFightResult() {
        return fightResult;
    }

    public void setFightResult(Integer fightResult) {
        this.fightResult = fightResult;
    }

    public String getFightUuid() {
        return fightUuid;
    }

    public void setFightUuid(String fightUuid) {
        this.fightUuid = fightUuid;
    }

    public Integer getFightType() {
        return fightType;
    }

    public void setFightType(Integer fightType) {
        this.fightType = fightType;
    }

    public String getPaperUuid() {
        return paperUuid;
    }

    public void setPaperUuid(String paperUuid) {
        this.paperUuid = paperUuid;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
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