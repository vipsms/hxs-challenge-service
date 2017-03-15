package com.eeduspace.challenge.persist.po;

import java.util.Date;
import java.util.UUID;

public class Question {
    private Long id;

    private String uuid=UUID.randomUUID().toString().replace("-", "");

    private String paperUuid;

    private String questionUuid;
    //题干信息 
    private String questionStem;
    //选项信息
    private String quesionOption;
    //正确答案
    private String rightOption;
    //试题解析信息 
    private String questionAnalysis;
    //试题分数
    private Double score;
    //创建时间
    private Date createDate;
    //更新时间 
    private Date updateDate;
    
    private String questionType;
    private String subjectCode;
    private String subjectName;
    private String unitCode;
    private String gradeCode;
    /**
     * 难易程度
     */
    private String difficultStar;

    public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getDifficultStar() {
		return difficultStar;
	}

	public void setDifficultStar(String difficultStar) {
		this.difficultStar = difficultStar;
	}

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

    public String getPaperUuid() {
        return paperUuid;
    }

    public void setPaperUuid(String paperUuid) {
        this.paperUuid = paperUuid;
    }

    public String getQuestionUuid() {
        return questionUuid;
    }

    public void setQuestionUuid(String questionUuid) {
        this.questionUuid = questionUuid;
    }

    public String getQuestionStem() {
        return questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    public String getQuesionOption() {
        return quesionOption;
    }

    public void setQuesionOption(String quesionOption) {
        this.quesionOption = quesionOption;
    }

    public String getRightOption() {
        return rightOption;
    }

    public void setRightOption(String rightOption) {
        this.rightOption = rightOption;
    }

    public String getQuestionAnalysis() {
        return questionAnalysis;
    }

    public void setQuestionAnalysis(String questionAnalysis) {
        this.questionAnalysis = questionAnalysis;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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