/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员体现记录表Entity
 * @author lzb
 * @version 2016-05-10
 */
public class CustomerWithdrawHis extends DataEntity<CustomerWithdrawHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Double amount;		// 体现金额
	private String feeMode;		// 费率模式
	private String withdrawType;		// 体现模式
	private String bankCardNo;		// 体现银行卡号
	private String bankCode;		// 所属银行
	private String remark;		// 体现备注
	private String thirdPartyReq;		// 体现流水号
	private Date opDt;		// 操作时间
	private String opTermType;		// 操作终端
	private Date beginOpDt;		// 开始 操作时间
	private Date endOpDt;		// 结束 操作时间
	
	public CustomerWithdrawHis() {
		super();
	}

	public CustomerWithdrawHis(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	

	
	@Length(min=0, max=20, message="费率模式长度必须介于 0 和 20 之间")
	public String getFeeMode() {
		return feeMode;
	}

	public void setFeeMode(String feeMode) {
		this.feeMode = feeMode;
	}
	
	@Length(min=0, max=20, message="体现模式长度必须介于 0 和 20 之间")
	public String getWithdrawType() {
		return withdrawType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}
	
	@Length(min=0, max=50, message="体现银行卡号长度必须介于 0 和 50 之间")
	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
	@Length(min=0, max=20, message="所属银行长度必须介于 0 和 20 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=0, max=200, message="体现备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=50, message="体现流水号长度必须介于 0 和 50 之间")
	public String getThirdPartyReq() {
		return thirdPartyReq;
	}

	public void setThirdPartyReq(String thirdPartyReq) {
		this.thirdPartyReq = thirdPartyReq;
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
		
}