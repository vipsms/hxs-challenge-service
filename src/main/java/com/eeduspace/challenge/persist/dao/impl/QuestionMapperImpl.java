package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.QuestionMapper;
import com.eeduspace.challenge.persist.po.Question;
@Repository("questionMapperImpl")
public class QuestionMapperImpl extends BaseDaoImpl<Question> implements QuestionMapper{
	private final String GET_NOT_DONE_QUESTION="get_not_done_question";
	private final String GET_MAX_QUESTIONSN="get_max_quesionSn";

	@Override
	public List<Question> findNotDoneQuestions(String paperUUID,
			String userCode, String fightUUID) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("paperUUID", paperUUID);
		queryMap.put("userCode", userCode);
		queryMap.put("fightUUID", fightUUID);
		return sessionTemplate.selectList(GET_NOT_DONE_QUESTION, queryMap);
	}
	@Override
	public Long findMaxQuestionSn(String fightUUID) {
		return sessionTemplate.selectOne(GET_MAX_QUESTIONSN,fightUUID);
	}
}
