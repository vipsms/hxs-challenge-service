package com.eeduspace.challenge.persist.dao.impl;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.UserFightMapper;
import com.eeduspace.challenge.persist.po.UserFight;
@Repository("userFightMapperImpl")
public class UserFightMapperImpl extends BaseDaoImpl<UserFight> implements UserFightMapper{
}
