/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerDoubleElevenActivity;

/**
 * 双11活动手动送现金DAO接口
 * @author lzb
 * @version 2015-11-03
 */
@MyBatisDao
public interface CustomerDoubleElevenActivityDao extends CrudDao<CustomerDoubleElevenActivity> {
	/**
	 * 选择更新
	 * @param customerDoubleElevenActivity
	 */
	public void updateSelected(CustomerDoubleElevenActivity customerDoubleElevenActivity);
}