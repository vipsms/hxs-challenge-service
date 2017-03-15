package com.eeduspace.challenge.model.paper;

import java.util.List;


public class QuestionEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	/** id */
	private String id;
	/**
	 * 试题uuid
	 */
	private String quesionUUID;
	private String paperUUID;
	/** 试题标题 */
	private String title;
	/** 试题题干 */
	private String stem;
	/** 试题选项 */
	private String quesOption;
	/** 试题解析 */
	private String quesAnalyze;
	/** 试题答案 */
	private String answer;
	/** 试题难易度 */
	private Integer difficultStar;
	/** 试题类型 */
	private String type;
	/** 学科名称 */
	private String subjectName;
	/** 学年 */
	private String gradeCode;
	/** 单元code */
	private String unitCode;
	/**
	 * 选项信息
	 */
	private List<QuesionOptionModel> options;
	/**
	 * 解析信息
	 */
	private List<QuestionAnalysisModel> analysis;
	
	public List<QuesionOptionModel> getOptions() {
		return options;
	}

	public void setOptions(List<QuesionOptionModel> options) {
		this.options = options;
	}

	public List<QuestionAnalysisModel> getAnalysis() {
		return analysis;
	}

	public void setAnalysis(List<QuestionAnalysisModel> analysis) {
		this.analysis = analysis;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public String getQuesOption() {
		return quesOption;
	}

	public void setQuesOption(String quesOption) {
		this.quesOption = quesOption;
	}

	public String getQuesAnalyze() {
		return quesAnalyze;
	}

	public void setQuesAnalyze(String quesAnalyze) {
		this.quesAnalyze = quesAnalyze;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getDifficultStar() {
		return difficultStar;
	}

	public void setDifficultStar(Integer difficultStar) {
		this.difficultStar = difficultStar;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getPaperUUID() {
		return paperUUID;
	}

	public void setPaperUUID(String paperUUID) {
		this.paperUUID = paperUUID;
	}

	public String getQuesionUUID() {
		return quesionUUID;
	}

	public void setQuesionUUID(String quesionUUID) {
		this.quesionUUID = quesionUUID;
	}

}
