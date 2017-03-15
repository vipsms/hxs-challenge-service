package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.model.UserAnswerDeliaModel;
import com.eeduspace.challenge.persist.dao.UserAnswerMapper;
import com.eeduspace.challenge.persist.po.UserAnswer;
@Repository("userAnswerMapperImpl")
public class UserAnswerMapperImpl extends BaseDaoImpl<UserAnswer> implements UserAnswerMapper{
	private final String GET_USER_ANSWER="get_user_answer";
	@Override
	public List<UserAnswerDeliaModel> findUserAnswerDelia(String userCode,
			String fightUUID, String paperUUID) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		queryMap.put("paperUUID", paperUUID);
		queryMap.put("fightUUID", fightUUID);
		return sessionTemplate.selectList(GET_USER_ANSWER, queryMap);
	}
}
