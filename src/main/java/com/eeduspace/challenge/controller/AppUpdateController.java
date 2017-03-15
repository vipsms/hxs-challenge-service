package com.eeduspace.challenge.controller;


import com.eeduspace.challenge.model.AppUpdateModel;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.AppUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**  
 * Author: zz
 * Date: 2016/7/18
 * Description:app下载
 */
@Controller
@RequestMapping("/appupdate")
public class AppUpdateController {
	private final Logger logger = LoggerFactory.getLogger(AppUpdateController.class);
	@Value("${hxs.server.url}")
	private String address;
	@Inject
	private AppUpdateService appUpdateService;
	/**
	 * app下载
	 */
	@RequestMapping(value="/download",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse downloadFile(@RequestParam("requestId") String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
		try {
			 AppUpdateModel appUpdateModel = appUpdateService.getAppUpdateModel();
			 String path =address + appUpdateModel.getDownUrl();
			 logger.debug("path地址为：" + path);
			 appUpdateModel.setDownUrl(path);
			 baseResponse.setResult(appUpdateModel);
		     baseResponse.setMessage("success");
			 return baseResponse;
		} catch (Exception e) {
			 logger.error("downloadFile  error:", e);
			 return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
	}
	
}
