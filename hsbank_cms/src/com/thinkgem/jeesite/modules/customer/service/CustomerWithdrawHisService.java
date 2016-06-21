/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawHis;
import com.thinkgem.jeesite.modules.customer.dao.CustomerWithdrawHisDao;

/**
 * 会员体现记录表Service
 * @author lzb
 * @version 2016-05-10
 */
@Service
@Transactional(readOnly = true)
public class CustomerWithdrawHisService extends CrudService<CustomerWithdrawHisDao, CustomerWithdrawHis> {

	public CustomerWithdrawHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerWithdrawHis> findList(CustomerWithdrawHis customerWithdrawHis) {
		return super.findList(customerWithdrawHis);
	}
	
	public Page<CustomerWithdrawHis> findPage(Page<CustomerWithdrawHis> page, CustomerWithdrawHis customerWithdrawHis) {
		return super.findPage(page, customerWithdrawHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerWithdrawHis customerWithdrawHis) {
		super.save(customerWithdrawHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerWithdrawHis customerWithdrawHis) {
		super.delete(customerWithdrawHis);
	}
	
}