package com.eeduspace.challenge.mina.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import com.eeduspace.challenge.mina.model.ClientModel;
import com.google.gson.Gson;

public class SessionMap {
	/**
	 * @Description: 单例工具类，保存所有mina客户端连接
	 * 
	 */
	private final static Log log = LogFactory.getLog(SessionMap.class);
	private Gson gson=new Gson();
	private static SessionMap sessionMap = null;

	private Map<String, IoSession> map = new HashMap<String, IoSession>();

	// 构造私有化 单例
	private SessionMap() {
	}

	/**
	 * @Description: 获取唯一实例
	 */
	public static SessionMap newInstance() {
		log.debug("SessionMap单例获取---");
		if (sessionMap == null) {
			sessionMap = new SessionMap();
		}
		return sessionMap;
	}

	/**
	 * @Description: 保存session会话
	 */
	public void addSession(String key, IoSession session) {
		log.debug("保存会话到SessionMap单例---key=" + key);
		this.map.put(key, session);
	}

	/**
	 * @Description: 根据key查找缓存的session
	 */
	public IoSession getSession(String key) {
		log.debug("获取会话从SessionMap单例---key=" + key);
		return this.map.get(key);
	}

	/**
	 * @Description: 发送消息到客户端
	 */
	public void sendMessage(String[] keys, Object message) {
		for (String key : keys) {
			IoSession session = getSession(key);

			log.debug("反向发送消息到客户端Session---key=" + key + "----------消息="
					+ message);
			if (session == null) {
				return;
			}
			session.write(message);

		}
	}
	/**
	 * 给指定的客户端发送消息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年6月27日 上午10:41:50
	 * @param clientModel
	 */
	public void sendMessageToOne(ClientModel clientModel){
		IoSession session = getSession(clientModel.getToUserCode());
		if (session == null) {
			return;
		}
		session.write(gson.toJson(clientModel));
	}
	/**
	 * 群发消息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年6月27日 上午10:42:07
	 * @param clientModel
	 */
	public void sendMessageToMany(ClientModel clientModel){
		for (String userCode : clientModel.getToUserCodes()) {
			IoSession session = getSession(userCode);
			log.debug("反向发送消息到客户端Session---key=" + userCode + "----------消息="
					+ clientModel.getMessage());
			if (session == null) {
				return;
			}
			session.write(gson.toJson(clientModel));
		}
	}

}
