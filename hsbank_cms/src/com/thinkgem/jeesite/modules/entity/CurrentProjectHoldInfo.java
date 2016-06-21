/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期产品持有信息Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectHoldInfo extends DataEntity<CurrentProjectHoldInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Long accountId;		// 投资账户编号
	private Double principal;		// 持有本金
	private Double applyRedeemPrincipal;		// 申请赎回本金
	private Double interest;		// 持有利息
	private String status;		// 状态
	private Double beginPrincipal;		// 开始 持有本金
	private Double endPrincipal;		// 结束 持有本金
	
	private Double totalProfit;		//累计收益
	
	private CurrentProjectInfo currentProjectInfo;
	private CustomerBase customerBase;
	
	
	
	

	public CustomerBase getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBase customerBase) {
		this.customerBase = customerBase;
	}

	public CurrentProjectInfo getCurrentProjectInfo() {
		return currentProjectInfo;
	}

	public void setCurrentProjectInfo(CurrentProjectInfo currentProjectInfo) {
		this.currentProjectInfo = currentProjectInfo;
	}

	public CurrentProjectHoldInfo() {
		super();
	}

	public CurrentProjectHoldInfo(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	
	public Double getApplyRedeemPrincipal() {
		return applyRedeemPrincipal;
	}

	public void setApplyRedeemPrincipal(Double applyRedeemPrincipal) {
		this.applyRedeemPrincipal = applyRedeemPrincipal;
	}
	
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Double getBeginPrincipal() {
		return beginPrincipal;
	}

	public void setBeginPrincipal(Double beginPrincipal) {
		this.beginPrincipal = beginPrincipal;
	}
	
	public Double getEndPrincipal() {
		return endPrincipal;
	}

	public void setEndPrincipal(Double endPrincipal) {
		this.endPrincipal = endPrincipal;
	}
	
	public void setDefaultValue() {
		if(principal == null){
			principal = 0D;
		}
		if(applyRedeemPrincipal == null){
			applyRedeemPrincipal = 0D;
		}
		if(interest == null){
			interest = 0D;
		}
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}



	
	
		
}