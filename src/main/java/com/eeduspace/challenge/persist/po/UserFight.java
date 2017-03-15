package com.eeduspace.challenge.persist.po;

import java.util.Date;
import java.util.UUID;

public class UserFight {
    private Long id;

    private String uuid= UUID.randomUUID().toString().replace("-", "");
    //对战用户A
    private String playerA;
    //对战用户B
    private String plaeryB;

    private String subjectCode;

    private String subjectName;

    private String gradeCode;

    private String gradeName;

    private String unitCode;

    private String unitName;
    //试题标识
    private String questionsCode;
    //对战类型（好友/快速）
    private Integer battleType;
    //是否结束
    private Integer isOver;
    //战斗类型（对战/挑战）
    private Integer fightType;

    private Date createTime=new Date();

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

    public String getPlayerA() {
        return playerA;
    }

    public void setPlayerA(String playerA) {
        this.playerA = playerA;
    }

    public String getPlaeryB() {
        return plaeryB;
    }

    public void setPlaeryB(String plaeryB) {
        this.plaeryB = plaeryB;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getQuestionsCode() {
        return questionsCode;
    }

    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    public Integer getBattleType() {
        return battleType;
    }

    public void setBattleType(Integer battleType) {
        this.battleType = battleType;
    }

    public Integer getIsOver() {
        return isOver;
    }

    public void setIsOver(Integer isOver) {
        this.isOver = isOver;
    }

    public Integer getFightType() {
        return fightType;
    }

    public void setFightType(Integer fightType) {
        this.fightType = fightType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}