/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAttachment;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAttachmentDao;

/**
 * 活动关联附件Service
 * @author lizibo
 * @version 2015-09-09
 */
@Service
@Transactional(readOnly = true)
public class MarketingActivityAttachmentService extends CrudService<MarketingActivityAttachmentDao, MarketingActivityAttachment> {

	public MarketingActivityAttachment get(String id) {
		return super.get(id);
	}
	
	public List<MarketingActivityAttachment> findList(MarketingActivityAttachment marketingActivityAttachment) {
		return super.findList(marketingActivityAttachment);
	}
	
	public Page<MarketingActivityAttachment> findPage(Page<MarketingActivityAttachment> page, MarketingActivityAttachment marketingActivityAttachment) {
		return super.findPage(page, marketingActivityAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingActivityAttachment marketingActivityAttachment) {
		super.save(marketingActivityAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingActivityAttachment marketingActivityAttachment) {
		super.delete(marketingActivityAttachment);
	}
	
}