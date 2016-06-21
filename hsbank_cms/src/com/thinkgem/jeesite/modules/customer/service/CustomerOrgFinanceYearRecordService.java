/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerOrgFinanceYearRecord;
import com.thinkgem.jeesite.modules.customer.dao.CustomerOrgFinanceYearRecordDao;

/**
 * 企业会员财务年表Service
 * @author ydt
 * @version 2015-06-30
 */
@Service
@Transactional(readOnly = true)
public class CustomerOrgFinanceYearRecordService extends CrudService<CustomerOrgFinanceYearRecordDao, CustomerOrgFinanceYearRecord> {

	public CustomerOrgFinanceYearRecord get(String id) {
		return super.get(id);
	}
	
	public List<CustomerOrgFinanceYearRecord> findList(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord) {
		return super.findList(customerOrgFinanceYearRecord);
	}
	
	public Page<CustomerOrgFinanceYearRecord> findPage(Page<CustomerOrgFinanceYearRecord> page, CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord) {
		return super.findPage(page, customerOrgFinanceYearRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord) {
		super.save(customerOrgFinanceYearRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerOrgFinanceYearRecord customerOrgFinanceYearRecord) {
		super.delete(customerOrgFinanceYearRecord);
	}
	
}