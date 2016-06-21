package com.thinkgem.jeesite.modules.message.handler;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBaseDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerPushSwitchDao;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.Message;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.MessageDao;
import com.thinkgem.jeesite.modules.message.dao.MessageInstanceDao;
import com.thinkgem.jeesite.modules.message.dao.ReceiveMessageSwitchDao;

@Component
public class MessageCreateRuleBaseHandler {
	
	@Autowired
	private CustomerBaseDao customerBaseDao;
	@Autowired
	private ReceiveMessageSwitchDao receiveMessageSwitchDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MessageInstanceDao messageInstanceDao;
	@Autowired
	private CustomerPushSwitchDao customerPushSwitchDao;
	
	public Map<String,Object> repay(Map<String,Object> para) {
		return para;
	}
	
	protected void createMessage(MessageCreateRule messageCreateRule, Map<String,Object> para) {
		Message message = new Message();
		message.setAccountId((Long)para.get(MessageConstant.PARA_ACCOUNT_ID));
		message.setTitle(messageCreateRule.getMessageTitle());
		message.setContent(dealContent(messageCreateRule.getMessageContent(), para));
		message.setSendDt(new Date());
		message.setType(messageCreateRule.getMessageType());
		message.setLabel(messageCreateRule.getLabel());
		message.setFromType(MessageConstant.MESSAGE_FROM_TYPE_RULE);
		message.setFromId(Long.parseLong(messageCreateRule.getId()));
		messageDao.insert(message);
		for(String messageChannel : messageCreateRule.getMessageChannelList()) {
			//若接收此消息，则创建消息实例
			if(shouldReceiveMessage((Long)para.get(MessageConstant.PARA_ACCOUNT_ID), messageCreateRule.getMessageType(), messageChannel)) {
				//若是android或ios消息，若用户未登录过android或ios应用，则不创建此消息
				if((MessageConstant.MESSAGE_CHANNEL_ANDROID.equals(messageChannel) || MessageConstant.MESSAGE_CHANNEL_IOS.equals(messageChannel))
						&& customerPushSwitchDao.getCustomerPushSwitch(message.getAccountId(), messageChannel) == null) {
					continue;
				}
				MessageInstance messageInstance = new MessageInstance();
				messageInstance.setMessageId(Long.parseLong(message.getId()));
				messageInstance.setMessageChannel(messageChannel);
				messageInstance.setCreateDt(new Date());
				messageInstance.setStatus(MessageConstant.MESSAGE_STATUS_CREATE);
				messageInstanceDao.insert(messageInstance);
			}
		}
	}

	private boolean shouldReceiveMessage(Long accountId, String messageType, String messageChannel) {
		if(!MessageConstant.IS_RECEIVE_MESSAGE_YES.equals(receiveMessageSwitchDao.getIsReceive(MessageConstant.SYSTEM_MESSAGE_SWITCH_ACCOUNT_ID, messageType, messageChannel))) {
			return false;
		}
		return !MessageConstant.IS_RECEIVE_MESSAGE_NO.equals(receiveMessageSwitchDao.getIsReceive(accountId, messageType, messageChannel));
	}

	private String dealContent(String content, Map<String,Object> para) {
		Long accountId = (Long)para.get(MessageConstant.PARA_ACCOUNT_ID);
		if(content.contains("#customerName#") || content.contains("#mobile#")) {
			CustomerBase customerBase = customerBaseDao.getByAccountId(accountId);
			para.put("customerName", customerBase.getCustomerName());
			para.put("mobile", customerBase.getMobile());
		}
		for(String variable : MessageConstant.MESSAGE_VARIABLE) {
			if(content.contains(variable)) {
				content = content.replace(variable, getVariableValue(variable, para));
			}
		}
		return content;
	}

	private String getVariableValue(String variable, Map<String,Object> para) {
		String dealVariable = variable.replace("#", "");
		if(para.get(dealVariable) == null) {
			return variable;
		}
		if(para.get(dealVariable) instanceof Date) {
			return DateUtils.formatDate((Date)para.get(dealVariable), "yyyy年MM月dd日");
		}
		return String.valueOf(para.get(dealVariable));
	}

	public static void main(String[] args) {
		String as = "尊敬的用户，您在${date }大力士排行榜中位列第${rank }名，获得${amount }元现金券，请查收。";
		Pattern pattern = Pattern.compile("\\$\\{([^\\}]*) \\}");
		Matcher matcher = pattern.matcher(as);
		while(matcher.find()) {
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
		}
	}
}
