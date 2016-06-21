/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;

/**
 * 客户端缓存信息DAO接口
 * @author lzb
 * @version 2015-10-14
 */
@MyBatisDao
public interface CustomerClientTokenDao extends CrudDao<CustomerClientToken> {
	/**
	 * 根据token获取有效的缓存信息
	 * @param token
	 * @param date
	 * @return
	 */
	CustomerClientToken getByTokenAndTheDate(@Param("token") String token, @Param("theDate") Date date);
	
	/**
	 * 根据会员编号及终端获取缓存信息
	 * @param customerId
	 * @param termType
	 * @return
	 */
	CustomerClientToken getClientTokenByCustomerIdAndTermType(@Param("customerId") String customerId, @Param("termType") String termType);
	
	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	CustomerClientToken getByToken(@Param("token") String token);
}