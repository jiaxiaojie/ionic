/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;

/**
 * 营销活动奖励记录DAO接口
 * @author ydt
 * @version 2015-11-12
 */
@MyBatisDao
public interface MarketingActivityAwardRecordDao extends CrudDao<MarketingActivityAwardRecord> {

	/**
	 * 查询用户邀请好友的收益
	 * @param map
	 * @return
	 */
	List<MarketingActivityAwardRecord> findCustomerEarningCauseByFriendPage(Map<String, Object> map);
	
	/**
	 * 选择查询好友参加活动福利
	 * @param map
	 * @return
	 */
	List<MarketingActivityAwardRecord> findListByFriendSelected(Map<String, Object> map);

	
	

	/**
	 * 获取用户某一活动得到的奖励列表
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	List<MarketingActivityAwardRecord> findListByAccountIdAndActivityId(@Param("accountId") Long accountId, @Param("activityId") Long activityId);

	/**
	 * 获取用户某一活动因某一好友得到奖励的次数
	 * @param accountId
	 * @param activityId
	 * @param friendId
	 * @return
	 */
	int getCountByActivityIdAndFriendId(@Param("accountId") long accountId, @Param("activityId") long activityId, @Param("friendId") long friendId);
	/**
	 * 查询指定用户在指定活动获取到的奖励次数
	 * @return
	 */
	int getCountByAccountIdAndActivityId(HashMap<String, Object> params);
	
	/**
	 * 查询指定用户在指定活动获取到的奖励次数
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	int getCountByAccountIdAndActivityId(@Param("accountId") long accountId, @Param("activityId") long activityId);
	

	/**
	 * 获取好友奖励值
	 * @param map
	 * @return
	 */
	public Double getFriendsTotalAwardValue(Map<String, Object> map);
	
	/**
	 * 统计奖励总额
	 * @param accountId
	 * @param awardType
	 * @return
	 */
	public Double getSumAwardValueByAccountId(@Param("accountId") long accountId, @Param("awardType") String awardType);
}