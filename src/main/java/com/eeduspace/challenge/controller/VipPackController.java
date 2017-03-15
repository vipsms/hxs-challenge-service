package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.model.VipPackModel;
import com.eeduspace.challenge.model.request.UserBuyVipRequestModel;
import com.eeduspace.challenge.model.request.UserBuyVipSuccessModel;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.VipPack;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.UserTaskLogService;
import com.eeduspace.challenge.service.VipPackService;
import com.eeduspace.challenge.util.HttpServletStream;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zz
 * Date: 2016/7/18
 * Description:vip包
 */
@Controller
@RequestMapping("/vip_pack")
public class VipPackController {
    private final Logger logger = LoggerFactory.getLogger(VipPackController.class);
    private Gson gson = new Gson();
    @Inject
    private VipPackService vipPackService;

    @Autowired
    private UserService userService;
    @Autowired
    private SystemDictionaryService systemDictionaryService;
    @Autowired
    private UserTaskLogService userTaskLogService;

    /**
     * 获取VIP包列表
     */
    @RequestMapping(value = "/vip_pack_list", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getVipPackList(@RequestParam("requestId") String requestId) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<VipPack> packs = new ArrayList<>();
        List<VipPackModel> packModels = new ArrayList<>();
        try {
            packs = vipPackService.findAll();
            if (packs.size() != 0) {
                for (VipPack vipPack : packs) {
                    VipPackModel vipPackModel1 = new VipPackModel();
                    vipPackModel1 = UserConvert.fromVipPackPo(vipPack);
                    packModels.add(vipPackModel1);
                }
            }
            baseResponse.setResult(packModels);
            baseResponse.setMessage("success");
            return baseResponse;
        } catch (Exception e) {
            logger.error("getVipPackList  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取VIP包添加
     */
    @RequestMapping(value = "/vip_pack_save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse SaveOrUpdateVipPackPo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        VipPackModel vipPackModel = gson.fromJson(requestBody, VipPackModel.class);
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<VipPack> packs = new ArrayList<>();
        try {
            if (StringUtils.isBlank(vipPackModel.getUuid())) {
                if (StringUtils.isBlank(String.valueOf(vipPackModel.getVipPrice()))) {
                    logger.error("SaveOrUpdateVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".vipPrice");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "vipPrice");
                }
                VipPack vipPacks = UserConvert.fromVipPackModel(vipPackModel);
                logger.debug("----------------------------vipPacks:{}", gson.toJson(vipPacks));
                VipPack vipPack = vipPackService.saveVipPack(vipPacks);
                if (vipPack == null) {
                    logger.error("SaveOrUpdateVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".savevipPack");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "savevipPack");
                }
                baseResponse.setResult(vipPack);
            } else {
                VipPack vipPack = vipPackService.findById(vipPackModel.getUuid());
                logger.debug("findById:{}", gson.toJson(vipPack));
                if (vipPack == null) {
                    logger.error(" SaveOrUpdateVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".updatevipPack");
                    return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "updatevipPack");
                }

                vipPackService.updateVipPack(vipPack, vipPackModel);
            }
            baseResponse.setMessage("success");
            return baseResponse;
        } catch (Exception e) {
            logger.error("SaveOrUpdateVipPack  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取VIP包删除
     */
    @RequestMapping(value = "/vip_pack_delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteVipPackPo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        VipPackModel vipPackModel = gson.fromJson(requestBody, VipPackModel.class);
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<VipPack> packs = new ArrayList<>();
        try {
            if (StringUtils.isBlank(vipPackModel.getUuid())) {
                logger.error(" deleteVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".uuid");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "uuid");
            }
            VipPack vipPack = vipPackService.findById(vipPackModel.getUuid());
            logger.debug("findById:{}", gson.toJson(vipPack));
            if (vipPack == null) {
                logger.error(" deleteVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".vipPack");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "vipPack");
            }
            vipPackService.deleteVipPack(vipPack);
            baseResponse.setMessage("success");
            return baseResponse;
        } catch (Exception e) {
            logger.error("deleteVipPack  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 用户购买Vip请求
     *
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/tobuy", method = RequestMethod.POST)
    @ResponseBody
    public void UserFindByVipList(@RequestParam("requestId") String requestId, @RequestBody String requestBody, HttpServletResponse response) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        String gsonResponse = "";
        try {
            UserBuyVipRequestModel model = gson.fromJson(requestBody, UserBuyVipRequestModel.class);
            logger.debug("tobuyVipPack:{}", requestBody);
            if (StringUtils.isBlank(model.getUserCode())) {
                logger.error("tobuyVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode")), response);
            }
            if (StringUtils.isBlank(model.getVipCode())) {
                logger.error("tobuyVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".vipCode");
                HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "vipCode")), response);
            }
            if (StringUtils.isBlank(model.getVipPrice())) {
                logger.error("tobuyVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".vipPrice");
                HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "vipPrice")), response);
            }
            if (StringUtils.isBlank(model.getGradeType())) {
                logger.error("tobuyVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".gradeType");
                HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "gradeType")), response);
            }
            if (StringUtils.isBlank(model.getVipDesc())) {
                logger.error("tobuyVipPack Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".vipDesc");
                HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "vipDesc")), response);
            }
            User user = userService.findByUserCode(model.getUserCode());
            if (null == user) {
                logger.error("buyStamina Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".user");
                HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user")), response);
            }
            if (StringUtils.isBlank(model.getIp())) {
                gsonResponse = userService.zhiFuBaoBuyVipPack(model);
                HttpServletStream.putString(gsonResponse, response);
            } else {
                gsonResponse = userService.weiXinBuyVipPack(model);
                HttpServletStream.putString(gsonResponse, response);
            }
        } catch (Exception e) {
            logger.error("tobuyVipPack  error:", e);
            HttpServletStream.putString(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString())), response);
        }
    }

    /**
     * 用户购买Vip成功返回
     *
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/buysuccess", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse UserToBuySuccess(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        UserBuyVipSuccessModel model = gson.fromJson(requestBody, UserBuyVipSuccessModel.class);
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            if (StringUtils.isBlank(model.getUserUniqueId())) {
                logger.error("buysuccess Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".userCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userCode");
            }
            if (StringUtils.isBlank(model.getVipProductCode())) {
                logger.error("buysuccess Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.PARAMETER_MISS.toString() + ".vipCode");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "vipCode");
            }
            User user = userService.findByUserCode(model.getUserUniqueId());
            if (null == user) {
                logger.error("buyStamina Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".user");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "user");
            }
            VipPack vipPack = vipPackService.findById(model.getVipProductCode());
            if (null == vipPack) {
                logger.error("buyStamina Exception：requestId：" + baseResponse.getRequestId() + "," + ResponseCode.RESOURCE_NOTFOUND.toString() + ".vipPack");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "vipPack");
            }
            userService.buyVipPackSuccess(model,user,vipPack);
            userTaskLogService.UserTaskLogSave(model.getUserUniqueId(),"vip_pack", null, null,"buy_vip",null);
            return baseResponse;
        } catch (Exception e) {
            logger.error("buysuccess  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
}
