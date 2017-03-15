package com.eeduspace.challenge.test.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientMessageHandlerAdapter extends IoHandlerAdapter{
	//private final static Logger log = LoggerFactory.getLogger(ClientMessageHandlerAdapter.class);
    
    public void messageReceived(IoSession session, Object message) throws Exception {
        String content = message.toString();
        //log.info("client receive a message is : " + content);
        System.out.println("client receive a message is : "+content);
    }
    
    public void messageSent(IoSession session , Object message) throws Exception{
        //log.info("messageSent 客户端发送消息：" + message);
        System.out.println("messageSent 客户端发送消息：" + message);

    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
       // log.info("服务器发生异常： {}", cause.getMessage());
        System.out.println("服务器发生异常： {}"+cause.getMessage());

    }
}
