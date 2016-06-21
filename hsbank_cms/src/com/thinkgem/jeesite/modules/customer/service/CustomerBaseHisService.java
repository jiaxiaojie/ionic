/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerBaseHis;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBaseHisDao;

/**
 * 会员基本信息变更历史Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerBaseHisService extends CrudService<CustomerBaseHisDao, CustomerBaseHis> {

	public CustomerBaseHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerBaseHis> findList(CustomerBaseHis customerBaseHis) {
		return super.findList(customerBaseHis);
	}
	
	public Page<CustomerBaseHis> findPage(Page<CustomerBaseHis> page, CustomerBaseHis customerBaseHis) {
		return super.findPage(page, customerBaseHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBaseHis customerBaseHis) {
		super.save(customerBaseHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBaseHis customerBaseHis) {
		super.delete(customerBaseHis);
	}
	
}