/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户接收短信时间段Entity
 * @author ydt
 * @version 2016-01-14
 */
public class ReceiveSmsMessageTime extends DataEntity<ReceiveSmsMessageTime> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账户编号
	private String startTime;		// 开始时间段
	private String endTime;		// 结束时间段
	
	public ReceiveSmsMessageTime() {
		super();
	}

	public ReceiveSmsMessageTime(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}