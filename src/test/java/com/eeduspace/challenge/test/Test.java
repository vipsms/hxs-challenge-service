package com.eeduspace.challenge.test;

import com.eeduspace.challenge.mina.model.ClientModel;
import com.google.gson.Gson;


public class Test {
	public static void main(String[] args) {
		ClientModel clientModel=new ClientModel();
		clientModel.setFromUserCode("消息发送者userCode");
		clientModel.setMessage("消息内容");
		clientModel.setMessageType("createmessage");
		clientModel.setToUserCode("消息接收者userCode");
		Gson gson=new Gson();
		System.out.println(gson.toJson(clientModel));
	}
}
