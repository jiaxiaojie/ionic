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
import com.thinkgem.jeesite.modules.entity.MarketingActivityUserBehaviorLimit;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityUserBehaviorLimitDao;

/**
 * 会员行为限制Service
 * @author lizibo
 * @version 2015-09-10
 */
@Service
@Transactional(readOnly = true)
public class MarketingActivityUserBehaviorLimitService extends CrudService<MarketingActivityUserBehaviorLimitDao, MarketingActivityUserBehaviorLimit> {
    @Autowired
    private MarketingActivityUserBehaviorLimitDao marketingActivityUserBehaviorLimitDao;
    
	public MarketingActivityUserBehaviorLimit get(String id) {
		return super.get(id);
	}
	
	public List<MarketingActivityUserBehaviorLimit> findList(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit) {
		return super.findList(marketingActivityUserBehaviorLimit);
	}
	
	public Page<MarketingActivityUserBehaviorLimit> findPage(Page<MarketingActivityUserBehaviorLimit> page, MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit) {
		return super.findPage(page, marketingActivityUserBehaviorLimit);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit) {
		super.save(marketingActivityUserBehaviorLimit);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingActivityUserBehaviorLimit marketingActivityUserBehaviorLimit) {
		super.delete(marketingActivityUserBehaviorLimit);
	}
	
	/**
	 * 根据活动编号查询列表
	 * @param activityCode
	 * @return
	 */
	public List<MarketingActivityUserBehaviorLimit> findListByActivityCode(Long activityCode){
		return marketingActivityUserBehaviorLimitDao.findListByActivityCode(activityCode);
	}
}