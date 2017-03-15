package com.eeduspace.challenge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.convert.QuestionConvert;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.UserAnswerDeliaModel;
import com.eeduspace.challenge.model.battle.BattleUserAnswerModel;
import com.eeduspace.challenge.model.challenge.ChallengeQuestionModel;
import com.eeduspace.challenge.model.challenge.ChallengeUserAnswerModel;
import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.dao.QuestionMapper;
import com.eeduspace.challenge.persist.po.Question;
import com.eeduspace.challenge.service.QuestionService;
import com.eeduspace.challenge.service.UserAnswerService;
import com.eeduspace.challenge.service.UserFightService;

@Service("questionServiceImpl")
public class QuestionServiceImpl extends BaseServiceImpl<Question>
		implements QuestionService {
	@Resource
	private QuestionMapper questionMapperImpl;
	@Autowired
	private UserFightService userFightServiceImpl;
	@Autowired 
	private UserAnswerService userAnswerServiceImpl;
	@Autowired
	public QuestionServiceImpl(QuestionMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	@Override
	public List<Question> findByPaperUUID(String paperUUID) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("paperUuid", paperUUID);
		return this.findByCondition(queryMap);
	}
	@Override
	public ChallengeQuestionModel getChallengeQuestion(String paperUUID,
			String userCode, String challengerCode, String challengeType,
			BaseResourceModel baseResourceModel) {
		String fightUUID=userFightServiceImpl.saveChallenge(userCode, challengerCode, paperUUID, challengeType, baseResourceModel);
		ChallengeQuestionModel challengeQuestionModel=new ChallengeQuestionModel();
		challengeQuestionModel.setFightUUID(fightUUID);
		List<Question> qList=this.findByPaperUUID(paperUUID);
		List<QuestionEntity> questionEntities=new ArrayList<>();
		for (Question question : qList) {
			QuestionEntity qEntity=QuestionConvert.toQuestionEctity(question);
			questionEntities.add(qEntity);
		}
		challengeQuestionModel.setQuestions(questionEntities);
		return challengeQuestionModel;
	}
	@Override
	public ChallengeUserAnswerModel getChallengeAnswer(String userCode,
			String fight, String challengerCode, String challengerFightUUID,
			String paperUUID) {
		List<UserAnswerDeliaModel> userAnswerDeliaModels=userAnswerServiceImpl.findAnswerDelia(userCode, fight, paperUUID);
		List<UserAnswerDeliaModel>challengerAnswerDeliaModels=userAnswerServiceImpl.findAnswerDelia(challengerCode, challengerFightUUID, paperUUID);
		ChallengeUserAnswerModel answerModel=new ChallengeUserAnswerModel();
		answerModel.setMyAnswer(userAnswerDeliaModels);
		answerModel.setOtherAnswer(challengerAnswerDeliaModels);
		return answerModel;
	}
	@Override
	public BattleUserAnswerModel getBattleAnswer(String myUserCode,
			String otherUserCode, String fightUUID,String paperUUID) {
		List<UserAnswerDeliaModel> userAnswerDeliaModels=userAnswerServiceImpl.findAnswerDelia(myUserCode, fightUUID, paperUUID);
		List<UserAnswerDeliaModel>challengerAnswerDeliaModels=userAnswerServiceImpl.findAnswerDelia(otherUserCode, fightUUID, paperUUID);
		BattleUserAnswerModel answerModel=new BattleUserAnswerModel();
		answerModel.setMyAnswer(userAnswerDeliaModels);
		answerModel.setOtherAnswer(challengerAnswerDeliaModels);
		return answerModel;
	}
	@Override
	public List<Question> getNotDoneQuestions(String paperUUID,
			String userCode, String fightUUID) {
		return questionMapperImpl.findNotDoneQuestions(paperUUID, userCode, fightUUID);
	}
	@Override
	public Long getMaxQuestionSn(String fightUUID) {
		return questionMapperImpl.findMaxQuestionSn(fightUUID);
	}
}
