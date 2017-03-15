package com.eeduspace.challenge.test.test;

import java.io.IOException;
import java.util.Date;
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

import com.eeduspace.challenge.model.UserFeedBackModel;
import com.eeduspace.challenge.persist.po.UserFeedBack;
import com.eeduspace.challenge.service.UserFeedBackService;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FeedBackTest {
    private final Logger logger = LoggerFactory.getLogger(FeedBackTest.class);
	@Inject
	private UserFeedBackService userFeedBackService;
    Gson gson = new Gson();
    String replaceUUID = UIDGenerator.getUUID().toString().replace("-", "");
    
	@Test
	public void testSave(){
		UserFeedBackModel feedBack = new UserFeedBackModel();
		String replace = UIDGenerator.getUUID().toString().replace("-", "");
		String replaces = UIDGenerator.getUUID().toString().replace("-", "");
		feedBack.setMessage("我是消息");
		//用户联系方式
		feedBack.setContactInformation("13145678900");
		feedBack.setUserCode(replace);
//		UserFeedBack saveUserFeedBack = userFeedBackService.saveUserFeedBack(feedBack);
//		System.out.println("返回信息："+gson.toJson(saveUserFeedBack));
		
	}
	
	//反馈
			@Test
			public void testfind(){
				Pageable pageable = new Pageable();
				pageable.setPageNumber(1);
				pageable.setPageSize(3);
				 Page<UserFeedBack> findByPager = userFeedBackService.findByPager(pageable);
				List<UserFeedBack> content = findByPager.getContent();
				long pageNumber = findByPager.getPageNumber();
				long pageSize = findByPager.getPageSize();
				long total = findByPager.getTotal();
				long totalPage = findByPager.getTotalPage();
				int totalPages = findByPager.getTotalPages();
				System.out.println("content:"+gson.toJson(content));
				System.out.println("pageNumber:"+gson.toJson(pageNumber));
				System.out.println("pageSize:"+gson.toJson(pageSize));
				System.out.println("total:"+gson.toJson(total));
				System.out.println("totalPage:"+gson.toJson(totalPage));
				System.out.println("totalPages:"+gson.toJson(totalPages));
			}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//	
			
			public static void main(String[] args) {
				FeedBackTest feedBackTest = new FeedBackTest();
				feedBackTest.postfeedback();
			}
			
			public void postfeedback(){
				
					String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/user/feedBack?requestId=6830396ce01a464ab3c97aec9300be0d";
		    		Map<String, String> paramMap = new HashMap<>();
		    		paramMap.put("contactInformation", "13520465211"); 
		    		paramMap.put("message", "我是消息"); 
		    		paramMap.put("userCode", "123456789"); 
		     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		     	   String data= gson.toJson(paramMap);
		   		try {
		   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
					System.out.println("--------------返回结果-------------------"+httpPost);
					} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
}	
			
			
			
			
			
			
			
			
	
