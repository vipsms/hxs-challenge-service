package com.eeduspace.challenge.mina.alive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler{
	/**
     * 超时几次关闭连接
     */
    private  int  timeoutNum = 3;
    private final Logger logger = LoggerFactory.getLogger(KeepAliveRequestTimeoutHandlerImpl.class);
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter,
			IoSession session) throws Exception {
		logger.debug("keepAliveRequestTimedOut:{}",session.getId());
//		if(num==timeoutNum){
			session.closeOnFlush();
//		}else{
//			session.setAttribute("alive_time", num+1);
//		}
	}
	
	
	public int getTimeoutNum() {
		return timeoutNum;
	}
	public void setTimeoutNum(int timeoutNum) {
		this.timeoutNum = timeoutNum;
	}

}
