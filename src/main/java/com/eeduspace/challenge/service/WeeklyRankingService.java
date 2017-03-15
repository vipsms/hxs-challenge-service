package com.eeduspace.challenge.service;

import java.util.List;
import java.util.Map;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.weeklyrank.RankModel;
import com.eeduspace.challenge.model.weeklyrank.WeekRankingModel;
import com.eeduspace.challenge.persist.po.WeeklyRanking;

public interface WeeklyRankingService extends BaseService<WeeklyRanking>{
	/**
	 * 查询周实时排行榜前N名
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月11日 下午8:55:11
	 * @param item 个数
	 * @return
	 */
	public List<WeeklyRanking> findTop(Long item);
	/**
	 * 统计用户周榜实时榜当前排名
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 上午10:38:43
	 * @param userCode
	 * @return
	 */
	public Long countCurrentRank(String userCode,String subjectCode,String gradeCode);
	/**
	 * 统计用户在好友中的当前排名
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午8:50:31
	 * @param userCode
	 * @return
	 */
	public Long countCurrentRankInFriend(String userCode,String subjectCode,String gradeCode);
	/**
	 * 查询排行榜指定区间数据
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午3:03:30
	 * @param start 区间开始值
	 * @param end   区间结束值
	 * @return
	 */
	public List<WeeklyRanking> findUserRank(Long start,Long end);
	
	/**
	 * 查询排行榜指定区间数据
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午3:03:30
	 * @param start 区间开始值
	 * @param end   区间结束值
	 * @param baseResourceModel 资源基础信息条件
	 * @return
	 */
	public List<WeeklyRanking> findUserRank(Long start,Long end,BaseResourceModel baseResourceModel);
	
	/**
	 * 获取挑战者名次附近排名数据
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午3:06:51
	 * @param userCode 挑战者code
	 * @return
	 */
	public List<WeeklyRanking> findChallengerRank(String userCode,String subjectCode,String gradeCode);
	
	/**
	 * 周榜实时排行榜
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午7:17:49
	 * @param order
	 * @param currentRank
	 * @param item
	 * @param baseResourceModel
	 * @return
	 */
	public List<WeeklyRanking> findWeeklyRanking(String order,Long currentRank,Long item,BaseResourceModel baseResourceModel);
	/**
	 * 好友实时排行榜
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月13日 上午10:01:58
	 * @param order
	 * @param currentRank
	 * @param item
	 * @param baseResourceModel
	 * @param userCode
	 * @return
	 */
	public List<WeekRankingModel> findFriendRanking(String userCode,String order,Long currentRank,Long item,BaseResourceModel baseResourceModel);
	/**
	 * 学科实时排行榜根据学年分组
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午7:51:57
	 * @param subjectCode
	 * @return
	 */
	public List<RankModel> findSubjectRank(String subjectCode,String gradeCode);
	/**
	 * 保存周实时榜信息 保存时校验是否存在并验证是否符合条件
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月21日 上午11:51:33
	 * @param userCode 用户code
	 * @param subjectCode 学科code
	 * @param gradeCode 学年code
	 */
	public void saveWeekRanking(String userCode,String subjectCode,String gradeCode);
	
	/**
	 * 获取实时周榜中所有的学年和学科
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 上午9:18:40
	 * @return
	 */
	public List<WeeklyRanking> findSubjectAndGrade();
	/**
	 * 将战斗力和积分更新为0
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 上午10:53:01
	 */
	public void updateWeekFightAndIntegral();
	/**
	 * 获取挑战排行榜信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月25日 下午6:21:50
	 * @param order
	 * @param currentRank
	 * @param item
	 * @param baseResourceModel
	 * @return
	 */
	public List<WeekRankingModel>  findNationalRank(String order,Long currentRank,Long item,BaseResourceModel baseResourceModel);
	public List<WeekRankingModel>  findNationalRank(Long currentRank,Long item,BaseResourceModel baseResourceModel);
	
	public List<WeekRankingModel>  findFriendRank(String userCode,String order,Long currentRank,Long item,BaseResourceModel baseResourceModel);
	public List<WeekRankingModel>  findFriendRank(String userCode,Long currentRank,Long item,BaseResourceModel baseResourceModel);
	/**
	 * 根据app传递过来的subject找到每个年级这个学科对应的最高分
	 * @author  作者 : gaofengming
			E-mail : gaofengming@e-eduspace.com
	 * @date 创建时间   ：2016年8月15日下午5:37:12  
	 * @param  gradeCode
	 * @return  List<String>
	 */
	public List<String> findAllMaxGrade(String subjectCode);
	/**
	 * 根据subjectcode和gradecode查询周榜的总人数
	 * @param subjectCode
	 * @param gradeCode
	 * @return
	 */
	public Long countCurrentRankTotal(String subjectCode, String gradeCode);
	
	/**
	 * 根据学科code 学级code 查询全国排行榜前X名
	 * @param params
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
