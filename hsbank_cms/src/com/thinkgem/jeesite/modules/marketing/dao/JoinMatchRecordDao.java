/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;

/**
 * 参赛记录DAO接口
 * @author ydt
 * @version 2016-04-20
 */
@MyBatisDao
public interface JoinMatchRecordDao extends CrudDao<JoinMatchRecord> {

	/**
	 * 根据activityId,accountId获取参赛记录列表
	 * @param activityId
	 * @param accountId
	 * @return
	 */
	List<JoinMatchRecord> findListByActivityIdAndAccountId(@Param("activityId") Long activityId, @Param("accountId") Long accountId);

	/**
	 * 根据活动，及活动的参赛情况 获取有效投资排行榜（有效投资额：参赛后的投资）
	 * @param activityId
	 * @return
	 */
	List<Map<String, Object>> getOnedayInvestmentRankByActivityId(@Param("activityId") Long activityId, @Param("date") Date date, @Param("limit") int limit);

	/**
	 * 获取时间段内某队有效投资额（时间段：date(startDate)<=date(investmentDt)<=date(endDate)）（有效投资额：参赛后的投资）
	 * @param side
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Double getSideInvestmentAmount(@Param("activityId") Long activityId, @Param("side") String side, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 根据activityId获取参赛记录
	 * @param activityId
	 * @return
	 */
	List<JoinMatchRecord> findListByActivityId(@Param("activityId") Long activityId);

	/**
	 * 获取用户某活动时间段内有效投资额（时间段：date(startDate)<=date(investmentDt)<=date(endDate)）（有效投资额：参赛后的投资）
	 * @param acticityId
	 * @param accountId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Double getUserInvestmentAmount(@Param("activityId") Long acticityId, @Param("accountId") Long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 根据activityId,accountId获取参赛记录
	 * @param activityId
	 * @param accountId
	 * @return
	 */
	JoinMatchRecord getByActivityIdAndAccountId(@Param("activityId") Long activityId, @Param("accountId") Long accountId);

	/**
	 * 根据活动，及活动的参赛情况 获取时间段内有效投资排行榜（有效投资额：参赛后的投资）
	 * @param activityId
	 * @return
	 */
	List<Map<String, Object>> getInvestmentRankByActivityId(@Param("activityId") Long activityId, @Param("side") String side,
															@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("limit") int limit);
	
}