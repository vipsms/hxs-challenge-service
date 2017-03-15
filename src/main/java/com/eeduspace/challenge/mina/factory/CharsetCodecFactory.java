package com.eeduspace.challenge.mina.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.http.HttpServerDecoder;
import org.apache.mina.http.HttpServerEncoder;

/**
 *  * <b>function:</b> 字符编码、解码工厂类，编码过滤工厂
 * @author zhuchaowei
 * 2016年6月24日
 * Description
 */
public class CharsetCodecFactory implements ProtocolCodecFactory{

	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return new HttpServerDecoder();
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return new HttpServerEncoder();
	}

}
