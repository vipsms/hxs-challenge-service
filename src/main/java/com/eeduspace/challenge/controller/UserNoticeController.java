package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.enumeration.NoticeEnum;
import com.eeduspace.challenge.model.request.UserNoticeRequestModel;
import com.eeduspace.challenge.persist.po.Notice;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.NoticeService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:通知管理
 */
@RestController
@RequestMapping(value="/user/notice")
public class UserNoticeController {

    private final Logger logger = LoggerFactory.getLogger(UserScanController.class);
    private Gson gson=new Gson();
    @Autowired
    private NoticeService noticeService;
    /**
     * 获取好友列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse list(@RequestParam("requestId") String requestId,
                             @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserNoticeRequestModel requestModel = gson.fromJson(requestBody, UserNoticeRequestModel.class);
            if(StringUtils.isBlank(requestModel.getUserCode())){
                logger.error("user notice list Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".MyUserCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            baseResponse.setResult(noticeService.findNoticeByUserCode(requestModel.getUserCode()));
            return baseResponse;
        } catch (Exception e) {
            logger.error("user notice list  error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 请求添加为好友
     * 向对方用户发送消息
     */
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse apply(@RequestParam("requestId") String requestId,
                              @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            UserNoticeRequestModel requestModel = gson.fromJson(requestBody, UserNoticeRequestModel.class);
            if(StringUtils.isBlank(requestModel.getNoticeUuid())){
                logger.error("user notice info  Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".NoticeUuid");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "NoticeUuid");
            }

            //验证是不是好友
            Notice notice= noticeService.findByUuid(requestModel.getNoticeUuid());
            if(notice==null){
                logger.error("user notice info  Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".notice");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "notice");
            }
            if(notice.getReadState().intValue() == NoticeEnum.ReadState.NOREAD.getValue()) {
            	baseResponse.setResult(notice);
            	notice.setReadState(NoticeEnum.ReadState.READ.getValue());
                noticeService.update(notice);
            }

            return baseResponse;
            
        } catch (Exception e) {
            logger.error("user notice info  error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
}
