/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MobileAwardRecord;
import com.thinkgem.jeesite.modules.marketing.dao.MobileAwardRecordDao;

/**
 * 手机号中奖记录Service
 * @author ydt
 * @version 2016-05-05
 */
@Service
@Transactional(readOnly = true)
public class MobileAwardRecordService extends CrudService<MobileAwardRecordDao, MobileAwardRecord> {

	public MobileAwardRecord get(String id) {
		return super.get(id);
	}
	
	public List<MobileAwardRecord> findList(MobileAwardRecord mobileAwardRecord) {
		return super.findList(mobileAwardRecord);
	}
	
	public Page<MobileAwardRecord> findPage(Page<MobileAwardRecord> page, MobileAwardRecord mobileAwardRecord) {
		return super.findPage(page, mobileAwardRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(MobileAwardRecord mobileAwardRecord) {
		super.save(mobileAwardRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(MobileAwardRecord mobileAwardRecord) {
		super.delete(mobileAwardRecord);
	}

	/**
	 * 根据activityId、mobile、status获取奖励记录
	 * @param activityId
	 * @param mobile
	 * @param status 可为空
	 * @return
	 */
	public MobileAwardRecord getByActivityIdAndMobileAndStatus(Long activityId, String mobile, String status) {
		return dao.getByActivityIdAndMobileAndStatus(activityId, mobile, status);
	}
}