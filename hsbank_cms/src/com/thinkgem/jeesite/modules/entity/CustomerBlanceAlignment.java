/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员账户余额对齐Entity
 * @author lzb
 * @version 2015-11-03
 */
public class CustomerBlanceAlignment extends DataEntity<CustomerBlanceAlignment> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String customerName;		// 姓名
	private Double taskGoldBalance;		// task_平台账户余额
	private Double taskYeepayBalance;		// task_易宝账户余额
	private Double newGoldBalance;		// new_平台账户余额
	private Double newYeepayBalance;		// new_易宝账户余额
	private String status;		// 状态
	private Date createDt;		// 创建时间
	private Date beginCreateDt;		// 开始 创建时间
	private Date endCreateDt;		// 结束 创建时间
	private Date modifyDt;		// 修改时间
	
	public CustomerBlanceAlignment() {
		super();
	}

	public CustomerBlanceAlignment(String id){
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
	
	
	
	public Double getTaskGoldBalance() {
		return taskGoldBalance;
	}

	public void setTaskGoldBalance(Double taskGoldBalance) {
		this.taskGoldBalance = taskGoldBalance;
	}

	public Double getTaskYeepayBalance() {
		return taskYeepayBalance;
	}

	public void setTaskYeepayBalance(Double taskYeepayBalance) {
		this.taskYeepayBalance = taskYeepayBalance;
	}

	public Double getNewGoldBalance() {
		return newGoldBalance;
	}

	public void setNewGoldBalance(Double newGoldBalance) {
		this.newGoldBalance = newGoldBalance;
	}

	public Double getNewYeepayBalance() {
		return newYeepayBalance;
	}

	public void setNewYeepayBalance(Double newYeepayBalance) {
		this.newYeepayBalance = newYeepayBalance;
	}

	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public Date getBeginCreateDt() {
		return beginCreateDt;
	}

	public void setBeginCreateDt(Date beginCreateDt) {
		this.beginCreateDt = beginCreateDt;
	}

	public Date getEndCreateDt() {
		return endCreateDt;
	}

	public void setEndCreateDt(Date endCreateDt) {
		this.endCreateDt = endCreateDt;
	}
	
	
	
}