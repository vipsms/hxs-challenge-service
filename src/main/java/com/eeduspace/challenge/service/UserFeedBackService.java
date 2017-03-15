package com.eeduspace.challenge.service;


import java.util.List;

import com.eeduspace.challenge.model.UserFeedBackModel;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserFeedBack;
/**
 * 反馈
 */
public interface UserFeedBackService extends BaseService<UserFeedBack>{
	/**
	 * 保存反馈信息
	 */
	public void saveUserFeedBack(User user, UserFeedBackModel userFeedBackModel);

	/**
	 * 反馈信息查询
	 */ 
	public List<UserFeedBack> findByUsercode(String userCode);


	
	
	
}
