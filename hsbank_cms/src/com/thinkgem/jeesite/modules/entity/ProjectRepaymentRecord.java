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
 * @version 2015-06-24
 */
public class ProjectRepaymentRecord extends DataEntity<ProjectRepaymentRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long recordId;		// 还款流水号
	private Long projectId;		// 项目流水号
	private Long planId;		// 对应计划流水号
	private Date repaymentDate;		// 还款日期
	private Long repaymentChannelId;		// 还款渠道
	private String sumMoney;		// 还款总金额
	private Long status;		// 还款状态
	private String splitBalance;		// 还款拆分余额
	private Long queryProjectId; //查询项目编号
	private Long repayType;	//还款类型
	private String thirdPartyOrder;//资金还款流水号
	
	private Double principal; //本金
	private Double interest; //利息
	private Double rateTicketInterest;	//加息
	public Double getOldMoney() {
		return oldMoney;
	}

	public void setOldMoney(Double oldMoney) {
		this.oldMoney = oldMoney;
	}

	private Double prePenaltyMoney; //提前还款罚金
	private Double latePenaltyMoney; //逾期还款罚金
	private Double oldMoney;//违约前金额
	

	
	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	
	
	public Double getRateTicketInterest() {
		return rateTicketInterest;
	}

	public void setRateTicketInterest(Double rateTicketInterest) {
		this.rateTicketInterest = rateTicketInterest;
	}

	public Double getPrePenaltyMoney() {
		return prePenaltyMoney;
	}

	public void setPrePenaltyMoney(Double prePenaltyMoney) {
		this.prePenaltyMoney = prePenaltyMoney;
	}

	public Double getLatePenaltyMoney() {
		return latePenaltyMoney;
	}

	public void setLatePenaltyMoney(Double latePenaltyMoney) {
		this.latePenaltyMoney = latePenaltyMoney;
	}

	public Long getRepayType() {
		return repayType;
	}

	public void setRepayType(Long repayType) {
		this.repayType = repayType;
	}

	public String getThirdPartyOrder() {
		return thirdPartyOrder;
	}

	public void setThirdPartyOrder(String thirdPartyOrder) {
		this.thirdPartyOrder = thirdPartyOrder;
	}

	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

	public ProjectRepaymentRecord() {
		super();
	}

	public ProjectRepaymentRecord(String id){
		super(id);
	}

	@NotNull(message="还款流水号不能为空")
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	
	public Long getRepaymentChannelId() {
		return repaymentChannelId;
	}

	public void setRepaymentChannelId(Long repaymentChannelId) {
		this.repaymentChannelId = repaymentChannelId;
	}
	
	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	public String getSplitBalance() {
		return splitBalance;
	}

	public void setSplitBalance(String splitBalance) {
		this.splitBalance = splitBalance;
	}
	
}