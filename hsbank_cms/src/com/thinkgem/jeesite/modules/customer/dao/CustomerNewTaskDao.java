/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerNewTask;

/**
 * 新手任务DAO接口
 * @author lzb
 * @version 2015-11-13
 */
@MyBatisDao
public interface CustomerNewTaskDao extends CrudDao<CustomerNewTask> {
	
}