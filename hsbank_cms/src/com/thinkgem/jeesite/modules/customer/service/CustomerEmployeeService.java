/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerEmployee;
import com.thinkgem.jeesite.modules.customer.dao.CustomerEmployeeDao;

/**
 * 员工信息Service
 * @author ydt
 * @version 2015-09-15
 */
@Service
@Transactional(readOnly = true)
public class CustomerEmployeeService extends CrudService<CustomerEmployeeDao, CustomerEmployee> {

	public CustomerEmployee get(String id) {
		return super.get(id);
	}
	
	public List<CustomerEmployee> findList(CustomerEmployee customerEmployee) {
		return super.findList(customerEmployee);
	}
	
	public Page<CustomerEmployee> findPage(Page<CustomerEmployee> page, CustomerEmployee customerEmployee) {
		return super.findPage(page, customerEmployee);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerEmployee customerEmployee) {
		super.save(customerEmployee);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerEmployee customerEmployee) {
		super.delete(customerEmployee);
	}

	/**
	 * 根据手机号获取员工信息
	 * @param recommendMobile
	 * @return
	 */
	public CustomerEmployee getByMobile(String mobile) {
		return dao.getByMobile(mobile);
	}
	
}