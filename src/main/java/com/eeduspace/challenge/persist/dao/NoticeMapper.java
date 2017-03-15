package com.eeduspace.challenge.persist.dao;

import java.util.List;
import java.util.Map;

import com.eeduspace.challenge.persist.po.Notice;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:通知管理
 */
public interface NoticeMapper extends BaseDao<Notice>{
	
	public List<Notice> getNoticeFriendList(Map<String, Object> queryMap);
	
	public List<Notice> getNoticeListExcFriend(Map<String, Object> queryMap);
	
}
