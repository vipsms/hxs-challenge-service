package com.eeduspace.challenge.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.persist.po.UserAnswer;
import com.eeduspace.challenge.service.UserAnswerService;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserAnswerTest {
	@Inject
	private UserAnswerService userAnswerServiceImpl;
	@Test
	public void save(){
		for (int i = 0; i < 10; i++) {
			UserAnswer userAnswer=new UserAnswer();
			userAnswer.setIsRight(true);
			userAnswer.setPaperUuid("12312333");
			userAnswer.setScore(10d);
			userAnswer.setUserCode("us3");
			userAnswer.setUseTime(10l);
			userAnswerServiceImpl.save(userAnswer);
		}
	}
}
