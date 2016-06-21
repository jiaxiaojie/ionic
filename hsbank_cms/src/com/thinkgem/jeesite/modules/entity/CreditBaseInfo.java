/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 债券基本信息Entity
 * @author wanduanrui
 * @version 2016-03-29
 */
public class CreditBaseInfo extends DataEntity<CreditBaseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String creditName;		// 债权名称
	private String creditProjectType;		// 债权项目类型
	private Double creditOriginalMoney;		// 债权原始金额
	private Double creditFinancingMoney;		// 债权融资金额
	
	private Double creditInterest;		// 债权利率
	private String financierName;		// 融资人名称
	private String realBorrowerName;		// 实际借款人名称
	private String receivablesAnalogueName;		// 应收账款对手方名称
	private String isAssigned;		// 是否已做转让
	
	
	
	private Double raisedMoneyOnLine;		// 线上已经募集金额
	private Double raisedMoneyBelowLine;		// 线下已经募集金额
	
	private Double raisingMoney;		// 募集中金额
	private Double toRaiseMoney;		// 待募集金额
	private String creditStatus;		// 债权状态
	private String relevantDocumentOriginal;		// 相关文件原件
	private String publicDocument;		// 公开文件
	
	private Date raiseBeginDate; //募集起始时间
	private Date raiseEndDate; //募集结束时间
	
	
	private String businessLicense; //营业执照
	private String tradingContract; //贸易合同
	private String loanContract; //借款合同
	private String centralBankRegistrationInformation; //央行登记信息
	private String fieldTripPhotos; //实地考察照片
	private String invoice; //发票
	private String logisticsSignReceipts; //物流签收单据
	
	
	private Date creditRealBeginDate;
	private Date creditRealEndDate;
	private Date creditFinancingBeginDate;
	private Date creditFinancingEndDate;
	private String isDraft; //是否为草稿
	
	
	public CreditBaseInfo() {
		super();
	}

	public CreditBaseInfo(String id){
		super(id);
	}

	
	
	public String getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(String isDraft) {
		this.isDraft = isDraft;
	}

	public Date getCreditRealBeginDate() {
		return creditRealBeginDate;
	}

	public void setCreditRealBeginDate(Date creditRealBeginDate) {
		this.creditRealBeginDate = creditRealBeginDate;
	}

	public Date getCreditRealEndDate() {
		return creditRealEndDate;
	}

	public void setCreditRealEndDate(Date creditRealEndDate) {
		this.creditRealEndDate = creditRealEndDate;
	}

	public Date getCreditFinancingBeginDate() {
		return creditFinancingBeginDate;
	}

	public void setCreditFinancingBeginDate(Date creditFinancingBeginDate) {
		this.creditFinancingBeginDate = creditFinancingBeginDate;
	}

	public Date getCreditFinancingEndDate() {
		return creditFinancingEndDate;
	}

	public void setCreditFinancingEndDate(Date creditFinancingEndDate) {
		this.creditFinancingEndDate = creditFinancingEndDate;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getTradingContract() {
		return tradingContract;
	}

	public void setTradingContract(String tradingContract) {
		this.tradingContract = tradingContract;
	}

	public String getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(String loanContract) {
		this.loanContract = loanContract;
	}

	public String getCentralBankRegistrationInformation() {
		return centralBankRegistrationInformation;
	}

	public void setCentralBankRegistrationInformation(String centralBankRegistrationInformation) {
		this.centralBankRegistrationInformation = centralBankRegistrationInformation;
	}

	public String getFieldTripPhotos() {
		return fieldTripPhotos;
	}

	public void setFieldTripPhotos(String fieldTripPhotos) {
		this.fieldTripPhotos = fieldTripPhotos;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getLogisticsSignReceipts() {
		return logisticsSignReceipts;
	}

	public void setLogisticsSignReceipts(String logisticsSignReceipts) {
		this.logisticsSignReceipts = logisticsSignReceipts;
	}

	public Double getRaisedMoneyOnLine() {
		return raisedMoneyOnLine;
	}

	public void setRaisedMoneyOnLine(Double raisedMoneyOnLine) {
		this.raisedMoneyOnLine = raisedMoneyOnLine;
	}

	public Double getRaisedMoneyBelowLine() {
		return raisedMoneyBelowLine;
	}

	public void setRaisedMoneyBelowLine(Double raisedMoneyBelowLine) {
		this.raisedMoneyBelowLine = raisedMoneyBelowLine;
	}

	public Date getRaiseBeginDate() {
		return raiseBeginDate;
	}

	public void setRaiseBeginDate(Date raiseBeginDate) {
		this.raiseBeginDate = raiseBeginDate;
	}

	public Date getRaiseEndDate() {
		return raiseEndDate;
	}

	public void setRaiseEndDate(Date raiseEndDate) {
		this.raiseEndDate = raiseEndDate;
	}

	@Length(min=1, max=200, message="债权名称长度必须介于 1 和 200 之间")
	public String getCreditName() {
		return creditName;
	}

	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}
	
	

	
	
	
	@NotNull(message="债权原始金额不能为空")
	public Double getCreditOriginalMoney() {
		return creditOriginalMoney;
	}

	@NotNull(message="债权项目类型不能为空")
	public String getCreditProjectType() {
		return creditProjectType;
	}

	public void setCreditProjectType(String creditProjectType) {
		this.creditProjectType = creditProjectType;
	}

	public void setCreditOriginalMoney(Double creditOriginalMoney) {
		this.creditOriginalMoney = creditOriginalMoney;
	}
	
	@NotNull(message="债权融资金额不能为空")
	public Double getCreditFinancingMoney() {
		return creditFinancingMoney;
	}

	public void setCreditFinancingMoney(Double creditFinancingMoney) {
		this.creditFinancingMoney = creditFinancingMoney;
	}
	
 
	
	@NotNull(message="债权利率不能为空")
	public Double getCreditInterest() {
		return creditInterest;
	}

	public void setCreditInterest(Double creditInterest) {
		this.creditInterest = creditInterest;
	}
	
	@Length(min=1, max=50, message="融资人名称长度必须介于 1 和 50 之间")
	public String getFinancierName() {
		return financierName;
	}

	public void setFinancierName(String financierName) {
		this.financierName = financierName;
	}
	
	@Length(min=1, max=50, message="实际借款人名称长度必须介于 1 和 50 之间")
	public String getRealBorrowerName() {
		return realBorrowerName;
	}

	public void setRealBorrowerName(String realBorrowerName) {
		this.realBorrowerName = realBorrowerName;
	}
	
	@Length(min=1, max=50, message="应收账款对手方名称长度必须介于 1 和 50 之间")
	public String getReceivablesAnalogueName() {
		return receivablesAnalogueName;
	}

	public void setReceivablesAnalogueName(String receivablesAnalogueName) {
		this.receivablesAnalogueName = receivablesAnalogueName;
	}
	
	@Length(min=1, max=2, message="是否已做转让长度必须介于 1 和 2 之间")
	public String getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(String isAssigned) {
		this.isAssigned = isAssigned;
	}
	

	
	
	
	public Double getRaisingMoney() {
		return raisingMoney;
	}

	public void setRaisingMoney(Double raisingMoney) {
		this.raisingMoney = raisingMoney;
	}
	
	public Double getToRaiseMoney() {
		return toRaiseMoney;
	}

	public void setToRaiseMoney(Double toRaiseMoney) {
		this.toRaiseMoney = toRaiseMoney;
	}
	
	@Length(min=1, max=2, message="债权状态长度必须介于 1 和 2 之间")
	public String getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	
	@Length(min=0, max=2000, message="相关文件原件长度必须介于 0 和 2000 之间")
	public String getRelevantDocumentOriginal() {
		return relevantDocumentOriginal;
	}

	public void setRelevantDocumentOriginal(String relevantDocumentOriginal) {
		this.relevantDocumentOriginal = relevantDocumentOriginal;
	}
	
	@Length(min=0, max=2000, message="公开文件长度必须介于 0 和 2000 之间")
	public String getPublicDocument() {
		return publicDocument;
	}

	public void setPublicDocument(String publicDocument) {
		this.publicDocument = publicDocument;
	}
	
}