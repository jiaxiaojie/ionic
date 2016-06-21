/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CustomerNewTask;
import com.thinkgem.jeesite.modules.customer.dao.CustomerNewTaskDao;

/**
 * 新手任务Service
 * @author lzb
 * @version 2015-11-13
 */
@Service
@Transactional(readOnly = true)
public class CustomerNewTaskService extends CrudService<CustomerNewTaskDao, CustomerNewTask> {

	public CustomerNewTask get(String id) {
		return super.get(id);
	}
	
	public List<CustomerNewTask> findList(CustomerNewTask customerNewTask) {
		return super.findList(customerNewTask);
	}
	
	public Page<CustomerNewTask> findPage(Page<CustomerNewTask> page, CustomerNewTask customerNewTask) {
		return super.findPage(page, customerNewTask);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerNewTask customerNewTask) {
		super.save(customerNewTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerNewTask customerNewTask) {
		super.delete(customerNewTask);
	}
	
}