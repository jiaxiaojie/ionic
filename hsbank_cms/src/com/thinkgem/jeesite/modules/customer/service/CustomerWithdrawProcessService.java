/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawProcess;
import com.thinkgem.jeesite.modules.customer.dao.CustomerWithdrawProcessDao;

/**
 * 会员提现审批Service
 * @author yangtao
 * @version 2015-07-22
 */
@Service
@Transactional(readOnly = true)
public class CustomerWithdrawProcessService extends CrudService<CustomerWithdrawProcessDao, CustomerWithdrawProcess> {

	public CustomerWithdrawProcess get(String id) {
		return super.get(id);
	}
	
	public List<CustomerWithdrawProcess> findList(CustomerWithdrawProcess customerWithdrawProcess) {
		return super.findList(customerWithdrawProcess);
	}
	
	public Page<CustomerWithdrawProcess> findPage(Page<CustomerWithdrawProcess> page, CustomerWithdrawProcess customerWithdrawProcess) {
		return super.findPage(page, customerWithdrawProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerWithdrawProcess customerWithdrawProcess) {
		super.save(customerWithdrawProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerWithdrawProcess customerWithdrawProcess) {
		super.delete(customerWithdrawProcess);
	}
	
}