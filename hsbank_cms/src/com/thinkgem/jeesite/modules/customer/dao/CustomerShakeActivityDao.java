/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerShakeActivity;

/**
 * 摇一摇活动DAO接口
 * @author ydt
 * @version 2015-09-10
 */
@MyBatisDao
public interface CustomerShakeActivityDao extends CrudDao<CustomerShakeActivity> {
	public List<CustomerShakeActivity> findSuperList();
}