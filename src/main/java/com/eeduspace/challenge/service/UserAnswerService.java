package com.eeduspace.challenge.service;

import java.util.List;

import com.eeduspace.challenge.model.UserAnswerDeliaModel;
import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.po.UserAnswer;

public interface UserAnswerService extends BaseService<UserAnswer>{
	/**
	 * 获取用户答题详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 上午10:12:05
	 * @param userCode 用户code
	 * @param fightUUID 战斗记录UUID
	 * @param paperUUID 试卷UUID
	 * @return
	 */
	List<UserAnswerDeliaModel> findAnswerDelia(String userCode,String fightUUID,String paperUUID);
	/**
	 * 获取对战双方答题详情
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 上午10:12:18
	 * @param fightUUID  战斗记录UUID
	 * @param paperUUID  试卷UUID
	 * @return
	 */
	List<UserAnswerDeliaModel> findAnswerDelia(String fightUUID,String paperUUID);
	/**
	 * 统计正确题的数量
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 下午5:08:04
	 * @param paperUUID  试卷ID
	 * @param userCode   用户code
	 * @param fightUUID  战斗记录UUID
	 * @return
	 */
	Long countRight(String paperUUID,String userCode,String fightUUID);
	/**
	 * 保存机器人答题
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月2日 下午4:27:04
	 * @param questionEntity 试题实体
	 * @param fightUUID  对战记录UUID
	 * @param questionSn 试题序号
	 */
	public UserAnswer saveRobotAnswer(QuestionEntity questionEntity,String fightUUID,String questionSn );
}
