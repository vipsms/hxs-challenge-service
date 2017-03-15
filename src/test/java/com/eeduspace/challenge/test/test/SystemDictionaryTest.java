package com.eeduspace.challenge.test.test;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.model.SystemDictionaryModel;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemDictionaryTest {
    private final Logger logger = LoggerFactory.getLogger(SystemDictionaryTest.class);
    Gson gson = new Gson();
	@Inject
	private  SystemDictionaryService  systemDictionaryService;
	//字典项添加数据
	@Test
	public void testSave(){
		SystemDictionaryModel systemDictionary1 = new SystemDictionaryModel( "积分","20","描述");
		SystemDictionaryModel systemDictionary2 = new SystemDictionaryModel( "对战","30","描述");
		SystemDictionaryModel systemDictionary3 = new SystemDictionaryModel( "挑战","40","描述");
		SystemDictionaryModel systemDictionary4 = new SystemDictionaryModel( "签到","50","描述");
		SystemDictionaryModel systemDictionary5 = new SystemDictionaryModel( "诊断","60","描述");
		SystemDictionaryModel systemDictionary6 = new SystemDictionaryModel( "兑换","70","描述");
		SystemDictionary saveSystemDictionary1 = systemDictionaryService.saveSystemDictionary(systemDictionary1);
		SystemDictionary saveSystemDictionary2 = systemDictionaryService.saveSystemDictionary(systemDictionary2);
		SystemDictionary saveSystemDictionary3 = systemDictionaryService.saveSystemDictionary(systemDictionary3);
		SystemDictionary saveSystemDictionary4 = systemDictionaryService.saveSystemDictionary(systemDictionary4);
		SystemDictionary saveSystemDictionary5 = systemDictionaryService.saveSystemDictionary(systemDictionary5);
		SystemDictionary saveSystemDictionary6 = systemDictionaryService.saveSystemDictionary(systemDictionary6);
		
		System.out.println("添加返回结果1"+gson.toJson(saveSystemDictionary1));
		System.out.println("添加返回结果2"+gson.toJson(saveSystemDictionary2));
		System.out.println("添加返回结果3"+gson.toJson(saveSystemDictionary3));
		System.out.println("添加返回结果4"+gson.toJson(saveSystemDictionary4));
		System.out.println("添加返回结果5"+gson.toJson(saveSystemDictionary5));
		System.out.println("添加返回结果6"+gson.toJson(saveSystemDictionary6));

		
	}
	//字典项搜索
		@Test
		public void testfind(){
			Pageable pageable = new Pageable();
			pageable.setPageNumber(1);
			pageable.setPageSize(3);
			Page<SystemDictionary> findByPager = systemDictionaryService.findByPager(pageable);
			List<SystemDictionary> content = findByPager.getContent();
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
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~字典项~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//	

		public static void main(String[] args) {
			SystemDictionaryTest systemDictionaryTest = new SystemDictionaryTest();
			systemDictionaryTest.testSystemDictionary() ;
			//systemDictionaryTest.testfindAll();
		}
		
		//http 保存字典项
	    public void testSystemDictionary() {  
				String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/systemDictionary/save?requestId=6830396ce01a464ab3c97aec9300be0d";
	    		Map<String, String> paramMap = new HashMap<>();
	    		paramMap.put("name", "体力"); 
	    		paramMap.put("value", "70"); 
	    		paramMap.put("description", "体力"); 
	     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
	     	   String data= gson.toJson(paramMap);
	   		try {
	   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
				System.out.println("--------------返回结果-------------------"+httpPost);
				} catch (IOException e) {
				e.printStackTrace();
			}
	    }  
	  //http 查询字典项
	    public void testfindAll() {  
				String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/systemDictionary/list?requestId=6830396ce01a464ab3c97aec9300be0d";
	     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
	     	    String data="";
	   		try {
	   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
				System.out.println("--------------返回结果-------------------"+httpPost);
				} catch (IOException e) {
				e.printStackTrace();
			}
	    }  
		
		
		
}
