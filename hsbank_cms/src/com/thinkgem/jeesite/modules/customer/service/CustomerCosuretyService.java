/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerCosurety;
import com.thinkgem.jeesite.modules.customer.dao.CustomerCosuretyDao;

/**
 * 会员联保人Service
 * @author ydt
 * @version 2015-06-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerCosuretyService extends CrudService<CustomerCosuretyDao, CustomerCosurety> {

	public CustomerCosurety get(String id) {
		return super.get(id);
	}
	
	public List<CustomerCosurety> findList(CustomerCosurety customerCosurety) {
		return super.findList(customerCosurety);
	}
	
	public Page<CustomerCosurety> findPage(Page<CustomerCosurety> page, CustomerCosurety customerCosurety) {
		return super.findPage(page, customerCosurety);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerCosurety customerCosurety) {
		super.save(customerCosurety);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerCosurety customerCosurety) {
		super.delete(customerCosurety);
	}

	public CustomerCosurety getByCustomerId(Long customerId) {
		return dao.getByCustomerId(customerId);
	}
	
}