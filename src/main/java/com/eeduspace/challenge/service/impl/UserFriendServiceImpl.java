package com.eeduspace.challenge.service.impl;

import com.eeduspace.challenge.asynchronous.EventOperationService;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.FriendBattleRankModel;
import com.eeduspace.challenge.model.request.FriendRequestModel;
import com.eeduspace.challenge.persist.dao.UserFriendApplyMapper;
import com.eeduspace.challenge.persist.dao.UserFriendMapper;
import com.eeduspace.challenge.persist.po.UserFriend;
import com.eeduspace.challenge.persist.po.UserFriendApply;
import com.eeduspace.challenge.service.UserFriendService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.*;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:
 */
@Service
public class UserFriendServiceImpl extends BaseServiceImpl<UserFriend> implements UserFriendService {
    private final Logger logger = LoggerFactory.getLogger(UserFriendServiceImpl.class);
    @Autowired
    private UserFriendMapper userFriendMapper;
    @Autowired
    private UserFriendApplyMapper userFriendApplyMapper;
    @Inject
    private EventOperationService eventOperationService;
    @Autowired
    public UserFriendServiceImpl(UserFriendMapper userFriendMapper) {
        this.baseDao =userFriendMapper;
    }
    @Override
    public List<UserModel> findFriendByUserCode(String userCode) {
        return userFriendMapper.findFriendByUserCode(userCode);
    }

    @Override
    public UserFriend findByMyUserCodeAndFriendCode(String myCode, String friendCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("myUserCode", myCode);
        queryMap.put("friendCode", friendCode);
        List<UserFriend> list=this.findByCondition(queryMap);
        if(list==null ||list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public UserFriendApply findApplyByMyUserCodeAndFriendCode(String myCode, String friendCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("myUserCode", myCode);
        queryMap.put("friendCode", friendCode);
        List<UserFriendApply> list=userFriendApplyMapper.findByCondition(queryMap,null);
        if(list==null ||list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public UserFriendApply findByApplyUuid(String applyUuid) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("uuid", applyUuid);
        List<UserFriendApply>list= userFriendApplyMapper.findByCondition(queryMap,null);
        if(list==null ||list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<UserFriendApply> findApplyByUserCode(String userCode) {
        return null;
    }

    @Override
    public List<UserFriendApply> findApplyByFriendCode(String userCode) {
        return null;
    }

    @Override
    public List<UserFriendApply> findApplyByMyUserCode(String userCode) {
        return null;
    }

    /**
     * 同意或拒绝好友请求
     * @param userFriendApply
     * @param isAgree
     * @return
     */
    @Override
    @Transactional
    public void agreeOrDisagreeApply( UserFriendApply userFriendApply, boolean isAgree) {
        //当对方在线时 使用推送方式
        //当对方不在线时 使用推送方式
        //记录消息记录
        if(isAgree){
            userFriendApply.setApplyState(UserEnum.FriendApplyType.IsAgreed.toString());
            List<UserFriend> userFriendList=new ArrayList<>();
            UserFriend userFriend=new UserFriend();
            userFriend.setMyUserCode(userFriendApply.getMyUserCode());
            userFriend.setFriendCode(userFriendApply.getFriendCode());
            userFriend.setCreateDate(new Date());
            userFriend.setUpdateDate(new Date());
            userFriendList.add(userFriend);
            UserFriend userFriend1=new UserFriend();
            userFriend1.setMyUserCode(userFriendApply.getFriendCode());
            userFriend1.setFriendCode(userFriendApply.getMyUserCode());
            userFriend1.setCreateDate(new Date());
            userFriend1.setUpdateDate(new Date());
            userFriendList.add(userFriend1);
            userFriendMapper.saveList(userFriendList);
        }else {
            userFriendApply.setApplyState(UserEnum.FriendApplyType.DisAgreed.toString());
        }
//        userFriendApplyMapper.update(userFriendApply);
        logger.debug("同意或拒绝好友申请service："+userFriendApply);
        eventOperationService.handleSendFriend(userFriendApply);
    }

    /**
     * 申请加为好友
     * @param friendRequestModel
     * @return
     */
    @Override
    public UserFriendApply apply(FriendRequestModel friendRequestModel) {
        //当对方在线时 使用消息
        //当对方不在线时 使用推送方式
        //记录消息记录
        UserFriendApply userFriendApply= this.findApplyByMyUserCodeAndFriendCode(friendRequestModel.getMyUserCode(), friendRequestModel.getFriendCode());
        if(userFriendApply==null ){
            userFriendApply=new UserFriendApply();
            userFriendApply.setMyUserCode(friendRequestModel.getMyUserCode());
            userFriendApply.setFriendCode(friendRequestModel.getFriendCode());
            userFriendApply.setUuid(UUID.randomUUID().toString().replace("-", ""));
            userFriendApply.setApplySource(friendRequestModel.getApplySource());
            userFriendApply.setApplyState(UserEnum.FriendApplyType.BeSend.toString());
            userFriendApply.setCreateDate(new Date());
            userFriendApply.setUpdateDate(new Date());
            userFriendApplyMapper.save(userFriendApply);
        }
        eventOperationService.handleSendFriend(userFriendApply);
        return  userFriendApplyMapper.get(userFriendApply.getId());

    }
	@Override
	public List<FriendBattleRankModel> findFriendBattleRank(String userCode,Long start,int item) {
		return userFriendMapper.findFriendBattleRank(userCode,start,item);
	}
	@Override
	public Long getUserFriendCount(String userCode) {
		return userFriendMapper.getFriendCount(userCode);
	}
}
