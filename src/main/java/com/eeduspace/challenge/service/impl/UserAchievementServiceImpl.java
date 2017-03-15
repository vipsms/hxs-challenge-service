package com.eeduspace.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.persist.dao.UserAchievementMapper;
import com.eeduspace.challenge.persist.po.UserAchievement;
import com.eeduspace.challenge.service.UserAchievementService;
@Service("userAchievementServiceImpl")
public class UserAchievementServiceImpl extends BaseServiceImpl<UserAchievement> implements UserAchievementService{
	@Autowired
    public UserAchievementServiceImpl(UserAchievementMapper userAchievementMapper) {
        this.baseDao =userAchievementMapper;
    }
}
