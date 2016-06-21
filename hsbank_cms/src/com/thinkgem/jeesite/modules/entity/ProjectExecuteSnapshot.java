/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同执行快照Entity
 * @author yangtao
 * @version 2015-06-24
 */
public class ProjectExecuteSnapshot extends DataEntity<ProjectExecuteSnapshot> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;				// 项目流水号
	private Long transferProjectId;		// 转让编号
	private Double financeMoney;		// 借款金额

	private Double endFinanceMoney;		// 已融资金额
	private Double endRepayMoney;		// 已还款金额
	private Double sumServiceCharge ;	// 已冻结服务费
	private Integer remainingTime;		// 剩余期限（单位：月）
	private Double sumPlatformAmount; 	// 已冻结抵用额
	private Double sumTicketMoney; 		// 已冻结抵用券对应金额
	
	public Double getSumPlatformAmount() {
		return sumPlatformAmount;
	}

	public void setSumPlatformAmount(Double sumPlatformAmount) {
		this.sumPlatformAmount = sumPlatformAmount;
	}

	public Double getSumTicketMoney() {
		return sumTicketMoney;
	}

	public void setSumTicketMoney(Double sumTicketMoney) {
		this.sumTicketMoney = sumTicketMoney;
	}


	private String status; //状态
	
	public Double getSumServiceCharge() {
		return sumServiceCharge;
	}

	public Long getTransferProjectId() {
		return transferProjectId;
	}

	public void setTransferProjectId(Long transferProjectId) {
		this.transferProjectId = transferProjectId;
	}
	public void setSumServiceCharge(Double sumServiceCharge) {
		this.sumServiceCharge = sumServiceCharge;
	}

	
	private Long queryProjectId; //查询项目编号
	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

	public ProjectExecuteSnapshot() {
		super();
	}

	public ProjectExecuteSnapshot(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Double getFinanceMoney() {
		return financeMoney;
	}

	public void setFinanceMoney(Double financeMoney) {
		this.financeMoney = financeMoney;
	}
	
	public Double getEndFinanceMoney() {
		return endFinanceMoney;
	}

	public void setEndFinanceMoney(Double endFinanceMoney) {
		this.endFinanceMoney = endFinanceMoney;
	}
	
	public Double getEndRepayMoney() {
		return endRepayMoney;
	}

	public void setEndRepayMoney(Double endRepayMoney) {
		this.endRepayMoney = endRepayMoney;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Integer remainingTime) {
		this.remainingTime = remainingTime;
	}

}