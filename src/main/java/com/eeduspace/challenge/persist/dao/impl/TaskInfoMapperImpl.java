package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.model.TaskInfoModel;
import com.eeduspace.challenge.persist.dao.TaskInfoMapper;
import com.eeduspace.challenge.persist.po.TaskInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:任务信息
 */
@Repository("TaskInfoMapperImpl")
public class TaskInfoMapperImpl extends BaseDaoImpl<TaskInfo> implements TaskInfoMapper {

    private final String TASK_INFO_FIND_BY_TYPE="TaskInfo_findByType";
    @Override
    public TaskInfoModel findByTaskTypeAndConcreteType(String taskType, String concreteType) {

            Map<String, Object> queryMap=new HashMap<String, Object>();
            queryMap.put("taskType", taskType);
            queryMap.put("concreteTaskCode", concreteType);
            return sessionTemplate.selectOne(TASK_INFO_FIND_BY_TYPE, queryMap);

    }
}
