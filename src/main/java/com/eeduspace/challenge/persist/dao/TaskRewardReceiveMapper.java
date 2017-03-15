package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.TaskRewardReceive;

import java.util.List;
import java.util.Map;

/**
 * 任务奖励领取记录
 */
public interface TaskRewardReceiveMapper  extends BaseDao<TaskRewardReceive>{
    /**
     * 获取用户奖励领取记录
     * @param userCode
     * @param taskUuid
     * @return
     */
    List<TaskRewardReceive> findByUserCodeAndTaskUuid(String userCode,String taskUuid);
    /**
     * 根据用户名还有 任务uuid查询更新该记录
     */
    void updateByUuidAndUserCode(Map<String, Object> queryMap);
	TaskRewardReceive findByTaskUuidAndUserCode(Map<String, Object> queryMap);
}