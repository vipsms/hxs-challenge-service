package com.eeduspace.challenge.service;

import com.eeduspace.challenge.persist.po.Notice;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:通知管理
 */
public interface NoticeService extends BaseService<Notice>{
    /**
     * 根据userCode 获取通知列表
     * @param userCode
     * @return
     */
    List<Notice> findNoticeByUserCode(String userCode);

    Notice findByUuid(String noticeUuid);
}
