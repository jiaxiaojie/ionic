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
 * 会员银行卡信息Entity
 * @author ydt
 * @version 2015-06-25
 */
public class CustomerBankCard extends DataEntity<CustomerBankCard> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String cardNo;		// 绑定的卡号
	private String cardStatusCode;	//银行卡绑定状态
	private String bankCode;	//银行卡所属银行
	private String message;	//描述信息
	private String requestNo;	//请求流水号
	private Date opDt;	//银行卡填写时间
	private Date lastModifyDt;	//最后一次修改时间
	private String creditReportFile;	//信用报告
	private String unbindRequestNo;  //解绑请求流水号
	
	private Long customerId;	//会员编号
	private String accountType;	//账号类型

	
	public String getUnbindRequestNo() {
		return unbindRequestNo;
	}

	public void setUnbindRequestNo(String unbindRequestNo) {
		this.unbindRequestNo = unbindRequestNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public CustomerBankCard() {
		super();
	}

	public CustomerBankCard(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastModifyDt() {
		return lastModifyDt;
	}

	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardStatusCode() {
		return cardStatusCode;
	}

	public void setCardStatusCode(String cardStatusCode) {
		this.cardStatusCode = cardStatusCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

    @Length(min=0, max=500, message="信用报告长度必须介于 0 和 500 之间")
	public String getCreditReportFile() {
		return creditReportFile;
	}

	public void setCreditReportFile(String creditReportFile) {
		this.creditReportFile = creditReportFile;
	}
}