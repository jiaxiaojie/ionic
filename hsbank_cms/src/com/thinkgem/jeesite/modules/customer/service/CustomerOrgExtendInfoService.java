/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerOrgExtendInfo;
import com.thinkgem.jeesite.modules.customer.dao.CustomerOrgExtendInfoDao;

/**
 * 组织会员扩展信息Service
 * @author ydt
 * @version 2015-06-30
 */
@Service
@Transactional(readOnly = true)
public class CustomerOrgExtendInfoService extends CrudService<CustomerOrgExtendInfoDao, CustomerOrgExtendInfo> {

	public CustomerOrgExtendInfo get(String id) {
		return super.get(id);
	}
	
	public List<CustomerOrgExtendInfo> findList(CustomerOrgExtendInfo customerOrgExtendInfo) {
		return super.findList(customerOrgExtendInfo);
	}
	
	public Page<CustomerOrgExtendInfo> findPage(Page<CustomerOrgExtendInfo> page, CustomerOrgExtendInfo customerOrgExtendInfo) {
		return super.findPage(page, customerOrgExtendInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerOrgExtendInfo customerOrgExtendInfo) {
		super.save(customerOrgExtendInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerOrgExtendInfo customerOrgExtendInfo) {
		super.delete(customerOrgExtendInfo);
	}
	
}