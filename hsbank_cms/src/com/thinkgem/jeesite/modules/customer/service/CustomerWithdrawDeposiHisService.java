/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawDeposiHis;
import com.thinkgem.jeesite.modules.customer.dao.CustomerWithdrawDeposiHisDao;

/**
 * 会员提现额流水Service
 * @author yangtao
 * @version 2015-07-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerWithdrawDeposiHisService extends CrudService<CustomerWithdrawDeposiHisDao, CustomerWithdrawDeposiHis> {

	public CustomerWithdrawDeposiHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerWithdrawDeposiHis> findList(CustomerWithdrawDeposiHis customerWithdrawDeposiHis) {
		return super.findList(customerWithdrawDeposiHis);
	}
	
	public Page<CustomerWithdrawDeposiHis> findPage(Page<CustomerWithdrawDeposiHis> page, CustomerWithdrawDeposiHis customerWithdrawDeposiHis) {
		return super.findPage(page, customerWithdrawDeposiHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerWithdrawDeposiHis customerWithdrawDeposiHis) {
		super.save(customerWithdrawDeposiHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerWithdrawDeposiHis customerWithdrawDeposiHis) {
		super.delete(customerWithdrawDeposiHis);
	}

	public List<CustomerWithdrawDeposiHis> findListDuringDate(Long accountId, Date startDate, Date endDate) {
		return dao.findListDuringDate(accountId, startDate, endDate);
	}
	
}