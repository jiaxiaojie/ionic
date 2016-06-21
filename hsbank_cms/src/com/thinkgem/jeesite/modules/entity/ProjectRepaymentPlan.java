/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 还款记录Entity
 * @author yangtao
 * @version 2015-07-10
 */
public class ProjectRepaymentPlan extends DataEntity<ProjectRepaymentPlan> {
	
	private static final long serialVersionUID = 1L;
	private Long planId;		// 还款计划流水
	private Long projectId;		// 项目流水号
	private Date planDate;		// 还款日期
	private String planMoney;		// 还款金额
	private String principal;		// 本金
	private String interest;		// 利息
	private Double rateTicketInterest;	//加息券利息
	private String remainingPrincipal;		// 剩余本金
	private String remainingPrincipalInterest;		// 剩余本息
	
	private String status;			//还款状态
	
	private String queryProjectId;
	
	
	public ProjectRepaymentPlan() {
		super();
	}

	public ProjectRepaymentPlan(String id){
		super(id);
	}

	@NotNull(message="还款计划流水不能为空")
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	public String getPlanMoney() {
		return planMoney;
	}

	public void setPlanMoney(String planMoney) {
		this.planMoney = planMoney;
	}
	
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	
	public Double getRateTicketInterest() {
		return rateTicketInterest;
	}

	public void setRateTicketInterest(Double rateTicketInterest) {
		this.rateTicketInterest = rateTicketInterest;
	}

	public String getRemainingPrincipal() {
		return remainingPrincipal;
	}

	public void setRemainingPrincipal(String remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}

	public String getRemainingPrincipalInterest() {
		return remainingPrincipalInterest;
	}

	public void setRemainingPrincipalInterest(String remainingPrincipalInterest) {
		this.remainingPrincipalInterest = remainingPrincipalInterest;
	}

	public String getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(String queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}