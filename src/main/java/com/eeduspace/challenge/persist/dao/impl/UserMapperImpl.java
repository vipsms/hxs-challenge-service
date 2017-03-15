package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.persist.dao.UserMapper;
import com.eeduspace.challenge.persist.po.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2016/7/14
 * Description:
 */
@Repository("userMapperImpl")
public class UserMapperImpl extends BaseDaoImpl<User>  implements UserMapper {
    private final String FIND_FRIEND_BY_USERCODE="User_findFriendByUserCode";
    private final String USER_DETAIL="User_findDetailByUserCode";
    private final String USER_SET_FRIEND_RANK="User_set_friend_rank";
    private final String USER_SET_FIGHT_RANK="User_set_fight_rank";
    private final String USER_GET_FRIEND_BANK_BY_USERCODE="User_getFriendBankByUserCode";
    private final String USER_GET_FIGHT_BANK_BY_USERCODE="User_getFightBankByUserCode";
    private final String FIND_USERINFO_BY_MOBILE = "findUserInfoByMobile";
    private final String UPDATE_WEEK_STATUS="Update_weekStatus";

    @Override
    public User insert(User record) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("mobile", record.getMobile());
        List<User> users= this.findByCondition(queryMap,null);
        if(users!=null && !users.isEmpty()){
            return users.get(0);
        }
        this.save(record);
        return this.get(record.getId());
    }

    @Override
    public UserModel findDetailByUserCode(String userCode) {
        UserModel userModel=sessionTemplate.selectOne(USER_DETAIL, userCode);
        return userModel;
    }

    @Override
    public List<UserModel> findFriendByUserCode(String userCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        return sessionTemplate.selectList(FIND_FRIEND_BY_USERCODE, queryMap);
    }

    @Override
    public UserModel getFriendBankByUserCode(String userCode) {
    	sessionTemplate.selectOne(USER_SET_FRIEND_RANK);
        UserModel userModel= sessionTemplate.selectOne(USER_GET_FRIEND_BANK_BY_USERCODE, userCode);
        if(null==userModel){
            userModel= new UserModel();
            userModel.setFightRank(01);
        }
        return userModel;
    }

    @Override
    public UserModel getFightBankByUserCode(String userCode) {
    	sessionTemplate.selectOne(USER_SET_FIGHT_RANK);
        UserModel userModel=sessionTemplate.selectOne(USER_GET_FIGHT_BANK_BY_USERCODE, userCode);
          if(null==userModel){
               userModel= new UserModel();
              userModel.setFightRank(01);
          }
        return userModel;
    }

	@Override
	public UserModel findUserInfoByMobile(String mobile) {
		return sessionTemplate.selectOne(FIND_USERINFO_BY_MOBILE, mobile);
	}
	
	@Override
	public void updateWeekStatus() {
		sessionTemplate.update(UPDATE_WEEK_STATUS);
	}
	
}
