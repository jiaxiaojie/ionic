/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerLoginLog;
import com.thinkgem.jeesite.modules.customer.dao.CustomerLoginLogDao;

/**
 * 会员客户端访问流水Service
 * @author ydt
 * @version 2015-06-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerLoginLogService extends CrudService<CustomerLoginLogDao, CustomerLoginLog> {

	public CustomerLoginLog get(String id) {
		return super.get(id);
	}
	
	public List<CustomerLoginLog> findList(CustomerLoginLog customerLoginLog) {
		return super.findList(customerLoginLog);
	}
	
	public Page<CustomerLoginLog> findPage(Page<CustomerLoginLog> page, CustomerLoginLog customerLoginLog) {
		return super.findPage(page, customerLoginLog);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerLoginLog customerLoginLog) {
		super.save(customerLoginLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerLoginLog customerLoginLog) {
		super.delete(customerLoginLog);
	}
	
}