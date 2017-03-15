package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.persist.po.User;

import java.util.List;

public interface UserMapper extends BaseDao<User> {

    /**
     * 插入一条数据
     * @param record
     * @return
     */
    User insert(User record);

    /**
     * 根据用户code获取用户信息包含详细信息
     * @param userCode
     * @return
     */
    UserModel findDetailByUserCode(String userCode);

    /**
     * 获取用户好友列表
     * @param userCode
     * @return
     */
    List<UserModel> findFriendByUserCode(String userCode);


    /**
     * 获取用户总战斗力的好友中的排名
     * @param userCode
     * @return
     */
    UserModel getFriendBankByUserCode(String userCode);
    /**
     * 获取用户总战斗力的排行榜中的排名
     * @param userCode
     * @return
     */
    UserModel getFightBankByUserCode(String userCode);
    
    /**
     * 根据手机号查找用户信息  table: user, user_info
     * @param mobile
     * @return
     */
	UserModel findUserInfoByMobile(String mobile);

	/**
     * 清除掉战斗次数
     */
    void updateWeekStatus();
}