package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.model.TaskInfoModel;
import com.eeduspace.challenge.persist.po.TaskInfo;

/**
 * 任务信息
 */
public interface TaskInfoMapper  extends BaseDao<TaskInfo>{

    /**
     * 根据任务类型获取任务
     * @param taskType
     * @param concreteType
     * @return
     */
    TaskInfoModel findByTaskTypeAndConcreteType(String taskType,String concreteType);

}