package com.eeduspace.challenge.asynchronous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.asynchronous.event.HandleFightResultNoticeEvent;
import com.eeduspace.challenge.asynchronous.event.HandleLogEvent;
import com.eeduspace.challenge.asynchronous.event.HandleUserFriendEvent;
import com.eeduspace.challenge.asynchronous.event.HandleUserOnlineStateEvent;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.BattleResultNotice;
import com.eeduspace.challenge.persist.po.UserFriendApply;
import com.eeduspace.challenge.persist.po.UserTaskLog;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:
 */
@Service
public class EventOperationService {


    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 处理好友申请
     * @param obj
     */
    @Async
    public void handleSendFriend(UserFriendApply obj) {
        applicationContext.publishEvent(new HandleUserFriendEvent(obj));
    }
    
    /**
     * 处理日志
     */
    @Async
    public void handleUserLog(UserTaskLog log){
    	applicationContext.publishEvent(new HandleLogEvent(log));
    }
    /**
     * 处理老用户在线状态
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年8月4日 下午4:56:19
     * @param userModel
     */
    @Async
    public void userOnLineState(UserModel userModel){
    	applicationContext.publishEvent(new HandleUserOnlineStateEvent(userModel));
    }
    
    @Async
    public void userFightResultNotice(BattleResultNotice battleResultNotice){
    	applicationContext.publishEvent(new HandleFightResultNoticeEvent(battleResultNotice));
    }
}
