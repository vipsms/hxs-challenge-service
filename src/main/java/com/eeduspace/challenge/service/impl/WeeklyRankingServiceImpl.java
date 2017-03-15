package com.eeduspace.challenge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.weeklyrank.RankModel;
import com.eeduspace.challenge.model.weeklyrank.WeekRankingModel;
import com.eeduspace.challenge.persist.dao.WeeklyRankingMapper;
import com.eeduspace.challenge.persist.po.WeeklyRanking;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.WeeklyRankingService;
import com.eeduspace.challenge.util.Order;
import com.eeduspace.challenge.util.Order.Direction;

@Service("weeklyRankingServiceImpl")
public class WeeklyRankingServiceImpl extends BaseServiceImpl<WeeklyRanking>
		implements WeeklyRankingService {
	@Resource
	private WeeklyRankingMapper weeklyRankingMapperImpl;
	@Autowired
	private FightResultService fightResultServiceImpl;
	@Autowired
	public WeeklyRankingServiceImpl(WeeklyRankingMapper weeklyRankingMapper) {
		this.baseDao = weeklyRankingMapper;
	}
	@Override
	public List<WeeklyRanking> findTop(Long item) {
		Order order=new Order("week_fight_value", Direction.desc);
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("limit", item);
		queryMap.put("start", 0);
		return weeklyRankingMapperImpl.findByCondition(queryMap, order);
	}
	@Override
	public Long countCurrentRank(String userCode,String subjectCode,String gradeCode) {
		return weeklyRankingMapperImpl.countUserCurrentRank(userCode,subjectCode,gradeCode);
	}
	@Override
	public List<WeeklyRanking> findUserRank(Long start, Long end) {
		return this.findUserRank(start,end,null);
	}
	@Override
	public List<WeeklyRanking> findChallengerRank(String userCode,String subjectCode,String gradeCode) {
		Long userCurrentRank=this.countCurrentRank(userCode,subjectCode,gradeCode);
		if(userCurrentRank>10){
			return this.findUserRank((userCurrentRank-1)-10, userCurrentRank-1);
		}else if(userCurrentRank==1){
			return this.findTop(10l);
		}else{
			return this.findTop(userCurrentRank);
		}
	}
	@Override
	public List<WeeklyRanking> findUserRank(Long start, Long end,
			BaseResourceModel baseResourceModel) {
		Order order=new Order("week_fight_value", Direction.desc);
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("limit", end);
		queryMap.put("start", start);
		if(baseResourceModel!=null){
			queryMap.put("subjectCode", baseResourceModel.getSubjectCode());
			queryMap.put("gradeCode", baseResourceModel.getGradeCode());
			queryMap.put("unitCode", baseResourceModel.getUnitCode());
			queryMap.put("volumeCode", baseResourceModel.getVolumeCode());
		}
		return weeklyRankingMapperImpl.findByCondition(queryMap, order);
	}
	@Override
	public List<WeeklyRanking> findWeeklyRanking(String order,
			Long currentRank, Long item, BaseResourceModel baseResourceModel) {
		Long start=0l;
		if(order.equals("down")){//向下滑动
			return findUserRank(currentRank,item,baseResourceModel);
		}else if(order.equals("up")){
			if(currentRank-item>=0){
				start=currentRank-item;
			}
			return findUserRank(start,currentRank,baseResourceModel);
		}else{
			return null;
		}
	}
	@Override
	public Long countCurrentRankInFriend(String userCode,String subjectCode,String gradeCode) {
		return weeklyRankingMapperImpl.countUserCurrentRankInFriend(userCode,subjectCode,gradeCode);
	}
	@Override
	public List<WeekRankingModel> findFriendRanking(String userCode,String order,
			Long currentRank, Long item, BaseResourceModel baseResourceModel) {
		Long start=0l;
		if(order.equals("down")){//向下滑动
			if(currentRank<item){
				currentRank=start;
			}else{
				currentRank=currentRank-item;
			}
			return weeklyRankingMapperImpl.findUserFriendRank(userCode, currentRank, item, baseResourceModel);
		}else if(order.equals("up")){
			return weeklyRankingMapperImpl.findUserFriendRank(userCode, currentRank, item, baseResourceModel);
		}else{
			return null;
		}
	}
	@Override
	public List<RankModel> findSubjectRank(String subjectCode,String gradeCode) {
		return weeklyRankingMapperImpl.findSubjectRank(subjectCode,gradeCode);
	}
	@Override
	public void saveWeekRanking(String userCode, String subjectCode,
			String gradeCode) {
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("userCode", userCode);
		if(this.findByCondition(queryMap).size()==0){
			if(fightResultServiceImpl.verifyEligibility(userCode, subjectCode, gradeCode)){
				WeeklyRanking weeklyRanking=new WeeklyRanking();
				weeklyRanking.setGradeCode(gradeCode);
				weeklyRanking.setSubjectCode(subjectCode);
				weeklyRanking.setUserCode(userCode);
				this.save(weeklyRanking);
			}
			
		}
		
	}
	@Override
	public List<WeeklyRanking> findSubjectAndGrade() {
		return weeklyRankingMapperImpl.findGroupBySubjectGrade();
	}
	@Override
	public void updateWeekFightAndIntegral() {
		weeklyRankingMapperImpl.updateWeekFightAndIntegral();
	}
	@Override
	public List<WeekRankingModel> findNationalRank(String order,Long currentRank, Long item, BaseResourceModel baseResourceModel) {
		Long start=0l;
		if(order.equals("down")){//向下滑动
			if(currentRank<item){
				currentRank=start;
			}else{
				currentRank=currentRank-item;
			}
			return weeklyRankingMapperImpl.findRanking(currentRank, item, baseResourceModel);
		}else if(order.equals("up")){
			return weeklyRankingMapperImpl.findRanking(currentRank, item, baseResourceModel);
		}else{
			return null;
		}
		
	}
	@Override
	public List<WeekRankingModel> findNationalRank(Long currentRank, Long item,
			BaseResourceModel baseResourceModel) {
		return weeklyRankingMapperImpl.findRanking(currentRank, item, baseResourceModel);
	}
	@Override
	public List<WeekRankingModel> findFriendRank(String userCode,String order,
			Long currentRank, Long item, BaseResourceModel baseResourceModel) {
		Long start=0l;
		if(order.equals("down")){//向下滑动
			if(currentRank<item){
				currentRank=start;
			}else{
				currentRank=currentRank-item;
			}
			return weeklyRankingMapperImpl.findUserFriendRank(userCode, currentRank, item, baseResourceModel);
		}else if(order.equals("up")){
			return weeklyRankingMapperImpl.findUserFriendRank(userCode, currentRank, item, baseResourceModel);
		}else{
			return null;
		}
	}
	@Override
	public List<WeekRankingModel> findFriendRank(String userCode,Long currentRank, Long item,
			BaseResourceModel baseResourceModel) {
		return weeklyRankingMapperImpl.findUserFriendRank(userCode, currentRank, item, baseResourceModel);
	}
	@Override
	public List<String> findAllMaxGrade(String subjectCode) {
		
		return weeklyRankingMapperImpl.findAllMaxGrade(subjectCode);
	}
	@Override
	public Long countCurrentRankTotal(String subjectCode, String gradeCode) {
		return weeklyRankingMapperImpl.getCurrentCountTotal(subjectCode, gradeCode);
	}
	@Override
	public List<WeekRankingModel> getHxsTvNationalRank(Map<String, Object> params) {
		return weeklyRankingMapperImpl.getHxsTvNationalRank(params);
	}
	@Override
	public WeekRankingModel findUserNationalRanking(Map<String, Object> queryMap) {
		return weeklyRankingMapperImpl.findUserNationalRanking(queryMap);
	}
	@Override
	public List<WeekRankingModel> getHxsTvFriendRank(Map<String, Object> params) {
		return weeklyRankingMapperImpl.getHxsTvFriendRank(params);
	}
	@Override
	public WeekRankingModel findUserNationalRankingTv(Map<String, Object> queryMap) {
		return weeklyRankingMapperImpl.findUserNationalRankingTv(queryMap);
	}
}
