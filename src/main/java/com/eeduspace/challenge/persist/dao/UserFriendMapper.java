package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.FriendBattleRankModel;
import com.eeduspace.challenge.persist.po.UserFriend;

import java.util.List;

public interface UserFriendMapper extends BaseDao<UserFriend>  {

    /**
     * 获取我的好友列表
     * @param userCode
     * @return
     */
    public List<UserModel> findFriendByUserCode(String userCode);
    /**
     * 获取好友挑战排行榜
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年8月1日 上午9:38:23
     * @param userCode 
     * @return
     */
    public List<FriendBattleRankModel> findFriendBattleRank(String userCode,Long start,int item);
    /**
     * 根据usercode获取好友数量
     * @param userCode
     * @return
     */
    public Long getFriendCount(String userCode);
}