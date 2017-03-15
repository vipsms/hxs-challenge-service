package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.model.SystemDictionaryModel;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**  
 * Author: zz
 * Date: 2016/7/16
 * Description:字典项
 */

@RestController
@RequestMapping(value="/systemDictionary")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
public class SystemDictionaryController {

    private final Logger logger = LoggerFactory.getLogger(SystemDictionaryController.class);
    private Gson gson=new Gson();
   
   
    @Autowired
    private SystemDictionaryService systemDictionaryService;
   
    /**
     *  字典项保存
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse UserDictionarySave( @RequestParam("requestId") String requestId,@RequestBody String requestBody) {
    	SystemDictionaryModel systemDictionaryModel = gson.fromJson(requestBody, SystemDictionaryModel.class);
    	BaseResponse baseResponse = new BaseResponse(requestId);
    	try {
            if (StringUtils.isBlank(systemDictionaryModel.getName())) {
                logger.error("systemDictionary save Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".name");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "name");
            }
            if (StringUtils.isBlank(systemDictionaryModel.getValue())) {
                logger.error("systemDictionary save Exception：requestId："+baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".value");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "value");
            } 	
            if (StringUtils.isBlank(systemDictionaryModel.getDescription())) {
                logger.error("systemDictionary save Exception：requestId："+baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".description");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "description");
            } 	
            SystemDictionary saveSystemDictionary = systemDictionaryService.saveSystemDictionary(systemDictionaryModel);
             if(saveSystemDictionary==null){
                 logger.error("systemDictionary save save  po exception.");
                 return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "systemDictionarySave");
             }
             baseResponse.setResult(saveSystemDictionary);
             return baseResponse;
        }catch (Exception e){
            logger.error("systemDictionary save  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    
    /**
     *  字典项查询
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse UserDictionaryFind( @RequestParam("requestId") String requestId) {
    	BaseResponse baseResponse = new BaseResponse(requestId);
    	try {
    		 List<SystemDictionary> list = systemDictionaryService.findAll();
             if(list.size()==0){
                 logger.error("systemDictionary list findAll po exception.");
                 return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "systemDictionaryList");
             }
             baseResponse.setResult(list);
             return baseResponse;
        }catch (Exception e){
            logger.error("systemDictionary list  error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }
    
    
    
    
    
    
    
}
