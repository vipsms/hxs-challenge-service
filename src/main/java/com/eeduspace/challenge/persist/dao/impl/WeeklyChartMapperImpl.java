package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.WeeklyChartMapper;
import com.eeduspace.challenge.persist.po.WeeklyChart;
@Repository("weeklyChartMapperImpl")
public class WeeklyChartMapperImpl extends BaseDaoImpl<WeeklyChart> implements WeeklyChartMapper{
	private final String GET_MAX_WEEKLYCHART="get_max_weeklychart";
	@Override
	public WeeklyChart getMaxWeeklyChart(String subjectCode,String gradeCode) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		return sessionTemplate.selectOne(GET_MAX_WEEKLYCHART,queryMap);
	}

}
