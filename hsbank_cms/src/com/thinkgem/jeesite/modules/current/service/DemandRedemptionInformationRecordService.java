/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.DemandRedemptionInformationRecord;
import com.thinkgem.jeesite.modules.current.dao.DemandRedemptionInformationRecordDao;

/**
 * 活期赎回信息记录Service
 * @author huangyuchen
 * @version 2016-04-11
 */
@Service
@Transactional(readOnly = true)
public class DemandRedemptionInformationRecordService extends CrudService<DemandRedemptionInformationRecordDao, DemandRedemptionInformationRecord> {

	public DemandRedemptionInformationRecord get(String id) {
		return super.get(id);
	}
	
	public List<DemandRedemptionInformationRecord> findList(DemandRedemptionInformationRecord demandRedemptionInformationRecord) {
		return super.findList(demandRedemptionInformationRecord);
	}
	
	public Page<DemandRedemptionInformationRecord> findPage(Page<DemandRedemptionInformationRecord> page, DemandRedemptionInformationRecord demandRedemptionInformationRecord) {
		return super.findPage(page, demandRedemptionInformationRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(DemandRedemptionInformationRecord demandRedemptionInformationRecord) {
		super.save(demandRedemptionInformationRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(DemandRedemptionInformationRecord demandRedemptionInformationRecord) {
		super.delete(demandRedemptionInformationRecord);
	}
	
}