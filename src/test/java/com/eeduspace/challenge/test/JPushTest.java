package com.eeduspace.challenge.test;

import com.eeduspace.challenge.model.NoticeModel;
import com.eeduspace.challenge.util.JPushUtil;

import javax.inject.Inject;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
public class JPushTest extends BaseTest {

    @Inject
    private JPushUtil jPushUtil;

    @org.junit.Test
    public  void test(){
        String response = null;


        try {
            NoticeModel noticeModel=new NoticeModel();
            noticeModel.setContent("test");
            noticeModel.setObject("13681004142");
            noticeModel.setSendType("log");
            noticeModel.setTitle("test");
            noticeModel.setMessageId("uuid");
            jPushUtil.send(noticeModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("response:{}",response);
    }
}
