/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.entity.ReceiveMessageSwitch;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.MessageInstanceDao;
import com.thinkgem.jeesite.modules.message.dao.ReceiveMessageSwitchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接收消息开关Service
 * @author ydt
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class ReceiveMessageSwitchService extends CrudService<ReceiveMessageSwitchDao, ReceiveMessageSwitch> {
	@Autowired
	private ReceiveMessageSwitchDao receiveMessageSwitchDao;
	@Autowired
	private MessageInstanceDao messageInstanceDao;
	public ReceiveMessageSwitch get(String id) {
		return super.get(id);
	}
	
	public List<ReceiveMessageSwitch> findList(ReceiveMessageSwitch receiveMessageSwitch) {
		return super.findList(receiveMessageSwitch);
	}
	
	public Page<ReceiveMessageSwitch> findPage(Page<ReceiveMessageSwitch> page, ReceiveMessageSwitch receiveMessageSwitch) {
		return super.findPage(page, receiveMessageSwitch);
	}
	
	@Transactional(readOnly = false)
	public void save(ReceiveMessageSwitch receiveMessageSwitch) {
		super.save(receiveMessageSwitch);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReceiveMessageSwitch receiveMessageSwitch) {
		super.delete(receiveMessageSwitch);
	}

	public Page<Map<String,Object>> findCustomerSwitchPage(Page<Map<String,Object>> page,
			ReceiveMessageSwitch receiveMessageSwitch) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("page",page);
		params.put("accountId",receiveMessageSwitch.getAccountId());
		params.put("messageChannel",receiveMessageSwitch.getMessageChannel());
		params.put("messageType",receiveMessageSwitch.getMessageType());
		params.put("messageChannels",receiveMessageSwitch.getMessageChannels());
		page.setList(receiveMessageSwitchDao.findCustomerSwitchList(params));
		return page;
	}

	@Transactional(readOnly = false)
	public Boolean settingMessageSwitch(List<ReceiveMessageSwitch> rmss) {
		Boolean isCreate  = false;
		
		for(ReceiveMessageSwitch receiveMessageSwitch : rmss){
			if(settingMessageSwitch(receiveMessageSwitch)){
				isCreate = true;
			}
		}
		return isCreate;
	}
	
	@Transactional(readOnly = false)
	public Boolean settingMessageSwitch(ReceiveMessageSwitch receiveMessageSwitch) {
		Boolean isCreate  = false;
		if(receiveMessageSwitch==null){
			return isCreate;
		}
		
		ReceiveMessageSwitch entity = this.get(receiveMessageSwitch.getId());
		
		if(receiveMessageSwitch.getAccountId() != 0){//如果是用户消息开关设置
			isCreate = (entity.getAccountId()==0);
			if(isCreate){
				entity.setId(null);
				entity.setAccountId(receiveMessageSwitch.getAccountId());
				
				ReceiveMessageSwitch rms = Collections3.getFirst(this.findList(entity));
				entity = (rms==null?entity:rms);
			}
		}
		
		
		
		
		entity.setIsReceive(receiveMessageSwitch.getIsReceive());
		this.save(entity);
		
		//更新消息实例
		if(MessageConstant.IS_RECEIVE_MESSAGE_NO.equals(entity.getIsReceive())){
			messageInstanceDao.expireMessageSwitch(entity);
		}
		
		return isCreate;
	}
	
	public boolean shouldReceiveMessage(Long accountId, String messageType, String messageChannel) {
		if(!MessageConstant.IS_RECEIVE_MESSAGE_YES.equals(receiveMessageSwitchDao.getIsReceive(MessageConstant.SYSTEM_MESSAGE_SWITCH_ACCOUNT_ID, messageType, messageChannel))) {
			return false;
		}
		return !MessageConstant.IS_RECEIVE_MESSAGE_NO.equals(receiveMessageSwitchDao.getIsReceive(accountId, messageType, messageChannel));
	}

	public String getIsReceive(Long accountId, String messageType, String messageChannel) {
		return shouldReceiveMessage(accountId, messageType, messageChannel) ? MessageConstant.IS_RECEIVE_MESSAGE_YES : MessageConstant.IS_RECEIVE_MESSAGE_NO;
	}

	/**
	 * 设置消息开关
	 * @param accountId
	 * @param messageType
	 * @param messageChannel
	 * @param isReceive
	 */
	@Transactional(readOnly = false)
	public void settingSwitch(Long accountId, String messageType, String messageChannel, String isReceive) {
		dao.deleteSwitch(accountId, messageType, messageChannel);
		ReceiveMessageSwitch receiveMessageSwitch = new ReceiveMessageSwitch();
		receiveMessageSwitch.setAccountId(accountId);
		receiveMessageSwitch.setMessageType(messageType);
		receiveMessageSwitch.setMessageChannel(messageChannel);
		receiveMessageSwitch.setIsReceive(isReceive);
		dao.insert(receiveMessageSwitch);
	}
}