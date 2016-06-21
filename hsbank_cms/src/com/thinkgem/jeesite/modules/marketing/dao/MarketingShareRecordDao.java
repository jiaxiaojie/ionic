/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingShareRecord;

/**
 * 营销活动分享记录DAO接口
 * @author lzb
 * @version 2016-02-26
 */
@MyBatisDao
public interface MarketingShareRecordDao extends CrudDao<MarketingShareRecord> {
	
	/**
	 * 指定时间是否进行过分享
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	boolean hasShared(@Param("accountId") Long accountId, @Param("activityId") Long acticityId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
}