package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.model.request.TaskRequestModel;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.TaskRewardReceiveService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Author: dingran
 * Date: 2016/8/2
 * Description:
 */
@RestController
@RequestMapping(value="/task")
public class TaskController {


    private final Logger logger = LoggerFactory.getLogger(UserScanController.class);
    private Gson gson=new Gson();
    @Autowired
    private TaskRewardReceiveService  taskRewardReceiveService;
    /**
     * 获取好友列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse list(@RequestParam("requestId") String requestId,
                             @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            TaskRequestModel requestModel = gson.fromJson(requestBody, TaskRequestModel.class);
            if(StringUtils.isBlank(requestModel.getUserCode())){
                logger.error("user task list Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".MyUserCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            List<TaskRewardReceive> findByUser = taskRewardReceiveService.findByUser(requestModel.getUserCode());
            /**
             * 原来不准备在数据库之中加字段  这么做的
             */
//            for (TaskRewardReceive taskRewardReceive : findByUser) {
//            	Integer receiveState = taskRewardReceive.getReceiveState();
//            	if(receiveState==1){
//            		taskRewardReceive.setFinishState(TaskEnum.FinishState.IsFinish.getValue());
//            	} else {
//            		taskRewardReceive.setFinishState(TaskEnum.FinishState.NoFinish.getValue());
//            	}
//			}
            baseResponse.setResult(findByUser);
            logger.debug("/list 获取任务列表："+gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("user ask list error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 领取任务积分
     * 
     */
    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse apply(@RequestParam("requestId") String requestId,
                              @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            TaskRequestModel requestModel = gson.fromJson(requestBody, TaskRequestModel.class);
            if(StringUtils.isBlank(requestModel.getTaskRewardReceiveUuid())){
                logger.error("user task receive Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".NoticeUuid");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "NoticeUuid");
            }
            if(StringUtils.isBlank(requestModel.getUserCode())){
                logger.error("user task receive Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".MyUserCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }

            //验证是不是好友
            TaskRewardReceive taskRewardReceive= taskRewardReceiveService.findByTaskUuidAndUserCode(requestModel.getTaskRewardReceiveUuid(),requestModel.getUserCode());
            if(taskRewardReceive==null){
                logger.error("user task receive Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".taskRewardReceive");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "taskRewardReceive");
            }
            if(taskRewardReceive.getReceiveState()==TaskEnum.ReceiveState.IsReceive.getValue()){
                logger.error("user task receive Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.TASK_ALREADY_RECEIVE.toString()+".ReceiveState");
                return BaseResponse.setResponse(baseResponse, ResponseCode.TASK_ALREADY_RECEIVE.toString(), "ReceiveState");
            }
            taskRewardReceive.setReceiveDate(new Date());
            taskRewardReceive.setReceiveState(TaskEnum.ReceiveState.IsReceive.getValue());
            taskRewardReceiveService.receive(taskRewardReceive);
            baseResponse.setResult(taskRewardReceive);
            return baseResponse;
        } catch (Exception e) {
            logger.error("user task receive error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
}
