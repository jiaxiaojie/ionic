package com.thinkgem.jeesite.modules.message.handler;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.message.MessageConstant;

@Component
public class MessageCreateRule1000Handler extends MessageCreateRuleBaseHandler {
	
	@Transactional(readOnly = false)
	public Map<String,Object> repay(Map<String,Object> para) {
		MessageCreateRule messageCreateRule = (MessageCreateRule)para.get(MessageConstant.PARA_MESSAGE_CREATE_RULE);
		createMessage(messageCreateRule, para);
		return para;
	}
}
