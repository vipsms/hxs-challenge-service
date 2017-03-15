package com.eeduspace.challenge.mina.handler;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eeduspace.challenge.asynchronous.EventOperationService;
import com.eeduspace.challenge.enumeration.MessageEnumClass;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.enumeration.UserEnum.OnlineStatus;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.persist.po.Message;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.MessageService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

public class ServerMessageHandler implements IoHandler {
	@Inject
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private FightResultService fightResultServiceImpl;
	@Autowired
	private MessageService messageServiceImpl;
	@Inject
	private EventOperationService eventOperationService;
	@Autowired
	private MessageUtil messageUtil;
	public static ConcurrentMap<Long, IoSession> sessionsMap = new ConcurrentHashMap<Long, IoSession>();
	private Gson gson = new Gson();
	private final Logger log = LoggerFactory
			.getLogger(ServerMessageHandler.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {

	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		log.debug("message---{}" + gson.toJson(message.toString()));
		ClientModel clientModel = gson.fromJson(message.toString(),
				ClientModel.class);
		//saveMessage(clientModel);
		log.debug("clientModel{}" + gson.toJson(clientModel));
		if (clientModel.getMessageType().equals("createmessage")) {// 消息类型为用户注册绑定链接
			redisClientTemplate.set(session.getAttribute("id") + "_user",clientModel.getFromUserCode());
			//redisClientTemplate.set("session_getAttribute_"+clientModel.getFromUserCode(), session.getAttribute("id")+"");
			redisClientTemplate.set(clientModel.getFromUserCode() + "_session",session.getAttribute("id").toString());
			UserModel userModel=new UserModel();
			userModel.setUserCode(clientModel.getFromUserCode());
			userModel.setUserOnlineState(OnlineStatus.On_line.getValue());
			eventOperationService.userOnLineState(userModel);
		}else{
			messageUtil.sendMessageToOne(clientModel);
		}
//		
//		if (clientModel.getMessageType().equals("battlemessage")) {
//			log.debug("clientModel{}" + gson.toJson(clientModel));
//		}
//		// 聊天信息 需要发送给另一个客户端
//		if (clientModel.getMessageType().equals("chatmessage")
//				|| clientModel.getMessageType().equals("userfriend")) {
//			messageUtil.sendMessageToOne(clientModel);
//		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("messageSent{}", gson.toJson(message));
		log.debug("sessionID:{}", session.getId(), session.getServiceAddress());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO 断开连接是更改用户状态信息 清除redis中 用户对战的信息
		try {
			String userCode = redisClientTemplate.get(session
					.getAttribute("id") + "_user");
			log.debug("userCode:{}", userCode);
			// TODO 查找该用户是否在对战状态
			// TODO 如果在对战状态则进行对战结果处理
			log.debug("=====================从redis中获取用户的userCode为：：================="+"battle" + userCode);
			log.debug("=====================从redis中获取用户fightUUID为：：================="+redisClientTemplate.get("battle" + userCode));
			String fightUUID = redisClientTemplate.get("battle" + userCode);
			log.debug("取到的fightuuid为=========fightUUID："+fightUUID);
			redisClientTemplate.del(session.getAttribute("id") + "_user");// 删除链接信息
			redisClientTemplate.del(userCode + "_session");// 删除session关系
			sessionsMap.remove(session.getAttribute("id"));// 删除session
			log.debug(userCode+"-----------------断线打印-----------"+fightUUID);
			detailFight(userCode, fightUUID);
			log.info("关闭当前session：{}#{}", session.getAttribute("id"),
					session.getRemoteAddress());
		} catch (Exception e) {
			log.error("sessionClosed:{}", e);
		}
	}

	/**
	 * 断线处理 答题结果 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年7月21日
	 * 下午5:31:42
	 * 
	 * @param userCode
	 * @param fightUUID
	 */
	public void detailFight(final String userCode, final String fightUUID) {
		new Thread() {
			@Override
			public void run() {
				try {
					if (fightUUID != null && !fightUUID.equals("")) {
						fightResultServiceImpl.disconnectionFight(userCode,
								fightUUID);
					}
					User user = userService.findByUserCode(userCode);
					log.debug(user.getUserOnlineState()+"==========detailFight==========="+user.getUserCode());
					if (user != null) {
						user.setUpdateDate(new Date());
						user.setUserOnlineState(UserEnum.OnlineStatus.Off_line
								.getValue());// 0离线
						userService.update(user);
					}
				} catch (Exception e) {
					log.error("detailFight：{}", e);
				}
			}
		}.start();
	}

	/**
	 * 保存消息信息 Author： zhuchaowei e-mail:zhuchaowei@e-eduspace.com 2016年7月26日
	 * 下午6:43:12
	 * 
	 * @param clientModel
	 */
	public void saveMessage(final ClientModel clientModel) {
		new Thread() {
			@Override
			public void run() {
				try {
					Message message=new Message();
					message.setCreateDate(new Date());
					message.setMessage(gson.toJson(clientModel));
					message.setReadState(MessageEnumClass.MessageReadStateEnum.NOREAD.getValue());
					message.setSendDate(new Date());
					message.setReceiveUser(clientModel.getToUserCode());
					message.setSendUser(clientModel.getFromUserCode());
					if(clientModel.getMessageType().equals("userfriend")){
						message.setMessageType(MessageEnumClass.MessageEnum.FRIENDMESSAGE.getCode());
					}
					else if(clientModel.getMessageType().equals("battleResultMessage")){
						message.setMessageType(MessageEnumClass.MessageEnum.SYSTEMMESSAGE.getCode());
					}else{
						message.setMessageType(MessageEnumClass.MessageEnum.OTHERMESSAGE.getCode());
					}
					messageServiceImpl.save(message);
				} catch (Exception e) {
					log.error("saveMessage:{}",e);
				}
			}
		}.start();
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		Long time = System.currentTimeMillis();
		session.setAttribute("id", time);
		sessionsMap.put(time, session);
		// sessionsMap= (ConcurrentMap<Long, IoSession>)
		// session.getService().getManagedSessions();
		log.debug("----------sessionCreated----创建-----");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		//log.debug("------------sessionIdle---空闲------------");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.debug("------------sessionOpened---打开------------");
	}

	// @Override
	public void inputClosed(IoSession session) throws Exception {
		session.closeOnFlush();
		log.debug("------------inputClosed---------------");
	}

}
