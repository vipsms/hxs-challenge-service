package com.eeduspace.challenge.persist.po;

import java.util.Date;
import java.util.UUID;
/**
 *  用户答题记录 
 */
public class UserAnswer {
    private Long id;

    private String uuid=UUID.randomUUID().toString().replace("-", "");

    private String userCode;
    //战斗记录的uuid
    private String fightRecordUuid;
    //战斗类型
    private Integer fightType;
    //正确与否
    private Boolean isRight;
    //得分
    private Double score;
    //用时
    private Long useTime;
    //试题uuid
    private String questionUuid;
    //用户的选项
    private String userOption;
    //正确选项
    private String rightOption;
    //试卷UUID 
    private String paperUuid;
    //试题序号
    private String questionSn;
  
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

    public String getFightRecordUuid() {
        return fightRecordUuid;
    }

    public void setFightRecordUuid(String fightRecordUuid) {
        this.fightRecordUuid = fightRecordUuid;
    }

    public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	public Integer getFightType() {
        return fightType;
    }

    public void setFightType(Integer fightType) {
        this.fightType = fightType;
    }


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }


    public String getQuestionUuid() {
        return questionUuid;
    }

    public void setQuestionUuid(String questionUuid) {
        this.questionUuid = questionUuid;
    }

    public String getUserOption() {
        return userOption;
    }

    public void setUserOption(String userOption) {
        this.userOption = userOption;
    }

    public String getRightOption() {
        return rightOption;
    }

    public void setRightOption(String rightOption) {
        this.rightOption = rightOption;
    }

    public String getPaperUuid() {
        return paperUuid;
    }

    public void setPaperUuid(String paperUuid) {
        this.paperUuid = paperUuid;
    }

    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
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