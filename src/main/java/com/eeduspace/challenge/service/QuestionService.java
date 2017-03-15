package com.eeduspace.challenge.service;

import java.util.List;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.battle.BattleUserAnswerModel;
import com.eeduspace.challenge.model.challenge.ChallengeQuestionModel;
import com.eeduspace.challenge.model.challenge.ChallengeUserAnswerModel;
import com.eeduspace.challenge.persist.po.Question;

public interface QuestionService extends BaseService<Question>{
	/**
	 * 根据试卷UUID获取试题信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午5:50:09
	 * @param paperUUID
	 * @return
	 */
	List<Question> findByPaperUUID(String paperUUID);
	/**
	 * 获取挑战试题
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 上午9:34:54
	 * @param paperUUID 试卷UUID
	 * @param userCode 用户code
	 * @param challengerCode 被挑战者code
	 * @param baseResourceModel 资源基础信息
	 * @param challengeType 挑战类型
	 * @return
	 */
	ChallengeQuestionModel getChallengeQuestion(String paperUUID,String userCode,String challengerCode,String challengeType,BaseResourceModel baseResourceModel);
	/**
	 * 获取挑战答题信息详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 上午10:27:49
	 * @param userCode 用户code
	 * @param fight  用户挑战记录uuid
	 * @param challengerCode 被挑战者uuid
	 * @param challengerFightUUID 被挑战者挑战记录UUID
	 * @param paperUUID 试卷UUID
	 * @return
	 */
	ChallengeUserAnswerModel getChallengeAnswer(String userCode,String fight,String challengerCode,String challengerFightUUID,String paperUUID);
	/**
	 * 获取对战答题信息详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月18日 下午6:57:51
	 * @param myUserCode
	 * @param otherUserCode
	 * @param fightUUID
	 * @return
	 */
	BattleUserAnswerModel getBattleAnswer(String myUserCode,String otherUserCode,String fightUUID,String paperUUID);
	/**
	 * 获取未答试题信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月2日 上午10:49:34
	 * @param paperUUID
	 * @param userCode
	 * @param fightUUID
	 * @return
	 */
	List<Question> getNotDoneQuestions(String paperUUID,String userCode,String fightUUID);
	
	/**
	 * 获取最大题号
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月2日 上午10:51:27
	 * @param fightUUID
	 * @return
	 */
	Long getMaxQuestionSn(String fightUUID);
}
