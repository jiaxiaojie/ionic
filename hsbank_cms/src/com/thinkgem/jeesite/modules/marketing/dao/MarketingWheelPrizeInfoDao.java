/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;

/**
 * 大转盘奖品信息DAO接口
 * @author ydt
 * @version 2015-11-24
 */
@MyBatisDao
public interface MarketingWheelPrizeInfoDao extends CrudDao<MarketingWheelPrizeInfo> {

	/**
	 * 根据type，value获取奖品列表
	 * @param type
	 * @param value
	 */
	MarketingWheelPrizeInfo getByTypeAndValue(@Param("type") String type, @Param("value") String value);

	/**
	 * 根据奖品实例id获取奖品信息
	 * @param instanceId
	 * @return
	 */
	MarketingWheelPrizeInfo getByInstanceId(@Param("instanceId") long instanceId);

	/**
	 * 获取活动的默认奖品
	 * @param activityId
	 * @return
	 */
	MarketingWheelPrizeInfo getDefaultPrize(@Param("activityId") Long activityId);

	/**
	 * 根据活动编号获取奖品列表
	 * @param activityId
	 * @return
	 */
	List<MarketingWheelPrizeInfo> findListByActivityId(@Param("activityId") Long activityId);

	/**
	 * 根据活动编号设置奖品是否为默认奖品
	 * @param activityId
	 * @param isDefault
	 */
	void updateIsDefaultByActivityId(@Param("activityId") Long activityId, @Param("isDefault") String isDefault);

	/**
	 * 根据id设置奖品是否为默认奖品
	 * @param id
	 * @param isDefault
	 */
	void updateIsDefault(@Param("id") String id, @Param("isDefault") String isDefault);
}