package com.eeduspace.challenge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.model.IntegralChangeModel;
import com.eeduspace.challenge.model.TaskInfoModel;
import com.eeduspace.challenge.model.TaskRewardReceiveModel;
import com.eeduspace.challenge.persist.dao.TaskInfoMapper;
import com.eeduspace.challenge.persist.dao.TaskRewardReceiveMapper;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.eeduspace.challenge.service.UserService;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:任务领取管理
 */
@Service("TaskRewardReceiveServiceImpl")
public class TaskRewardReceiveServiceImpl extends BaseServiceImpl<TaskRewardReceive> implements TaskRewardReceiveService {
    private final Logger logger = LoggerFactory.getLogger(TaskRewardReceiveServiceImpl.class);

    @Autowired
    public TaskRewardReceiveServiceImpl(TaskRewardReceiveMapper taskRewardReceiveMapper) {
        this.baseDao = taskRewardReceiveMapper;
    }

    @Autowired
    private TaskInfoMapper taskInfoMapper;
    @Autowired
    private TaskRewardReceiveMapper taskRewardReceiveMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<TaskRewardReceive> findByUser(String userCode) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("userCode", userCode);
        return this.findByCondition(queryMap,null);
    }

    @Override
    public TaskRewardReceive findByUuid(String taskRewardReceiveUuid) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("task_uuid", taskRewardReceiveUuid);
        List<TaskRewardReceive> list= this.findByCondition(queryMap,null);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    /**
     * 添加用户任务奖励记录
     * @param user 用户实体 带有vip及用户code信息
     * @param taskRewardReceive
     */
    @Override
    public void saveByUser(User user, TaskRewardReceive taskRewardReceive) {
        TaskRewardReceiveModel taskRewardReceiveModel=new TaskRewardReceiveModel();
        taskRewardReceiveModel.setUser(user);
        taskRewardReceiveModel.setTaskType(taskRewardReceive.getTaskType());
        taskRewardReceiveModel.setConcreteTaskCode(taskRewardReceive.getConcreteTaskCode());
        this.saveByUser(taskRewardReceiveModel);
    }

    /**
     * 添加用户任务奖励记录
     * 1.获取具体任务、任务规则实体
     *
     * @param taskRewardReceive
     */
    @Override
    public void saveByUser(TaskRewardReceive taskRewardReceive) {
        User user=userService.findByUserCode(taskRewardReceive.getUserCode());
        if(user==null){
            return;
        }
        TaskRewardReceiveModel taskRewardReceiveModel=new TaskRewardReceiveModel();
        taskRewardReceiveModel.setUser(user);
        taskRewardReceiveModel.setTaskType(taskRewardReceive.getTaskType());
        taskRewardReceiveModel.setConcreteTaskCode(taskRewardReceive.getConcreteTaskCode());
        this.saveByUser(taskRewardReceiveModel);

    }

    /**
     * 保存用户任务领取记录
     * @param model
     */
    @Override
    public void saveByUser(TaskRewardReceiveModel model) {
        //FIXME 新手任务时，需要将分享战报与分享对战、分享挑战合并为分享战报
        if (TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode().equals(model.getTaskType())) {
            if(model.getConcreteTaskCode().contains(TaskEnum.SpecificTasks.STAND_SHARE.getTaskCode())){
                model.setConcreteTaskCode(TaskEnum.SpecificTasks.STAND_SHARE.getTaskCode());
            }

            TaskInfoModel taskInfoModel = taskInfoMapper.findByTaskTypeAndConcreteType(model.getTaskType(), model.getConcreteTaskCode());
            if (taskInfoModel == null) {
                return;
            }

            List<TaskRewardReceive> list = taskRewardReceiveMapper.findByUserCodeAndTaskUuid(model.getUserCode(), taskInfoModel.getUuid());
            if (list != null && list.size() > 0) {
                return;
            }
            logger.debug("------------------记录任务");
            TaskRewardReceive taskRewardReceive = new TaskRewardReceive();
            taskRewardReceive.setUserCode(model.getUser().getUserCode());
            taskRewardReceive.setTaskType(model.getTaskType());
            taskRewardReceive.setConcreteTaskCode(model.getConcreteTaskCode());
            taskRewardReceive.setUuid(UUID.randomUUID().toString().replace("-", ""));
            taskRewardReceive.setTaskUuid(taskInfoModel.getUuid());
            taskRewardReceive.setTaskName(taskInfoModel.getTaskName());
            taskRewardReceive.setConcreteTaskCode(taskInfoModel.getConcreteTaskCode());
            taskRewardReceive.setReceiveState(TaskEnum.ReceiveState.NoReceive.getValue());
            taskRewardReceive.setUpdateDate(new Date());
            //TODO 验证用户是否是VIP
            taskRewardReceive.setRewardPoint(taskInfoModel.getRewardPoint());
            if (model.getUser().getIsVip() == UserEnum.Vip.Vip_user.getValue() && model.getUser().getVipEndDate().after(new Date())) {
                taskRewardReceive.setRewardPoint(taskInfoModel.getVipRewardPoint());
            }
            this.save(taskRewardReceive);
        }
    }

    @Override
    @Transactional
    public void receive(TaskRewardReceive taskRewardReceive) {
        this.update(taskRewardReceive);
        //TODO 处理用户积分 用户基础信息
        User winUser=userService.findByUserCode(taskRewardReceive.getUserCode());
        IntegralChangeModel winUserChangeModel=new IntegralChangeModel();
        winUserChangeModel.setChangeType(RewardEnum.ChangeType.REWARD);
        winUserChangeModel.setRewardType(RewardEnum.RewardType.TASK);
        winUserChangeModel.setUser(winUser);
        
        logger.debug("领取任务积分问题：taskRewardReceive.getRewardPoint()： "+taskRewardReceive.getRewardPoint());
        winUserChangeModel.setRewardIntegral(taskRewardReceive.getRewardPoint());
        winUserChangeModel.setRewardSourceUuid(taskRewardReceive.getUuid());
        userService.updateUserIntegralChange(winUserChangeModel);
        logger.debug("updateUserIntegralChange(winUserChangeModel):"+(winUserChangeModel));
    }
    @Transactional
    public void updateByUuidAndUserCode(String taskUuid,String userCode){
    	 Map<String, Object> queryMap=new HashMap<String, Object>();
         queryMap.put("taskUuid", taskUuid);
         queryMap.put("userCode", userCode);
    	taskRewardReceiveMapper.updateByUuidAndUserCode(queryMap);
    }

	@Override
	public TaskRewardReceive findByTaskUuidAndUserCode(String taskUuid, String userCode) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("taskUuid", taskUuid);
        queryMap.put("userCode", userCode);
        
		return taskRewardReceiveMapper.findByTaskUuidAndUserCode(queryMap);
	}
}
