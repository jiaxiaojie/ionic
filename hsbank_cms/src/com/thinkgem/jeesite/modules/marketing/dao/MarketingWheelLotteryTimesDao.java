/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingWheelLotteryTimes;

/**
 * 大转盘抽奖次数DAO接口
 * @author ydt
 * @version 2016-01-25
 */
@MyBatisDao
public interface MarketingWheelLotteryTimesDao extends CrudDao<MarketingWheelLotteryTimes> {
	/**
	 * 根据accountId获取抽奖次数信息
	 * @param accountId
	 * @return
	 */
	MarketingWheelLotteryTimes getByAccountIdAndActivityId(@Param("accountId") Long accountId, @Param("activityId") Long activityId);

	/**
	 * 更新用户的总抽奖次数
	 * @param accountId
	 * @param activityId
	 * @param totalTimes	为正时表示增加次数，为负时表示减少次数
	 */
	void updateTotalTimesByAccountIdAndActivityId(@Param("accountId") long accountId,
												  @Param("activityId") long activityId, @Param("totalTimes") int totalTimes);
	
	/**
	 * 更新用户的已抽奖次数
	 * @param accountId
	 * @param usedTimes	为正时表示增加次数，为负时表示减少次数
	 */
	void updateUsedTimesByAccountIdAndActivityId(@Param("accountId") long accountId,
												 @Param("activityId") long activityId, @Param("usedTimes") int usedTimes);

	/**
	 * 根据活动编号更新totalTimes、usedTimes
	 * @param totalTimes
	 * @param usedTimes
	 * @param acticityId
	 */
	void updateLotteryTimesByActivityId(@Param("totalTimes") int totalTimes,
										@Param("usedTimes") int usedTimes, @Param("activityId") Long acticityId);
}