/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.SachetRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 香囊记录DAO接口
 * @author ydt
 * @version 2016-05-24
 */
@MyBatisDao
public interface SachetRecordDao extends CrudDao<SachetRecord> {

	/**
	 * 获取用户某天领取香囊次数
	 * @param activityId
	 * @param accountId
	 * @param date
	 * @return
	 */
	int getGetSachetCountByAccountIdAndDate(@Param("activityId") Long activityId, @Param("accountId") Long accountId, @Param("date") Date date);

	/**
	 * 获取用户剩余香囊数量
	 * @param activityId
	 * @param accountId
	 * @return
	 */
	int getSachetCountByAccountId(@Param("activityId") Long activityId, @Param("accountId") Long accountId);

	/**
	 * 获取中奖榜单
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String,Object>> findGetPrizeList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("amount") double amount);
}