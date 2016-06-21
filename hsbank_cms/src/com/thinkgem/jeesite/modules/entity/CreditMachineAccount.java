/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 债权台账Entity
 * @author wanduanrui
 * @version 2016-03-30
 */
public class CreditMachineAccount extends DataEntity<CreditMachineAccount> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private Double investMoney;		// 投资金额
	private Double interestRate;		// 利率
	private String interestCalculation;		// 计息方式
	private Date valueDate;		// 起息日
	private Integer investmentHorizon;		// 投资期限（月）
	private Date expiringDate;		// 到期日
	private String financialManager;		// 理财经理
	private String businessManager;		// 营业部经理
	private String paymentMethod;		// 付款方式
	private Double commissionCharge;		// 手续费
	private String contractAddress;		// 合同地址
	private String relevantDocument;		// 相关文件
	private String[] procedureDocuments;		// 手续文件
	private String giftRecipients;		// 礼品领用
	private String aheadRedemptive;		// 提前赎回
	private String remark;		// 备注
	private Long creditInvestUserId;		// 债权投资用户ID
	private Long creditId; //债权ID
	
	private Date beginExpiringDate;
	private Date endExpiringDate;
	
	private CreditBaseInfo creditBaseInfo;
	
	public CreditMachineAccount() {
		super();
	}

	public CreditMachineAccount(String id){
		super(id);
	}

	
	
	public CreditBaseInfo getCreditBaseInfo() {
		return creditBaseInfo;
	}

	public void setCreditBaseInfo(CreditBaseInfo creditBaseInfo) {
		this.creditBaseInfo = creditBaseInfo;
	}

	public Date getBeginExpiringDate() {
		return beginExpiringDate;
	}

	public void setBeginExpiringDate(Date beginExpiringDate) {
		this.beginExpiringDate = beginExpiringDate;
	}

	public Date getEndExpiringDate() {
		return endExpiringDate;
	}

	public void setEndExpiringDate(Date endExpiringDate) {
		this.endExpiringDate = endExpiringDate;
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	@Length(min=1, max=500, message="合同编号长度必须介于 1 和 500 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@NotNull(message="投资金额不能为空")
	public Double getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(Double investMoney) {
		this.investMoney = investMoney;
	}
	
	@NotNull(message="利率不能为空")
	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	@Length(min=1, max=11, message="计息方式长度必须介于 1 和 11 之间")
	public String getInterestCalculation() {
		return interestCalculation;
	}

	public void setInterestCalculation(String interestCalculation) {
		this.interestCalculation = interestCalculation;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="起息日不能为空")
	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	@NotNull(message="投资期限（月）不能为空")
	public Integer getInvestmentHorizon() {
		return investmentHorizon;
	}

	public void setInvestmentHorizon(Integer investmentHorizon) {
		this.investmentHorizon = investmentHorizon;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	public Date getExpiringDate() {
		return expiringDate;
	}

	public void setExpiringDate(Date expiringDate) {
		this.expiringDate = expiringDate;
	}
	
	@Length(min=1, max=50, message="理财经理长度必须介于 1 和 50 之间")
	public String getFinancialManager() {
		return financialManager;
	}

	public void setFinancialManager(String financialManager) {
		this.financialManager = financialManager;
	}
	
	@NotNull(message="营业部经理不能为空")
	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}
	
	@Length(min=0, max=11, message="付款方式长度必须介于 0 和 11 之间")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public Double getCommissionCharge() {
		return commissionCharge;
	}

	public void setCommissionCharge(Double commissionCharge) {
		this.commissionCharge = commissionCharge;
	}
	
	@Length(min=0, max=100, message="合同地址长度必须介于 0 和 100 之间")
	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
	@Length(min=0, max=2000, message="相关文件长度必须介于 0 和 2000 之间")
	public String getRelevantDocument() {
		return relevantDocument;
	}

	public void setRelevantDocument(String relevantDocument) {
		this.relevantDocument = relevantDocument;
	}
	
	
	
	public String[] getProcedureDocuments() {
		return procedureDocuments;
	}

	public void setProcedureDocuments(String[] procedureDocuments) {
		this.procedureDocuments = procedureDocuments;
	}

	@Length(min=0, max=500, message="礼品领用长度必须介于 0 和 500 之间")
	public String getGiftRecipients() {
		return giftRecipients;
	}

	public void setGiftRecipients(String giftRecipients) {
		this.giftRecipients = giftRecipients;
	}
	
	@Length(min=0, max=500, message="提前赎回长度必须介于 0 和 500 之间")
	public String getAheadRedemptive() {
		return aheadRedemptive;
	}

	public void setAheadRedemptive(String aheadRedemptive) {
		this.aheadRedemptive = aheadRedemptive;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getCreditInvestUserId() {
		return creditInvestUserId;
	}

	public void setCreditInvestUserId(Long creditInvestUserId) {
		this.creditInvestUserId = creditInvestUserId;
	}
	
}