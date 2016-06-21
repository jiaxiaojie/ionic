/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.MarketingShareRecord;
import com.thinkgem.jeesite.modules.entity.MarketingWheelLotteryTimes;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingShareRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelLotteryTimesDao;

/**
 * 营销活动分享记录Service
 * @author lzb
 * @version 2016-02-26
 */
@Service
@Transactional(readOnly = true)
public class MarketingShareRecordService extends CrudService<MarketingShareRecordDao, MarketingShareRecord> {
	@Autowired
	private MarketingWheelLotteryTimesDao marketingWheelLotteryTimesDao;

	public MarketingShareRecord get(String id) {
		return super.get(id);
	}
	
	public List<MarketingShareRecord> findList(MarketingShareRecord marketingShareRecord) {
		return super.findList(marketingShareRecord);
	}
	
	public Page<MarketingShareRecord> findPage(Page<MarketingShareRecord> page, MarketingShareRecord marketingShareRecord) {
		return super.findPage(page, marketingShareRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingShareRecord marketingShareRecord) {
		super.save(marketingShareRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingShareRecord marketingShareRecord) {
		super.delete(marketingShareRecord);
	}
	
	/**
	 * 判断当天是否分享过
	 * @param accountId
	 * @param acticityId
	 * @return
	 */
	public boolean hasShared(Long accountId, Long acticityId){
		boolean hasShare = false;
		if(accountId == null){
			return false;
		}
		hasShare = dao.hasShared(accountId, acticityId, DateUtils.dateFormateDayOfTheBeginTime(new Date()), DateUtils.dateFormateDayOfTheLastTime(new Date()));
		return hasShare;
	}
	
	/**
	 * 添加分享记录
	 * @param accountId
	 * @param activityId
	 * @param channelId
	 * @param acticityCode
	 * @param shareReason
	 */
	@Transactional(readOnly = false)
	public void addShareRecord(Long accountId, Long activityId,
			String channelId, String acticityCode, String shareReason,Integer initTotalTimes) {
		//判断当天是否分享过
		if(!hasShared(accountId, activityId)){
			//更新抽奖次数
			this.updateLotteryTimes(accountId, activityId, initTotalTimes,1);
		}
		MarketingShareRecord shareRecord = new MarketingShareRecord();
		shareRecord.setAccountId(accountId);
		shareRecord.setActivityId(activityId);
		shareRecord.setChannelId(channelId);
		shareRecord.setShareDt(new Date());
		shareRecord.setShareReason(shareReason);
		shareRecord.setActicityCode(acticityCode);
		dao.insert(shareRecord);
	}
	
	/**
	 * 更新抽奖次数
	 * @param accountId
	 * @param activityId
	 * @param times
	 */
	public void updateLotteryTimes(Long accountId, Long activityId,int initTotalTimes, int times){
		MarketingWheelLotteryTimes mLotteryTimes = marketingWheelLotteryTimesDao.getByAccountIdAndActivityId(accountId, activityId);
		if(mLotteryTimes != null){
			marketingWheelLotteryTimesDao.updateTotalTimesByAccountIdAndActivityId(accountId, activityId, times);
		}else{
			MarketingWheelLotteryTimes mWheelLotteryTimes = new MarketingWheelLotteryTimes();
			mWheelLotteryTimes.setAccountId(accountId);
			mWheelLotteryTimes.setTotalTimes(initTotalTimes + times);
			mWheelLotteryTimes.setUsedTimes(0);
			mWheelLotteryTimes.setActivityId(activityId);
			marketingWheelLotteryTimesDao.insert(mWheelLotteryTimes);
		}
	}
	
}