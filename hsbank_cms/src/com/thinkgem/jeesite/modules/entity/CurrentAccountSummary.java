/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期账户总览Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentAccountSummary extends DataEntity<CurrentAccountSummary> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账户编号
	private Double totalInvestmentMoney;		// 累计投资金额
	private Double totalGetInterest;		// 累计获取利息
	private Double totalRedeemPrincipal;		// 累计赎回本金
	private Double totalRedeemInterest;		// 累计赎回利息
	private Double currentPrincipal;		// 当前持有本金
	private Date createDt;		// 创建时间
	private Date modifyDt;		// 修改时间
	private Double beginTotalInvestmentMoney;		// 开始 累计投资金额
	private Double endTotalInvestmentMoney;		// 结束 累计投资金额
	private Double beginCurrentPrincipal;		// 开始 当前持有本金
	private Double endCurrentPrincipal;		// 结束 当前持有本金
	
	public CurrentAccountSummary() {
		super();
	}

	public CurrentAccountSummary(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Double getTotalInvestmentMoney() {
		return totalInvestmentMoney;
	}

	public void setTotalInvestmentMoney(Double totalInvestmentMoney) {
		this.totalInvestmentMoney = totalInvestmentMoney;
	}
	
	public Double getTotalGetInterest() {
		return totalGetInterest;
	}

	public void setTotalGetInterest(Double totalGetInterest) {
		this.totalGetInterest = totalGetInterest;
	}
	
	public Double getTotalRedeemPrincipal() {
		return totalRedeemPrincipal;
	}

	public void setTotalRedeemPrincipal(Double totalRedeemPrincipal) {
		this.totalRedeemPrincipal = totalRedeemPrincipal;
	}
	
	public Double getTotalRedeemInterest() {
		return totalRedeemInterest;
	}

	public void setTotalRedeemInterest(Double totalRedeemInterest) {
		this.totalRedeemInterest = totalRedeemInterest;
	}
	
	public Double getCurrentPrincipal() {
		return currentPrincipal;
	}

	public void setCurrentPrincipal(Double currentPrincipal) {
		this.currentPrincipal = currentPrincipal;
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
	
	public Double getBeginTotalInvestmentMoney() {
		return beginTotalInvestmentMoney;
	}

	public void setBeginTotalInvestmentMoney(Double beginTotalInvestmentMoney) {
		this.beginTotalInvestmentMoney = beginTotalInvestmentMoney;
	}
	
	public Double getEndTotalInvestmentMoney() {
		return endTotalInvestmentMoney;
	}

	public void setEndTotalInvestmentMoney(Double endTotalInvestmentMoney) {
		this.endTotalInvestmentMoney = endTotalInvestmentMoney;
	}
		
	public Double getBeginCurrentPrincipal() {
		return beginCurrentPrincipal;
	}

	public void setBeginCurrentPrincipal(Double beginCurrentPrincipal) {
		this.beginCurrentPrincipal = beginCurrentPrincipal;
	}
	
	public Double getEndCurrentPrincipal() {
		return endCurrentPrincipal;
	}

	public void setEndCurrentPrincipal(Double endCurrentPrincipal) {
		this.endCurrentPrincipal = endCurrentPrincipal;
	}
	
	public void setDefaultValue() {
		if(totalGetInterest == null){
			totalGetInterest = 0D;
		}
		if(totalRedeemPrincipal == null){
			totalRedeemPrincipal = 0D;
		}
		if(totalRedeemInterest == null){
			totalRedeemInterest = 0D;
		}
	}
		
}