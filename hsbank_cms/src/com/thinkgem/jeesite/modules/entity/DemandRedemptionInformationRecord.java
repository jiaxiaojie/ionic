/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期赎回信息记录Entity
 * @author huangyuchen
 * @version 2016-04-11
 */
public class DemandRedemptionInformationRecord extends DataEntity<DemandRedemptionInformationRecord> {
	
	private static final long serialVersionUID = 1L;
	private String redemptionId;		// 赎回申请id
	private Date reedmptionDate;		// 申请时间
	private String infoReason;		// 记录信息原因
	private Double accountAmount;		// 融资人账户余额
	private Double redeemPrincipal;		// 赎回本金
	private String status;		// 状态
	
	public DemandRedemptionInformationRecord() {
		super();
	}

	public DemandRedemptionInformationRecord(String id){
		super(id);
	}

	public String getRedemptionId() {
		return redemptionId;
	}

	public void setRedemptionId(String redemptionId) {
		this.redemptionId = redemptionId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReedmptionDate() {
		return reedmptionDate;
	}

	public void setReedmptionDate(Date reedmptionDate) {
		this.reedmptionDate = reedmptionDate;
	}
	
	@Length(min=0, max=50, message="记录信息原因长度必须介于 0 和 50 之间")
	public String getInfoReason() {
		return infoReason;
	}

	public void setInfoReason(String infoReason) {
		this.infoReason = infoReason;
	}
	
	public Double getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Double accountAmount) {
		this.accountAmount = accountAmount;
	}
	
	public Double getRedeemPrincipal() {
		return redeemPrincipal;
	}

	public void setRedeemPrincipal(Double redeemPrincipal) {
		this.redeemPrincipal = redeemPrincipal;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}