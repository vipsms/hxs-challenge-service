package com.eeduspace.challenge.service.impl;


import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.model.ShareModel;
import com.eeduspace.challenge.persist.dao.ShareMapper;
import com.eeduspace.challenge.persist.po.Share;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.service.ShareService;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.eeduspace.challenge.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("ShareServiceImpl")
public class ShareServiceImpl extends BaseServiceImpl<Share> implements ShareService {

    @Autowired
    public ShareServiceImpl(ShareMapper questionMapper) {
        this.baseDao = questionMapper;
    }

    @Autowired
    private UserConvert userConvert;
    @Autowired
    private TaskRewardReceiveService taskRewardReceiveService;
    @Autowired
    private UserService userService;
    /**
     * 保存用户分享 *
     */
    @Override
    public void saveUserShare(ShareModel shareModel) {
        Share share = new Share();
        share.setEventType(shareModel.getEventType());
        share.setShareType(shareModel.getShareType());
        if (StringUtils.isNotBlank(shareModel.getEquipmentsource())) {
            share.setEquipmentsource(userConvert.converterSourceEquipmentType(shareModel.getEquipmentsource()).getValue());
        }
        share.setUserCode(shareModel.getUserCode());
        share.setUuid(UUID.randomUUID().toString().replace("-", ""));
        share.setCreatDate(new Date());
        this.save(share);
        //TODO 当用户分享时则添加任务 新手任务
        TaskRewardReceive taskRewardReceive = new TaskRewardReceive();
        taskRewardReceive.setUserCode(shareModel.getUserCode());
        taskRewardReceive.setTaskType(TaskEnum.TaskInfo.NOVICE_TASK.getTaskCode());
        taskRewardReceive.setConcreteTaskCode(shareModel.getEventType());
        taskRewardReceiveService.saveByUser(taskRewardReceive);

    }


}
