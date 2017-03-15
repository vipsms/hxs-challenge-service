package com.eeduspace.challenge.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/22
 * Description:
 */
@Component
public class TestJob {
    private final static Logger logger = LoggerFactory.getLogger(TestJob.class);

    /**
     * 定时器
     * 每周五23点执行操作
     */
    @Scheduled(cron = "0 0 23 ? * 5")
    public void test(){

         logger.info("定时器  每周五23点执行操作 :{}",new Date());

    }

}
