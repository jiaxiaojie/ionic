/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInstance;

/**
 * 大转盘奖品实例DAO接口
 * @author ydt
 * @version 2015-11-24
 */
@MyBatisDao
public interface MarketingWheelPrizeInstanceDao extends CrudDao<MarketingWheelPrizeInstance> {
	
	/**
	 * 获得所有奖品实例数量
	 * @return
	 */
	long getAllPrizeInstanceCountByActivityId(@Param("activityId") Long activityId);

	/**
	 * 批量插入奖品实例
	 * @return
	 */
	void insertBatch(@Param("list") List<MarketingWheelPrizeInstance> marketingWheelPrizeInstanceList);

	/**
	 * 根据活动编号、状态获取指定数量的实例列表
	 * @param limit 
	 * @param status 
	 * @return
	 */
	List<MarketingWheelPrizeInstance> findListByActivityIdAndStatus(@Param("activityId") Long activityId, @Param("status") String status, @Param("limit") Integer limit);

	/**
	 * 更改奖品实例状态
	 * @param id
	 * @param status
	 */
	void updateStatus(@Param("id") String id, @Param("status") String status);

	/**
	 * 将需要解锁的奖品实例置为解锁状态
	 */
	void unlockInstance();

	/**
	 * 根据token将奖品实例设置为已被抽中状态
	 * @param token
	 * @param status
	 */
	void updateStatusByToken(@Param("token") String token, @Param("status") String status);

	/**
	 * 根据活动编号、状态获取指定条实例
	 * @param activityId
	 * @param status
	 * @param index 第几条奖品，从0开始
	 * @return
	 */
	MarketingWheelPrizeInstance getByActivityIdAndStatus(@Param("activityId") Long activityId, @Param("status") String status, @Param("index") Integer index);

	/**
	 * 根据id获取实例及奖品信息
	 * @param prizeInstanceId
	 * @return
	 */
	MarketingWheelPrizeInstance getInfo(@Param("id") Long id);
}