package com.eeduspace.challenge.persist.dao;


import java.util.List;
import java.util.Map;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.weeklyrank.RankModel;
import com.eeduspace.challenge.model.weeklyrank.WeekRankingModel;
import com.eeduspace.challenge.persist.po.WeeklyRanking;

public interface WeeklyRankingMapper extends BaseDao<WeeklyRanking>{
	/**
	 * 统计用户当前排名
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 上午10:31:53
	 * @param userCode
	 * @return
	 */
	public Long countUserCurrentRank(String userCode,String subjetCode,String gradeCode);
	/**
	 * 统计用户在好友中的当前排名
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午8:51:24
	 * @param userCode
	 * @return
	 */
	public Long countUserCurrentRankInFriend(String userCode,String subjectCode,String gradeCode);
	
	/**
	 * 用户好友挑战排行版
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月13日 上午9:51:16
	 * @param userCode 用户code
	 * @param start  开始索引 
	 * @param end    结束索引
	 * @return
	 */
	public List<WeekRankingModel> findUserFriendRank(String userCode,Long start,Long end,BaseResourceModel baseResourceModel);
	/**
	 * 学科排行榜
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午7:48:45
	 * @param subjectCode
	 * @return
	 */
	public List<RankModel> findSubjectRank(String subjectCode,String gradeCode);
	
	/**
	 * 根据学年学科分组获取学年和学科信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 上午9:13:03
	 * @return
	 */
	public List<WeeklyRanking> findGroupBySubjectGrade();
	/**
	 * 将战斗力值和积分更新为0
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 上午10:51:12
	 */
	public void updateWeekFightAndIntegral();
	/**
	 * 获取挑战实时排行榜信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 下午6:36:51
	 * @param start
	 * @param end
	 * @param baseResourceMod
	 * @return
	 */
	public List<WeekRankingModel> findRanking(Long start,Long end,BaseResourceModel baseResourceMod);
	/**
	 * 获取每个年级的最高分
	 * @author  作者 : gaofengming
			E-mail : gaofengming@e-eduspace.com
	 * @date 创建时间   ：2016年8月15日下午5:42:58  
	 * @param  gradeCode
	 * @return  List<String>
	 */
	public List<String> findAllMaxGrade(String subjectCode);
	/**
	 * 根据subjectCode和gradeCode查询周榜总人数
	 * @param subjectCode
	 * @param gradeCode
	 * @return
	 */
	public Long getCurrentCountTotal(String subjectCode, String gradeCode);
	
	/**
	 * 根据学科code 学级code 查询全国排行榜前X名
	 * @param params
	 * @return
	 */
	public List<WeekRankingModel> getHxsTvNationalRank(Map<String, Object> params);
	
	/**
	 * 根据用户code 学科code 学年code 查询用户在全国榜中数据
	 * @param queryMap
	 * @return
	 */
	public WeekRankingModel findUserNationalRanking(Map<String, Object> queryMap);
	
	/**
	 * 根据用户code 学科code 学年code 查询好友排行榜前X名
	 * @param params
	 * @return
	 */
	public List<WeekRankingModel> getHxsTvFriendRank(Map<String, Object> params);
	
	/**
	 * 根据用户code 学科code 学年code 查询用户在全国榜中数据 TV
	 * @param queryMap
	 * @return
	 */
	public WeekRankingModel findUserNationalRankingTv(Map<String, Object> queryMap);
}