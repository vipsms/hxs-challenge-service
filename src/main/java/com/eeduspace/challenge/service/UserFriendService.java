package com.eeduspace.challenge.service;

import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.FriendBattleRankModel;
import com.eeduspace.challenge.model.request.FriendRequestModel;
import com.eeduspace.challenge.persist.po.UserFriend;
import com.eeduspace.challenge.persist.po.UserFriendApply;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:
 */
public interface UserFriendService extends BaseService<UserFriend>{

    /**
     * 查看好友列表
     * @param userCode
     * @return
     */
    List<UserModel> findFriendByUserCode(String userCode);

    /**
     * 查询是否是好友状态
     * @param myCode
     * @param friendCode
     * @return
     */
    UserFriend findByMyUserCodeAndFriendCode(String myCode,String friendCode);
    /**
     * 查询是否已经申请
     * @param myCode
     * @param friendCode
     * @return
     */
    UserFriendApply findApplyByMyUserCodeAndFriendCode(String myCode,String friendCode);
    /**
     * 根据申请ID获取申请记录
     * @param applyUuid
     * @return
     */
    UserFriendApply findByApplyUuid(String applyUuid);
    /**
     * 查看我的所有添加好友的请求列表
     * @param userCode
     * @return
     */
    List<UserFriendApply> findApplyByUserCode(String userCode);

    /**
     * 查看申请添加好友列表
     * @param userCode
     * @return
     */
    List<UserFriendApply> findApplyByFriendCode(String userCode);

    /**
     * 查看我的申请好友列表
     * @param userCode
     * @return
     */
    List<UserFriendApply> findApplyByMyUserCode(String userCode);

    /**
     * 同意或拒绝好友请求 true 为同意  false为拒绝
     * @param isAgree
     * @return
     */
    void agreeOrDisagreeApply( UserFriendApply userFriendApply,boolean isAgree);

    /**
     * 申请添加为好友
     * @param friendRequestModel
     * @return
     */
    UserFriendApply apply(FriendRequestModel friendRequestModel);
    /**
     * 获取好友对战排行榜
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年8月1日 上午9:40:23
     * @param userCode
     * @return
     */
    List<FriendBattleRankModel> findFriendBattleRank(String userCode,Long start,int item);
    
    /**
     * 根据usercode获取好友数量
     * @param userCode
     * @return
     */
	Long getUserFriendCount(String userCode);
}
