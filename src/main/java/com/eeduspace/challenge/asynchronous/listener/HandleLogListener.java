package com.eeduspace.challenge.asynchronous.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.eeduspace.challenge.asynchronous.event.HandleLogEvent;
import com.eeduspace.challenge.persist.po.UserTaskLog;
import com.eeduspace.challenge.service.UserTaskLogService;
import com.google.gson.Gson;

/**
 * Author:
 * Date: 2016/7/22
 * Description:日志监听器
 */
@Component
public class HandleLogListener implements SmartApplicationListener{
	@Autowired
	private UserTaskLogService userTaskLogService;
	private Gson gson=new Gson();
	private Logger log=LoggerFactory.getLogger(HandleLogListener.class);
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
        UserTaskLog userLog=(UserTaskLog) event.getSource();
        log.debug("HandleLogListenerImpl event.getSource():{}",gson.toJson(userLog));
        userTaskLogService.save(userLog);
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return eventType==HandleLogEvent.class;
	}

	@Override
	public boolean supportsSourceType(Class<?> sourceType) {
		return sourceType==UserTaskLog.class;
	}

}
