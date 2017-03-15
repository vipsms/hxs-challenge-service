package com.eeduspace.challenge.test.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.persist.dao.TaskRewardReceiveMapper;
import com.eeduspace.challenge.persist.po.TaskRewardReceive;

/**
 * @author  作者 : gaofengming
		E-mail : gaofengming@e-eduspace.com
 * @date 创建时间   ：2016年8月24日下午6:44:39   
 * @return  
 */
@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FindOneReceive {
	@Autowired
	private TaskRewardReceiveMapper taskRewardReceiveMapperImpl;
	@Test
	public void testFindOne(){
	}
	
}
