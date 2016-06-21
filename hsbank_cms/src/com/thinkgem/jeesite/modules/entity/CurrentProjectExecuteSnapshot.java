/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期项目执行快照Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectExecuteSnapshot extends DataEntity<CurrentProjectExecuteSnapshot> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Double hasFinancedMoney;		// 已融资金额
	private Double realPrincipal;		// 当前实际本金
	private Double hasRepaidMoney;		// 已产生利息
	private Double hasRedeemInterest;		// 已提取利息
	private Double beginHasFinancedMoney;		// 开始 已融资金额
	private Double endHasFinancedMoney;		// 结束 已融资金额
	
	public CurrentProjectExecuteSnapshot() {
		super();
	}

	public CurrentProjectExecuteSnapshot(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Double getHasFinancedMoney() {
		return hasFinancedMoney;
	}

	public void setHasFinancedMoney(Double hasFinancedMoney) {
		this.hasFinancedMoney = hasFinancedMoney;
	}
	
	public Double getRealPrincipal() {
		return realPrincipal;
	}

	public void setRealPrincipal(Double realPrincipal) {
		this.realPrincipal = realPrincipal;
	}

	public Double getHasRepaidMoney() {
		return hasRepaidMoney;
	}

	public void setHasRepaidMoney(Double hasRepaidMoney) {
		this.hasRepaidMoney = hasRepaidMoney;
	}
	
	public Double getHasRedeemInterest() {
		return hasRedeemInterest;
	}

	public void setHasRedeemInterest(Double hasRedeemInterest) {
		this.hasRedeemInterest = hasRedeemInterest;
	}
	
	public Double getBeginHasFinancedMoney() {
		return beginHasFinancedMoney;
	}

	public void setBeginHasFinancedMoney(Double beginHasFinancedMoney) {
		this.beginHasFinancedMoney = beginHasFinancedMoney;
	}
	
	public Double getEndHasFinancedMoney() {
		return endHasFinancedMoney;
	}

	public void setEndHasFinancedMoney(Double endHasFinancedMoney) {
		this.endHasFinancedMoney = endHasFinancedMoney;
	}
		
	
}