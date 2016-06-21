/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerCar;

/**
 * 会员车辆信息DAO接口
 * @author ydt
 * @version 2015-07-08
 */
@MyBatisDao
public interface CustomerCarDao extends CrudDao<CustomerCar> {

	CustomerCar getByCustomerId(Long customerId);
	
}