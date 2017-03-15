package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.WeeklyChart;

public interface WeeklyChartMapper extends BaseDao<WeeklyChart>{
	/**
	 * 获取上周周榜信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午1:51:15
	 * @param subjectCode 学科code
	 * @param gradeCode 学年code
	 * @return
	 */
	public WeeklyChart getMaxWeeklyChart(String subjectCode,String gradeCode);
}