package com.eeduspace.challenge.client.service.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.client.model.response.QuestionResponseModel;
import com.eeduspace.challenge.client.service.BaseResourceClient;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
@Service
public class BaseResourceClientImpl implements BaseResourceClient{
	@Value("${baseresource.server.url}")
	private String serverUrl;
	private final Logger logger = LoggerFactory.getLogger(BaseResourceClient.class);
	private Gson gson=new Gson();
	@Override
	public QuestionResponseModel getPapper(BaseResourceModel baseResourceModel) throws ClientProtocolException, IOException {
		logger.debug("BaseResourceModel:{}",gson.toJson(baseResourceModel));
		String resString=HTTPClientUtils.httpPostRequestJson(serverUrl+"/paper/unit", gson.toJson(baseResourceModel));
		logger.debug("getPapper reponse:{}",resString);
		QuestionResponseModel questionResponseModel=gson.fromJson(resString, QuestionResponseModel.class);
	    //BaseResponse baseResponse=new BaseResponse();
	    //ObaseResponse.setResult(questionResponseModel);
	    return questionResponseModel;
	}

}
