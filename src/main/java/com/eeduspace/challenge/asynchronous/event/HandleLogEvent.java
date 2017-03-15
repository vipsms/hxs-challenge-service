package com.eeduspace.challenge.asynchronous.event;

import org.springframework.context.ApplicationEvent;

public class HandleLogEvent extends ApplicationEvent{

	public HandleLogEvent(Object source) {
		super(source);
	}

}
