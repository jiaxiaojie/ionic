/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductOrder;

/**
 * 花生乐园订单DAO接口
 * @author lizibo
 * @version 2015-09-21
 */
@MyBatisDao
public interface IntegralMallProductOrderDao extends CrudDao<IntegralMallProductOrder> {
	/**
	 * 获得最新订单列表
	 * @param limit
	 * @return
	 */
	public List<IntegralMallProductOrder> getLastOrders(@Param("limitCount") int limit);

	public List<IntegralMallProductOrder> findCustomerList(IntegralMallProductOrder integralMallProductOrder);

	public IntegralMallProductOrder getByOrderNo(@Param("orderNo") String orderNo);
	
	/**
	 * 分页查询订单列表
	 * @param map
	 * @return
	 */
	public List<IntegralMallProductOrder> findPageList(Map<String, Object> map);

	/**
	 * API
	 * 我的花生-兑换记录(订单)分页列表
	 * @param accountId
	 * @param startNumber
	 * @param endNumber
	 * @return
	 */
	public List<IntegralMallProductOrder> getOrderPageList(@Param("accountId") Long accountId, @Param("startNumber") Integer startNumber, @Param("endNumber") Integer endNumber,
														   @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);

	/**
	 *  API
	 * 我的订单总数量
	 * @param accountId
	 * @return
	 */
	public long countOrderByAccountId(@Param("accountId") Long accountId, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);

	/**
	 * API
	 * 我的花生-兑换记录(订单)详情
	 * @param accountId
	 * @param orderNo
	 * @return
	 */
	public IntegralMallProductOrder getDetailsByOrderNo(@Param("accountId") Long accountId, @Param("orderNo") String orderNo);

	/**
	 * API
	 * 花生乐园-参与记录
	 * @param limit
	 * @return
	 */
	public List<IntegralMallProductOrder> getOrderListByLast(int limit);
}