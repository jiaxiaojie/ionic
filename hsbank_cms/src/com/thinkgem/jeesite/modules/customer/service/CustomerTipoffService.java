/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerTipoff;
import com.thinkgem.jeesite.modules.customer.dao.CustomerTipoffDao;

/**
 * 会员举报信息Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerTipoffService extends CrudService<CustomerTipoffDao, CustomerTipoff> {

	public CustomerTipoff get(String id) {
		return super.get(id);
	}
	
	public List<CustomerTipoff> findList(CustomerTipoff customerTipoff) {
		return super.findList(customerTipoff);
	}
	
	public Page<CustomerTipoff> findPage(Page<CustomerTipoff> page, CustomerTipoff customerTipoff) {
		return super.findPage(page, customerTipoff);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerTipoff customerTipoff) {
		super.save(customerTipoff);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerTipoff customerTipoff) {
		super.delete(customerTipoff);
	}
	
}