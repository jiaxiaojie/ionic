/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.MarketingActivityChannelLimit;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.MarketingActivityUserBehaviorLimit;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityChannelLimitDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityUserBehaviorLimitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 营销活动Service
 * 
 * @author lizibo
 * @version 2015-09-09
 */
@Service
@Transactional(readOnly = true)
public class MarketingActivityInfoService extends
		CrudService<MarketingActivityInfoDao, MarketingActivityInfo> {
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;
	@Autowired
	private MarketingActivityChannelLimitDao marketingActivityChannelLimitDao;
	@Autowired
	private MarketingActivityUserBehaviorLimitDao marketingActivityUserBehaviorLimitDao;

	public MarketingActivityInfo get(String id) {
		return super.get(id);
	}

	public List<MarketingActivityInfo> findList(
			MarketingActivityInfo marketingActivityInfo) {
		return super.findList(marketingActivityInfo);
	}

	public Page<MarketingActivityInfo> findPage(
			Page<MarketingActivityInfo> page,
			MarketingActivityInfo marketingActivityInfo) {
		return super.findPage(page, marketingActivityInfo);
	}
	
	/**
	 * 审核
	 * @param marketingActivityInfo
	 */
	@Transactional(readOnly = false)
	public void review(MarketingActivityInfo marketingActivityInfo) {
		marketingActivityInfoDao.update(marketingActivityInfo);
	}

	@Transactional(readOnly = false)
	public void save(MarketingActivityInfo marketingActivityInfo) {
		if (marketingActivityInfo.getActicityId() != null
				&& !"".equals(marketingActivityInfo.getActicityId())) {
			marketingActivityInfoDao.update(marketingActivityInfo);
		} else {
			marketingActivityInfoDao.insert(marketingActivityInfo);
		}
		Long acticityId = marketingActivityInfo.getActicityId();
		// 维护活动渠道限制数据
		List<String> channelIdList = marketingActivityInfo.getChannelIdList();
		if (channelIdList != null && channelIdList.size() > 0) {
			this.channelLimitData(channelIdList, acticityId);
		}
		// 维护会员行为限制数据
		List<String> actionTypeList = marketingActivityInfo.getActionTypeList();
		if (actionTypeList != null && actionTypeList.size() > 0) {
			this.userBehaviorLimitData(actionTypeList, acticityId);
		}

	}

	/**
	 * 维护活动渠道限制数据
	 * 
	 * @param channelIdList
	 * @param acticityId
	 */
	public void channelLimitData(List<String> channelIdList, Long acticityId) {
		List<MarketingActivityChannelLimit> activityChannelLimitList = new ArrayList<MarketingActivityChannelLimit>();
		for (String channelId : channelIdList) {
			MarketingActivityChannelLimit activityChannelLimit = new MarketingActivityChannelLimit();
			activityChannelLimit.setActivityId(acticityId);
			activityChannelLimit.setChannelId(NumberUtil.toLong(channelId, 0L));
			activityChannelLimitList.add(activityChannelLimit);
		}
		// 先删除（根据活动编号）
		marketingActivityChannelLimitDao.deleteByActivityId(acticityId);
		// 批量插入
		marketingActivityChannelLimitDao.insertBatch(activityChannelLimitList);
	}

	/**
	 * 维护会员行为限制数据
	 * 
	 * @param actionTypeList
	 * @param acticityId
	 */
	public void userBehaviorLimitData(List<String> actionTypeList,
			Long acticityId) {
		List<MarketingActivityUserBehaviorLimit> activityUserBehaviorLimitList = new ArrayList<MarketingActivityUserBehaviorLimit>();
		for (String actionType : actionTypeList) {
			MarketingActivityUserBehaviorLimit activityUserBehaviorLimit = new MarketingActivityUserBehaviorLimit();
			activityUserBehaviorLimit.setActivityCode(acticityId);
			activityUserBehaviorLimit.setActionType(actionType);
			activityUserBehaviorLimitList.add(activityUserBehaviorLimit);
		}
		// 先删除（根据活动编号）
		marketingActivityUserBehaviorLimitDao.deleteByActivityCode(acticityId);
		// 批量插入
		marketingActivityUserBehaviorLimitDao
				.insertBatch(activityUserBehaviorLimitList);
	}

	@Transactional(readOnly = false)
	public void delete(MarketingActivityInfo marketingActivityInfo) {
		super.delete(marketingActivityInfo);
		Long acticityId = marketingActivityInfo.getActicityId();
		// 删除活动渠道限制（根据活动编号）
		marketingActivityChannelLimitDao.deleteByActivityId(acticityId);
		// 删除会员行为限制（根据活动编号）
		marketingActivityUserBehaviorLimitDao.deleteByActivityCode(acticityId);
	}

	public List<MarketingActivityInfo> findAllList() {
		return dao.findAllList(new MarketingActivityInfo());
	}

	/**
	 * 根据关联实现类名获取活动信息
	 * @param bizClassName
	 * @return
	 */
	public MarketingActivityInfo getByBizClassName(String bizClassName) {
		return dao.getByBizClassName(bizClassName);
	}
	
	/**
	 * 获取状态为status的活动列表
	 * @param status
	 * @return
	 */
	public List<MarketingActivityInfo> findListByStatus(String status) {
		return dao.findListByStatus(status);
	}

}