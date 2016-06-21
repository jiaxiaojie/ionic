/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingActivityChannelLimit;

/**
 * 活动渠道限制DAO接口
 * @author lizibo
 * @version 2015-09-09
 */
@MyBatisDao
public interface MarketingActivityChannelLimitDao extends CrudDao<MarketingActivityChannelLimit> {
	
	/**
	 * 批量插入活动渠道限制
	 * @param list
	 * @return
	 */
	public int insertBatch(List<MarketingActivityChannelLimit> list);
	
	/**
	 * 根据活动编号删除
	 * @param activityId
	 * @return
	 */
	public int deleteByActivityId(Long activityId);
	
	/**
	 * 根据活动编号查询列表
	 * @param activityId
	 * @return
	 */
	public List<MarketingActivityChannelLimit> findListByActivityId(Long activityId);

}