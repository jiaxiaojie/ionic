/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 投资排行Entity
 * @author lizibo
 * @version 2015-11-23
 */
public class ProjectInvestmentRank extends DataEntity<ProjectInvestmentRank> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 投资人
	private String customerName;		// 会员名称
	private String mobile;			// 手机号
	private String accountName;			//登录名
	private String amount;		// 投资金额
	private String rank;		// 名次
	private String opTerm;		// 终端
	private String type;		// 类型
	private Date createDt;		// 创建时间
	private Date dataDt;		// 数据时间
	private String remark;		// 备注
	private String beginAmount;		// 开始 投资金额
	private String endAmount;		// 结束 投资金额
	private Date beginDataDt;		// 开始 创建时间
	private Date endDataDt;		// 结束 创建时间
	private Date beginStatDt;		// 开始 统计时间
	private Date endStatDt;			// 结束 统计时间
	private int limit;				//条数限制
	private String year;			//年份
	private String month;			//月份
	private String week;			//周
	
	public ProjectInvestmentRank() {
		super();
	}

	public ProjectInvestmentRank(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=11, message="名次长度必须介于 0 和 11 之间")
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	@Length(min=0, max=11, message="终端长度必须介于 0 和 11 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
	@Length(min=0, max=2, message="类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		
	

	public Date getBeginDataDt() {
		return beginDataDt;
	}

	public void setBeginDataDt(Date beginDataDt) {
		this.beginDataDt = beginDataDt;
	}

	public Date getEndDataDt() {
		return endDataDt;
	}

	public void setEndDataDt(Date endDataDt) {
		this.endDataDt = endDataDt;
	}

	public Date getBeginStatDt() {
		return beginStatDt;
	}

	public void setBeginStatDt(Date beginStatDt) {
		this.beginStatDt = beginStatDt;
	}

	public Date getEndStatDt() {
		return endStatDt;
	}

	public void setEndStatDt(Date endStatDt) {
		this.endStatDt = endStatDt;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Date getDataDt() {
		return dataDt;
	}

	public void setDataDt(Date dataDt) {
		this.dataDt = dataDt;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}