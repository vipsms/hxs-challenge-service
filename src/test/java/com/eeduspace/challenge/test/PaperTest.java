package com.eeduspace.challenge.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.persist.po.Question;
import com.eeduspace.challenge.persist.po.UserAnswer;
import com.eeduspace.challenge.service.QuestionService;
import com.eeduspace.challenge.service.UserAnswerService;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PaperTest {
	@Inject
	private QuestionService questionServiceImpl;
	@Inject
	private UserAnswerService userAnswerServiceImpl;
	@Test
	public void testSaveQuestion(){
		for (int i = 0; i < 10; i++) {
//			Question question=new Question();
//			question.setPaperUuid("123456");
//			question.setQuestionUuid(i+"");
//			questionServiceImpl.save(question);
			UserAnswer userAnswer=new UserAnswer();
			userAnswer.setFightRecordUuid("2");
			userAnswer.setUserCode("2");
			userAnswer.setQuestionUuid(i+"");
			userAnswer.setPaperUuid("123456");
			userAnswerServiceImpl.save(userAnswer);
		}
	}
}
