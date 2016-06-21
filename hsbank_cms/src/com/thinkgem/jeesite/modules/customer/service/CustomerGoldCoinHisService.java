/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerGoldCoinHis;
import com.thinkgem.jeesite.modules.customer.dao.CustomerGoldCoinHisDao;

/**
 * 会员代币变更流水Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerGoldCoinHisService extends CrudService<CustomerGoldCoinHisDao, CustomerGoldCoinHis> {

	public CustomerGoldCoinHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerGoldCoinHis> findList(CustomerGoldCoinHis customerGoldCoinHis) {
		return super.findList(customerGoldCoinHis);
	}
	
	public Page<CustomerGoldCoinHis> findPage(Page<CustomerGoldCoinHis> page, CustomerGoldCoinHis customerGoldCoinHis) {
		return super.findPage(page, customerGoldCoinHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerGoldCoinHis customerGoldCoinHis) {
		super.save(customerGoldCoinHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerGoldCoinHis customerGoldCoinHis) {
		super.delete(customerGoldCoinHis);
	}
	
}