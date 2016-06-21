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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBankCardHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBankCardHis;

/**
 * 会员银行卡历史变更Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerBankCardHisService extends CrudService<CustomerBankCardHisDao, CustomerBankCardHis> {
	
	@Autowired
	private CustomerBankCardHisDao customerBankCardHisDao;

	public CustomerBankCardHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerBankCardHis> findList(CustomerBankCardHis customerBankCardHis) {
		return super.findList(customerBankCardHis);
	}
	
	public Page<CustomerBankCardHis> findPage(Page<CustomerBankCardHis> page, CustomerBankCardHis customerBankCardHis) {
		return super.findPage(page, customerBankCardHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBankCardHis customerBankCardHis) {
		super.save(customerBankCardHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBankCardHis customerBankCardHis) {
		super.delete(customerBankCardHis);
	}

	public boolean hasHisInAfter(Long accountId,int day) {
		boolean result = false;
		CustomerBankCardHis customerBankCardHis = new CustomerBankCardHis();
		customerBankCardHis.setAccountId(accountId);
		customerBankCardHis.setBeginChangeDt(DateUtils.dateAddDay(new Date(), day));
		List<CustomerBankCardHis> customerBankCardHiss = customerBankCardHisDao.findList(customerBankCardHis);
		if(customerBankCardHiss != null && customerBankCardHiss.size() > 0){
			result = true;
		}
		
		return result;
	}
	
}