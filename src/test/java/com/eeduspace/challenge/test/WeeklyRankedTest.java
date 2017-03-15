package com.eeduspace.challenge.test;

import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyRankedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeeklyRankedTest {
	@Inject
	private WeeklyRankedService weeklyRankedServiceImpl;
    @Inject
	private UserService  userServiceImpl;
    @Test
	public void testCurrentRank(){
//        User u=new User();
//        u.setAccessKey("sss");
//        userServiceImpl.save(u);

	}
}
