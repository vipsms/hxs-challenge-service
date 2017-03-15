package com.eeduspace.challenge.asynchronous.event;

import org.springframework.context.ApplicationEvent;

public class HandleUserOnlineStateEvent extends ApplicationEvent{

	public HandleUserOnlineStateEvent(Object source) {
		super(source);
	}

}
