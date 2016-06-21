/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.entity.MessageCreateRuleChannel;
import com.thinkgem.jeesite.modules.entity.MessageCreateRuleTerm;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleChannelDao;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleDao;
import com.thinkgem.jeesite.modules.message.dao.MessageCreateRuleTermDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 消息产生规则Service
 * @author ydt
 * @version 2016-01-14
 */
@Service
@Transactional(readOnly = true)
public class MessageCreateRuleService extends CrudService<MessageCreateRuleDao, MessageCreateRule> {

	@Autowired
	private MessageCreateRuleChannelDao messageCreateRuleChannelDao;
	@Autowired
	private MessageCreateRuleTermDao messageCreateRuleTermDao;
	@Autowired
	private MessageCreateRuleDao messageCreateRuleDao;
	
	
	
	public MessageCreateRule get(String id) {
		MessageCreateRule messageCreateRule = super.get(id);
		if(messageCreateRule != null) {
			List<String> messageChannelList = messageCreateRuleChannelDao.findMessageChannelListByRuleId(Long.parseLong(messageCreateRule.getId()));
			messageCreateRule.setMessageChannelList(messageChannelList);
			List<String> termList = messageCreateRuleTermDao.findTermListByRuleId(Long.parseLong(messageCreateRule.getId()));
			messageCreateRule.setTermList(termList);
		}
		return messageCreateRule;
	}
	
	public List<MessageCreateRule> findList(MessageCreateRule messageCreateRule) {
		return super.findList(messageCreateRule);
	}
	
	public Page<MessageCreateRule> findPage(Page<MessageCreateRule> page, MessageCreateRule messageCreateRule) {
		return super.findPage(page, messageCreateRule);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageCreateRule messageCreateRule) {
		messageCreateRule.setStatus(MessageConstant.MESSAGE_RULE_STATUS_CREATE);
		if (messageCreateRule.getIsNewRecord()){
			messageCreateRule.setCreateDt(new Date());
			messageCreateRule.setCreator(Long.parseLong(UserUtils.getUser().getId()));
		}
		super.save(messageCreateRule);
		messageCreateRuleChannelDao.deleteByRuleId(Long.parseLong(messageCreateRule.getId()));
		messageCreateRuleTermDao.deleteByRuleId(Long.parseLong(messageCreateRule.getId()));
		for(String messageChannel : messageCreateRule.getMessageChannelList()) {
			MessageCreateRuleChannel messageCreateRuleChannel = new MessageCreateRuleChannel();
			messageCreateRuleChannel.setRuleId(Long.parseLong(messageCreateRule.getId()));
			messageCreateRuleChannel.setMessageChannel(messageChannel);
			messageCreateRuleChannelDao.insert(messageCreateRuleChannel);
		}
		for(String term : messageCreateRule.getTermList()) {
			MessageCreateRuleTerm messageCreateRuleTerm = new MessageCreateRuleTerm();
			messageCreateRuleTerm.setRuleId(Long.parseLong(messageCreateRule.getId()));
			messageCreateRuleTerm.setOpTerm(term);
			messageCreateRuleTermDao.insert(messageCreateRuleTerm);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageCreateRule messageCreateRule) {
		super.delete(messageCreateRule);
	}

	/**
	 * 获取状态为status的消息产生规则列表，若dateTime不为null 则对列表时间进行过滤（dateTime在startDate endDate，startTime endTime内）
	 * @param status
	 * @param dateTime
	 * @return
	 */
	public List<MessageCreateRule> findListByStatus(String status, Date dateTime) {
		List<MessageCreateRule> messageCreateRuleList = dao.findListByStatus(status, dateTime);
		messageCreateRuleList = messageCreateRuleList == null ? new ArrayList<MessageCreateRule>() : messageCreateRuleList;
		for(MessageCreateRule messageCreateRule : messageCreateRuleList) {
			List<String> messageChannelList = messageCreateRuleChannelDao.findMessageChannelListByRuleId(Long.parseLong(messageCreateRule.getId()));
			messageCreateRule.setMessageChannelList(messageChannelList);
			List<String> termList = messageCreateRuleTermDao.findTermListByRuleId(Long.parseLong(messageCreateRule.getId()));
			messageCreateRule.setTermList(termList);
		}
		return messageCreateRuleList;
	}

	public List<MessageCreateRule> querySimpleList(MessageCreateRule qa) {
		return dao.querySimpleList(qa);
	}

	/**
	 * 审批消息产生规则
	 * @param id
	 * @param status
	 */
	@Transactional(readOnly = false)
	public void review(String id, String status) {
		dao.review(id, status, Long.parseLong(UserUtils.getUser().getId()));
	}

	public MessageCreateRule getByMessageId(Long messageId) {
		return messageCreateRuleDao.getByMessageId(messageId);
	}
}