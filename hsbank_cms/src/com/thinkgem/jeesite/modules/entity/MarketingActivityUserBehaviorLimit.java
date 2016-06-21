/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员行为限制Entity
 * @author lizibo
 * @version 2015-09-09
 */
public class MarketingActivityUserBehaviorLimit extends DataEntity<MarketingActivityUserBehaviorLimit> {
	
	private static final long serialVersionUID = 1L;
	private Long activityCode;		// 活动编号
	private String actionType;		// 行为类
	
	public MarketingActivityUserBehaviorLimit() {
		super();
	}

	public MarketingActivityUserBehaviorLimit(String id){
		super(id);
	}

	public Long getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Long activityCode) {
		this.activityCode = activityCode;
	}
	
	@Length(min=0, max=20, message="行为类长度必须介于 0 和 20 之间")
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
}