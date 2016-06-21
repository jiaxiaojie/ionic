/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerRateTicket;

/**
 * 会员加息券清单DAO接口
 * @author ydt
 * @version 2016-04-05
 */
@MyBatisDao
public interface CustomerRateTicketDao extends CrudDao<CustomerRateTicket> {
	/**
	 * 根据加息券ids获取列表
	 * @param ticketIds
	 * @return
	 */
	List<CustomerRateTicket> getListByRateTicketIds(@Param("rateTicketIds") String rateTicketIds);

	/**
	 * 根据账户编号获取列表
	 * @param accountId
	 * @param status
	 * @return
	 */
	List<CustomerRateTicket> getListByAccountId(@Param("accountId") Long accountId, @Param("status") String status);
	/**
	 * 根据map中的参数获取加息券列表
	 * @param map
	 * @return
	 */
	List<CustomerRateTicket> findListByMap(Map<String, Object> map);

	/**
	 * 将过期会员加息券状态设置为已过期
	 */
	void expiredTicket();

	/**
	 * 统计加息券总利息及张数
	 * @param map
	 * @return
     */
	Map<String,Object> getRateTicketStatistics(Map<String, Object> map);
}