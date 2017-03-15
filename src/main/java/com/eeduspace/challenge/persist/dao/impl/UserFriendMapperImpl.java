package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.battle.FriendBattleRankModel;
import com.eeduspace.challenge.persist.dao.UserFriendMapper;
import com.eeduspace.challenge.persist.po.UserFriend;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2016/7/14
 * Description:
 */
@Repository("userFriendMapperImpl")
public class UserFriendMapperImpl extends BaseDaoImpl<UserFriend>  implements UserFriendMapper {
    private final String USER_FRIEND_DETAIL="UserFriend_findFriendByUserCode";
    private final String FRIEND_BATTLE_RANK="friend_battle_rank";
    private final String FRIEND_COUNT = "getFriendCount";

    @Override
    public List<UserModel> findFriendByUserCode(String userCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        return sessionTemplate.selectList(USER_FRIEND_DETAIL, queryMap);
    }

	@Override
	public List<FriendBattleRankModel> findFriendBattleRank(String userCode,
			Long start, int item) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		queryMap.put("start", start);
		queryMap.put("item", item);
		return sessionTemplate.selectList(FRIEND_BATTLE_RANK, queryMap);
	}

	@Override
	public Long getFriendCount(String userCode) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        return sessionTemplate.selectOne(FRIEND_COUNT, queryMap);
	}
}
