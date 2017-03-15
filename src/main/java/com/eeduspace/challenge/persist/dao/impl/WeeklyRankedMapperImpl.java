package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.WeeklyRankedMapper;
import com.eeduspace.challenge.persist.po.WeeklyRanked;
@Repository("weeklyRankedMapperImpl")
public class WeeklyRankedMapperImpl extends BaseDaoImpl<WeeklyRanked> implements WeeklyRankedMapper{
	private final String GETMAXRANKINFRIEND="get_master_in_friend";
	@Override
	public WeeklyRanked getMaxRankInFriend(String userCode, String subjectCode,
			String gradeCode) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		return sessionTemplate.selectOne(GETMAXRANKINFRIEND, queryMap);
	}

}
