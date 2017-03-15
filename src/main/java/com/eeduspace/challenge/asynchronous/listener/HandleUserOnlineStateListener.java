package com.eeduspace.challenge.asynchronous.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.eeduspace.challenge.asynchronous.event.HandleUserOnlineStateEvent;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.service.UserService;
import com.google.gson.Gson;

@Component
public class HandleUserOnlineStateListener implements SmartApplicationListener{
    private static final Logger logger = LoggerFactory.getLogger(HandleUserOnlineStateListener.class);
	private Gson gson=new Gson();
	@Autowired
	private UserService userService;
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
			UserModel userModel=(UserModel) event.getSource();
			logger.debug("HandleLogListenerImpl event.getSource():{}",gson.toJson(userModel));
			User user=userService.findByUserCode(userModel.getUserCode());
			if(user!=null){
				user.setUserOnlineState(userModel.getUserOnlineState());
				userService.update(user);
			}
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return eventType==HandleUserOnlineStateEvent.class;
	}

	@Override
	public boolean supportsSourceType(Class<?> sourceType) {
		return sourceType==UserModel.class;
	}

}
