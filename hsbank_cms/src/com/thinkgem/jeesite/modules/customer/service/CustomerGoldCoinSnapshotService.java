/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerGoldCoinSnapshot;
import com.thinkgem.jeesite.modules.customer.dao.CustomerGoldCoinSnapshotDao;

/**
 * 会员代金币汇总Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerGoldCoinSnapshotService extends CrudService<CustomerGoldCoinSnapshotDao, CustomerGoldCoinSnapshot> {

	public CustomerGoldCoinSnapshot get(String id) {
		return super.get(id);
	}
	
	public List<CustomerGoldCoinSnapshot> findList(CustomerGoldCoinSnapshot customerGoldCoinSnapshot) {
		return super.findList(customerGoldCoinSnapshot);
	}
	
	public Page<CustomerGoldCoinSnapshot> findPage(Page<CustomerGoldCoinSnapshot> page, CustomerGoldCoinSnapshot customerGoldCoinSnapshot) {
		return super.findPage(page, customerGoldCoinSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerGoldCoinSnapshot customerGoldCoinSnapshot) {
		super.save(customerGoldCoinSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerGoldCoinSnapshot customerGoldCoinSnapshot) {
		super.delete(customerGoldCoinSnapshot);
	}
	
}