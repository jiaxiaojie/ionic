/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 大转盘抽奖次数Entity
 * @author ydt
 * @version 2016-01-25
 */
public class MarketingWheelLotteryTimes extends DataEntity<MarketingWheelLotteryTimes> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 用户编号
	private Integer totalTimes;		// 总次数
	private Integer usedTimes;		// 已使用次数
	private Long activityId;		//活动编号
	
	public MarketingWheelLotteryTimes() {
		super();
	}

	public MarketingWheelLotteryTimes(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}

	public Integer getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
}