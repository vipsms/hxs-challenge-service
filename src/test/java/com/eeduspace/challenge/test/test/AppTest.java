package com.eeduspace.challenge.test.test;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {
    private final Logger logger = LoggerFactory.getLogger(AppTest.class);
    
	
	//--------------返回结果-------------------{"requestId":"6830396ce01a464ab3c97aec9300be0d","code":"Success","message":"success","httpCode":"200","result":{"id":1,"uuid":null,"createDate":1464661925000,"appName":null,"available":true,"appVersion":1,"appDescribe":null,"downUrl":"http://testcibn.iwrong.cn/cibn_apk/cibn.apk","necessary":true}}
			public static void main(String[] args) {
				AppTest appTest = new AppTest();
				appTest.appDown();
			}
			
			public void appDown(){
					String replace = UIDGenerator.getUUID().toString().replace("-", "");
					String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/appupdate/download?requestId=6830396ce01a464ab3c97aec9300be0d";
		    		
		     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		     	    String data= "";
		   		try {
		   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
					System.out.println("--------------返回结果-------------------"+httpPost);
					} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
}	
			
			
			
			
			
			
			
			
	
