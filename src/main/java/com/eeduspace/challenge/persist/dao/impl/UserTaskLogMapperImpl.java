package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.UserTaskLogMapper;
import com.eeduspace.challenge.persist.po.UserTaskLog;
import org.springframework.stereotype.Repository;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:用户任务操作记录
 */
@Repository("UserTaskLogMapperImpl")
public class UserTaskLogMapperImpl extends BaseDaoImpl<UserTaskLog> implements UserTaskLogMapper {
}
