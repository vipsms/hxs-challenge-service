package com.eeduspace.challenge.asynchronous.event;

import org.springframework.context.ApplicationEvent;

public class HandleFightResultNoticeEvent extends ApplicationEvent{

	public HandleFightResultNoticeEvent(Object source) {
		super(source);
	}

}
