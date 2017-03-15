package com.eeduspace.challenge.test.test;

import java.io.IOException;
import java.util.ArrayList;
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

import com.eeduspace.challenge.model.OrderItemModel;
import com.eeduspace.challenge.model.UserFeedBackModel;
import com.eeduspace.challenge.persist.po.UserFeedBack;
import com.eeduspace.challenge.service.UserFeedBackService;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.google.gson.Gson;
/**
 * 订单
 * */
@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderTest {
    private final Logger logger = LoggerFactory.getLogger(OrderTest.class);
	@Inject
	private UserFeedBackService userFeedBackService;
    Gson gson = new Gson();
    String replaceUUID = UIDGenerator.getUUID().toString().replace("-", "");
    
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~订单~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//	
			
			public static void main(String[] args) {
				OrderTest orderTest = new OrderTest();
				//查全部
				//orderTest.orderList();
				//查询单个订单
				//orderTest.orderOneList();
				//查询单个订单商品详情
				//orderTest.orderItemList();
				orderTest.orderSave();
				//orderTest.orderUpdate();
			}
			
			private void orderItemList() {
				String replace = UIDGenerator.getUUID().toString().replace("-", "");
				String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/order/details_orderItem?requestId=6830396ce01a464ab3c97aec9300be0d";
	    		Map<String, String> paramMap = new HashMap<>();
	    		paramMap.put("uuid", "00acf1eda7e04bbe97ba41f5cb8db014");
	     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		     	   String data= gson.toJson(paramMap);
	   		try {
	   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
				System.out.println("--------------返回结果-------------------"+httpPost);
				} catch (IOException e) {
				e.printStackTrace();
			}
			}

			private void orderOneList() {
				String replace = UIDGenerator.getUUID().toString().replace("-", "");
				String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/order/details_order?requestId=6830396ce01a464ab3c97aec9300be0d";
	    		Map<String, String> paramMap = new HashMap<>();
	    		paramMap.put("uuid", "00acf1eda7e04bbe97ba41f5cb8db014");
	     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		     	   String data= gson.toJson(paramMap);
	   		try {
	   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
				System.out.println("--------------返回结果-------------------"+httpPost);
				} catch (IOException e) {
				e.printStackTrace();
			}
			}

			public void orderList(){
				String replace = UIDGenerator.getUUID().toString().replace("-", "");
				String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/order/list?requestId=6830396ce01a464ab3c97aec9300be0d";
	    		Map<String, String> paramMap = new HashMap<>();
	     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
	     	   String data= "";
	   		try {
	   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
				System.out.println("--------------返回结果-------------------"+httpPost);
				} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
					
			
			
			
			public void orderSave(){
					String replace = UIDGenerator.getUUID().toString().replace("-", "");
					String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/order/save?requestId=6830396ce01a464ab3c97aec9300be0d";
		    		
					OrderItemModel orderItemModel = new OrderItemModel();
					orderItemModel.setPrice("10");
					orderItemModel.setProductName("D");
					orderItemModel.setQuantity("aaaaa");
					OrderItemModel orderItemModel2 = new OrderItemModel();
					orderItemModel2.setPrice("11.1");
					orderItemModel2.setProductName("E");
					orderItemModel2.setQuantity("aaaaa");
					OrderItemModel orderItemModel3 = new OrderItemModel();
					orderItemModel3.setPrice("12.12");
					orderItemModel3.setProductName("F");
					orderItemModel3.setQuantity("aaaa");
					
					List<OrderItemModel> list = new ArrayList<OrderItemModel>();
					list.add(orderItemModel);
					list.add(orderItemModel2);
					list.add(orderItemModel3);
					
					Map<String, Object> paramMap = new HashMap<String, Object>();
		    		paramMap.put("list", list); 
		    		paramMap.put("userCode", "00acf1eda7e04bbe97ba41f5cb8db014"); 
		    		paramMap.put("orderType", "vip"); 
		    		paramMap.put("orderName", "vip"); 
		    		paramMap.put("payType", "未付款"); 
		    		paramMap.put("orderPrice", "200"); 
		    		
		     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		     	   String data= gson.toJson(paramMap);
		     	   System.out.println("data="+data);
		   		try {
		   			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
					System.out.println("--------------返回结果-------------------"+httpPost);
					} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			public void orderUpdate(){
				String replace = UIDGenerator.getUUID().toString().replace("-", "");
				String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/order/update?requestId=6830396ce01a464ab3c97aec9300be0d";
	    		Map<String, String> paramMap = new HashMap<>();
	    		paramMap.put("uuid", "123321"); 
	    		paramMap.put("orderPrice", "123"); 
	    		paramMap.put("orderName", "订单1号"); 
	    		paramMap.put("payType", "微信"); 
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
			
			
			
			
			
			
			
			
	
