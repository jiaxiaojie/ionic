package com.thinkgem.jeesite.modules.message.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.MessageInstance;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.service.MessageInstanceService;


/**
 * 发送app消息任务
 * @author ydt
 *
 */
@Component
public class PushAppMessageTask {
	
	@Autowired
	private MessageInstanceService messageInstanceService;

	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 推送app消息，每30分钟执行一次
	 */
	public void job() {
		logger.info("=====deal app message start=====");
		List<MessageInstance> appMessageInstanceList = messageInstanceService.findAppListByStatus(MessageConstant.MESSAGE_STATUS_CREATE);
		for(MessageInstance messageInstance : appMessageInstanceList) {
			if(messageInstance.getPlanSendDt().getTime() <= new Date().getTime()) {
				messageInstanceService.pushMessage(messageInstance);
			}
		}
		logger.info("=====deal app message end=====");
	}
}
