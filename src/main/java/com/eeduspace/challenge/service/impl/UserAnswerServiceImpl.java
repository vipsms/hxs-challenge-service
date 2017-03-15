package com.eeduspace.challenge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.enumeration.FightEnum;
import com.eeduspace.challenge.model.UserAnswerDeliaModel;
import com.eeduspace.challenge.model.paper.QuesionOptionModel;
import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.dao.UserAnswerMapper;
import com.eeduspace.challenge.persist.po.UserAnswer;
import com.eeduspace.challenge.service.UserAnswerService;

@Service("userAnswerServiceImpl")
public class UserAnswerServiceImpl extends BaseServiceImpl<UserAnswer>
		implements UserAnswerService {
	@Resource
	private UserAnswerMapper userAnswerMapperImpl;
	@Autowired
	public UserAnswerServiceImpl(UserAnswerMapper userAnswerMapper) {
		this.baseDao = userAnswerMapper;
	}
	@Override
	public List<UserAnswerDeliaModel> findAnswerDelia(String userCode,
			String fightUUID, String paperUUID) {
		return userAnswerMapperImpl.findUserAnswerDelia(userCode, fightUUID, paperUUID);
	}
	@Override
	public List<UserAnswerDeliaModel> findAnswerDelia(String fightUUID,
			String paperUUID) {
		return this.findAnswerDelia(null, fightUUID, paperUUID);
	}
	@Override
	public Long countRight(String paperUUID, String userCode, String fightUUID) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("paperUuid", paperUUID);
		queryMap.put("userCode", userCode);
		queryMap.put("fightRecordUuid", fightUUID);
		queryMap.put("isRight", true);
		return (long) this.findByCondition(queryMap).size();
	}
	@Override
	public UserAnswer saveRobotAnswer(QuestionEntity questionEntity,String fightUUID,String questionSn) {
		UserAnswer userAnswer=new UserAnswer();
		userAnswer.setCreateDate(new Date());
		userAnswer.setFightRecordUuid(fightUUID);
		userAnswer.setFightType(FightEnum.BATTLE.getValue());
		String optionString=randomOption(questionEntity.getOptions());
		if(optionString.equals(questionEntity.getAnswer())){
			userAnswer.setIsRight(true);
		}else{
			userAnswer.setIsRight(false);
		}
		userAnswer.setUserOption(optionString);
		userAnswer.setPaperUuid(questionEntity.getPaperUUID());
		userAnswer.setQuestionSn(questionSn);
		userAnswer.setRightOption(questionEntity.getAnswer());
		userAnswer.setQuestionUuid(questionEntity.getQuesionUUID());
		userAnswer.setUserCode("eeduspacerobot");
		this.save(userAnswer);
		return userAnswer;
	}
	/**
	 * 随机选项
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月2日 下午5:21:26
	 * @param options
	 * @return
	 */
	public String randomOption(List<QuesionOptionModel> options){
		String op="";
		Random random=new Random();
		int item=random.nextInt(options.size());
		Map optionModel=(Map) options.get(item);
		op=optionModel.get("optionKey").toString();
		return op;
	}
	
	
}
