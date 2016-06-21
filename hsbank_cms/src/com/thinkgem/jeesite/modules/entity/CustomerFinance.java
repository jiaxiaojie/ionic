/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员财务信息Entity
 * @author ydt
 * @version 2015-06-25
 */
public class CustomerFinance extends DataEntity<CustomerFinance> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private Integer monthIncome;		// 月均收入
	private String incomeRemark;		// 收入构成描述
	private Integer monthSpend;		// 月均支出
	private String spendRemark;		// 支出构成描述
	private String housingConditions;		// 住房条件
	private Integer housingVal;		// 房产价值（单位:万）
	private String hasHousingLoan;	//是否有房贷
	private String hasCar;		// 是否购车
	private Integer carVal;		// 车辆价值（单位:万）
	private String hasCarLoan;	//是否有车贷
	private String carRemark;		// 车辆信息描述
	private String shareholderEnt;		// 参股企业名称
	private Integer shareholderVal;		// 参股企业资额（单位:万）
	private String otherRemark;		// 其他资产描述
	
	private String accountId;	//账号编号
	private String accountType;	//账号类型

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public CustomerFinance() {
		super();
	}

	public CustomerFinance(String id){
		super(id);
	}

	@NotNull(message="会员编号不能为空")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Integer getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(Integer monthIncome) {
		this.monthIncome = monthIncome;
	}
	
	public String getIncomeRemark() {
		return incomeRemark;
	}

	public void setIncomeRemark(String incomeRemark) {
		this.incomeRemark = incomeRemark;
	}
	
	public Integer getMonthSpend() {
		return monthSpend;
	}

	public void setMonthSpend(Integer monthSpend) {
		this.monthSpend = monthSpend;
	}
	
	@Length(min=0, max=500, message="支出构成描述长度必须介于 0 和 500 之间")
	public String getSpendRemark() {
		return spendRemark;
	}

	public void setSpendRemark(String spendRemark) {
		this.spendRemark = spendRemark;
	}
	
	@Length(min=0, max=2, message="住房条件长度必须介于 0 和 2 之间")
	public String getHousingConditions() {
		return housingConditions;
	}

	public void setHousingConditions(String housingConditions) {
		this.housingConditions = housingConditions;
	}
	
	public Integer getHousingVal() {
		return housingVal;
	}

	public void setHousingVal(Integer housingVal) {
		this.housingVal = housingVal;
	}
	
	@Length(min=0, max=1, message="是否购车长度必须介于 0 和 1 之间")
	public String getHasCar() {
		return hasCar;
	}

	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}

	@Length(min=0, max=1, message="是否有房贷长度必须介于 0 和 1 之间")
	public String getHasHousingLoan() {
		return hasHousingLoan;
	}

	public void setHasHousingLoan(String hasHousingLoan) {
		this.hasHousingLoan = hasHousingLoan;
	}

	@Length(min=0, max=1, message="是否有车贷长度必须介于 0 和 1 之间")
	public String getHasCarLoan() {
		return hasCarLoan;
	}

	public void setHasCarLoan(String hasCarLoan) {
		this.hasCarLoan = hasCarLoan;
	}

	public Integer getCarVal() {
		return carVal;
	}

	public void setCarVal(Integer carVal) {
		this.carVal = carVal;
	}
	
	@Length(min=0, max=500, message="车辆信息描述长度必须介于 0 和 500 之间")
	public String getCarRemark() {
		return carRemark;
	}

	public void setCarRemark(String carRemark) {
		this.carRemark = carRemark;
	}
	
	@Length(min=0, max=500, message="参股企业名称长度必须介于 0 和 500 之间")
	public String getShareholderEnt() {
		return shareholderEnt;
	}

	public void setShareholderEnt(String shareholderEnt) {
		this.shareholderEnt = shareholderEnt;
	}
	
	public Integer getShareholderVal() {
		return shareholderVal;
	}

	public void setShareholderVal(Integer shareholderVal) {
		this.shareholderVal = shareholderVal;
	}
	
	@Length(min=0, max=500, message="其他资产描述长度必须介于 0 和 500 之间")
	public String getOtherRemark() {
		return otherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}
	
}