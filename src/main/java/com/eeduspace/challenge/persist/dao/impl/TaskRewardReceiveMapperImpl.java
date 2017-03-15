package com.eeduspace.challenge.persist.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.TaskRewardReceiveMapper;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:任务奖励领取记录 ：当用户完成任务时 则添加一条任务记录
 */
@Repository("TaskRewardReceiveMapperImpl")
public class TaskRewardReceiveMapperImpl extends BaseDaoImpl<TaskRewardReceive> implements TaskRewardReceiveMapper {
	private final String UpdateByUuidAndUserCode = "UpdateByUuidAndUserCode";
	private final String findByTaskUuidAndUserCode = "findByTaskUuidAndUserCode";
	
    @Override
    public List<TaskRewardReceive> findByUserCodeAndTaskUuid(String userCode, String taskUuid) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        queryMap.put("taskUuid", taskUuid);
        return this.findByCondition(queryMap,null);
    }

    /**
     * 根据用户名还有 任务uuid查询更新该记录
     */
    public void updateByUuidAndUserCode(Map<String, Object> queryMap){
    	this.sessionTemplate.update(UpdateByUuidAndUserCode, queryMap);
    }
    /**
     * 根据用户名还有 任务uuid查询
     */
    public TaskRewardReceive findByTaskUuidAndUserCode(Map<String, Object> queryMap){
    	return this.sessionTemplate.selectOne(findByTaskUuidAndUserCode, queryMap);
    }
}
