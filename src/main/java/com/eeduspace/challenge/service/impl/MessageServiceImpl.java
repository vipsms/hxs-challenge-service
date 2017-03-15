package com.eeduspace.challenge.service.impl;

import org.springframework.stereotype.Service;

import com.eeduspace.challenge.persist.po.Message;
import com.eeduspace.challenge.service.MessageService;
@Service("messageServiceImpl")
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService{

}
