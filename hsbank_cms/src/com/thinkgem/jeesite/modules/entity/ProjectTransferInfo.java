/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 债权转让Entity
 * @author yangtao
 * @version 2015-06-25
 */
public class ProjectTransferInfo extends DataEntity<ProjectTransferInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long transferProjectId;		// 转让编号
	private Long projectId;		// 原始项目编号
	private Long parentTransferProjectId;		// 上一转让编号
	private String isRecommend;	    //是否重点推荐
	private Long investmentRecordId;		// 原投资记录编号
	private Date projectEndDate;		// 原始项目还款结束日期
	private Date nextRepaymentDate;		// 下一还款日期
	private Long transferor;		// 转让人
	private Double transferPrice;		// 转让价格
	private String fairPrice;		// 公允价格
	private Date discountDate;		// 转让截止日期
	private String serviceChargeType;		// 手续费收取
	private Double remainderCreditor;		// 剩余债权
	private Double transactionCosts; //交易费用
	private String status;		// 转让状态
	private Date createDate; //创建时间
	private Date closeDate; //关闭日期
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private Long queryProjectId; //查询项目编号
	private String transferName; //查询转让人名称
	private CustomerBase transferCustomer; //转让人信息
	private CustomerAccount transferAccount;//转让人账号信息
	
	private ProjectBaseInfo projectBaseInfo;	//原始项目信息
	private ProjectExecuteSnapshot projectExecuteSnapshot;	//转让项目执行快照
	private ProjectInvestmentRecord pir;//投资记录信息
	
	private Double platformMoney; //平台服务费
	
	public Double getPlatformMoney() {
		return platformMoney;
	}

	public void setPlatformMoney(Double platformMoney) {
		this.platformMoney = platformMoney;
	}

	public ProjectInvestmentRecord getPir() {
		return pir;
	}

	public void setPir(ProjectInvestmentRecord pir) {
		this.pir = pir;
	}

	public void setTransferProjectId(Long transferProjectId) {
		this.transferProjectId = transferProjectId;
	}

	public Long getTransferProjectId() {
		return transferProjectId;
	}

	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

	public ProjectTransferInfo() {
		super();
	}

	public ProjectTransferInfo(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getParentTransferProjectId() {
		return parentTransferProjectId;
	}

	public void setParentTransferProjectId(Long parentTransferProjectId) {
		this.parentTransferProjectId = parentTransferProjectId;
	}
	
	public Long getInvestmentRecordId() {
		return investmentRecordId;
	}

	public void setInvestmentRecordId(Long investmentRecordId) {
		this.investmentRecordId = investmentRecordId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNextRepaymentDate() {
		return nextRepaymentDate;
	}

	public void setNextRepaymentDate(Date nextRepaymentDate) {
		this.nextRepaymentDate = nextRepaymentDate;
	}
	
	public Long getTransferor() {
		return transferor;
	}

	public void setTransferor(Long transferor) {
		this.transferor = transferor;
	}
	
	public Double getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(Double transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public String getFairPrice() {
		return fairPrice;
	}

	public void setFairPrice(String fairPrice) {
		this.fairPrice = fairPrice;
	}
	
	public Date getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Date discountDate) {
		this.discountDate = discountDate;
	}
	
	@Length(min=0, max=11, message="手续费收取长度必须介于 0 和 11 之间")
	public String getServiceChargeType() {
		return serviceChargeType;
	}

	public void setServiceChargeType(String serviceChargeType) {
		this.serviceChargeType = serviceChargeType;
	}
	
	public Double getRemainderCreditor() {
		return remainderCreditor;
	}

	public void setRemainderCreditor(Double remainderCreditor) {
		this.remainderCreditor = remainderCreditor;
	}
	
	@Length(min=0, max=2, message="转让状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}	
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public CustomerBase getTransferCustomer() {
		return transferCustomer;
	}

	public void setTransferCustomer(CustomerBase transferCustomer) {
		this.transferCustomer = transferCustomer;
	}

	public CustomerAccount getTransferAccount() {
		return transferAccount;
	}

	public void setTransferAccount(CustomerAccount transferAccount) {
		this.transferAccount = transferAccount;
	}

	public ProjectBaseInfo getProjectBaseInfo() {
		return projectBaseInfo;
	}

	public void setProjectBaseInfo(ProjectBaseInfo projectBaseInfo) {
		this.projectBaseInfo = projectBaseInfo;
	}

	public ProjectExecuteSnapshot getProjectExecuteSnapshot() {
		return projectExecuteSnapshot;
	}
	public void setProjectExecuteSnapshot(
			ProjectExecuteSnapshot projectExecuteSnapshot) {
		this.projectExecuteSnapshot = projectExecuteSnapshot;
	}
	

	public Double getTransactionCosts() {
		return transactionCosts;
	}

	public void setTransactionCosts(Double transactionCosts) {
		this.transactionCosts = transactionCosts;
	}
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

}