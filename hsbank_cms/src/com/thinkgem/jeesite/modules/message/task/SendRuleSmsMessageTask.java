package com.thinkgem.jeesite.modules.message.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.message.service.ReceiveSmsMessageTimeService;
import com.thinkgem.jeesite.modules.sms.utils.SmsUtils;

/**
 * 发送规则短信消息任务
 * @author ydt
 *
 */
@Component
public class SendRuleSmsMessageTask {
	
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	private ReceiveSmsMessageTimeService receiveSmsMessageTimeService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 发送规则短信消息，每15分钟执行一次
	 */
	public void job() {
		logger.info("=====deal rule create sms message start=====");
		List<MessageInstance> list = messageInstanceService.findListByFromType(MessageConstant.MESSAGE_FROM_TYPE_RULE, MessageConstant.MESSAGE_CHANNEL_SMS, MessageConstant.MESSAGE_STATUS_CREATE);
		for(MessageInstance messageInstance : list) {
			if(messageInstance.getPlanSendDt().getTime() <= new Date().getTime() && receiveSmsMessageTimeService.isReceive(messageInstance.getAccountId(), new Date())) {
				SmsUtils.sendSms(messageInstance.getMobile(), messageInstance.getContent());
				messageInstanceService.updateStatus(Long.parseLong(messageInstance.getId()), MessageConstant.MESSAGE_STATUS_SEND, new Date(), null, null);
			}
		}
		logger.info("=====deal rule create sms message end=====");
	}
}
