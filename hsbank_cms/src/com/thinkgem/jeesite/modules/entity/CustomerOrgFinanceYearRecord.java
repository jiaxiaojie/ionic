/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业会员财务年表Entity
 * @author ydt
 * @version 2015-06-30
 */
public class CustomerOrgFinanceYearRecord extends DataEntity<CustomerOrgFinanceYearRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String yearId;		// 年度
	private Float mainRevenue;		// 主营收入（万）
	private Float grossProfit;		// 毛利润（万）
	private Float netProfit;		// 净利润（万）
	private Float totalAssets;		// 总资产（万）
	private Float netAssets;		// 净资产（万）
	private String remark;		//备注
	
	private String accountName;
	private String customerName;
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public CustomerOrgFinanceYearRecord() {
		super();
	}

	public CustomerOrgFinanceYearRecord(String id){
		super(id);
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=4, message="年度长度必须介于 0 和 4 之间")
	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	
	public Float getMainRevenue() {
		return mainRevenue;
	}

	public void setMainRevenue(Float mainRevenue) {
		this.mainRevenue = mainRevenue;
	}
	
	public Float getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Float grossProfit) {
		this.grossProfit = grossProfit;
	}
	
	public Float getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(Float netProfit) {
		this.netProfit = netProfit;
	}
	
	public Float getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(Float totalAssets) {
		this.totalAssets = totalAssets;
	}
	
	public Float getNetAssets() {
		return netAssets;
	}

	public void setNetAssets(Float netAssets) {
		this.netAssets = netAssets;
	}

	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}