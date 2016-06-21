/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerEmployee;

/**
 * 员工信息DAO接口
 * @author ydt
 * @version 2015-09-15
 */
@MyBatisDao
public interface CustomerEmployeeDao extends CrudDao<CustomerEmployee> {

	/**
	 * 根据手机号获取员工信息
	 * @param mobile
	 * @return
	 */
	CustomerEmployee getByMobile(@Param("mobile") String mobile);
	
}