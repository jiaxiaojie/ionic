/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.LogSendValidateCode;
import com.thinkgem.jeesite.modules.log.dao.LogSendValidateCodeDao;

/**
 * 验证码发送日志Service
 * @author ydt
 * @version 2015-08-13
 */
@Service
@Transactional(readOnly = true)
public class LogSendValidateCodeService extends CrudService<LogSendValidateCodeDao, LogSendValidateCode> {

	public LogSendValidateCode get(String id) {
		return super.get(id);
	}
	
	public List<LogSendValidateCode> findList(LogSendValidateCode logSendValidateCode) {
		return super.findList(logSendValidateCode);
	}
	
	public Page<LogSendValidateCode> findPage(Page<LogSendValidateCode> page, LogSendValidateCode logSendValidateCode) {
		return super.findPage(page, logSendValidateCode);
	}
	
	@Transactional(readOnly = false)
	public void save(LogSendValidateCode logSendValidateCode) {
		super.save(logSendValidateCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogSendValidateCode logSendValidateCode) {
		super.delete(logSendValidateCode);
	}

	/**
	 * 获取发送给email的最后一条验证码
	 * @param email
	 * @return
	 */
	public LogSendValidateCode getLastEmailValidateCodeRecordByEmail(String email) {
		return dao.getLastEmailValidateCodeRecordByEmail(email);
	}
}