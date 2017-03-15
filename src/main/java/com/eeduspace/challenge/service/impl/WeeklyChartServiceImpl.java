package com.eeduspace.challenge.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.persist.dao.WeeklyChartMapper;
import com.eeduspace.challenge.persist.po.WeeklyChart;
import com.eeduspace.challenge.service.WeeklyChartService;

@Service("weeklyChartServiceImpl")
public class WeeklyChartServiceImpl extends BaseServiceImpl<WeeklyChart>
		implements WeeklyChartService {
	@Resource
	private WeeklyChartMapper weeklyChartMapperImpl;
	@Autowired
	public WeeklyChartServiceImpl(WeeklyChartMapper weeklyChartMapper) {
		this.baseDao = weeklyChartMapper;
	}
	@Override
	public WeeklyChart findMaxWeeklyChart(String subjectCode,String gradeCode) {
		return weeklyChartMapperImpl.getMaxWeeklyChart(subjectCode,gradeCode);
	}
}
