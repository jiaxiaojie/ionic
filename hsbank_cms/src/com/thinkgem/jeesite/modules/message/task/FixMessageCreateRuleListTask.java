package com.thinkgem.jeesite.modules.message.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.MessageUtils;
import com.thinkgem.jeesite.modules.message.service.MessageCreateRuleService;

/**
 * @author ydt 维护cache中的有效消息产生规则列表
 */
@Component
public class FixMessageCreateRuleListTask implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MessageCreateRuleService messageCreateRuleService;

	/**
	 * 每5分执行一次
	 */
	public void job() {
		Date startDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTime) + "】fixMessageCreateRuleListTask start...");
		List<MessageCreateRule> messageCreateRuleList = messageCreateRuleService.findListByStatus(MessageConstant.MESSAGE_RULE_STATUS_REVIEW_PASS, new Date());
		for(MessageCreateRule messageCreateRule : messageCreateRuleList) {
			if(MessageUtils.get(messageCreateRule.getId()) == null) {
				MessageUtils.add(messageCreateRule);
			}
		}
		MessageUtils.check();
		String effectiveMessageCreateRuleIds = "";
		List<MessageCreateRule> cacheList = MessageUtils.getList();
		for(MessageCreateRule messageCreateRule : cacheList) {
			effectiveMessageCreateRuleIds += messageCreateRule.getId();
		}
		logger.info("effectiveMessageCreateRuleList sql size:" + messageCreateRuleList.size() + ", cache size:" + cacheList.size() + ", ids:" + effectiveMessageCreateRuleIds);
		Date endDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTiem) + "】fixMessageCreateRuleListTask end...");
		logger.info("fixMessageCreateRuleListTask total time consuming：【" + (endDateTiem.getTime() - startDateTime.getTime()) / 1000 + "s】");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			job();
		}
	}
}
