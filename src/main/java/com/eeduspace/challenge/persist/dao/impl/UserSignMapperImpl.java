package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.UserSignMapper;
import com.eeduspace.challenge.persist.po.UserSign;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/7/21
 * Description:
 */
@Repository("UserSignMapperImpl")
public class UserSignMapperImpl extends BaseDaoImpl<UserSign> implements UserSignMapper {
    private final String FIND_LAST_BY_USERCODE="UserSign_findLastByUserCode";

    @Override
    public UserSign findLastByUserCode(String userCode) {
        List<UserSign> userSign=sessionTemplate.selectList(FIND_LAST_BY_USERCODE, userCode);
        if(userSign==null || userSign.isEmpty()){
            return null;
        }
        return userSign.get(0);
    }
}
