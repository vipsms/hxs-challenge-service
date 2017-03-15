package com.eeduspace.challenge.mina.factory;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eeduspace.challenge.mina.alive.KeepAliveRequestTimeoutHandlerImpl;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.message.AliveModel;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

/**
 * 心跳监测
 * 
 * @author zhuchaowei 2016年7月22日 Description
 */
public class MinaKeepAliveMessageFactory implements KeepAliveMessageFactory {
    private final Logger logger = LoggerFactory.getLogger(KeepAliveRequestTimeoutHandlerImpl.class);
    @Inject
	private RedisClientTemplate redisClientTemplate;
	/** 心跳包请求 */
	private static final String HEARTBEATREQUEST = "0x11";
	/** 心跳响应 */
	private static final String HEARTBEATRESPONSE = "0x12";
	private Gson gson = new Gson();
	private AliveModel aliveModel = new AliveModel();

	@Override
	public boolean isRequest(IoSession session, Object message) {
//		logger.debug("请求心跳包信息: " + message.toString());
		ClientModel clientModel = gson.fromJson(message.toString(),
				ClientModel.class);
		if(clientModel.getMessageType().equals("hxs_alive_message")){
			aliveModel = gson.fromJson(gson.toJson(clientModel.getMessage()),AliveModel.class);
			if (aliveModel.getAliveMessage().equals(HEARTBEATREQUEST)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
//		logger.debug("响应心跳包信息: " + message.toString());
		ClientModel clientModel = gson.fromJson(message.toString(),ClientModel.class);
		if(clientModel.getMessageType().equals("hxs_alive_message")){
			aliveModel = gson.fromJson(gson.toJson(clientModel.getMessage()),AliveModel.class);
//			logger.debug("------------------userCode----------"+clientModel.getFromUserCode());//测试心跳是否可以把user_code传递过来
			if (aliveModel.getAliveMessage().equals(HEARTBEATRESPONSE)){
				if(StringUtils.isBlank(redisClientTemplate.get(clientModel.getFromUserCode()+"_session"))){
					redisClientTemplate.set(clientModel.getFromUserCode()+"_session", session.getAttribute("id").toString());
				}
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
//		logger.debug("请求预设信息: " + HEARTBEATREQUEST);
		aliveModel = new AliveModel();
		aliveModel.setAliveMessage(HEARTBEATREQUEST);
		ClientModel clientModel = new ClientModel();
		clientModel.setMessageType("hxs_alive_message");
		clientModel.setMessage(aliveModel);
		return gson.toJson(clientModel);
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
//		logger.debug("响应预设信息: " + HEARTBEATRESPONSE);
		aliveModel = new AliveModel();
		aliveModel.setAliveMessage(HEARTBEATRESPONSE);
		ClientModel clientModel = new ClientModel();
		clientModel.setMessageType("hxs_alive_message");
		clientModel.setMessage(aliveModel);
		return gson.toJson(clientModel);// 返回响应消息
	}

}
