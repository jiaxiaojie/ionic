/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceAlert;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceAlertDao;

/**
 * 账户余额警戒Service
 * @author huangyuchen
 * @version 2016-04-14
 */
@Service
@Transactional(readOnly = true)
public class CustomerBalanceAlertService extends CrudService<CustomerBalanceAlertDao, CustomerBalanceAlert> {

	public CustomerBalanceAlert get(String id) {
		return super.get(id);
	}
	
	public List<CustomerBalanceAlert> findList(CustomerBalanceAlert customerBalanceAlert) {
		return super.findList(customerBalanceAlert);
	}
	
	public Page<CustomerBalanceAlert> findPage(Page<CustomerBalanceAlert> page, CustomerBalanceAlert customerBalanceAlert) {
		return super.findPage(page, customerBalanceAlert);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBalanceAlert customerBalanceAlert) {
		super.save(customerBalanceAlert);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBalanceAlert customerBalanceAlert) {
		super.delete(customerBalanceAlert);
	}
	
	/**
	 * 查询出账户余额提醒相关的数据
	 * @param title
	 * @return
	 */
	public CustomerBalanceAlert selectCustomerBalaneceAlertBytitle(String title) {
		return dao.selectCustomerBalaneceAlertBytitle(title);
	}
	
}