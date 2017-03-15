package com.eeduspace.challenge.model.paper;

import java.util.List;

import com.eeduspace.challenge.persist.po.Question;

public class PaperModel {
	private String uuid;

	private String subjectCode;

	private String gradeCode;

	private String unitCode;

	private String volumeCode;
	private List<Question> questionList;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public List<Question> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
}
