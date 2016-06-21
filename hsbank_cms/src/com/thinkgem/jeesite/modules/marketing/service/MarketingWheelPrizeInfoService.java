/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInfo;
import com.thinkgem.jeesite.modules.entity.MarketingWheelPrizeInstance;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelGetPrizeRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelPrizeInfoDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelPrizeInstanceDao;

/**
 * 大转盘奖品信息Service
 * @author ydt
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class MarketingWheelPrizeInfoService extends CrudService<MarketingWheelPrizeInfoDao, MarketingWheelPrizeInfo> {

	@Autowired
	private MarketingWheelPrizeInstanceDao marketingWheelPrizeInstanceDao;
	@Autowired
	private MarketingWheelGetPrizeRecordDao marketingWheelGetPrizeRecordDao;
	
	public MarketingWheelPrizeInfo get(String id) {
		return super.get(id);
	}
	
	public List<MarketingWheelPrizeInfo> findList(MarketingWheelPrizeInfo marketingWheelPrizeInfo) {
		return super.findList(marketingWheelPrizeInfo);
	}
	
	public Page<MarketingWheelPrizeInfo> findPage(Page<MarketingWheelPrizeInfo> page, MarketingWheelPrizeInfo marketingWheelPrizeInfo) {
		return super.findPage(page, marketingWheelPrizeInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingWheelPrizeInfo marketingWheelPrizeInfo) {
		super.save(marketingWheelPrizeInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingWheelPrizeInfo marketingWheelPrizeInfo) {
		super.delete(marketingWheelPrizeInfo);
	}

	/**
	 * 获得所有奖品实例数量
	 * @return
	 */
	public long getAllPrizeInstanceCount(Long activityId) {
		return marketingWheelPrizeInstanceDao.getAllPrizeInstanceCountByActivityId(activityId);
	}
	
	/**
	 * 是否已经生成奖品实例
	 * @return
	 */
	public boolean hasMadePrizeInstance(Long activityId) {
		return marketingWheelPrizeInstanceDao.getAllPrizeInstanceCountByActivityId(activityId) == 0 ? false : true;
	}
	
	/**
	 * 生成奖品实例
	 * @return
	 */
	@Transactional(readOnly = false)
	public void makePrizeInstance(Long activityId) {
		if(hasMadePrizeInstance(activityId)) {
			throw new ServiceException("cannot make parize instance again.");
		}
		List<MarketingWheelPrizeInfo> marketingWheelPrizeInfoList = dao.findListByActivityId(activityId);
		List<MarketingWheelPrizeInstance> marketingWheelPrizeInstanceList = new ArrayList<MarketingWheelPrizeInstance>();
		for(MarketingWheelPrizeInfo marketingWheelPrizeInfo : marketingWheelPrizeInfoList) {
			for(int i = 0; i < marketingWheelPrizeInfo.getNumber(); i++) {
				MarketingWheelPrizeInstance marketingWheelPrizeInstance = new MarketingWheelPrizeInstance();
				marketingWheelPrizeInstance.setPrizeId(Long.parseLong(marketingWheelPrizeInfo.getId()));
				marketingWheelPrizeInstance.setStatus(MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_NORMAL);
				marketingWheelPrizeInstanceList.add(marketingWheelPrizeInstance);
			}
		}
		Collections.shuffle(marketingWheelPrizeInstanceList);
		marketingWheelPrizeInstanceDao.insertBatch(marketingWheelPrizeInstanceList);
	}
	/**
	 * 根据活动编号、状态获取指定数量的实例列表
	 * @param activityId
	 * @param status
	 * @param limit
	 * @return
	 */
	public List<MarketingWheelPrizeInstance> findListByActivityIdAndStatus(Long activityId, String status, Integer limit) {
		return marketingWheelPrizeInstanceDao.findListByActivityIdAndStatus(activityId, status, limit);
	}

	/**
	 * 更改奖品实例状态
	 * @param prizeInstanceId
	 * @param status
	 */
	@Transactional(readOnly = false)
	public void updateInstanceStatus(String prizeInstanceId, String status) {
		marketingWheelPrizeInstanceDao.updateStatus(prizeInstanceId, status);
	}

	/**
	 * 将中奖超时且未注册的token置为失效状态
	 * 		1.将奖品实例置为解锁状态
	 * 		2.将中将记录置为失效状态
	 */
	@Transactional(readOnly = false)
	public void invalidateWheelToken() {
		marketingWheelPrizeInstanceDao.unlockInstance();
		marketingWheelGetPrizeRecordDao.invalidateRecord();
	}

	/**
	 * 根据type，value获取奖品列表
	 * @param type
	 * @param value
	 */
	public MarketingWheelPrizeInfo getByTypeAndValue(String type, String value) {
		return dao.getByTypeAndValue(type, value);
	}

	/**
	 * 根据奖品实例id获取奖品信息
	 * @param instanceId
	 * @return
	 */
	public MarketingWheelPrizeInfo getByInstanceId(long instanceId) {
		return dao.getByInstanceId(instanceId);
	}

	/**
	 * 获取活动的默认奖品
	 * @param activityId
	 * @return
	 */
	public MarketingWheelPrizeInfo getDefaultPrize(Long activityId) {
		return dao.getDefaultPrize(activityId);
	}

	/**
	 * 将此奖品设置为活动的默认奖品
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void setDefaultPrize(String id) {
		MarketingWheelPrizeInfo marketingWheelPrizeInfo = dao.get(id);
		dao.updateIsDefaultByActivityId(marketingWheelPrizeInfo.getActivityId(), MarketConstant.IS_DEFAULT_PRIZE_NOT);
		dao.updateIsDefault(id, MarketConstant.IS_DEFAULT_PRIZE_YES);
	}

	/**
	 * 插入奖品实例
	 * @param marketingWheelPrizeInstance
	 * @return
	 */
	@Transactional(readOnly = false)
	public void insertPrizeInstance(MarketingWheelPrizeInstance marketingWheelPrizeInstance) {
		marketingWheelPrizeInstanceDao.insert(marketingWheelPrizeInstance);
	}
}