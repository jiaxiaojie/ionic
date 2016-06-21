/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 双11活动手动送现金Entity
 * @author lzb
 * @version 2015-11-03
 */
public class CustomerDoubleElevenActivity extends DataEntity<CustomerDoubleElevenActivity> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String customerName;		// 姓名
	private Double amount;		// 金额
	private String changeReason;		// 变更原因
	private String status;		// 状态
	private Date opDt;		// 操作时间
	
	public CustomerDoubleElevenActivity() {
		super();
	}

	public CustomerDoubleElevenActivity(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Length(min=0, max=300, message="变更原因长度必须介于 0 和 300 之间")
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
}