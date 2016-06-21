/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员账户余额变更流水Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerBalanceHis extends DataEntity<CustomerBalanceHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Double changeVal;		// 变更值
	private String changeType;		// 变更类型
	private String changeReason;		// 变更原因
	private String relProject;		// 关联项目
	private Date opDt;		// 操作时间
	private String opTermType;		// 操作终端
	private Integer beginChangeVal;		// 开始 变更值
	private Integer endChangeVal;		// 结束 变更值
	private Date beginOpDt;		// 开始操作时间
	private Date endOpDt;		// 结束操作时间
	private Double balance; //余额
	private String ext1;	//扩展字段
	private Date investmentDt;		// 投资时间
	private CustomerBase customerBase;	//用户基本信息
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setChangeVal(Double changeVal) {
		this.changeVal = changeVal;
	}

	public Date getBeginOpDt() {
		return beginOpDt;
	}

	public void setBeginOpDt(Date beginOpDt) {
		this.beginOpDt = beginOpDt;
	}

	public Date getEndOpDt() {
		return endOpDt;
	}

	public void setEndOpDt(Date endOpDt) {
		this.endOpDt = endOpDt;
	}

	public CustomerBalanceHis() {
		super();
	}

	public CustomerBalanceHis(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	
	public Double getChangeVal() {
		return changeVal;
	}

	@Length(min=1, max=2, message="变更类型长度必须介于 0 和 10 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@Length(min=0, max=300, message="变更原因长度必须介于 0 和 3 之间")
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	@Length(min=0, max=200, message="关联项目长度必须介于 0 和 200 之间")
	public String getRelProject() {
		return relProject;
	}

	public void setRelProject(String relProject) {
		this.relProject = relProject;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=2, message="操作终端长度必须介于 0 和 2 之间")
	public String getOpTermType() {
		return opTermType;
	}

	public void setOpTermType(String opTermType) {
		this.opTermType = opTermType;
	}
	
	public Integer getBeginChangeVal() {
		return beginChangeVal;
	}

	public void setBeginChangeVal(Integer beginChangeVal) {
		this.beginChangeVal = beginChangeVal;
	}
	
	public Integer getEndChangeVal() {
		return endChangeVal;
	}

	public void setEndChangeVal(Integer endChangeVal) {
		this.endChangeVal = endChangeVal;
	}

	

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public Date getInvestmentDt() {
		return investmentDt;
	}

	public void setInvestmentDt(Date investmentDt) {
		this.investmentDt = investmentDt;
	}

	public CustomerBase getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBase customerBase) {
		this.customerBase = customerBase;
	}
		
}