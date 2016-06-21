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
import com.thinkgem.jeesite.modules.customer.dao.CustomerCarDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerCreditAuthHandler;
import com.thinkgem.jeesite.modules.entity.CustomerCar;

/**
 * 会员车辆信息Service
 * @author ydt
 * @version 2015-07-08
 */
@Service
@Transactional(readOnly = true)
public class CustomerCarService extends CrudService<CustomerCarDao, CustomerCar> {
	@Autowired
	private CustomerCreditAuthHandler customerCreditAuthHandler;

	public CustomerCar get(String id) {
		return super.get(id);
	}
	
	public List<CustomerCar> findList(CustomerCar customerCar) {
		return super.findList(customerCar);
	}
	
	public Page<CustomerCar> findPage(Page<CustomerCar> page, CustomerCar customerCar) {
		return super.findPage(page, customerCar);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerCar customerCar) {
		super.save(customerCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerCar customerCar) {
		super.delete(customerCar);
	}

	public CustomerCar getByCustomerId(Long customerId) {
		return dao.getByCustomerId(customerId);
	}

	@Transactional(readOnly = false)
	public void authCarInfo(CustomerCar customerCar) {
		save(customerCar);
		customerCreditAuthHandler.authCarInfo(customerCar);
	}
	
}