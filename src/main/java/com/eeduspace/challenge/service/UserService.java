package com.eeduspace.challenge.service;

import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.model.IntegralChangeModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.request.UserBuyVipRequestModel;
import com.eeduspace.challenge.model.request.UserBuyVipSuccessModel;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserInfo;
import com.eeduspace.challenge.persist.po.VipPack;
import com.eeduspace.challenge.responsecode.BaseResponse;

import java.io.IOException;
import java.util.List;

/**
 * Author: dingran
 * Date: 2016/7/12
 * Description:
 */

public interface UserService extends BaseService<User> {
    /**
     * 获取用户好友列表
     * @param userCode
     * @return
     */
    List<UserModel> findFriendByUserCode(String userCode);
    /**
     * 根据用户code获取用户model详细信息
     * @param userCode
     * @return
     */
    UserModel findDetailAllByUserCode(String userCode);

    /**
     * 根据用户code获取用户
     * @param userCode
     * @return
     */
    User findByUserCode(String userCode);

    /**
     * 根据手机号获取用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 用户注册
     * @param userModel
     * @return
     * @throws IOException
     */
    User register(UserRequestModel userModel) throws IOException;

    /**
     * 刷新令牌
     * @param userModel
     * @return
     * @throws IOException
     */
    BaseResponse refreshToken(UserRequestModel userModel) throws IOException;

    /**
     * 用户登录
     * @param userModel
     * @param isScan
     * @return
     * @throws IOException
     */
    BaseResponse login(UserRequestModel userModel,boolean isScan) throws IOException;

    /**
     * 修改用户密码
     * @param userModel
     * @return
     * @throws IOException
     */
    BaseResponse editPwd(UserRequestModel userModel) throws IOException;

    /**
     * 重置用户密码
     * @param userModel
     * @return
     * @throws IOException
     */
    BaseResponse resetPwd(UserRequestModel userModel) throws IOException;

    /**
     * 更新
     * @param userRequestModel
     */
    void update(UserRequestModel userRequestModel);
    /**
     * 获取用户详细信息 包含userInfo表中的详细信息
     * @param userCode
     * @return
     */
    UserInfo getDetailByUserCode(String userCode);

    /**
     * 保存用户详情
     * @param userInfo
     */
    public UserInfo saveUserInfo(UserInfo userInfo);
    /**
     * 修改用户信息
     * @param userInfo
     */
//    void updateUserInfo(UserInfo userInfo);

    /**
     * 修改用户基本信息
     * @param user
     * @param userInfo
     */
    void updateUserAndUserInfo(User user,UserInfo userInfo);

    /**
     * 用户积分变更记录表
     * @param userCode 用户code
     * @param rewardIntegral 变动积分
     * @param changeType 变更类型：兑换、奖励
     * @param rewardType 积分类型：签到、任务、对战等
     * @param rewardSourceUuid 积分兑换资源：任务ID、签到Id等
     */
    void updateUserIntegralChange(String userCode,Long rewardIntegral,RewardEnum.ChangeType changeType,RewardEnum.RewardType rewardType,String rewardSourceUuid);

    /**
     * 用户积分变更记录表
     */
    void updateUserIntegralChange(IntegralChangeModel integralChangeModel);
    /**
     * 用户请求购买vip包  微信
     */
    public String  weiXinBuyVipPack(UserBuyVipRequestModel model) throws Exception;
    /**
     * 用户请求购买vip包  支付宝
     */
    public  String zhiFuBaoBuyVipPack(UserBuyVipRequestModel model) throws Exception;
    /**
     * 用户请求购买vip包成功 修改用户信息
     * @param vipPack 
     * @param user 
     * @throws Exception
     */
    public  void buyVipPackSuccess(UserBuyVipSuccessModel model, User user, VipPack vipPack) throws Exception;
    
    /**
     * 根据手机号查找用户信息  table: user, user_info
     * @param mobile
     * @return
     */
    public UserModel findUserInfoByMobile(String mobile);
    
    /**
     * 清除这周的所有战斗次数
     */
    public void updateWeekStatus();

}
