/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动现金奖励记录Entity
 * @author ydt
 * @version 2016-01-18
 */
public class MarketingMoneyAwardRecord extends DataEntity<MarketingMoneyAwardRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 用户编号
	private Double amount;		// 金额
	private String parameters;		// 参数
	private String status;		// 状态
	private Date createDt;		// 创建时间
	private Date finishDt;		// 完成时间
	private String beginAmount;		// 开始 金额
	private String endAmount;		// 结束 金额
	
	public MarketingMoneyAwardRecord() {
		super();
	}

	public MarketingMoneyAwardRecord(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
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
	public Date getFinishDt() {
		return finishDt;
	}

	public void setFinishDt(Date finishDt) {
		this.finishDt = finishDt;
	}
	
	public String getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(String beginAmount) {
		this.beginAmount = beginAmount;
	}
	
	public String getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(String endAmount) {
		this.endAmount = endAmount;
	}
		
}