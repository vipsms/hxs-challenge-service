package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.WeeklyRanked;

public interface WeeklyRankedMapper extends BaseDao<WeeklyRanked>{
	/**
	 * 获取好友中的榜主信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月26日 下午7:51:38
	 * @param userCode
	 * @param subjectCode
	 * @param gradeCode
	 * @return
	 */
	public WeeklyRanked getMaxRankInFriend(String userCode,String subjectCode,String gradeCode);
}