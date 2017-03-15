package com.eeduspace.challenge.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.persist.po.Question;
import com.eeduspace.challenge.service.QuestionService;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestionTest {
	private Gson gson=new Gson();
	@Inject
	private QuestionService questionServiceImpl;
	@Test
	public void saveQuestion(){
		for (int i = 0; i < 10; i++) {
			Question question=new Question();
			question.setPaperUuid("12312333");
			question.setQuestionUuid(question.getUuid());
			questionServiceImpl.save(question);
		}
	}
	@Test
	public void getQuestionByPapperUUID(){
		String paperUUID="12312333";
		System.out.println(gson.toJson(questionServiceImpl.findByPaperUUID(paperUUID)));
	}
}
