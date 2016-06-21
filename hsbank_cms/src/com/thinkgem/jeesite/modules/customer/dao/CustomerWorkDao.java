/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerWork;

/**
 * 会员工作信息DAO接口
 * @author ydt
 * @version 2015-06-24
 */
@MyBatisDao
public interface CustomerWorkDao extends CrudDao<CustomerWork> {

	CustomerWork getByCustomerId(Long customerId);

	/**
	 * 用户修改其信息
	 * @param customerWork 
	 */
	void customerChangeHisInfo(CustomerWork customerWork);

	/**
	 * 根据customerId查询信息
	 * @param customerId
	 * @return
	 */
	CustomerWork getCustomerWorkByCustomerId(@Param("customerId") Long customerId);
	
}