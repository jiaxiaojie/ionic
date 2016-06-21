/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerWorkDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerCreditAuthHandler;
import com.thinkgem.jeesite.modules.entity.CustomerWork;

/**
 * 会员工作信息Service
 * @author ydt
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class CustomerWorkService extends CrudService<CustomerWorkDao, CustomerWork> {
	@Autowired
	private CustomerCreditAuthHandler customerCreditAuthHandler;
	
	public CustomerWork get(String id) {
		return super.get(id);
	}
	
	public List<CustomerWork> findList(CustomerWork customerWork) {
		return super.findList(customerWork);
	}
	
	public Page<CustomerWork> findPage(Page<CustomerWork> page, CustomerWork customerWork) {
		return super.findPage(page, customerWork);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerWork customerWork) {
		if (customerWork.getIsNewRecord()){
			customerWork.setCreateDt(new Date());
		}else {
			customerWork.setLastModifyDt(new Date());
		}
		super.save(customerWork);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerWork customerWork) {
		super.delete(customerWork);
	}

	public CustomerWork getByCustomerId(Long customerId) {
		return dao.getByCustomerId(customerId);
	}

	@Transactional(readOnly = false)
	public void authIncomeInfo(CustomerWork customerWork) {
		save(customerWork);
		customerCreditAuthHandler.authIncomeInfo(customerWork);
	}
}