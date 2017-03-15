package com.eeduspace.challenge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeduspace.challenge.convert.WeeklyRankedConvert;
import com.eeduspace.challenge.persist.dao.WeeklyRankedMapper;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserAchievement;
import com.eeduspace.challenge.persist.po.WeeklyChart;
import com.eeduspace.challenge.persist.po.WeeklyRanked;
import com.eeduspace.challenge.persist.po.WeeklyRanking;
import com.eeduspace.challenge.service.UserAchievementService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.WeeklyChartService;
import com.eeduspace.challenge.service.WeeklyRankedService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.eeduspace.challenge.util.Order;
import com.eeduspace.challenge.util.Order.Direction;

@Service("weeklyRankedServiceImpl")
public class WeeklyRankedServiceImpl extends BaseServiceImpl<WeeklyRanked>
		implements WeeklyRankedService {
	@Resource
	private WeeklyRankedMapper weeklyRankedMapperImpl;
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;
	@Autowired
	private WeeklyChartService weeklyChartServiceImpl;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAchievementService userAchievementServiceImpl;
	@Autowired
	public WeeklyRankedServiceImpl(WeeklyRankedMapper weeklyRankedMapper) {
		this.baseDao = weeklyRankedMapper;
	}
	@Transactional
	@Override
	public void weekRankJob() {
		//根据学科和学年分组  取出 学科学年code
		List<WeeklyRanking> weeklyRankings=weeklyRankingServiceImpl.findSubjectAndGrade();
		//根据学科学年去统计出来排行信息  保存周榜信息
		for (WeeklyRanking weeklyRanking : weeklyRankings) {
			//查询当前最新周榜信息
			WeeklyChart weeklyChart= weeklyChartServiceImpl.findMaxWeeklyChart(weeklyRanking.getSubjectCode(),weeklyRanking.getGradeCode());
			Map<String, Object> queryMap=new HashMap<String, Object>();
			queryMap.put("subjectCode", weeklyRanking.getSubjectCode());
			queryMap.put("gradeCode", weeklyRanking.getGradeCode());
			List<WeeklyRanking> ranks=weeklyRankingServiceImpl.findByCondition(queryMap, new Order("week_fight_value", Direction.desc));
			Long allWeekLong=1l;
			if(weeklyChart!=null){
				allWeekLong=weeklyChart.getAllWeek()+allWeekLong;
			}
			saveWeeklyChart(allWeekLong, ranks,weeklyRanking.getSubjectCode(),weeklyRanking.getGradeCode());
		}
		// 将周实时榜信息中的战斗力清空
		weeklyRankingServiceImpl.updateWeekFightAndIntegral();
		//将user表中的weekStatus战斗次数清零
		userService.updateWeekStatus();
	}
	
	/**
	 * 保存消息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月26日 下午5:59:39
	 */
	public void saveMessage(){
		
	}
	/**
	 * 保存周榜定榜信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 上午10:31:12
	 * @param allWeekLong
	 * @param ranks
	 * @param subjectCode
	 * @param gradeCode
	 */
	public void saveWeeklyChart(Long allWeekLong,List<WeeklyRanking> ranks,String subjectCode,String gradeCode){
		WeeklyChart  currentWeeklyChart=new WeeklyChart();
		currentWeeklyChart.setAllWeek(allWeekLong);
		for (int i = 0; i < ranks.size(); i++) {
			if(i==0){
				updateUserAchievement(ranks.get(i).getUserCode(), subjectCode, gradeCode, i);
				currentWeeklyChart.setChampionUserCode(ranks.get(i).getUserCode());
				currentWeeklyChart.setChampionNickName(ranks.get(i).getNickName());
				updateUser(ranks.get(i).getUserCode(),i);
			}else if(i==1){
				updateUserAchievement(ranks.get(i).getUserCode(), subjectCode, gradeCode, i);
				currentWeeklyChart.setSecondUserCode(ranks.get(i).getUserCode());
				updateUser(ranks.get(i).getUserCode(),i);
			}else if (i==2) {
				updateUserAchievement(ranks.get(i).getUserCode(), subjectCode, gradeCode, i);
				currentWeeklyChart.setThirdUserCode(ranks.get(i).getUserCode());
				updateUser(ranks.get(i).getUserCode(),i);
			}
			WeeklyRanked weeklyRanked=WeeklyRankedConvert.toWeeklyRanked(ranks.get(i));
			weeklyRanked.setWeeklyChartUuid(currentWeeklyChart.getUuid());
			weeklyRanked.setWeekRank((double)i+1);
			this.save(weeklyRanked);
		}
		currentWeeklyChart.setCreateDate(new Date());
		currentWeeklyChart.setSubjectCode(subjectCode);
		currentWeeklyChart.setGradeCode(gradeCode);
		weeklyChartServiceImpl.save(currentWeeklyChart);
	}
	/**
	 * 更新用户成就信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月26日 下午1:26:44
	 * @param userCode
	 * @param type
	 */
	public void updateUser(String userCode,int type){
		User user=userService.findByUserCode(userCode);
		if(user!=null){
			if(type==0){
				user.setFirst(user.getFirst()==null?0l:user.getFirst()+1);
			}
			if(type==1){
				user.setSecond(user.getSecond()==null?0l:user.getSecond()+1);
			}
			if(type==2){
				user.setThird(user.getThird()==null?0l:user.getThird()+1);
			}
			user.setUpdateDate(new Date());
//			userService.save(user);//原来的shabi写的
			userService.update(user);
		}
	}
	/**
	 * 保存成绩记录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月26日 下午3:26:02
	 * @param userCode
	 * @param subjectCode
	 * @param gradeCode
	 * @param type
	 */
	public void updateUserAchievement(String userCode,String subjectCode,String gradeCode,int type){
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("userCode", userCode);
		queryMap.put("userCode", userCode);
		queryMap.put("userCode", userCode);
		List<UserAchievement> list=userAchievementServiceImpl.findByCondition(queryMap);
		if(list.size()>0){
			UserAchievement userAchievement=list.get(0);
			userAchievement.setUpdateDate(new Date());
			if(type==0){
				userAchievement.setFirst(userAchievement.getFirst()+1);
			}
			if(type==1){
				userAchievement.setSecond(userAchievement.getSecond()+1);
			}
			if(type==2){
				userAchievement.setThird(userAchievement.getThird()+1);
			}
			userAchievementServiceImpl.update(userAchievement);
		}else{
			UserAchievement userAchievement=new UserAchievement();
			userAchievement.setCreateDate(new Date());
			if(type==0){
				userAchievement.setFirst(1l);
			}
			if(type==1){
				userAchievement.setSecond(1l);
			}
			if(type==2){
				userAchievement.setThird(1l);
			}
			userAchievement.setUserCode(userCode);
			userAchievementServiceImpl.save(userAchievement);
		}
	}
	@Override
	public WeeklyRanked findMasterInFriend(String userCode, String subjectCode,
			String gradeCode) {
		return weeklyRankedMapperImpl.getMaxRankInFriend(userCode, subjectCode, gradeCode);
	}
}
