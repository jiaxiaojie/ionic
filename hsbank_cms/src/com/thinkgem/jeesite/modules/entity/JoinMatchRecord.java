/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 参赛记录Entity
 * @author ydt
 * @version 2016-04-20
 */
public class JoinMatchRecord extends DataEntity<JoinMatchRecord> {
	
	private static final long serialVersionUID = 1L;
	private String side;		// 队伍
	private Long accountId;		// 用户
	private Long activityId;		// 活动编号
	private Date opDt;		// 参赛时间
	private String opTerm;	//参数终端
	
	public JoinMatchRecord() {
		super();
	}

	public JoinMatchRecord(String id){
		super(id);
	}

	@Length(min=0, max=20, message="队伍长度必须介于 0 和 20 之间")
	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
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

	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
}