package com.eeduspace.challenge.persist.dao;

import java.util.List;

import com.eeduspace.challenge.model.UserAnswerDeliaModel;
import com.eeduspace.challenge.persist.po.UserAnswer;


public interface UserAnswerMapper extends BaseDao<UserAnswer>{
	/**
	 * 获取用户答题详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 上午9:59:25
	 * @param userCode 用户code
	 * @param fightUUID 战斗记录uuid
	 * @param paperUUID 试卷UUID
	 * @return
	 */
	public List<UserAnswerDeliaModel> findUserAnswerDelia(String userCode,String fightUUID,String paperUUID);
}