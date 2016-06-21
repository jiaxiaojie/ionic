/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 营销活动奖励记录Entity
 * @author ydt
 * @version 2015-11-12
 */
public class MarketingActivityAwardRecord extends DataEntity<MarketingActivityAwardRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long activityId;		// 活动编号
	private String behaviorCode;		// 行为编号
	private String channelId;		// 渠道编号
	private Long accountId;		// 奖励用户
	private String awardType;		// 奖励产品类型
	private Date awardDt;		// 获得奖励时间
	private String awardValue;		// 奖励值
	private String awardReason;		// 奖励说明
	private String causeType;		//奖励来源类型
	private Long causeId;			//来源编号
	private User user;		// 操作人员
	private Date beginAwardDt;		// 开始 获得奖励时间
	private Date endAwardDt;		// 结束 获得奖励时间
	
	private String customerName;		// 用户姓名
	private String customerMobile;		// 用户手机号
	
	public MarketingActivityAwardRecord() {
		super();
	}

	public MarketingActivityAwardRecord(String id){
		super(id);
	}
	
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Length(min=0, max=6, message="行为编号长度必须介于 0 和 6 之间")
	public String getBehaviorCode() {
		return behaviorCode;
	}

	public void setBehaviorCode(String behaviorCode) {
		this.behaviorCode = behaviorCode;
	}
	
	@Length(min=0, max=10, message="渠道编号长度必须介于 0 和 10 之间")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=10, message="奖励产品类型长度必须介于 0 和 10 之间")
	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAwardDt() {
		return awardDt;
	}

	public void setAwardDt(Date awardDt) {
		this.awardDt = awardDt;
	}
	
	@Length(min=0, max=50, message="奖励值长度必须介于 0 和 50 之间")
	public String getAwardValue() {
		return awardValue;
	}

	public void setAwardValue(String awardValue) {
		this.awardValue = awardValue;
	}
	
	@Length(min=0, max=100, message="奖励说明长度必须介于 0 和 100 之间")
	public String getAwardReason() {
		return awardReason;
	}

	public void setAwardReason(String awardReason) {
		this.awardReason = awardReason;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getBeginAwardDt() {
		return beginAwardDt;
	}

	public void setBeginAwardDt(Date beginAwardDt) {
		this.beginAwardDt = beginAwardDt;
	}
	
	public Date getEndAwardDt() {
		return endAwardDt;
	}

	public void setEndAwardDt(Date endAwardDt) {
		this.endAwardDt = endAwardDt;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCauseType() {
		return causeType;
	}

	public void setCauseType(String causeType) {
		this.causeType = causeType;
	}

	public Long getCauseId() {
		return causeId;
	}

	public void setCauseId(Long causeId) {
		this.causeId = causeId;
	}
}