package com.eeduspace.challenge.service;


import java.util.List;

import com.eeduspace.challenge.persist.po.UserTaskLog;




/**
 * 用户的操作日志
 */
public interface UserTaskLogService extends BaseService<UserTaskLog>{

	
public  void  UserTaskLogSave(UserTaskLog userTaskLog);

public  void  UserTaskLogSave(String userCode,String module,String logType,String logResult,String operate,String concreteTaskCode);

public   UserTaskLog   findByUuid(String uuid);

public   List<UserTaskLog>   findAll();

}
