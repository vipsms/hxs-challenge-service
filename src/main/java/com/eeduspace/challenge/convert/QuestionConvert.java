package com.eeduspace.challenge.convert;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.po.Question;
import com.google.gson.Gson;

public class QuestionConvert {
	public static Question toQuestion(QuestionEntity questionEntity,String paperUUID){
		Question question=new Question();
		question.setCreateDate(new Date());
		question.setDifficultStar(questionEntity.getDifficultStar()+"");
		question.setGradeCode(questionEntity.getGradeCode());
		question.setPaperUuid(paperUUID);
		question.setQuesionOption(questionEntity.getQuesOption());
		question.setQuestionAnalysis(questionEntity.getQuesAnalyze());
		question.setQuestionStem(questionEntity.getStem());
		question.setQuestionType(questionEntity.getType());
		question.setQuestionUuid(questionEntity.getId());
		question.setRightOption(questionEntity.getAnswer());
		//question.setSubjectCode(questionEntity.get());
		question.setSubjectName(questionEntity.getSubjectName());
		question.setUnitCode(questionEntity.getUnitCode());
		
		return question;
	}
	@SuppressWarnings("unchecked")
	public static QuestionEntity toQuestionEctity(Question question){
		Gson gson=new Gson();
		QuestionEntity questionEntity=new QuestionEntity();
		questionEntity.setAnalysis(gson.fromJson(question.getQuestionAnalysis(),List.class));
		questionEntity.setAnswer(question.getRightOption());
		questionEntity.setGradeCode(question.getGradeCode());
		questionEntity.setOptions(gson.fromJson(question.getQuesionOption(), List.class));
		questionEntity.setStem(question.getQuestionStem());
		questionEntity.setType(question.getQuestionType());
		questionEntity.setQuesionUUID(question.getQuestionUuid());
		questionEntity.setSubjectName(question.getSubjectName());
		questionEntity.setDifficultStar(Integer.valueOf(question.getDifficultStar()));
		questionEntity.setPaperUUID(question.getPaperUuid());
		questionEntity.setUnitCode(question.getUnitCode());
		return questionEntity;
	}
	
}
