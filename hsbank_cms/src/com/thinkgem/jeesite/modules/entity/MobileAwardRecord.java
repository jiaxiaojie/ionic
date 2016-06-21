/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 手机号中奖记录Entity
 * @author ydt
 * @version 2016-05-05
 */
public class MobileAwardRecord extends DataEntity<MobileAwardRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long activityId;		// 活动编号
	private String mobile;		// 手机号
	private Long prizeInstanceId;		// 奖品实例编号
	private Date opDt;		// 获奖时间
	private String opTerm;		// 获奖终端
	private String status;		// 状态
	private Long accountId;		// 用户编号
	private Date awardDt;		// 赠送时间
	
	public MobileAwardRecord() {
		super();
	}

	public MobileAwardRecord(String id){
		super(id);
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=20, message="手机号长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getPrizeInstanceId() {
		return prizeInstanceId;
	}

	public void setPrizeInstanceId(Long prizeInstanceId) {
		this.prizeInstanceId = prizeInstanceId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=2, message="获奖终端长度必须介于 0 和 2 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
	@Length(min=0, max=20, message="状态长度必须介于 0 和 20 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAwardDt() {
		return awardDt;
	}

	public void setAwardDt(Date awardDt) {
		this.awardDt = awardDt;
	}
	
}