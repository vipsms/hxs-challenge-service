package com.eeduspace.challenge.service;

import com.eeduspace.challenge.persist.po.WeeklyRanked;

public interface WeeklyRankedService extends BaseService<WeeklyRanked>{
	/**
	 * 定时处理周榜更新
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月24日 上午9:57:22
	 */
	public void weekRankJob();
	/**
	 * 获取好友榜榜主信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月26日 下午8:04:41
	 * @param userCode 用户code
	 * @param subjectCode 学科code
	 * @param gradeCode 学年code
	 * @return
	 */
	public WeeklyRanked findMasterInFriend(String userCode,String subjectCode,String gradeCode);
}
