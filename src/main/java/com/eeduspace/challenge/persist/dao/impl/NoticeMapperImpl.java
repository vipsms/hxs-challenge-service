package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.NoticeMapper;
import com.eeduspace.challenge.persist.po.Notice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:通知管理
 */
@Repository("NoticeMapperImpl")
public class NoticeMapperImpl extends BaseDaoImpl<Notice> implements NoticeMapper {
	
	private final String NoticeFriendList = "NoticeFriendList";
	private final String NoticeListExcFriend = "NoticeListExcFriend";

	@Override
	public List<Notice> getNoticeFriendList(Map<String, Object> queryMap) {
		return sessionTemplate.selectList(NoticeFriendList, queryMap);
	}

	@Override
	public List<Notice> getNoticeListExcFriend(Map<String, Object> queryMap) {
		return sessionTemplate.selectList(NoticeListExcFriend, queryMap);
	}
	
}
