package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.UserInfo;

public interface UserInfoMapper extends BaseDao<UserInfo>  {


    UserInfo findByUserCode(String userCode);
}