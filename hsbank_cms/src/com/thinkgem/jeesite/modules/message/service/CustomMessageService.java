/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomMessage;
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;
import com.thinkgem.jeesite.modules.message.dao.CustomMessageDao;
import com.thinkgem.jeesite.modules.message.dao.CustomMessageSendChannelDao;

/**
 * 自定义消息Service
 * 
 * @author ydt
 * @version 2016-01-14
 */
@Service
@Transactional(readOnly = true)
public class CustomMessageService extends CrudService<CustomMessageDao, CustomMessage> {

	@Autowired
	private CustomMessageSendChannelDao customMessageSendChannelDao;
	@Autowired
	private CustomMessageDao customMessageDao;

	public CustomMessage get(String id) {
		return super.get(id);
	}

	public List<CustomMessage> findList(CustomMessage customMessage) {
		return super.findList(customMessage);
	}

	public Page<CustomMessage> findPage(Page<CustomMessage> page, CustomMessage customMessage) {
		page.setOrderBy(
				page.getOrderBy() == null || "".equals(page.getOrderBy()) ? " a.create_dt desc " : page.getOrderBy());
		return super.findPage(page, customMessage);
	}

	@Transactional(readOnly = false)
	public void save(CustomMessage customMessage) {
		super.save(customMessage);
	}

	@Transactional(readOnly = false)
	public void create(CustomMessage customMessage) {
		if (customMessage.getId() != null && !"".equals(customMessage.getId())) {
			customMessageDao.update(customMessage);
		} else {
			customMessageDao.insert(customMessage);
		}
		Long customerMessageId = Long.valueOf(customMessage.getId());
		// 维护活动渠道限制数据
		List<String> messageChannelList = customMessage.getMessageChannelList();
		if (messageChannelList != null && messageChannelList.size() > 0) {
			this.channelLimitData(messageChannelList, customerMessageId);
		}
	}

	/**
	 * 渠道
	 * 
	 * @param messageChannelList
	 * @param id
	 */
	public void channelLimitData(List<String> messageChannelList, Long customerMessageId) {
		List<CustomMessageSendChannel> channelList = new ArrayList<CustomMessageSendChannel>();
		for (String channelId : messageChannelList) {
			CustomMessageSendChannel messageChannel = new CustomMessageSendChannel();
			messageChannel.setCustomerMessageId(customerMessageId);
			messageChannel.setMessageChannel(channelId);
			channelList.add(messageChannel);
		}
		// 先删除（根据编号）
		customMessageSendChannelDao.deleteById(customerMessageId);
		// 批量插入
		customMessageSendChannelDao.insertBatch(channelList);
	}

	@Transactional(readOnly = false)
	public void delete(CustomMessage customMessage) {
		super.delete(customMessage);
	}

	/**
	 * 审核
	 * 
	 * @param customMessage
	 */
	@Transactional(readOnly = false)
	public void review(CustomMessage customMessage) {
		if(customMessage.getIsUrgent().equals("1")){
			customMessageDao.updateIsUrgent(customMessage);
		}
		customMessageDao.update(customMessage);
	}
	/**
	 * 获取自定义消息
	 * @param customMessage
	 * @return
	 */
	public CustomMessage getCustomMessage(CustomMessage customMessage) {

		return customMessageDao.getCustomMessage(customMessage);
	}

	public String findStatusFromCustomMessageByAccountId(String id) {
		return customMessageDao.findStatusFromCustomMessageByAccountId(id);
	}

	public CustomMessage selectCustomMessageList(String id) {
		return customMessageDao.selectCustomMessageList(id);
	}
	public CustomMessage getByMessageId(Long messageId) {
		return customMessageDao.getByMessageId(messageId);
	}

/*	@Transactional(readOnly = false)
	public void changeStatus(String id) {
	customMessageDao.changeStatus(id);
	}*/
	@Transactional(readOnly = false)
	public void changeStatus(String id) {
		customMessageDao.changeStatus(id);
		
	}

}