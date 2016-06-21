/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerFinance;
import com.thinkgem.jeesite.modules.customer.dao.CustomerFinanceDao;

/**
 * 会员财务信息Service
 * @author ydt
 * @version 2015-06-25
 */
@Service
@Transactional(readOnly = true)
public class CustomerFinanceService extends CrudService<CustomerFinanceDao, CustomerFinance> {

	public CustomerFinance get(String id) {
		return super.get(id);
	}
	
	public List<CustomerFinance> findList(CustomerFinance customerFinance) {
		return super.findList(customerFinance);
	}
	
	public Page<CustomerFinance> findPage(Page<CustomerFinance> page, CustomerFinance customerFinance) {
		return super.findPage(page, customerFinance);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerFinance customerFinance) {
		super.save(customerFinance);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerFinance customerFinance) {
		super.delete(customerFinance);
	}

	public CustomerFinance getByCustomerId(Long customerId) {
		return dao.getByCustomerId(customerId);
	}
	
}