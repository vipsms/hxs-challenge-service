package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.UserInfoMapper;
import com.eeduspace.challenge.persist.po.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * Author: dingran
 * Date: 2016/7/14
 * Description:
 */
@Repository("userInfoMapperImpl")
public class UserInfoMapperImpl extends BaseDaoImpl<UserInfo>  implements UserInfoMapper {

    @Override
    public UserInfo findByUserCode(String userCode) {
//        Map<String, Object> queryMap=new HashMap<String, Object>();
//        queryMap.put("userCode", userCode);
//        return this.findByCondition(queryMap,null).get(0);
        return this.get(userCode,"userCode");
    }
}
