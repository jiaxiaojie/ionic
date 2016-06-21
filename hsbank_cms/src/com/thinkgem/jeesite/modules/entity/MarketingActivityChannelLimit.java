/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动渠道限制Entity
 * @author lizibo
 * @version 2015-09-09
 */
public class MarketingActivityChannelLimit extends DataEntity<MarketingActivityChannelLimit> {
	
	private static final long serialVersionUID = 1L;
	private Long activityId;		// activity_id
	private Long channelId;		// channel_id
	
	public MarketingActivityChannelLimit() {
		super();
	}

	public MarketingActivityChannelLimit(String id){
		super(id);
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
}