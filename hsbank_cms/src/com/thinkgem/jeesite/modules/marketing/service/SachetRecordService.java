/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.SachetRecord;
import com.thinkgem.jeesite.modules.marketing.dao.SachetRecordDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 香囊记录Service
 * @author ydt
 * @version 2016-05-31
 */
@Service
@Transactional(readOnly = true)
public class SachetRecordService extends CrudService<SachetRecordDao, SachetRecord> {

	/**
	 * 获取用户某天领取香囊次数
	 * @param activityId
	 * @param accountId
	 * @param date
	 * @return
	 */
	public int getGetSachetCountByAccountIdAndDate(Long activityId, Long accountId, Date date) {
		return dao.getGetSachetCountByAccountIdAndDate(activityId, accountId, date);
	}

	/**
	 * 获取用户剩余香囊数量
	 * @param activityId
	 * @param accountId
	 * @return
	 */
	public int getSachetCountByAccountId(Long activityId, Long accountId) {
		return dao.getSachetCountByAccountId(activityId,accountId);
	}

	/**
	 * 获取中奖榜单
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String,Object>> findGetPrizeList(Date startDate, Date endDate, double amount) {
		return dao.findGetPrizeList(startDate, endDate,amount);
	}
}