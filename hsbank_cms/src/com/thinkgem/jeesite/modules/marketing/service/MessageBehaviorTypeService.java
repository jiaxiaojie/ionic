/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MessageBehaviorType;
import com.thinkgem.jeesite.modules.marketing.dao.MessageBehaviorTypeDao;

/**
 * 行为编码Service
 * @author ydt
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class MessageBehaviorTypeService extends CrudService<MessageBehaviorTypeDao, MessageBehaviorType> {

	public MessageBehaviorType get(String id) {
		return super.get(id);
	}
	
	public List<MessageBehaviorType> findList(MessageBehaviorType messageBehaviorType) {
		return super.findList(messageBehaviorType);
	}
	
	public Page<MessageBehaviorType> findPage(Page<MessageBehaviorType> page, MessageBehaviorType messageBehaviorType) {
		return super.findPage(page, messageBehaviorType);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageBehaviorType messageBehaviorType) {
		super.save(messageBehaviorType);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageBehaviorType messageBehaviorType) {
		super.delete(messageBehaviorType);
	}
	
}