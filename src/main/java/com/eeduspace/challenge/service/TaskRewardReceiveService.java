package com.eeduspace.challenge.service;

import com.eeduspace.challenge.model.TaskRewardReceiveModel;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.persist.po.User;

import java.util.List;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
public interface TaskRewardReceiveService extends BaseService<TaskRewardReceive> {

    /**
     * 根据用户code获取任务列表
     * @param userCode
     * @return
     */
    List<TaskRewardReceive> findByUser(String userCode);

    /**
     * 根据UUID查找
     * @param taskRewardReceiveUuid
     * @return
     */
    TaskRewardReceive findByUuid(String taskRewardReceiveUuid);
    /**
     * 保存任务奖励领取记录 验证是否已存在该记录
     *
     * @param taskRewardReceive
     * @param user 用户实体 带有vip及用户code信息
     */
    void saveByUser(User user,TaskRewardReceive taskRewardReceive);
    /**
     * 保存任务奖励领取记录 验证是否已存在该记录
     *
     * @param taskRewardReceive
     */
    void saveByUser(TaskRewardReceive taskRewardReceive);

    /**
     * 保存任务奖励领取记录 验证是否已存在该记录
     *
     * @param model
     * @ taskType 任务code
     * @ concreteTaskCode 具体小类任务code
     * @ user 用户实体 带有vip及用户code信息
     */
    void saveByUser(TaskRewardReceiveModel model);

    /**
     * 用户领取任务奖励
     * @param taskRewardReceive
     */

    void receive(TaskRewardReceive taskRewardReceive);
    /**
     * 根据用户名还有 任务uuid查询更新该记录
     */
    void updateByUuidAndUserCode(String taskUuid,String userCode);
    /**
     * 根据用户名还有uuid查找
     * @param taskUuid
     * @param userCode
     * @return
     */
    TaskRewardReceive findByTaskUuidAndUserCode(String taskUuid,String userCode);
}
