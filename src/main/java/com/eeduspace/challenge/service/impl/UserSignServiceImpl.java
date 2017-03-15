package com.eeduspace.challenge.service.impl;

import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.persist.dao.SignRuleMapper;
import com.eeduspace.challenge.persist.dao.UserSignMapper;
import com.eeduspace.challenge.persist.po.SignRule;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserSign;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.UserSignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: dingran
 * Date: 2016/7/21
 * Description:
 */
@Service
public class UserSignServiceImpl implements UserSignService {

    private final Logger logger = LoggerFactory.getLogger(UserSignServiceImpl.class);

    @Resource
    private UserService userService;

    @Resource
    private UserSignMapper userSignMapper;

    @Resource
    private SignRuleMapper signRuleMapper;

    @Override
    public List<UserSign> findAllByUserCode(String userCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        List<UserSign> list=userSignMapper.findByCondition(queryMap, null);
        return list;
    }

    /**
     * 用户签到
     * 1.获取最后一次签到记录
     * 2.获取规则
     * 3.更新当初签到记录
     * 4.变更积分
     * @param user
     * @return
     */
    @Override
    @Transactional
    public UserSign sign(User user) {

        UserSign userSignLast=this.findLastByUserCode(user.getUserCode());
        long times=1;
        if(userSignLast!=null && userSignLast.getSignTimes()!=7){
            times=userSignLast.getSignTimes()+1;
        }
        //获取规则
        SignRule signRule=signRuleMapper.findBySignTimes(times);
        if(signRule==null){
            return null;
        }
        UserSign userSign=new UserSign();
        userSign.setUserCode(user.getUserCode());
        String uuid=UUID.randomUUID().toString().replace("-", "");
        userSign.setUuid(uuid);
        userSign.setIntegral(signRule.getRewardPoints());
        if(1==user.getIsVip()){
            userSign.setIntegral(signRule.getVipRewardPoints());
        }
        userSign.setSignTimes(times);
        userSign.setSignDate(new Date());
        userSign.setCreateDate(new Date());
        userSign.setUpdateDate(new Date());
        userSignMapper.save(userSign);
        //积分变更
        userService.updateUserIntegralChange(user.getUserCode(),userSign.getIntegral(), RewardEnum.ChangeType.REWARD, RewardEnum.RewardType.SIGN,uuid);
        return userSign;
    }

    /**
     * 获取用户最后一次签到记录
     * @param userCode
     * @return
     */
    @Override
    public UserSign findLastByUserCode(String userCode) {
        return userSignMapper.findLastByUserCode(userCode);
    }
}
