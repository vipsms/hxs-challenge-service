package com.eeduspace.challenge.service.impl;




import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.enumeration.ModuleEnum;
import com.eeduspace.challenge.enumeration.OperateEnum;
import com.eeduspace.challenge.persist.dao.UserTaskLogMapper;
import com.eeduspace.challenge.persist.po.UserTaskLog;
import com.eeduspace.challenge.service.UserTaskLogService;

@Service("UserTaskLogServiceImpl")
public class UserTaskLogServiceImpl extends BaseServiceImpl<UserTaskLog> implements UserTaskLogService{
	@Resource
	private UserTaskLogMapper userTaskLogMapperImpl;
	@Autowired
	public UserTaskLogServiceImpl(UserTaskLogMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	/**
	 *  保存用户操作日志 
	 **/
	@Override
	public void UserTaskLogSave(UserTaskLog userTaskLog) {
		this.save(userTaskLog);
	}
	/**
	 *  保存用户操作日志 
	 **/
	@Override
	public void UserTaskLogSave(String userCode, String module, String logType,String logResult, String operate,String concreteTaskCode) {
		UserTaskLog log = new UserTaskLog();
		log.setUserCode(userCode);
		log.setModule(module);
		log.setLogType(logType);
		log.setLogResult(logResult);
		log.setOperate(operate);
		log.setUuid(UUID.randomUUID().toString().replace("-", ""));
		log.setCreateDate(new Date());
		log.setConcreteTaskCode(concreteTaskCode);
		this.save(log);
	}
	/**
	 *  根据uuid查询UserTaskLog
	 **/
	@Override
	public UserTaskLog findByUuid(String uuid) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("uuid", uuid);
		return this.findByCondition(queryMap).get(0);
	}
	/**
	 * 查询UserTaskLog集合
	 **/
	@Override
	public List<UserTaskLog> findAll() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		return this.findByCondition(queryMap);
	}
	
}
