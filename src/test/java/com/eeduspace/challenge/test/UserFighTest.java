package com.eeduspace.challenge.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.persist.po.UserFight;
import com.eeduspace.challenge.service.UserFightService;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserFighTest {
	@Inject
	private UserFightService userFightServiceImpl;
	@Test
	public void save(){
		for (int i = 1; i < 10; i++) {
			UserFight userFight=new UserFight();
			userFight.setPlayerA("us"+i);
			userFight.setPlaeryB("us"+(i+1));
			userFight.setQuestionsCode("12312333");
			userFight.setGradeCode("111");
			userFight.setSubjectCode("11");
			userFightServiceImpl.save(userFight);
		}
		
	}
}
