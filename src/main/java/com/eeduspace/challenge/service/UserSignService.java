package com.eeduspace.challenge.service;

import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserSign;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/7/21
 * Description:
 */
public interface UserSignService {
    /**
     * 获取用户签到记录
     * @param userCode
     * @return
     */
    List<UserSign> findAllByUserCode(String userCode);

    /**
     * 用户签到
     * @param user
     */
    UserSign sign(User user);

    /**
     * 获取用户最后一次签到记录
     * @param userCode
     * @return
     */
    UserSign findLastByUserCode(String userCode);


}
