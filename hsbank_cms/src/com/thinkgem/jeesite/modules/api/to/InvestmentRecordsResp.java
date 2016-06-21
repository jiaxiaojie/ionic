package com.thinkgem.jeesite.modules.api.to;


import com.thinkgem.jeesite.common.persistence.DataEntity;

public class InvestmentRecordsResp extends DataEntity<InvestmentRecordsResp> {
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String investmentUser;		// 投资人
	private String opTerm;		// 操作终端
	private String opDt;		// 投资时间
	private Double amount;		// 投资金额
	private String mobile;		//手机号
	private String avatar;		// 头像url
	private String intervalTime;	//投资时间间隔
	private String description;		//描述

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getInvestmentUser() {
		return investmentUser;
	}
	public void setInvestmentUser(String investmentUser) {
		this.investmentUser = investmentUser;
	}
	public String getOpTerm() {
		return opTerm;
	}
	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	public String getOpDt() {
		return opDt;
	}
	public void setOpDt(String opDt) {
		this.opDt = opDt;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
