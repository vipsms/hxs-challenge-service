package com.eeduspace.challenge.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.battle.BattlePushModel;
import com.eeduspace.challenge.persist.po.UserFight;

public interface UserFightService extends BaseService<UserFight>{
	/**
	 * 挑战排行榜挑开始挑战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 上午10:42:14
	 * @param userCode 挑战者code
	 * @param challenger 被挑战者code
	 * @param paperUUID  试卷uuid
	 * @param challengeType 挑战类型  排行榜挑战  好友挑战
	 */
	public String saveChallenge(String userCode,String challenger,String paperUUID,String challengeType,BaseResourceModel baseResourceModel);
	/**
	 * 对战连接保存对战记录信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午8:04:34
	 * @param userACode 用户A
	 * @param userBCode 用户B
	 * @param battleType 对战类型  好友对战  快速对战
	 * @param baseResourceModel 资源基础信息
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BattlePushModel saveBattle(String userACode,String userBCode,String battleType,BaseResourceModel baseResourceModel) throws ClientProtocolException, IOException;
	/**
	 * 匹配对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月14日 下午8:17:41
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BattlePushModel matchBattle(BaseResourceModel baseResourceModel,String userCode,String battleType) throws ClientProtocolException, IOException;
	/**
	 * 与机器人匹配对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月19日 上午10:26:44
	 * @param baseResourceModel
	 * @param userCode
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BattlePushModel battleWithRobot(BaseResourceModel baseResourceModel,String userCode) throws ClientProtocolException, IOException;
	/**
	 * 同意与好友对战
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年8月1日 下午1:04:49
	 * @param baseResourceModel
	 * @param userCode
	 * @param friendCode
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public BattlePushModel agreeBattleWithFriend(BaseResourceModel baseResourceModel,String userCode,String friendCode) throws ClientProtocolException, IOException;
}
