package com.eeduspace.challenge.service;

import com.eeduspace.challenge.persist.po.WeeklyChart;

public interface WeeklyChartService extends BaseService<WeeklyChart>{
	/**
	 *  获取上一周周榜信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午2:00:54
	 * @param subjectCode
	 * @param gradeCode
	 * @return
	 */
	public WeeklyChart findMaxWeeklyChart(String subjectCode,String gradeCode);
}

