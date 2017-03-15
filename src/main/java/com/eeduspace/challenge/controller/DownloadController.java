package com.eeduspace.challenge.controller;


import com.eeduspace.challenge.model.DownloadModel;
import com.eeduspace.challenge.responsecode.BaseResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**  
 * Author: yongqian.chen
 * Date: 2016/9/22
 * Description:app下载
 */
@Controller
@RequestMapping("/download")
public class DownloadController {
	private final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	@Value("${hxs.xiaoxue.download.url}")
	private String xiaoxueDownloadUrl;
	@Value("${hxs.chuzhong.download.url}")
	private String chuzhongDownloadUrl;
	@Value("${hxs.gaozhong.download.url}")
	private String gaozhongDownloadUrl;
	/**
	 * app下载
	 */
	@RequestMapping(value="/download",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse downloadFile(@RequestParam("requestId") String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
        
        List<DownloadModel> list = new ArrayList<DownloadModel>();
        if(StringUtils.isNotBlank(xiaoxueDownloadUrl)) {
        	DownloadModel xiaoxueDownloadModel = new DownloadModel();
            xiaoxueDownloadModel.setStageName("挑战好学生-小学版app");
            xiaoxueDownloadModel.setDownloadUrl(xiaoxueDownloadUrl);
            list.add(xiaoxueDownloadModel);
        }
        if(StringUtils.isNotBlank(chuzhongDownloadUrl)) {
        	DownloadModel chuzhongDownloadModel = new DownloadModel();
            chuzhongDownloadModel.setStageName("挑战好学生-初中版app");
            chuzhongDownloadModel.setDownloadUrl(chuzhongDownloadUrl);
            list.add(chuzhongDownloadModel);
        }
        if(StringUtils.isNotBlank(gaozhongDownloadUrl)) {
        	DownloadModel gaozhongDownloadModel = new DownloadModel();
            gaozhongDownloadModel.setStageName("挑战好学生-高中版app");
            gaozhongDownloadModel.setDownloadUrl(gaozhongDownloadUrl);
            list.add(gaozhongDownloadModel);
        }
        baseResponse.setResult(list);
	    baseResponse.setMessage("success");
		return baseResponse;
	}
	
}
