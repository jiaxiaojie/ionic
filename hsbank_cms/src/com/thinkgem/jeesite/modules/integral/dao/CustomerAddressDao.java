/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;

/**
 * 花生乐园用户地址DAO接口
 * @author lizibo
 * @version 2015-09-21
 */
@MyBatisDao
public interface CustomerAddressDao extends CrudDao<CustomerAddress> {

	/**
	 * 根据accountId及status获取地址列表
	 * @param accountId
	 * @param status
	 * @return
	 */
	List<CustomerAddress> findListByAccountId(@Param("accountId") Long accountId, @Param("status") String status);



	List<CustomerAddress> findDetailAddressList(CustomerAddress customerAddress);
}