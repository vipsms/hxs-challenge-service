package com.eeduspace.challenge.service;

import com.eeduspace.challenge.persist.po.Paper;

public interface PaperService extends BaseService<Paper>{
	/**
	 * 根据卷子ID获取卷子基本信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月12日 下午5:46:01
	 * @param uuid
	 * @return
	 */
	Paper findByUUID(String uuid);
}
