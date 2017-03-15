package com.eeduspace.challenge.test;

import com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Author: dingran
 * Date: 2016/7/16
 * Description:
 */
@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
    protected final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    @Inject
    protected AbstractApplicationContext context;

    protected Gson gson=new Gson();
}
