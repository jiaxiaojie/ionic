/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MarketingWheelLotteryTimes;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelLotteryTimesDao;

/**
 * 大转盘抽奖次数Service
 * @author ydt
 * @version 2016-01-27
 */
@Service
@Transactional(readOnly = true)
public class MarketingWheelLotteryTimesService extends CrudService<MarketingWheelLotteryTimesDao, MarketingWheelLotteryTimes> {
	
	public MarketingWheelLotteryTimes get(String id) {
		return super.get(id);
	}
	
	public List<MarketingWheelLotteryTimes> findList(MarketingWheelLotteryTimes marketingWheelLotteryTimes) {
		return super.findList(marketingWheelLotteryTimes);
	}
	
	public Page<MarketingWheelLotteryTimes> findPage(Page<MarketingWheelLotteryTimes> page, MarketingWheelLotteryTimes marketingWheelLotteryTimes) {
		return super.findPage(page, marketingWheelLotteryTimes);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingWheelLotteryTimes marketingWheelLotteryTimes) {
		super.save(marketingWheelLotteryTimes);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingWheelLotteryTimes marketingWheelLotteryTimes) {
		super.delete(marketingWheelLotteryTimes);
	}
	
	/**
	 * 根据accountId、activityId获取抽奖次数信息
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	public MarketingWheelLotteryTimes getByAccountIdAndActivityId(Long accountId, Long activityId) {
		return dao.getByAccountIdAndActivityId(accountId, activityId);
	}

	/**
	 * 根据活动编号更新totalTimes、usedTimes
	 * @param totalTimes
	 * @param usedTimes
	 * @param acticityId
	 */
	@Transactional(readOnly = false)
	public void updateLotteryTimesByActivityId(int totalTimes, int usedTimes, Long acticityId) {
		dao.updateLotteryTimesByActivityId(totalTimes, usedTimes, acticityId);
	}

	/**
	 * 初始化抽奖次数
	 * @param accountId
	 * @param activityId
	 */
	@Transactional(readOnly = false)
	public void initLotteryTimes(Long accountId, Long activityId, Integer totalTimes) {
		if(dao.getByAccountIdAndActivityId(accountId, activityId) == null) {
			MarketingWheelLotteryTimes marketingWheelLotteryTimes = new MarketingWheelLotteryTimes();
			marketingWheelLotteryTimes.setAccountId(accountId);
			marketingWheelLotteryTimes.setActivityId(activityId);
			marketingWheelLotteryTimes.setTotalTimes(totalTimes);
			marketingWheelLotteryTimes.setUsedTimes(0);
			dao.insert(marketingWheelLotteryTimes);
		}
	}

	/**
	 * 如果数据库中没有则创建，有则直接返回
	 * @param accountId
	 * @param activityId
	 * @param totalTimes
	 * @return
	 */
	@Transactional(readOnly = false)
	public MarketingWheelLotteryTimes getMarketingWheelLotteryTimes(Long accountId, Long activityId,Integer totalTimes) {
		MarketingWheelLotteryTimes marketingWheelLotteryTimes = dao.getByAccountIdAndActivityId(accountId, activityId);
		if(marketingWheelLotteryTimes == null) {
			marketingWheelLotteryTimes = new MarketingWheelLotteryTimes();
			marketingWheelLotteryTimes.setAccountId(accountId);
			marketingWheelLotteryTimes.setActivityId(activityId);
			marketingWheelLotteryTimes.setTotalTimes(totalTimes);
			marketingWheelLotteryTimes.setUsedTimes(0);
			dao.insert(marketingWheelLotteryTimes);

			marketingWheelLotteryTimes = dao.getByAccountIdAndActivityId(accountId, activityId);
		}
		return marketingWheelLotteryTimes;
	}
}