package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.UserSign;

public interface UserSignMapper  extends BaseDao<UserSign>{
    /**
     * 获取用户最后一次记录
     * @param userCode
     * @return
     */
    UserSign findLastByUserCode(String userCode);
}