/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ShootRecord;
import com.thinkgem.jeesite.modules.marketing.dao.ShootRecordDao;

/**
 * 射门记录Service
 * @author lzb
 * @version 2016-06-13
 */
@Service
@Transactional(readOnly = true)
public class ShootRecordService extends CrudService<ShootRecordDao, ShootRecord> {

	public ShootRecord get(String id) {
		return super.get(id);
	}
	
	public List<ShootRecord> findList(ShootRecord shootRecord) {
		return super.findList(shootRecord);
	}
	
	public Page<ShootRecord> findPage(Page<ShootRecord> page, ShootRecord shootRecord) {
		return super.findPage(page, shootRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ShootRecord shootRecord) {
		super.save(shootRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShootRecord shootRecord) {
		super.delete(shootRecord);
	}

	public List<ShootRecord> findLastTimesList(ShootRecord shootRecord) {
		return  dao.findLastTimesList(shootRecord);
	}
}