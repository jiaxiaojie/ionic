/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MarketingBehaviorType;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingBehaviorTypeDao;

/**
 * 会员行为编码Service
 * @author lizibo
 * @version 2015-09-10
 */
@Service
@Transactional(readOnly = true)
public class MarketingBehaviorTypeService extends CrudService<MarketingBehaviorTypeDao, MarketingBehaviorType> {

	public MarketingBehaviorType get(String id) {
		return super.get(id);
	}
	
	public List<MarketingBehaviorType> findList(MarketingBehaviorType marketingBehaviorType) {
		return super.findList(marketingBehaviorType);
	}
	
	public Page<MarketingBehaviorType> findPage(Page<MarketingBehaviorType> page, MarketingBehaviorType marketingBehaviorType) {
		return super.findPage(page, marketingBehaviorType);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingBehaviorType marketingBehaviorType) {
		super.save(marketingBehaviorType);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingBehaviorType marketingBehaviorType) {
		super.delete(marketingBehaviorType);
	}
	
}