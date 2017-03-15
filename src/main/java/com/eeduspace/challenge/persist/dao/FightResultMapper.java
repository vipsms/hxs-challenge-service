package com.eeduspace.challenge.persist.dao;

import java.util.List;

import com.eeduspace.challenge.model.battle.BattleResultModel;
import com.eeduspace.challenge.persist.po.FightResult;


public interface FightResultMapper extends BaseDao<FightResult>{
	/**
	 * 获取用户得分最高的N张卷子  默认值为5
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午4:32:51
	 * @param paperSize  卷子数量
	 * @param userCode 用户code
	 * @return
	 */
	public List<FightResult> findUserHighestScorePaper(String userCode,Long paperSize,String subjectCode,String gradeCode);

	/**
	 * 获取对战结果详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月16日 上午10:43:05
	 * @param fightUUID 对战记录UUID
	 * @return
	 */
	public List<BattleResultModel> findBattleResultModel(String fightUUID);
}