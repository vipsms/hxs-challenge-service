package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.model.UserSignModel;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserSign;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.UserSignService;
import com.eeduspace.uuims.comm.util.base.DateUtils;
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
 * Date: 2016/7/21
 * Description:用户签到
 */
@RestController
@RequestMapping(value="/user/sign")
public class UserSignController {


    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Gson gson=new Gson();

    @Autowired
    private UserService userService;
    @Autowired
    private UserSignService userSignService;
    /**
     * 获取用户最后签到记录
     *
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse list( @RequestParam("requestId") String requestId,
                                    @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);

            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("user sign list  Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            List<UserSign> list=userSignService.findAllByUserCode(userRequestModel.getUserCode());
            baseResponse.setResult(list);
            return baseResponse;

        } catch (Exception e) {
            logger.error("user sign list  error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取用户最后签到记录
     *
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse info( @RequestParam("requestId") String requestId,
                                    @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);

            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("user sign info  Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            UserSign userSign= userSignService.findLastByUserCode(userRequestModel.getUserCode());
            UserSignModel userSignModel=new UserSignModel();
            if(userSign==null){
            	userSignModel.setIsTodeaySign(false);
            	userSignModel.setSignTimes(0l);
            }else{
            	 userSignModel =  UserConvert.fromuserSignToModel(userSign);
            }
            baseResponse.setResult(userSignModel);
            return baseResponse;
        } catch (Exception e) {
            logger.error("user sign info  error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
    }
    /**
     * 用户签到
     *
     * @return
     */
    @RequestMapping(value = "/past",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse sign( @RequestParam("requestId") String requestId,
                              @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            UserRequestModel userRequestModel = gson.fromJson(requestBody, UserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("sign Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userCode");
            }
            User user= userService.findByUserCode(userRequestModel.getUserCode());
            if(user==null){
                logger.error("sign Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            }
            UserSign userSign= userSignService.findLastByUserCode(userRequestModel.getUserCode());
            if (userSign!=null) {
        	   int isTodeySign = DateUtils.compareDate(userSign.getSignDate(), new Date(), 5);
	           	if (isTodeySign==0) {
	                logger.error("sign Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_DUPLICATE.toString()+".userSign");
	                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "userSign");
	       		}
		    }
            userSign= userSignService.sign(user);
            baseResponse.setResult(userSign);
            return baseResponse;

        } catch (Exception e) {
            logger.error("sign error:{}",e);
            return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
        }
    }
}
