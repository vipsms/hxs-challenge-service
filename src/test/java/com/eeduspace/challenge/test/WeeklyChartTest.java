package com.eeduspace.challenge.test;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.WeeklyChart;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyChartService;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeeklyChartTest {
	@Inject
	private WeeklyChartService weeklyChartServiceImpl;
	@Inject
	private UserService userServiceImpl;
	@Test
	public void testCurrentRank(){
		User user=new User();
		user.setAccessKey("qqq");
		user.setUserName("sss");
		userServiceImpl.save(user);
//		for (int i = 1; i < 21; i++) {
//			WeeklyChart weeklyChart=new WeeklyChart();
//			weeklyChart.setAllWeek(Long.valueOf(i+""));
//			weeklyChart.setUuid(UUID.randomUUID().toString().replace("-", ""));
//			weeklyChartServiceImpl.save(weeklyChart);
//			System.out.println(weeklyChart.getId());
//		}
	}
}
