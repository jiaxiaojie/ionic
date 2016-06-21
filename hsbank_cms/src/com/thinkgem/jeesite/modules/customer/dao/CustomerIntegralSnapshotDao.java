/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralSnapshot;

/**
 * 会员花生豆汇总DAO接口
 * @author ydt
 * @version 2015-06-26
 */
@MyBatisDao
public interface CustomerIntegralSnapshotDao extends CrudDao<CustomerIntegralSnapshot> {

	CustomerIntegralSnapshot getByAccountId(@Param("accountId") Long accountId);

	/**
	 * 更新用户的积分值
	 * @param accountId
	 * @param integral
	 */
	void updateIntegralValue(@Param("accountId") Long accountId, @Param("integral") int integral);

	/**
	 * 更新连续签到天数
	 * @param accountId
	 * @param consecutiveDays
     */
	void updateConsecutiveDays(@Param("accountId") Long accountId, @Param("consecutiveDays") int consecutiveDays);


}