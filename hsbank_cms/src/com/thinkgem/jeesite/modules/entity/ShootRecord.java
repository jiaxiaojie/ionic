/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 射门记录Entity
 * @author lzb
 * @version 2016-06-13
 */
public class ShootRecord extends DataEntity<ShootRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 用户
	private Long activityId;		// 活动编号
	private Date opDt;		// 操作时间
	private String opTerm;		// 射门终端
	private String isGoal;		// 是否进球(0:否，1:是)
	private String status;        //状态




	public ShootRecord(Long activityId,Long accountId,  Date opDt, String status) {
		this.accountId = accountId;
		this.activityId = activityId;
		this.opDt = opDt;
		this.status = status;
	}


	
	public ShootRecord() {
		super();
	}



	public ShootRecord(String id){
		super(id);
	}

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
	
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=2, message="射门终端长度必须介于 0 和 2 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
	@Length(min=0, max=2, message="是否进球(0:否，1:是)长度必须介于 0 和 2 之间")
	public String getIsGoal() {
		return isGoal;
	}

	public void setIsGoal(String isGoal) {
		this.isGoal = isGoal;
	}
	
}