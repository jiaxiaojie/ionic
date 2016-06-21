/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.AppMessagePushRecord;
import com.thinkgem.jeesite.modules.message.dao.AppMessagePushRecordDao;

/**
 * 客户端消息推送记录Service
 * @author ydt
 * @version 2016-02-19
 */
@Service
@Transactional(readOnly = true)
public class AppMessagePushRecordService extends CrudService<AppMessagePushRecordDao, AppMessagePushRecord> {

	public AppMessagePushRecord get(String id) {
		return super.get(id);
	}
	
	public List<AppMessagePushRecord> findList(AppMessagePushRecord appMessagePushRecord) {
		return super.findList(appMessagePushRecord);
	}
	
	public Page<AppMessagePushRecord> findPage(Page<AppMessagePushRecord> page, AppMessagePushRecord appMessagePushRecord) {
		return super.findPage(page, appMessagePushRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AppMessagePushRecord appMessagePushRecord) {
		super.save(appMessagePushRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppMessagePushRecord appMessagePushRecord) {
		super.delete(appMessagePushRecord);
	}
	
}