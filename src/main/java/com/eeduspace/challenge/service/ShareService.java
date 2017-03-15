package com.eeduspace.challenge.service;



import com.eeduspace.challenge.model.ShareModel;
import com.eeduspace.challenge.persist.po.Share;

/**
 * 分享
 */
public interface ShareService extends BaseService<Share>{
	/**
	 * 保存用户分享信息
	 */
	public  void  saveUserShare(ShareModel shareModel);

	
}
