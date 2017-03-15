package com.eeduspace.challenge.asynchronous.event;

import org.springframework.context.ApplicationEvent;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:
 */
public class HandleUserFriendEvent extends ApplicationEvent {
    public HandleUserFriendEvent(Object source) {
        super(source);
    }
}
