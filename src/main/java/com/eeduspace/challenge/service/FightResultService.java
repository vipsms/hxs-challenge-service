package com.eeduspace.challenge.service;

import java.util.List;

import com.eeduspace.challenge.enumeration.BattleEnum;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.battle.BattleResultModel;
import com.eeduspace.challenge.model.battle.BattleResultSubmitModel;
import com.eeduspace.challenge.model.challenge.ChallengeResultModel;
import com.eeduspace.challenge.persist.po.FightResult;

public interface FightResultService extends BaseService<FightResult>{
	/**
	 * 获取用户得分最高的N张卷子
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午4:42:16
	 * @param userCode 用户code
	 * @param paperSize 卷子数量
	 * @return
	 */
	public List<FightResult> findUserHighestPaper(String userCode,Long paperSize,String subjectCode,String gradeCode);
	/**
	 * 获取用户得分最高的N张卷子默认5张
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午4:42:16
	 * @param userCode 用户code
	 * @return
	 */
	public List<FightResult> findUserHighestPaper(String userCode,String subjectCode,String gradeCode);
	/**
	 * 保存个人答题结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月13日 上午11:51:08
	 * @param fightResult
	 */
	public void saveUserPaper(FightResult fightResult);
	/**
	 * 计算挑战结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月13日 下午4:02:09
	 * @param challengerCombatValue 被挑战者战斗力值
	 * @param challengerFightUUID 被挑战者记录UUID
	 * @param userCode 用户code
	 * @param challengerCode 被挑战者code
	 * @param challengerName 被挑战者名次
	 * @param getScore 用户做题得分
	 * @param useTime  用户做题用时
	 * @param userRank 用户当前排名
	 * @return
	 */
	public ChallengeResultModel computeChallengeFightResult(String challengerCombatValue,String challengerFightUUID,String userCode,String challengerCode,String challengerName,String getScore,Long useTime,Long userRank,String userFightUUID,BaseResourceModel baseResourceModel,String fightType);
	/**
	 * 计算对战结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午8:22:35
	 * @param fightUUID 对战UUID
	 * @param battleEnum 对战类型
	 */
	public void computeBattleFightResult(String fightUUID,BattleEnum battleEnum);
	/**
	 *  提交对战答题结果信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 上午9:17:28
	 * @param userCode 用户code 
	 * @param useTime  答题用时
	 * @param score    答题总得分
	 * @param baseResourceModel  资源基础信息
	 * @param fightUUID 对战记录UUID
	 */
	public BattleResultSubmitModel submitResult(String userCode,Long useTime,Double score,BaseResourceModel baseResourceModel,
			String fightUUID,String paperUUID,BattleEnum battleEnum,Boolean isWithRobot,String unitCode);
	/**
	 * 获取对战结果详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月16日 上午9:38:08
	 * @param fightUUID
	 */
	public List<BattleResultModel> getBattleDetail(String fightUUID);
	/**
	 * 对战断线处理
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月21日 下午4:53:53
	 * @param userCode 用户code
	 * @param fightUUID  对战记录UUID
	 */
	public void disconnectionFight(String userCode,String fightUUID);
	
	/**
	 * 校验是否有挑战资格
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月4日 上午11:55:40
	 * @param userCode
	 * @param subjectCode
	 * @param gradeCode
	 * @return
	 */
	public Boolean verifyEligibility(String userCode,String subjectCode,String gradeCode);
}
