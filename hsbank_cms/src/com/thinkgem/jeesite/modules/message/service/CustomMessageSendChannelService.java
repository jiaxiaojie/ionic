/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;
import com.thinkgem.jeesite.modules.message.dao.CustomMessageSendChannelDao;

/**
 * 信息渠道Service
 * @author huangyuchen
 * @version 2016-01-18
 */
@Service
@Transactional(readOnly = true)
public class CustomMessageSendChannelService extends CrudService<CustomMessageSendChannelDao, CustomMessageSendChannel> {
	@Autowired
	private CustomMessageSendChannelDao customMessageSendChannelDao;
	public CustomMessageSendChannel get(String id) {
		return super.get(id);
	}
	
	public List<CustomMessageSendChannel> findList(CustomMessageSendChannel customMessageSendChannel) {
		return super.findList(customMessageSendChannel);
	}
	
	public Page<CustomMessageSendChannel> findPage(Page<CustomMessageSendChannel> page, CustomMessageSendChannel customMessageSendChannel) {
		return super.findPage(page, customMessageSendChannel);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomMessageSendChannel customMessageSendChannel) {
		super.save(customMessageSendChannel);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomMessageSendChannel customMessageSendChannel) {
		super.delete(customMessageSendChannel);
	}
	/**
	 *根据id查询消息发送渠道
	 * @param id
	 * @return
	 */
	public List<CustomMessageSendChannel> findListById(String id) {
		return customMessageSendChannelDao.findListById(id);
	}

	
}