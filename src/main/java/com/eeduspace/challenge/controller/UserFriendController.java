package com.eeduspace.challenge.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eeduspace.challenge.enumeration.NoticeEnum;
import com.eeduspace.challenge.model.UserFriendModel;
import com.eeduspace.challenge.model.request.FriendRequestModel;
import com.eeduspace.challenge.persist.po.Notice;
import com.eeduspace.challenge.persist.po.UserFriend;
import com.eeduspace.challenge.persist.po.UserFriendApply;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.NoticeService;
import com.eeduspace.challenge.service.UserFriendService;
import com.google.gson.Gson;


/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:
 */
@RestController
@RequestMapping(value="/user/friend")
public class UserFriendController {

    private final Logger logger = LoggerFactory.getLogger(UserScanController.class);
    private Gson gson=new Gson();
    @Autowired
    private UserFriendService userFriendService;
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
            FriendRequestModel friendRequestModel = gson.fromJson(requestBody, FriendRequestModel.class);

            if(StringUtils.isBlank(friendRequestModel.getMyUserCode())){
                logger.error("user friend list Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".MyUserCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "MyUserCode");
            }
            baseResponse.setResult(userFriendService.findFriendByUserCode(friendRequestModel.getMyUserCode()));
            return baseResponse;
        } catch (Exception e) {
            logger.error("user friend list error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 请求添加为好友
     * 向对方用户发送消息
     */
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse apply(@RequestParam("requestId") String requestId,
                              @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            FriendRequestModel friendRequestModel = gson.fromJson(requestBody, FriendRequestModel.class);

            if(StringUtils.isBlank(friendRequestModel.getMyUserCode())){
                logger.error("user friend apply Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".MyUserCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "MyUserCode");
            }
            if(StringUtils.isBlank(friendRequestModel.getFriendCode())){
                logger.error("user friend apply Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".friendCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "friendCode");
            }
            //验证是不是好友
            UserFriend userFriend= userFriendService.findByMyUserCodeAndFriendCode(friendRequestModel.getMyUserCode(), friendRequestModel.getFriendCode());
            if(userFriend!=null){
            	if(userFriend.getFriendCode().equals(friendRequestModel.getFriendCode())){
            		logger.error("user friend apply Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.ALREADY_FRIEND.toString()+".friendCode");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.ALREADY_FRIEND.toString(), "friendCode");
            	}
            }
            
            UserFriendApply userFriendApply= userFriendService.apply(friendRequestModel);
            if(userFriendApply==null){
                logger.error("user friend apply error:{}");
                return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
            }
            baseResponse.setResult(userFriendApply);
            return baseResponse;
        } catch (Exception e) {
            logger.error("user friend apply error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 对方同意添加为好友
     * 向对方用户发送消息
     */
    @RequestMapping(value="/reply",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse agree(@RequestParam("requestId") String requestId,
                              @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            FriendRequestModel friendRequestModel = gson.fromJson(requestBody, FriendRequestModel.class);

            if(StringUtils.isBlank(friendRequestModel.getApplyUuid())){
                logger.error("user friend agree Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".ApplyUuid");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "ApplyUuid");
            }
            if(null==friendRequestModel.getReplyState()){
                logger.error("user friend agree Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".ApplyState");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "ReplyState");
            }
            UserFriendApply userFriendApply=userFriendService.findByApplyUuid(friendRequestModel.getApplyUuid());
            if(null==userFriendApply/* || !"BeSend".equals(userFriendApply.getApplyState())*/){
                logger.error("user friend agree Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".userFriendApply");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userFriendApply");
            }
            HashMap<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("sendUser", userFriendApply.getMyUserCode());
            queryMap.put("receiveUser", userFriendApply.getFriendCode());
            List<Notice> list = noticeService.findByCondition(queryMap);
            for (Notice notice : list) {
            	notice.setReadState(NoticeEnum.ReadState.READ.getValue());
			}
            noticeService.updateList(list);
            //当对方在线时 使用消息
            //当对方不在线时 使用推送方式
            userFriendService.agreeOrDisagreeApply(userFriendApply,friendRequestModel.getReplyState());
            return baseResponse;
        } catch (Exception e) {
            logger.error("user friend agree error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 验证是否是好友
     * 向对方用户发送消息
     */
    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse validate(@RequestParam("requestId") String requestId,
                                 @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            FriendRequestModel friendRequestModel = gson.fromJson(requestBody, FriendRequestModel.class);

            if(StringUtils.isBlank(friendRequestModel.getMyUserCode())){
                logger.error("user friend validate Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".MyUserCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "MyUserCode");
            }
            if(StringUtils.isBlank(friendRequestModel.getFriendCode())){
                logger.error("user friend validate Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".friendCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "friendCode");
            }
            UserFriendModel userFriendModel=new UserFriendModel();
            //验证是不是好友
            UserFriend userFriend= userFriendService.findByMyUserCodeAndFriendCode(friendRequestModel.getMyUserCode(), friendRequestModel.getFriendCode());
            if(userFriend!=null){
                userFriendModel.setFriend(true);
            }else{
            	userFriendModel.setFriend(false);
            }
            baseResponse.setResult(userFriendModel);
            return baseResponse;
        } catch (Exception e) {
            logger.error("user friend validate error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }
}
