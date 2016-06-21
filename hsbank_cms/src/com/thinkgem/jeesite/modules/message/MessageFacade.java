package com.thinkgem.jeesite.modules.message;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.message.handler.MessageCreateRuleBaseHandler;

@Component
public class MessageFacade {
	
Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 还款
	 * @param para
	 */
	public Map<String, Object> repay(Map<String, Object> para) {
		logger.info("message login begin ...");
		List<MessageCreateRule> messageCreateRuleList = MessageUtils.getList();
		for (MessageCreateRule messageCreateRule : messageCreateRuleList) {
			if (MessageUtils.check(messageCreateRule)) {
				if(messageCreateRule.shouldHandler((String)para.get(MessageConstant.PARA_BEHAVIOR_CODE), (String)para.get(MessageConstant.PARA_OP_TERM))) {
					MessageCreateRuleBaseHandler messageCreateRuleHandler = SpringContextHolder.getBean(messageCreateRule.getClassName());
					para.put(MessageConstant.PARA_MESSAGE_CREATE_RULE, messageCreateRule);
					messageCreateRuleHandler.repay(para);
				}
			} else {
				MessageUtils.remove(messageCreateRule);
			}
		}
		logger.info("message login end ...");
		return para;
	}
}
