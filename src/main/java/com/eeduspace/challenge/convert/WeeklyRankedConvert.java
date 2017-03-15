package com.eeduspace.challenge.convert;

import java.util.Date;

import com.eeduspace.challenge.persist.po.WeeklyRanked;
import com.eeduspace.challenge.persist.po.WeeklyRanking;

public class WeeklyRankedConvert {
	public static WeeklyRanked toWeeklyRanked(WeeklyRanking weeklyRanking){
		WeeklyRanked weeklyRanked=new WeeklyRanked();
		weeklyRanked.setCreateDate(new Date());
		weeklyRanked.setNickName(weeklyRanking.getNickName());
		weeklyRanked.setUserCode(weeklyRanking.getUserCode());
		weeklyRanked.setWeekFight(weeklyRanking.getWeekFightValue());
		weeklyRanked.setWeekIntegral(weeklyRanking.getWeekIntegral());
		return weeklyRanked;
	}
}
