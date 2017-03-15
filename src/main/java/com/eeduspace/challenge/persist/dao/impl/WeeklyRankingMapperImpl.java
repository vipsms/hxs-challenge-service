package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.weeklyrank.RankModel;
import com.eeduspace.challenge.model.weeklyrank.WeekRankingModel;
import com.eeduspace.challenge.model.weeklyrank.WeeklyRankingModel;
import com.eeduspace.challenge.persist.dao.WeeklyRankingMapper;
import com.eeduspace.challenge.persist.po.WeeklyRanking;
@Repository("weeklyRankingMapperImpl")
public class WeeklyRankingMapperImpl extends BaseDaoImpl<WeeklyRanking> implements WeeklyRankingMapper{
	private final String COUNT_CURRENT_RANK="count_user_current_rank";
	private final String SET_RANK="set_rank";
	private final String COUNT_CURRENT_RANK_IN_FRIEND="count_user_current_rank_in_friend";
	private final String USER_FRIEND_RANK="user_friend_rank";
	private final String SUBJECT_RANK="subject_user_rank";
	private final String FINDSUBJECT="find_subjectandgrade";
	private final String UPDATEWEEKFIGHTANDINTEGRAL="updateWeekFight";
	private final String FINDCHALLENGERANKING="find_challenge_ranking";
	private final String FINDMAXGRADECODE="find_max_grade_code";
	private final String GET_CURRENT_COUNT_TOTAL = "getCurrentCountTotal";
	private final String GET_HXSTV_NATIONAL_RANK = "getHxsTvNationalRank";
	private final String USER_NATIONAL_RANKING = "UserNationalRanking";
	private final String USER_NATIONAL_RANKING_TV = "UserNationalRankingForTv";
	private final String GET_FRIEND_TOP = "getFriendTop";
	@Override
	public Long countUserCurrentRank(String userCode,String subjectCode,String gradeCode) {
		sessionTemplate.selectOne(SET_RANK);
		Map<String, Object> querMap=new HashMap<>();
		querMap.put("userCode", userCode);
		querMap.put("subjectCode", subjectCode);
		querMap.put("gradeCode", gradeCode);
		WeeklyRankingModel weeklyRankingModel=sessionTemplate.selectOne(COUNT_CURRENT_RANK, querMap);
		if(weeklyRankingModel==null){
			return 0l;
		}
		return weeklyRankingModel.getRank();
	}
	@Override
	public Long countUserCurrentRankInFriend(String userCode,String subjectCode,String gradeCode) {
		sessionTemplate.selectOne(SET_RANK);
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("userCode", userCode);
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		WeeklyRankingModel weeklyRankingModel=sessionTemplate.selectOne(COUNT_CURRENT_RANK_IN_FRIEND, queryMap);
		if(weeklyRankingModel==null){
			return 0l;
		}
		return weeklyRankingModel.getRank();
	}
	@Override
	public List<WeekRankingModel> findUserFriendRank(String userCode, Long start,
			Long end,BaseResourceModel baseResourceModel) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		queryMap.put("start", start);
		queryMap.put("end", end);
		if(baseResourceModel!=null){
			queryMap.put("subjectCode", baseResourceModel.getSubjectCode());
			queryMap.put("gradeCode", baseResourceModel.getGradeCode());
			queryMap.put("unitCode", baseResourceModel.getUnitCode());
			queryMap.put("volumeCode", baseResourceModel.getVolumeCode());
		}
		return sessionTemplate.selectList(USER_FRIEND_RANK, queryMap);
	}
	@Override
	public List<RankModel> findSubjectRank(String subjectCode,String gradeCode) {
		Map<String, Object> findSubjectRankMap=new HashMap<String, Object>();
		findSubjectRankMap.put("subjectCode", subjectCode);
		findSubjectRankMap.put("gradeCode", gradeCode);
		return sessionTemplate.selectList(SUBJECT_RANK, findSubjectRankMap);
	}
	@Override
	public List<WeeklyRanking> findGroupBySubjectGrade() {
		return sessionTemplate.selectList(FINDSUBJECT);
	}
	@Override
	public void updateWeekFightAndIntegral() {
		sessionTemplate.update(UPDATEWEEKFIGHTANDINTEGRAL);
	}
	@Override
	public List<WeekRankingModel> findRanking( Long start,
			Long end, BaseResourceModel baseResourceMod) {
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("start", start);
		queryMap.put("end", end);
		queryMap.put("subjectCode", baseResourceMod.getSubjectCode());
		queryMap.put("gradeCode", baseResourceMod.getGradeCode());
		return sessionTemplate.selectList(FINDCHALLENGERANKING,queryMap);
	}
	
	@Override
	public List<String> findAllMaxGrade(String subjectCode) {
		return sessionTemplate.selectList(FINDMAXGRADECODE,subjectCode);
	}
	@Override
	public Long getCurrentCountTotal(String subjectCode, String gradeCode) {
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		return sessionTemplate.selectOne(GET_CURRENT_COUNT_TOTAL, queryMap);
	}
	@Override
	public List<WeekRankingModel> getHxsTvNationalRank(Map<String, Object> params) {
		return sessionTemplate.selectList(GET_HXSTV_NATIONAL_RANK, params);
	}
	@Override
	public WeekRankingModel findUserNationalRanking(Map<String, Object> queryMap) {
		return sessionTemplate.selectOne(USER_NATIONAL_RANKING, queryMap);
	}
	@Override
	public List<WeekRankingModel> getHxsTvFriendRank(Map<String, Object> params) {
		return sessionTemplate.selectList(GET_FRIEND_TOP, params);
	}
	@Override
	public WeekRankingModel findUserNationalRankingTv(Map<String, Object> queryMap) {
		return sessionTemplate.selectOne(USER_NATIONAL_RANKING_TV, queryMap);
	}
}
