/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerRechargeHis;
import com.thinkgem.jeesite.modules.customer.dao.CustomerRechargeHisDao;

/**
 * 会员充值记录Service
 * @author yangtao
 * @version 2015-07-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerRechargeHisService extends CrudService<CustomerRechargeHisDao, CustomerRechargeHis> {

	public CustomerRechargeHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerRechargeHis> findList(CustomerRechargeHis customerRechargeHis) {
		return super.findList(customerRechargeHis);
	}
	
	public Page<CustomerRechargeHis> findPage(Page<CustomerRechargeHis> page, CustomerRechargeHis customerRechargeHis) {
		return super.findPage(page, customerRechargeHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerRechargeHis customerRechargeHis) {
		super.save(customerRechargeHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerRechargeHis customerRechargeHis) {
		super.delete(customerRechargeHis);
	}
	
}