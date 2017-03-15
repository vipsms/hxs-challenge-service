package com.eeduspace.challenge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.enumeration.NoticeEnum;
import com.eeduspace.challenge.persist.dao.NoticeMapper;
import com.eeduspace.challenge.persist.po.Notice;
import com.eeduspace.challenge.service.NoticeService;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:通知管理
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService {
    private final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
    
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.baseDao =noticeMapper;
    }

    @Override
    public List<Notice> findNoticeByUserCode(String userCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("readState", String.valueOf(NoticeEnum.ReadState.NOREAD.getValue()));
        queryMap.put("receiveUser", userCode);
        List<Notice> list1 = noticeMapper.getNoticeListExcFriend(queryMap);
        List<Notice> list2 = noticeMapper.getNoticeFriendList(queryMap);
        list1.addAll(list2);
        return list1;
    }

    @Override
    public Notice findByUuid(String noticeUuid) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("uuid", noticeUuid);
        List<Notice> list= this.findByCondition(queryMap,null);
        if(list==null ||list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
