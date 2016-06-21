/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawDepositSnapshot;
import com.thinkgem.jeesite.modules.customer.dao.CustomerWithdrawDepositSnapshotDao;

/**
 * 会员提现额汇总Service
 * @author yangtao
 * @version 2015-07-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerWithdrawDepositSnapshotService extends CrudService<CustomerWithdrawDepositSnapshotDao, CustomerWithdrawDepositSnapshot> {

	public CustomerWithdrawDepositSnapshot get(String id) {
		return super.get(id);
	}
	
	public List<CustomerWithdrawDepositSnapshot> findList(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot) {
		return super.findList(customerWithdrawDepositSnapshot);
	}
	
	public Page<CustomerWithdrawDepositSnapshot> findPage(Page<CustomerWithdrawDepositSnapshot> page, CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot) {
		return super.findPage(page, customerWithdrawDepositSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot) {
		super.save(customerWithdrawDepositSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerWithdrawDepositSnapshot customerWithdrawDepositSnapshot) {
		super.delete(customerWithdrawDepositSnapshot);
	}
	
}