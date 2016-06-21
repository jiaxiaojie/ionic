/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingActivityUserBehaviorLimit;

/**
 * 会员行为限制DAO接口
 * @author lizibo
 * @version 2015-09-09
 */
@MyBatisDao
public interface MarketingActivityUserBehaviorLimitDao extends CrudDao<MarketingActivityUserBehaviorLimit> {
	
	/**
	 * 批量插入会员行为限制
	 * @param list
	 * @return
	 */
	public int insertBatch(List<MarketingActivityUserBehaviorLimit> list);
	
	/**
	 * 根据活动编号删除
	 * @param activityCode
	 * @return
	 */
	public int deleteByActivityCode(Long activityCode);
	
	/**
	 * 根据活动编号查询列表
	 * @param activityCode
	 * @return
	 */
	public List<MarketingActivityUserBehaviorLimit> findListByActivityCode(Long activityCode);
	
}