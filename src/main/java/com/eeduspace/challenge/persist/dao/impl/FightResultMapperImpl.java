package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.model.battle.BattleResultModel;
import com.eeduspace.challenge.persist.dao.FightResultMapper;
import com.eeduspace.challenge.persist.po.FightResult;
@Repository("fightResultMapperImpl")
public class FightResultMapperImpl extends BaseDaoImpl<FightResult> implements FightResultMapper{
	private final String GET_USER_HIGHEST_PAPER="get_user_highest_score_paper";
	private final String GRT_BATTLE_DETAIL="get_battle_result_detail";

	@Override
	public List<FightResult> findUserHighestScorePaper(String userCode,Long paperSize,String subjectCode,String gradeCode) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("paperSize", paperSize);
		queryMap.put("userCode", userCode);
		queryMap.put("subjectCode", subjectCode);
		queryMap.put("gradeCode", gradeCode);
		return sessionTemplate.selectList(GET_USER_HIGHEST_PAPER, queryMap);
	}
	@Override
	public List<BattleResultModel> findBattleResultModel(String fightUUID) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("fightUUID", fightUUID);
		return sessionTemplate.selectList(GRT_BATTLE_DETAIL, queryMap);
	}

}
