/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingWheelGetPrizeRecord;

/**
 * 大转盘中奖记录DAO接口
 * @author ydt
 * @version 2015-11-24
 */
@MyBatisDao
public interface MarketingWheelGetPrizeRecordDao extends CrudDao<MarketingWheelGetPrizeRecord> {

	/**
	 * 将需要失效的中将记录置为失效状态
	 */
	void invalidateRecord();

	/**
	 * 根据token将中奖奖品记录状态置为待赠送状态
	 * @param token
	 * @param accountId
	 */
	void updateStatusGetToGivingByToken(@Param("token") String token, @Param("accountId") long accountId);

	/**
	 * 根据状态查询获奖记录列表
	 * @param status
	 * @return
	 */
	List<MarketingWheelGetPrizeRecord> findListByStatus(@Param("status") String status);

	/**
	 * 更新奖励记录状态为已赠送
	 * @param id
	 * @param status
	 */
	void updateStatusToGiven(@Param("id") String id);
	
	/**
	 * 获取记录的所有信息（包括 用户名 手机号……）
	 * @param id
	 * @return
	 */
	MarketingWheelGetPrizeRecord getAllInfo(@Param("id") String id);

	/**
	 * 获取某活动奖品类型为prizeType的获奖记录列表
	 * @param status
	 * @param limit
	 * @return
	 */
	List<MarketingWheelGetPrizeRecord> findGotListByActivityIdAndPrizeType(@Param("activityId") Long activityId, @Param("prizeType") String prizeType, @Param("limit") int limit);

	/**
	 * 获取用户某活动的中奖信息
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	List<MarketingWheelGetPrizeRecord> findGotListByAccountIdAndActivityId(@Param("accountId") Long accountId, @Param("activityId") Long activityId);

	/**
	 * 获取用户某活动的奖品分页列表
	 * @param map
	 * @return
     */
	List<MarketingWheelGetPrizeRecord> findPrizeRecordPageList(Map<String, Object> map);


	/**
	 * 获取用户某活动的某个中奖信息
	 * @param accountId
	 * @param activityId
	 * @param status
     * @return
     */
	MarketingWheelGetPrizeRecord getPrizeRecordByAccountIdAndActivityId(@Param("accountId") Long accountId, @Param("activityId") Long activityId, @Param("status") String status);
}