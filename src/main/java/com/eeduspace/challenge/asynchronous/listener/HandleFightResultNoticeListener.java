package com.eeduspace.challenge.asynchronous.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.eeduspace.challenge.asynchronous.event.HandleFightResultNoticeEvent;
import com.eeduspace.challenge.enumeration.NoticeEnum;
import com.eeduspace.challenge.model.NoticeModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.BattleResultNotice;
import com.eeduspace.challenge.persist.po.FightResult;
import com.eeduspace.challenge.persist.po.Notice;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.challenge.service.NoticeService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.util.JPushUtil;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.google.gson.Gson;
/**
 * 推送对战结果监听
 * @author zhuchaowei
 * 2016年8月8日
 * Description
 */
@Component
public class HandleFightResultNoticeListener implements SmartApplicationListener{
	private static final Logger logger = LoggerFactory.getLogger(HandleFightResultNoticeListener.class);
	private Gson gson=new Gson();
	@Autowired
	private NoticeService noticeService;
	@Inject
	private JPushUtil jPushUtil;
	@Autowired
	private FightResultService fightResultServiceImpl;
	@Autowired
	private UserService userService;
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		BattleResultNotice battleResultNotice=(BattleResultNotice) event.getSource();
		logger.debug("HandleFightResultNoticeListener onApplicationEvent battleResultNotice is :"+battleResultNotice);
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("fightUuid", battleResultNotice.getFightUUID());
		queryMap.put("userCode", battleResultNotice.getReceiver());
		List<FightResult> results = fightResultServiceImpl.findByCondition(queryMap);
		logger.debug("HandleFightResultNoticeListener List<FightResult> results.size is :"+results.size());
		if(results.size()>0){
			//记录消息
			FightResult fightResult=results.get(0);
			UserModel userModel=userService.findDetailAllByUserCode(battleResultNotice.getOpponentUserCode());
			if(userModel!=null){
				battleResultNotice.setOpponentNickName(userModel.getNickName());
			}
			battleResultNotice.setFightDate(DateUtils.toString(fightResult.getCreateDate(), "yyyy-MM-dd"));
			battleResultNotice.setFightValue(fightResult.getFightValue());
			battleResultNotice.setIntegral(fightResult.getIntegral());
			battleResultNotice.setFightResult(fightResult.getFightResult());
			logger.debug("fightResult1:--------------------"+fightResult.getFightResult());
			if(fightResult.getFightResult() == null){
				Integer fightResult2 = fightResultServiceImpl.findByCondition(queryMap).get(0).getFightResult();
				logger.debug("new fightresult ============================================="+fightResult2);
				battleResultNotice.setFightResult(fightResult2);
			}
			logger.debug("fightResult2:--------------------"+battleResultNotice.getFightResult());
			
			battleResultNotice.setUnitCode(fightResult.getUnitCode());
			
			Notice notice=new Notice();
            String uuid=UUID.randomUUID().toString().replace("-","");
			notice.setUuid(uuid);
			notice.setMessage(gson.toJson(battleResultNotice));
			notice.setNoticeType(NoticeEnum.NoticeType.BATTLE_NOTICE.getCode());//对战通知
			notice.setReadState(NoticeEnum.ReadState.NOREAD.getValue());
			notice.setReceiveUser(battleResultNotice.getReceiver());
			notice.setSendUser("system");
			Date dateNow=new Date();
			notice.setSendDate(dateNow);
			notice.setCreateDate(dateNow);
			notice.setUpdateDate(dateNow);
			noticeService.save(notice);
			logger.debug("消息保存成功========================================================gson.toJson(battleResultNotice)："+gson.toJson(battleResultNotice));
			// 使用极光推送
			NoticeModel noticeModel=new NoticeModel();
			noticeModel.setTitle(NoticeEnum.NoticeType.BATTLE_NOTICE.getDesc());//对战通知
			noticeModel.setObject(battleResultNotice.getReceiver());
			noticeModel.setContent(gson.toJson(battleResultNotice));
			noticeModel.setSendType(NoticeEnum.NoticeType.BATTLE_NOTICE.getCode());//对战通知
			noticeModel.setType(NoticeEnum.Type.ALIAS.getCode());
            noticeModel.setMessageId(uuid);
            logger.debug("极光推送之前========================================================gson.toJson(battleResultNotice)："+gson.toJson(battleResultNotice));
			jPushUtil.send(noticeModel);
			logger.debug("极光推送之后=========================================================noticeModel："+noticeModel);
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return eventType==HandleFightResultNoticeEvent.class;
	}

	@Override
	public boolean supportsSourceType(Class<?> sourceType) {
		return sourceType==BattleResultNotice.class;
	}

}
