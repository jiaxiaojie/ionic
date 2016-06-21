/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerRestPwdLog;
import com.thinkgem.jeesite.modules.customer.dao.CustomerRestPwdLogDao;

/**
 * 密码重置记录Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerRestPwdLogService extends CrudService<CustomerRestPwdLogDao, CustomerRestPwdLog> {

	public CustomerRestPwdLog get(String id) {
		return super.get(id);
	}
	
	public List<CustomerRestPwdLog> findList(CustomerRestPwdLog customerRestPwdLog) {
		return super.findList(customerRestPwdLog);
	}
	
	public Page<CustomerRestPwdLog> findPage(Page<CustomerRestPwdLog> page, CustomerRestPwdLog customerRestPwdLog) {
		return super.findPage(page, customerRestPwdLog);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerRestPwdLog customerRestPwdLog) {
		super.save(customerRestPwdLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerRestPwdLog customerRestPwdLog) {
		super.delete(customerRestPwdLog);
	}
	
}