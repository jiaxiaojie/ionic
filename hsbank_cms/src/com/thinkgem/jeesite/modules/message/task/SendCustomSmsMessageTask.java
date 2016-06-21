package com.thinkgem.jeesite.modules.message.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;
import com.thinkgem.jeesite.modules.message.service.ReceiveSmsMessageTimeService;
import com.thinkgem.jeesite.modules.sms.utils.SmsUtils;

/**
 * 发送自定义短信消息任务
 * @author ydt
 *
 */
@Component
public class SendCustomSmsMessageTask {
	
	@Autowired
	private MessageInstanceService messageInstanceService;
	@Autowired
	private ReceiveSmsMessageTimeService receiveSmsMessageTimeService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 发送自定义短信消息，每30分钟执行一次
	 */
	public void job() {
		logger.info("=====deal custom sms message start=====");
		//获取待发送消息
		List<MessageInstance> smsMessageInstanceList = messageInstanceService.findListByFromType(MessageConstant.MESSAGE_FROM_TYPE_CUSTOM, MessageConstant.MESSAGE_CHANNEL_SMS, MessageConstant.MESSAGE_STATUS_CREATE);
		Map<Long,List<MessageInstance>> map = new HashMap<Long,List<MessageInstance>>();
		for(MessageInstance smsMessageInstance : smsMessageInstanceList) {
			if(smsMessageInstance.getPlanSendDt().getTime() <= new Date().getTime() && receiveSmsMessageTimeService.isReceive(smsMessageInstance.getAccountId(), new Date())) {
				if(map.containsKey(smsMessageInstance.getFromId())) {
					List<MessageInstance> list = map.get(smsMessageInstance.getFromId());
					list.add(smsMessageInstance);
				} else {
					List<MessageInstance> list = new ArrayList<MessageInstance>();
					list.add(smsMessageInstance);
					map.put(smsMessageInstance.getFromId(), list);
				}
			}
		}
		//批量发送消息
		List<Long> hasSentMessageInstanceIds = new ArrayList<Long>();
		List<String> mobiles = new ArrayList<String>();
		String content = "";
		for(Map.Entry<Long, List<MessageInstance>> entry : map.entrySet()) {
			List<MessageInstance> list = entry.getValue();
			mobiles = new ArrayList<String>();
			content = list.get(0).getContent();
			for(MessageInstance messageInstance : list) {
				mobiles.add(messageInstance.getMobile());
				hasSentMessageInstanceIds.add(Long.parseLong(messageInstance.getId()));
			}
			SmsUtils.sendSms(mobiles, content);
		}
		//批量更新已发送消息状态
		if(hasSentMessageInstanceIds.size() > 0) {
			messageInstanceService.updateStatusBatch(hasSentMessageInstanceIds, MessageConstant.MESSAGE_STATUS_SEND, new Date(), null, null);
		}
		logger.info("=====deal custom sms message end=====");
	}
}
