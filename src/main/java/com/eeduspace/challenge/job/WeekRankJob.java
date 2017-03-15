package com.eeduspace.challenge.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eeduspace.challenge.service.WeeklyRankedService;

/**
 * @author zhuchaowei
 * 2016年7月24日
 * Description  周榜定榜定时器
 */
@Component
public class WeekRankJob {
    private final static Logger logger = LoggerFactory.getLogger(WeekRankJob.class);
	@Autowired
	private WeeklyRankedService weeklyRankedServiceImpl;
	//每周周一 凌晨00:00 执行
	/**
	 *此注解是控制是否开启 	
	 *		每周清零操作 
	 */
	 
//	@Scheduled(cron = "0 0 0 ? * 2")//原来的傻逼写的
	@Scheduled(cron = "0 0 0 ? * 1")
	
	public void WeekRank(){
		weeklyRankedServiceImpl.weekRankJob();
	}
}
