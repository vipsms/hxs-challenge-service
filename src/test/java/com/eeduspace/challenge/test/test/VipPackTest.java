package com.eeduspace.challenge.test.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.model.VipPackModel;
import com.eeduspace.challenge.persist.po.VipPack;
import com.eeduspace.challenge.service.VipPackService;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class VipPackTest {
    private final Logger logger = LoggerFactory.getLogger(VipPackTest.class);
	@Inject
	private VipPackService vipPackService;
    Gson gson = new Gson();
    String replaceUUID = UIDGenerator.getUUID().toString().replace("-", "");
    /**
     * 添加
     * @throws ParseException 
     */
	@Test
	public void testSave() throws ParseException{
		VipPackModel vipPackModel = new VipPackModel();
		vipPackModel.setVipPrice("13.0");
		VipPack vipPack=vipPackService.saveVipPack(UserConvert.fromVipPackModel(vipPackModel));
		System.out.println("返回信息："+gson.toJson(vipPack));
	}
	/**
     * 查询
     */
	@Test
	public void testfind(){
		VipPack vipPackModel = new VipPack();
		String replace = UIDGenerator.getUUID().toString().replace("-", "");
		String replaces = UIDGenerator.getUUID().toString().replace("-", "");
		vipPackModel.setVipPrice(12.0);
		VipPack saveVipPack = vipPackService.saveVipPack(vipPackModel);
		System.out.println("返回信息："+gson.toJson(saveVipPack));
		
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	public static void main(String[] args) {
		VipPackTest vipPackTest = new VipPackTest();
		//vipPackTest.testSaveVip() ;
		//vipPackTest.testUpdateVip();
		vipPackTest.testfindAllVip();
		//vipPackTest.testdeleteVip();
		//vipPackTest.vipToBuy();
		//vipPackTest.vipToBuySuccess();
		
	}
	

	//http vip   save
    public void testSaveVip() {  
		    String replace = UIDGenerator.getUUID().toString().replace("-", "");
			String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/vip_pack/vip_pack_save?requestId=6830396ce01a464ab3c97aec9300be0d";
    		Map<String, String> paramMap = new HashMap<>();
    		paramMap.put("vipPrice","16"); 
    		paramMap.put("vipDesc","2个月"); 
    		paramMap.put("vipType","TWO_MONTH"); 
     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
     	    
     	    String data= gson.toJson(paramMap);
		try {
			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
			System.out.println("--------------返回结果-------------------"+httpPost);
			} catch (IOException e) {
			e.printStackTrace();
		}
    }  
  //http vip   update
    public void testUpdateVip() {  
		    String replace = UIDGenerator.getUUID().toString().replace("-", "");
			String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/vip_pack/vip_pack_save?requestId=6830396ce01a464ab3c97aec9300be0d";
    		Map<String, String> paramMap = new HashMap<>();
    		paramMap.put("vipPrice","100"); 
    		paramMap.put("uuid","d8bfc2706ff64bf2b8adb74e6e9fddc1"); 
     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
     	   String data= gson.toJson(paramMap);
		try {
			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl,data );
			System.out.println("--------------返回结果-------------------"+httpPost);
			} catch (IOException e) {
			e.printStackTrace();
		}
    }  
    
    //http vip   delete
	private void testdeleteVip() {
		String replace = UIDGenerator.getUUID().toString().replace("-", "");
		String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/vip_pack/vip_pack_delete?requestId=6830396ce01a464ab3c97aec9300be0d";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("uuid","4f41b03c1ca047228299f369e3970bb4"); 
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
    public void testfindAllVip() {  
			String getPaperListUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/vip_pack/vip_pack_list?requestId=6830396ce01a464ab3c97aec9300be0d&token=ac03a014578446a89252972c02642126";
    		  String data= "";
     	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
		try {
			String httpPost = HTTPClientUtils.httpPostRequestJson(getPaperListUrl, data);
			System.out.println("--------------返回结果-------------------"+httpPost);
			} catch (IOException e) {
			e.printStackTrace();
		}
    }  
	/*vip包的购买*/
    private void vipToBuy() {
    	//,"spbillCreateIp":"127.0.0.1","body":"好学生VIP-1个月","totalFee":14}
		String getUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/vip_pack/tobuy?requestId=6830396ce01a464ab3c97aec9300be0d";
		Map<String, String> paramMap = new HashMap<>();
	    paramMap.put("GradeType","GAO_ZHONG");
		paramMap.put("userCode","4739482039820394209383409");
		paramMap.put("vipCode","55287EC4CDFB42");
		paramMap.put("ip","127.0.0.1");
		paramMap.put("vipDesc","好学生VIP-1个月");
		paramMap.put("vipPrice","14");
 	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
 	    String data= gson.toJson(paramMap);
 	    System.out.println("请求参数data:"+data);
 	 		try {
 	 			String httpPost = HTTPClientUtils.httpPostRequestJson(getUrl,data );
		System.out.println("--------------返回结果-------------------"+httpPost);
		} catch (IOException e) {
		e.printStackTrace();
	}
	}
    /*vip包的购买 修改用户的信息*/
    private void vipToBuySuccess() {
    	//,"spbillCreateIp":"127.0.0.1","body":"好学生VIP-1个月","totalFee":14}
		String getUrl="http://192.168.1.69:8081/hxs-challenge-service/challenge/vip_pack/buysuccess?requestId=6830396ce01a464ab3c97aec9300be0d";
		//String getUrl="http://192.168.1.124:8080/challenge/vip_pack/buysuccess?requestId=6830396ce01a464ab3c97aec9300be0d";
		Map<String, String> paramMap = new HashMap<>();
	    paramMap.put("userUniqueId","123456789");
		paramMap.put("vipProductCode","2fba53da7f0248a991012398029c8594");
 	    HTTPClientUtils httpClientUtils = new HTTPClientUtils();
 	    String data= gson.toJson(paramMap);
 	    System.out.println("请求参数data:"+data);
 	 		try {
 	 			String httpPost = HTTPClientUtils.httpPostRequestJson(getUrl,data );
		System.out.println("--------------返回结果-------------------"+httpPost);
		} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
