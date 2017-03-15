package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.model.TokenModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.UserTelevisionModel;
import com.eeduspace.challenge.model.request.LoginRequestModel;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.util.CommonUtil;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Author: dingran
 * Date: 2016/7/12
 * Description:
 */
@RestController
@RequestMapping(value="/user/scan")
public class UserScanController {

    private final Logger logger = LoggerFactory.getLogger(UserScanController.class);
    private Gson gson=new Gson();
    @Value("${user.scan.login.timeout}")
    private String scanExpires;
    @Value("${hxs.manager.ak}")
    private String ak;
    @Value("${user.confirm.login.timeout}")
    private String timeoutExpires;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConvert userConvert;
    /**
     * 根据设备号返回扫描登录状态或者userInfo
     * 1.当用户未确认登录时 则返回扫描状态
     * 2.当用户已确认登录是 则返回用户基本信息
     * @return
     */
    @RequestMapping(value = "/status",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse process(
            @RequestParam("requestId") String requestId,
            @RequestBody String requestBody,HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            LoginRequestModel loginRequestModel = gson.fromJson(requestBody, LoginRequestModel.class);

            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), gson.toJson(loginRequestModel));

            if (StringUtils.isBlank(loginRequestModel.getAccessKey())) {
                logger.error("login status Exception：requestId：" + baseResponse.getRequestId()+ "," + ResponseCode.PARAMETER_MISS.toString() + ".accessKey");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "accessKey");
            }
            if (StringUtils.isBlank(loginRequestModel.getTelevisionCode())) {
                logger.error("login status Exception：requestId：" + baseResponse.getRequestId()+ "," + ResponseCode.PARAMETER_MISS.toString() + ".TelevisionCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "TelevisionCode");
            }
            logger.debug("______>" + ak);
            logger.debug("______>" + loginRequestModel.getAccessKey());
            if (!loginRequestModel.getAccessKey().equals(ak)) {
                logger.error("login status Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.FORBIDDEN_AUTHFAILURE.toString() + ".accessKey");
                return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_AUTHFAILURE.toString(), "accessKey");
            }
            String televisionRedis = redisClientTemplate.get(loginRequestModel.getTelevisionCode());
            if (StringUtils.isBlank(loginRequestModel.getTelevisionCode())) {
                logger.debug("requestId：" + baseResponse.getRequestId() + "," + "televisionCode is not find.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_AUTHFAILURE.toString(), "accessKey");
            }
            UserTelevisionModel userTelevisionModel = gson.fromJson(televisionRedis, UserTelevisionModel.class);
            if (userTelevisionModel == null || StringUtils.isBlank(userTelevisionModel.getUserCode())) {
                logger.error("requestId：" + baseResponse.getRequestId() + "," + "userTelevisionModel or userTelevisionModel.getUserCode is null.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_AUTHFAILURE.toString(), "accessKey");
            }
            User userPo = userService.findByUserCode(userTelevisionModel.getUserCode());
            if (userPo == null) {
                logger.error("requestId：" + baseResponse.getRequestId() + "," + "userPo.getUserCode(" + userTelevisionModel.getUserCode() + ") is not find.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            }

            UserModel userModel = new UserModel();
            if (userTelevisionModel.getScanStatus().equals(UserEnum.ScanStatus.ConfirmLogin)) {
                userModel = userConvert.fromUserPo(userPo, userTelevisionModel.getScanStatus());
                String token_ = redisClientTemplate.get("hxs_challenge_"+userPo.getUserCode());
                TokenModel tokenModel = new TokenModel();
                //当redis中不存在token信息时，则重新生成？
                if (StringUtils.isBlank(token_)) {
                    logger.error("requestId：" + baseResponse.getRequestId() + "," + "redis.getUserCode(" + userPo.getUserCode() + ") is not find.");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_AUTHFAILURE.toString());
                }
                String tokenModel_ = redisClientTemplate.get(token_);
                tokenModel = gson.fromJson(tokenModel_, TokenModel.class);
                userModel.setToken(tokenModel.getToken());
                userModel.setRefreshToken(tokenModel.getRefreshToken());
            } else {
                userModel.setScanStatus(userTelevisionModel.getScanStatus().toString());
            }

            baseResponse.setResult(userModel);
            logger.debug("login get scanInfo by televisionCode response:" + gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("login get scanInfo by televisionCode error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 将设备号与用户进行绑定  用户扫码
     * 1.同一时间用户只能登陆一个终端设备
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse scan(
            @RequestParam("requestId") String requestId,
            @RequestBody String requestBody,HttpServletRequest request) {

        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            LoginRequestModel loginRequestModel = gson.fromJson(requestBody, LoginRequestModel.class);

            //TODO 需要传递产品类型
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), gson.toJson(loginRequestModel));
            if (StringUtils.isBlank(loginRequestModel.getTelevisionCode())) {
                logger.error("login scan upload Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".televisionCode");
                return BaseResponse.setResponse( baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "televisionCode");
            }
            if (StringUtils.isBlank(loginRequestModel.getUserCode())) {
                logger.error("login scan upload Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            //TODO 当查询不到时，则认为用户不存在
            User userPo = userService.findByUserCode(loginRequestModel.getUserCode());
            if (userPo == null) {
                logger.error("login scan upload Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userPo");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userPo");
            }

            UserTelevisionModel userTelevisionModel = new UserTelevisionModel();
            userTelevisionModel.setUserCode(userPo.getUserCode());
            userTelevisionModel.setScanStatus(UserEnum.ScanStatus.IsScan);
            userTelevisionModel.setTelevisionCode(loginRequestModel.getTelevisionCode());
            redisClientTemplate.setex(loginRequestModel.getTelevisionCode(),Integer.parseInt(scanExpires), gson.toJson(userTelevisionModel));
            logger.debug("login scan upload:userTelevisionModel{}", gson.toJson(userTelevisionModel));

            return baseResponse;
        } catch (Exception e) {
            logger.error("login scan upload error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 用户确认登录
     * 1.同一时间用户只能登陆一个终端设备  当用户确认登录后
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/confirm",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse confirm(
            @RequestParam("requestId") String requestId,
            @RequestBody String requestBody,HttpServletRequest request) {

        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            LoginRequestModel loginRequestModel = gson.fromJson(requestBody, LoginRequestModel.class);

            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", baseResponse.getRequestId(), CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), gson.toJson(loginRequestModel));
            if (StringUtils.isBlank(loginRequestModel.getTelevisionCode())) {
                logger.error("login scan confirm Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".televisionCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "televisionCode");
            }
            if (StringUtils.isBlank(loginRequestModel.getUserCode())) {
                logger.error("login scan confirm Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            //验证用户是否存在
            User userPo = userService.findByUserCode(loginRequestModel.getUserCode());
            if (userPo == null) {
                logger.error("login scan confirm Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".userPo");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "userPo");
            }
            //验证设备与用户关联 是否存在
            String televisionRedis = redisClientTemplate.get(loginRequestModel.getTelevisionCode());
            UserTelevisionModel userTelevisionModel = gson.fromJson(televisionRedis, UserTelevisionModel.class);
            if (userTelevisionModel == null || StringUtils.isBlank(userTelevisionModel.getUserCode())) {
                logger.error("requestId：" + baseResponse.getRequestId() + "," + "userTelevisionModel or userTelevisionModel.getUserCode is null.");
                return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_AUTHFAILURE.toString(), "accessKey");
            }
            if (!userTelevisionModel.getScanStatus().equals(UserEnum.ScanStatus.IsScan)) {
                logger.error("requestId：" + baseResponse.getRequestId() + "," + ResponseCode.FORBIDDEN_NOSCAN.toString());
                return BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_NOSCAN.toString());
            }
            userTelevisionModel.setScanStatus(UserEnum.ScanStatus.ConfirmLogin);
            logger.debug("login scan confirm:userTelevisionModel{}", gson.toJson(userTelevisionModel));
            //TODO 异步队列 将绑定删除 暂定20秒
            redisClientTemplate.setex(loginRequestModel.getTelevisionCode(), Integer.parseInt(timeoutExpires), gson.toJson(userTelevisionModel));

            //TODO 向UUIMS 发送登录请求
            UserRequestModel userRequestModel=new UserRequestModel();
            userRequestModel.setMobile(userPo.getMobile());
            userRequestModel.setPassword(userPo.getPassword());
            BaseResponse loginResponse= userService.login(userRequestModel,false);
            loginResponse.setRequestId(baseResponse.getRequestId());
            return loginResponse;
        } catch (Exception e) {
            logger.error("login scan confirm error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 用户取消登录
     * 1.删除设备与用户的绑定关系
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse cancel(
            @RequestParam("requestId") String requestId,
            @RequestBody String requestBody,HttpServletRequest request) {

        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            LoginRequestModel loginRequestModel = gson.fromJson(requestBody, LoginRequestModel.class);

            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}", baseResponse.getRequestId(), CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI(), gson.toJson(loginRequestModel));
            if (StringUtils.isBlank(loginRequestModel.getTelevisionCode())) {
                logger.error("login scan cancel Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".televisionCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "televisionCode");
            }
            if (StringUtils.isBlank(loginRequestModel.getUserCode())) {
                logger.error("login scan cancel Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            //TODO 扫码登录需要产品类型标识 同时一个产品一个TOKEN
            String token = redisClientTemplate.get("hxs_challenge_"+loginRequestModel.getUserCode());
            if (StringUtils.isNotBlank(token)) {
                redisClientTemplate.del(token);
            }
            redisClientTemplate.del("hxs_challenge_"+loginRequestModel.getUserCode());
            redisClientTemplate.del(loginRequestModel.getTelevisionCode());

            return baseResponse;
        } catch (Exception e) {
            logger.error("login scan cancel error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

}
