package com.eeduspace.challenge.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.model.AppUpdateModel;
import com.eeduspace.challenge.persist.dao.AppUpdateMapper;
import com.eeduspace.challenge.persist.po.AppUpdate;
import com.eeduspace.challenge.service.AppUpdateService;


@Service("AppUpdateServiceImpl")
public class AppUpdateServiceImpl extends BaseServiceImpl<AppUpdate> implements AppUpdateService{
	@Resource
	private AppUpdateMapper appUpdateMapperImpl;

	@Autowired
	public AppUpdateServiceImpl(AppUpdateMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	
	
	/**
	 * 下载
	 */
	@Override
	public AppUpdateModel getAppUpdateModel() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("available", true);
		AppUpdate appUpdate = this.findByCondition(queryMap).get(0);
		return UserConvert.fromAppUpdateToModel(appUpdate);
	}

	
	
	
}
