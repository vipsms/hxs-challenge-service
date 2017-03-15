package com.eeduspace.challenge.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeduspace.challenge.mina.handler.MessageUtil;
import com.eeduspace.challenge.mina.model.ClientModel;
import com.eeduspace.challenge.model.message.ChatMessageModel;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.service.WeeklyRankingService;

@RestController
@RequestMapping(value = "/testmessage")
public class TestController {
	@Inject
	private MessageUtil messageUtil;
	@Autowired
	private WeeklyRankingService weeklyRankingServiceImpl;

	// @RequestMapping(value="/createsession")
	// public BaseResponse test(String userCode){
	// MessageUtil messageUtil=new MessageUtil();
	// String msg="创立链接";
	// ClientModel clientModel=new ClientModel();
	// clientModel.setFromUserCode(userCode);
	// clientModel.setMessageType("createmessage");
	// clientModel.setMessage(msg);
	// messageUtil.sendMessageToOne(clientModel);
	// BaseResponse baseResponse=new BaseResponse();
	// baseResponse.setResult(clientModel);
	// return baseResponse;
	// }

	@RequestMapping(value = "/battlemessage")
	public BaseResponse battle(String fromUserCode, String toUserCode) {
		BaseResponse baseResponse = new BaseResponse();
		ChatMessageModel chatMessageModel = new ChatMessageModel();
		chatMessageModel.setContent("0");
		ClientModel clientModel = new ClientModel();
		clientModel.setFromUserCode(fromUserCode);
		clientModel.setToUserCode(toUserCode);
		clientModel.setMessageType("hxs_emoticon_message");
		clientModel.setMessage(chatMessageModel);
		messageUtil.sendMessageToOne(clientModel);
		return baseResponse;
	}

}
