/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MarketingActivityChannelLimit;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityChannelLimitDao;

/**
 * 活动渠道限制Service
 * @author lizibo
 * @version 2015-09-10
 */
@Service
@Transactional(readOnly = true)
public class MarketingActivityChannelLimitService extends CrudService<MarketingActivityChannelLimitDao, MarketingActivityChannelLimit> {
    @Autowired
    private MarketingActivityChannelLimitDao marketingActivityChannelLimitDao;
	public MarketingActivityChannelLimit get(String id) {
		return super.get(id);
	}
	
	public List<MarketingActivityChannelLimit> findList(MarketingActivityChannelLimit marketingActivityChannelLimit) {
		return super.findList(marketingActivityChannelLimit);
	}
	
	public Page<MarketingActivityChannelLimit> findPage(Page<MarketingActivityChannelLimit> page, MarketingActivityChannelLimit marketingActivityChannelLimit) {
		return super.findPage(page, marketingActivityChannelLimit);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingActivityChannelLimit marketingActivityChannelLimit) {
		super.save(marketingActivityChannelLimit);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingActivityChannelLimit marketingActivityChannelLimit) {
		super.delete(marketingActivityChannelLimit);
	}
	
	/**
	 * 根据活动编号查询列表
	 * @param activityId
	 * @return
	 */
	public List<MarketingActivityChannelLimit> findListByActivityId(Long activityId) {
		return marketingActivityChannelLimitDao.findListByActivityId(activityId);
	}
	
}