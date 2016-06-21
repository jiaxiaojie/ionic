/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MarketingActivityOpHis;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityOpHisDao;

/**
 * 营销活动操作流水Service
 * @author lizibo
 * @version 2015-09-09
 */
@Service
@Transactional(readOnly = true)
public class MarketingActivityOpHisService extends CrudService<MarketingActivityOpHisDao, MarketingActivityOpHis> {

	public MarketingActivityOpHis get(String id) {
		return super.get(id);
	}
	
	public List<MarketingActivityOpHis> findList(MarketingActivityOpHis marketingActivityOpHis) {
		return super.findList(marketingActivityOpHis);
	}
	
	public Page<MarketingActivityOpHis> findPage(Page<MarketingActivityOpHis> page, MarketingActivityOpHis marketingActivityOpHis) {
		return super.findPage(page, marketingActivityOpHis);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingActivityOpHis marketingActivityOpHis) {
		super.save(marketingActivityOpHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingActivityOpHis marketingActivityOpHis) {
		super.delete(marketingActivityOpHis);
	}
	
}