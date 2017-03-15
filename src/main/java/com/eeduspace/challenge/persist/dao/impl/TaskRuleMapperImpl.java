package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.TaskRuleMapper;
import com.eeduspace.challenge.persist.po.TaskRule;
import org.springframework.stereotype.Repository;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:任务规则表 给任务分配具体的类型、规则
 */
@Repository("TaskRuleMapperImpl")
public class TaskRuleMapperImpl extends BaseDaoImpl<TaskRule> implements TaskRuleMapper {
}
