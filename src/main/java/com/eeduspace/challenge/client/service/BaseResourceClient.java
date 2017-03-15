package com.eeduspace.challenge.client.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.eeduspace.challenge.client.model.response.QuestionResponseModel;
import com.eeduspace.challenge.model.BaseResourceModel;

/**
 * 访问资源信息客户端
 * @author zhuchaowei
 * 2016年7月15日
 * Description
 */
public interface BaseResourceClient {
	/**
	 * 根据学年学科获取试卷信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 下午2:10:52
	 * @param subjectCode
	 * @param gradeCode
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public QuestionResponseModel getPapper(BaseResourceModel baseResourceModel) throws ClientProtocolException, IOException;
}
