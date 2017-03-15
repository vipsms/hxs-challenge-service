package com.eeduspace.challenge.test.test;


import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.model.SystemDictionaryModel;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.persist.po.UserTaskLog;
import com.eeduspace.challenge.service.UserTaskLogService;
import com.google.gson.Gson;


@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTaskLogTest {
    private final Logger logger = LoggerFactory.getLogger(UserTaskLogTest.class);
    
    @Inject
	private   UserTaskLogService userTaskLogService ;
    
    @Test
	public void testSave(){
    	UserTaskLog userTaskLog = new UserTaskLog();
    	userTaskLog.setUserCode("123456789");
    	userTaskLog.setUuid(UUID.randomUUID().toString().replace("-", ""));
    	userTaskLog.setCreateDate(new Date());
    	userTaskLog.setConcreteTaskCode("13112345679");
    	userTaskLog.setModule("STAND_SHARE");
    	userTaskLog.setOperate("USER_SHARE");
    	userTaskLogService.UserTaskLogSave(userTaskLog);
	}
    @Test
   	public void testUserTaskLog(){
       	userTaskLogService.UserTaskLogSave("123456789","SHARE", null, null, "USER_SHARE", null);
   	}
    @Test
   	public void testFindByUuid(){
    	Gson gson =new Gson();
    	System.out.println("FindByUuid:"+gson.toJson(userTaskLogService.findByUuid("b421a29bb2c54c05bb5c3126f3e70494")));
    }
    @Test
   	public void testFindAll(){
       	Gson gson =new Gson();
    	System.out.println("FindByUuid:"+gson.toJson(userTaskLogService.findAll()));
   	}
}	
			
			
			
			
			
			
			
			
	
