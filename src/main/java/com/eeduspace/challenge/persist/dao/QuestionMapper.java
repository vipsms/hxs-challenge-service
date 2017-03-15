package com.eeduspace.challenge.persist.dao;

import java.util.List;

import com.eeduspace.challenge.persist.po.Question;

public interface QuestionMapper extends BaseDao<Question>{
	/**
	 * 获取用户未答的试题
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月2日 上午10:38:37
	 * @param paperUUID
	 * @param userCode
	 * @param fightUUID
	 * @return
	 */
	List<Question> findNotDoneQuestions(String paperUUID,String userCode,String fightUUID);
	
	/**
	 * 获取最大试题编号
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月2日 上午10:42:05
	 * @param fightUUID
	 * @return
	 */
	Long findMaxQuestionSn(String fightUUID);
}