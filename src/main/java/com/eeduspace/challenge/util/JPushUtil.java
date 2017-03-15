package com.eeduspace.challenge.util;

import com.eeduspace.challenge.model.NoticeModel;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
@Component("JPushUtil")
public class JPushUtil {
    protected final Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    @Inject
    private Gson gson;
    @Value("${hxs.jpush.url}")
    private String JPush_url;
    @Value("${hxs.jpush.appKey}")
    private String JPush_appKey;
    @Value("${hxs.jpush.sercet}")
    private String JPush_sercet ;
    @Value("${hxs.sms.password}")
    private String sms_password;
    @Value("${hxs.sms.openId}")
    private String sms_openId;
    @Value("${hxs.sms.sendType}")
    private String sms_sendType;
    
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    /**
     * @aa add by dingran
     * @aa 2016-05-11
     * @return
     */
    public String send(NoticeModel noticeModel) {

        String code = "";
        logger.debug("sms_url:{},sms_password:{},sms_openId:{},appKey:{},sercet:{}",JPush_url,sms_password,sms_openId,JPush_appKey,JPush_sercet);
        if (StringUtils.isNotBlank(JPush_url)
                && StringUtils.isNotBlank(sms_password)
                && StringUtils.isNotBlank(sms_openId)) {
            String type=noticeModel.getType();
            if(StringUtils.isEmpty(type)){
                type="ALIAS";
            }

            /**
             * 添加token
             */
            String userToken =  redisClientTemplate.get("hxs_challenge_"+noticeModel.getObject());

            String paramter = "content=" +noticeModel.getTitle() + "&requestId=''&password="
                    + sms_password + "&openId=" + sms_openId
                    + "&object="+noticeModel.getObject()+"&appKey="+JPush_appKey
                    + "&title=好学生"+"&sercet="+JPush_sercet
                    + "&token="+userToken
                    + "&sendType="+noticeModel.getSendType()+"&type="+type+"&extraContent="+noticeModel.getContent()
                    + "&messageId="+noticeModel.getMessageId()
                    ;

            logger.debug("==================================极光推送工具类中的参数："+paramter);
            // 极光推送
            String result = postRequest(JPush_url, paramter);
            
           
            // result={"code":"Success","requestId":"''","httpCode":"200","message":"Success","result":{"code":"877430"}}

            logger.debug("JPush client result:{}",result);
            // 解析返回结果 获取验证码code值
            if (StringUtils.isNotBlank(result)) {
                JsonNode jsonNode = Json.parse(result);
                // 获取 code
                JsonNode subJsonNode = jsonNode.findValue("result");
                if (subJsonNode != null) {
                    JsonNode codeJsonNode = subJsonNode.findValue("code");
                    if (codeJsonNode != null) {
                        code = codeJsonNode.textValue();
                    }
                }

            }

        }

        return code;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param paramter
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */

    public String postRequest(String url, String paramter) {

        // url: http://218.240.38.108/uutool/sms/verificationCode?
        // paramter:
        // phone=value1&requestId=sdfsds2343546546ffsdfw&password=e10adc3949ba59abbe56e057f20f883e&openId:d4ae37ce-24a8-4763-82b5-51b261a416c8

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	logger.debug("===============================url=======:"+url);
            URL realUrl = new URL(url);
            logger.debug("===============================realUrl===:"+realUrl);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            logger.debug("================================conn.getOutputStream():"+conn.getOutputStream());
            // 发送请求参数
            out.print(paramter);
            logger.debug("=================================paramter:"+paramter);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
