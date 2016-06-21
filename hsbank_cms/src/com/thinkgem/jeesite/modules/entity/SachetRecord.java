/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 香囊记录Entity
 * @author ydt
 * @version 2016-05-24
 */
public class SachetRecord extends DataEntity<SachetRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long activityId;		// 活动编号
	private Long accountId;		// 用户编号
	private Integer changeValue;		// 变化数量
	private String changeReason;		// 变化原因
	private String opTerm;		// 操作终端
	private Date opDt;		// 操作时间
	private String prize;		// 奖品
	
	public SachetRecord() {
		super();
	}

	public SachetRecord(String id){
		super(id);
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Integer getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(Integer changeValue) {
		this.changeValue = changeValue;
	}
	
	@Length(min=0, max=20, message="变化原因长度必须介于 0 和 20 之间")
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	@Length(min=0, max=2, message="操作终端长度必须介于 0 和 2 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=50, message="奖品长度必须介于 0 和 50 之间")
	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}
	
}