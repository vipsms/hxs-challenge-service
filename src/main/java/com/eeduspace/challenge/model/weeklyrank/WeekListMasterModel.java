package com.eeduspace.challenge.model.weeklyrank;

import com.eeduspace.challenge.model.UserModel;


public class WeekListMasterModel {
	/**
	 * 全国榜榜主
	 */
	private UserModel nationwideMaster;
	/**
	 * 好友榜榜主
	 */
	private UserModel friendMaster;
	public UserModel getNationwideMaster() {
		return nationwideMaster;
	}
	public void setNationwideMaster(UserModel nationwideMaster) {
		this.nationwideMaster = nationwideMaster;
	}
	public UserModel getFriendMaster() {
		return friendMaster;
	}
	public void setFriendMaster(UserModel friendMaster) {
		this.friendMaster = friendMaster;
	}
	
}
