/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;

/**
 * 营销活动DAO接口
 * @author lizibo
 * @version 2015-09-09
 */
@MyBatisDao
public interface MarketingActivityInfoDao extends CrudDao<MarketingActivityInfo> {
	
	/**
	 * 获取活动列表
	 * @param behaviorCode		行为编号
	 * @param channelId			渠道
	 * @param currDate			当前日期
	 * @param currTime			当前时间段
	 * @param status			状态
	 * @return
	 */
	public List<MarketingActivityInfo> getRelationActivityList(
			@Param("behaviorCode") String behaviorCode,
			@Param("channelId") String channelId,
			@Param("currDate") String currDate,
			@Param("currTime") String currTime, @Param("status") String status);
	
	/**
	 * 获取有效活动列表
	 * @param currDate
	 * @param currTime
	 * @return
	 */
	public List<MarketingActivityInfo> getEffectiveActivityList(@Param("currDate") String currDate, @Param("currTime") String currTime, @Param("status") String status);

	/**
	 * 根据关联实现类名获取活动信息
	 * @param bizClassName
	 * @return
	 */
	public MarketingActivityInfo getByBizClassName(@Param("bizClassName") String bizClassName);

	/**
	 * 获取状态为status的活动列表
	 * @param status
	 * @return
	 */
	public List<MarketingActivityInfo> findListByStatus(@Param("status") String status);
}