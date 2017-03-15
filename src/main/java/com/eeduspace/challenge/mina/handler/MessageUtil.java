package com.eeduspace.challenge.mina.handler;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eeduspace.challenge.controller.BattleController;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

public class MessageUtil {
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	private Gson gson=new Gson();
	
	private final Logger logger = LoggerFactory.getLogger(BattleController.class);
	/**
	 * 给指定的客户端发送消息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年6月27日 上午10:41:50
	 * @param clientModel
	 */
	public void sendMessageToOne(ClientModel clientModel){
		;
		//String requestId= redisClientTemplate.get("session_getAttribute_"+clientModel.getToUserCode());//中间变量
		String longString=redisClientTemplate.get(clientModel.getToUserCode()+"_session");
		if(longString==null||longString.equals("")){
			return;
		}else{
			Long sessionID=Long.valueOf(longString);
			if(sessionID!=null){
				IoSession session = ServerMessageHandler.sessionsMap.get(sessionID);
				if (session == null) {
					return;
				}
				session.write(gson.toJson(clientModel));
			}
		}
	}
}	
