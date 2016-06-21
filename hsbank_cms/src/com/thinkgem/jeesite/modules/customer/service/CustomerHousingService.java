/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerHousingDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerCreditAuthHandler;
import com.thinkgem.jeesite.modules.entity.CustomerHousing;

/**
 * 会员房产信息Service
 * @author ydt
 * @version 2015-06-25
 */
@Service
@Transactional(readOnly = true)
public class CustomerHousingService extends CrudService<CustomerHousingDao, CustomerHousing> {
	@Autowired
	private CustomerCreditAuthHandler customerCreditAuthHandler;

	public CustomerHousing get(String id) {
		return super.get(id);
	}
	
	public List<CustomerHousing> findList(CustomerHousing customerHousing) {
		return super.findList(customerHousing);
	}
	
	public Page<CustomerHousing> findPage(Page<CustomerHousing> page, CustomerHousing customerHousing) {
		return super.findPage(page, customerHousing);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerHousing customerHousing) {
		super.save(customerHousing);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerHousing customerHousing) {
		super.delete(customerHousing);
	}

	public CustomerHousing getByCustomerId(Long customerId) {
		return dao.getByCustomerId(customerId);
	}

	@Transactional(readOnly = false)
	public void authHousingInfo(CustomerHousing customerHousing) {
		save(customerHousing);
		customerCreditAuthHandler.authHousingInfo(customerHousing);
		
	}
	
}