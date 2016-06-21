package com.thinkgem.jeesite.modules.message.handler;

import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
public class MessageCreateRule1003Handler extends MessageCreateRuleBaseHandler {
	
	@Transactional(readOnly = false)
	public Map<String,Object> luckDraw(Map<String,Object> para) {
		MessageCreateRule messageCreateRule = (MessageCreateRule)para.get(MessageConstant.PARA_MESSAGE_CREATE_RULE);
		createMessage(messageCreateRule, para);
		return para;
	}
}
