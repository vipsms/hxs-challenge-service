package com.eeduspace.challenge.asynchronous.listener;

import com.eeduspace.challenge.asynchronous.event.HandleUserFriendEvent;
import com.eeduspace.challenge.enumeration.NoticeEnum;
import com.eeduspace.challenge.model.NoticeModel;
import com.eeduspace.challenge.model.UserFriendModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.persist.dao.UserFriendApplyMapper;
import com.eeduspace.challenge.persist.po.Notice;
import com.eeduspace.challenge.persist.po.UserFriendApply;
import com.eeduspace.challenge.service.NoticeService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.util.JPushUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:好友监听器
 */
@Component
public class HandleUserFriendListener implements SmartApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(HandleUserFriendListener.class);

    @Inject
    private Gson gson;
//    @Inject
//    private MessageUtil messageUtil;
    @Inject
    private JPushUtil jPushUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserFriendApplyMapper userFriendApplyMapper;
    @Autowired
    private NoticeService noticeService;
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return aClass == HandleUserFriendEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return aClass == UserFriendApply.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        logger.debug("HandleUserFriendListener onApplicationEvent begin...");

        try{
            UserFriendApply userFriendApply=(UserFriendApply) applicationEvent.getSource();
            if(userFriendApply==null){
                logger.error("HandleUserFriendListener onApplicationEvent userFriendApply is null.");
                return;
            }
            if("BeSend".equals(userFriendApply.getApplyState())){
                //TODO 推送消息  chatmessage
                UserModel userModel= userService.findDetailAllByUserCode(userFriendApply.getMyUserCode());
                UserFriendModel userFriendModel=new UserFriendModel();
                userFriendModel.setType("apply");
                userFriendModel.setFriendCode(userFriendApply.getMyUserCode());
                userFriendModel.setFriendHeadImgUrl(userModel.getHeadImgUrl());
                userFriendModel.setFriendNickName(userModel.getNickName());
                userFriendModel.setApplyUuid(userFriendApply.getUuid());

                //记录消息
                Notice notice=new Notice();
                String uuid=UUID.randomUUID().toString().replace("-","");
                notice.setUuid(uuid);
                notice.setMessage(gson.toJson(userFriendModel));
                notice.setNoticeType(NoticeEnum.NoticeType.FRIEND_APPLY_NOTICE.getCode());
                notice.setReadState(NoticeEnum.ReadState.NOREAD.getValue());
                notice.setReceiveUser(userFriendApply.getFriendCode());
                notice.setSendUser(userFriendApply.getMyUserCode());
                Date dateNow=new Date();
                notice.setSendDate(dateNow);
                notice.setCreateDate(dateNow);
                notice.setUpdateDate(dateNow);
                noticeService.save(notice);

                // 使用极光推送
                NoticeModel noticeModel=new NoticeModel();
                noticeModel.setTitle(NoticeEnum.NoticeType.FRIEND_APPLY_NOTICE.getDesc());
                noticeModel.setObject(userFriendApply.getFriendCode());
                noticeModel.setContent(gson.toJson(userFriendModel));
                noticeModel.setSendType(NoticeEnum.NoticeType.FRIEND_APPLY_NOTICE.getCode());
                noticeModel.setType(NoticeEnum.Type.ALIAS.getCode());
                noticeModel.setMessageId(uuid);
                jPushUtil.send(noticeModel);
 /*               ClientModel clientModel=new ClientModel();
                clientModel.setFromUserCode(userFriendApply.getMyUserCode());
                clientModel.setMessageType("userfriend");
                clientModel.setToUserCode(userFriendApply.getFriendCode());
                clientModel.setMessage(userFriendModel);
                messageUtil.sendMessageToOne(clientModel);*/
            }else {

                UserModel userModel= userService.findDetailAllByUserCode(userFriendApply.getFriendCode());
                UserFriendModel userFriendModel=new UserFriendModel();
                boolean temp = "DisAgreed".equals(userFriendApply.getApplyState());
                logger.debug("添加好友同意还是拒绝："+temp+"");
                if(temp){
                    userFriendModel.setResult("DisAgreed");
                }else{
                	userFriendModel.setResult("IsAgreed");
                }
                userFriendModel.setType("reply");
                userFriendModel.setFriendCode(userFriendApply.getMyUserCode());
                userFriendModel.setFriendHeadImgUrl(userModel.getHeadImgUrl());
                userFriendModel.setFriendNickName(userModel.getNickName());
                userFriendModel.setApplyUuid(userFriendApply.getUuid());

                //记录消息
                Notice notice=new Notice();
                String uuid=UUID.randomUUID().toString().replace("-","");
                notice.setUuid(uuid);
                notice.setMessage(gson.toJson(userFriendModel));
                notice.setNoticeType(NoticeEnum.NoticeType.FRIEND_APPLY_NOTICE.getCode());
                notice.setReadState(NoticeEnum.ReadState.READ.getValue());
                notice.setReceiveUser(userFriendApply.getMyUserCode());
                notice.setSendUser(userFriendApply.getFriendCode());
                Date dateNow=new Date();
                notice.setSendDate(dateNow);
                notice.setCreateDate(dateNow);
                notice.setUpdateDate(dateNow);
                noticeService.save(notice);

                //TODO 使用极光推送 未区分拒绝还是同意
                NoticeModel noticeModel=new NoticeModel();
                if(temp){
                	noticeModel.setTitle(userModel.getNickName() + NoticeEnum.NoticeType.FRIEND_REPLY_NOTICE_NO.getDesc());
                }else{
                	noticeModel.setTitle(userModel.getNickName() + NoticeEnum.NoticeType.FRIEND_REPLY_NOTICE_YES.getDesc());
                }
                noticeModel.setObject(userFriendApply.getMyUserCode());
                noticeModel.setContent(gson.toJson(userFriendModel));
                noticeModel.setSendType(NoticeEnum.NoticeType.FRIEND_REPLY_NOTICE_NO.getCode());
                noticeModel.setType(NoticeEnum.Type.ALIAS.getCode());
                noticeModel.setMessageId(uuid);
                logger.debug("极光推送消息："+gson.toJson(noticeModel));
                jPushUtil.send(noticeModel);

/*                ClientModel clientModel=new ClientModel();
                clientModel.setFromUserCode(userFriendApply.getFriendCode());
                clientModel.setMessageType("userfriend");
                clientModel.setToUserCode(userFriendApply.getMyUserCode());
                clientModel.setMessage(userFriendModel);
                messageUtil.sendMessageToOne(clientModel);*/
                userFriendApplyMapper.delete(userFriendApply.getId());

            }
        }catch (Exception e){
            logger.error("HandleUserFriendListener onApplicationEvent error:",e);
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
