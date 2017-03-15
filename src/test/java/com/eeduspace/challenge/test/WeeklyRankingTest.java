package com.eeduspace.challenge.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WeeklyRankingTest {
	@Inject
	private WeeklyRankingService weeklyRankingServiceImpl;
	private Gson gson=new Gson();
	@Test
	public void testCurrentRank(){
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		baseResourceModel.setSubjectCode("11");
		baseResourceModel.setGradeCode("111");
		System.out.println(gson.toJson(weeklyRankingServiceImpl.findWeeklyRanking("down", 0l, 10l, baseResourceModel)));
	}
}
