package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.ConcreteTaskMapper;
import com.eeduspace.challenge.persist.po.ConcreteTask;
import org.springframework.stereotype.Repository;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:具体任务 ：将系统中的功能指定为具体任务
 */
@Repository("ConcreteTaskMapperImpl")
public class ConcreteTaskMapperImpl extends BaseDaoImpl<ConcreteTask> implements ConcreteTaskMapper {
}
