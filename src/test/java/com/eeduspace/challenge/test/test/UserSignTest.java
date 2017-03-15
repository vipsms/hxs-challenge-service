package com.eeduspace.challenge.test.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.model.ShareModel;
import com.eeduspace.challenge.persist.po.Share;
import com.eeduspace.challenge.persist.po.UserSign;
import com.eeduspace.challenge.service.ShareService;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserSignTest {
    private final Logger logger = LoggerFactory.getLogger(UserSignTest.class);
	@Inject
	private ShareService shareService;
    Gson gson = new Gson();

	    public void testSign(){
	        try {
	        	 String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/user/sign/past?requestId=6830396ce01a464ab3c97aec9300be0d";
	            //String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/user/sign/info?requestId=6830396ce01a464ab3c97aec9300be0d";
	    		Map<String, String> paramMap = new HashMap<>();
	    		paramMap.put("userCode", "da95e1572e134177875c18a3289de842"); 
	     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
	     	   String data= gson.toJson(paramMap);
	   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
				System.out.println("--------------返回结果-------------------"+httpPost);
				} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	public static void main(String[] args) {
		UserSignTest shareTest = new UserSignTest();
		shareTest.testSign();
	}
	
	
	
	
}
	
	
	
	
	

