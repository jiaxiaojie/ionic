/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectNotice;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectNoticeDao;

/**
 * 活期产品公告Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentProjectNoticeService extends CrudService<CurrentProjectNoticeDao, CurrentProjectNotice> {

	public CurrentProjectNotice get(String id) {
		return super.get(id);
	}
	
	public List<CurrentProjectNotice> findList(CurrentProjectNotice currentProjectNotice) {
		return super.findList(currentProjectNotice);
	}
	
	public Page<CurrentProjectNotice> findPage(Page<CurrentProjectNotice> page, CurrentProjectNotice currentProjectNotice) {
		page.setOrderBy(" a.publish_dt desc ");
		return super.findPage(page, currentProjectNotice);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentProjectNotice currentProjectNotice) {
		super.save(currentProjectNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentProjectNotice currentProjectNotice) {
		super.delete(currentProjectNotice);
	}
	
}