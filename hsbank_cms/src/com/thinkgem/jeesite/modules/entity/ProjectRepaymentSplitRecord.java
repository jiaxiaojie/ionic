/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 还款记录查分明细Entity
 * @author yangtao
 * @version 2015-07-13
 */
public class ProjectRepaymentSplitRecord extends DataEntity<ProjectRepaymentSplitRecord> {
	
	public Double getMoney() {
		return money;
	}

	private static final long serialVersionUID = 1L;
	private Long splitRecordId;		// 还款分配流水号
	private Long recordId;		// 还款流水号
	private Long projectId;		// 项目流水号
	private Long repaymentUserId;		// 还款人
	private Long investmentRecordId;		// 投资流水编号
	private String repaymentAccount;		// 还款人账号
	private Long payeeUserId;		// 收款人
	private String payeeAccount;		// 收款人账号
	private Double money;		// 分配金额
	private Double prePenaltyMoney; //提前还款违约金额
	private Double latePenaltyMoney; //逾期还款违约金额
	private String repayType; //还款类型
	private Double sumInterest; //合计利息
	private Date startDate;
	private Date endDate;
	private Double principal;		// 本金
	private Double interest;		// 利息
	private Double rateTicketInterest;	//加息券利息
	private Double remainedPrincipal;		// 剩余本金
	private Date repaymentDt;		// 预计还款时间
	private Date actualRepaymentDt;		// 实际还款时间
	private String thirdPartyOrder;		// 分配第三方编号
	private String repayResult;		// 分配返回码
	private String status;		// 分配状态
	private Date createDt;		// 创建时间
	private Date modifyDt;		// 修改时间
	private String modifyRemark;		// 修改备注
	private ProjectBaseInfo projectBaseInfo ;//对应项目信息
	private Double sumMoneyShow;//显示合计总额
	private Double sumPrincipalShow; //显示合计本金
	private Double sumInterestShow; //显示合计利息
	private Double sumPenaltyMoneyShow; //显示合计违约金额
	private String statusShow; //状态显示转码
	private int day;
	private Double sumReceivedProfit;//合计已收收益总额
	private Double sumWillProfit; //合计待收收益总额
	private Date LastRepaymentDt;	//最后还款日期
	private Date firstRepaymentDt;	//首次还款日期
	private int receivedPeriods;	//已还款期数
	private int totalPeriods;	//总还款期数
	
	private CustomerAccount customerAccount;

	private ProjectInvestmentRecord investmentRecord;	//投资记录信息

	public CustomerAccount getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccount customerAccount) {
		this.customerAccount = customerAccount;
	}

	public ProjectInvestmentRecord getInvestmentRecord() {
		return investmentRecord;
	}

	public void setInvestmentRecord(ProjectInvestmentRecord investmentRecord) {
		this.investmentRecord = investmentRecord;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatusShow() {
		return statusShow;
	}

	public void setStatusShow(String statusShow) {
		this.statusShow = statusShow;
	}

	public Double getSumMoneyShow() {
		return sumMoneyShow;
	}

	public void setSumMoneyShow(Double sumMoney) {
		this.sumMoneyShow = sumMoney;
	}

	public Long getSplitRecordId() {
		return splitRecordId;
	}

	public void setSplitRecordId(Long splitRecordId) {
		this.splitRecordId = splitRecordId;
	}

	public Double getSumInterest() {
		return sumInterest;
	}

	public void setSumInterest(Double sumInterest) {
		this.sumInterest = sumInterest;
	}
	
	
	
	public Double getRateTicketInterest() {
		return rateTicketInterest;
	}

	public void setRateTicketInterest(Double rateTicketInterest) {
		this.rateTicketInterest = rateTicketInterest;
	}

	public Double getSumPrincipalShow() {
		return sumPrincipalShow;
	}

	public void setSumPrincipalShow(Double sumPrincipalShow) {
		this.sumPrincipalShow = sumPrincipalShow;
	}

	public Double getSumInterestShow() {
		return sumInterestShow;
	}

	public void setSumInterestShow(Double sumInterestShow) {
		this.sumInterestShow = sumInterestShow;
	}

	public Double getSumPenaltyMoneyShow() {
		return sumPenaltyMoneyShow;
	}

	public void setSumPenaltyMoneyShow(Double sumPenaltyMoneyShow) {
		this.sumPenaltyMoneyShow = sumPenaltyMoneyShow;
	}

	public ProjectBaseInfo getProjectBaseInfo() {
		return projectBaseInfo;
	}

	public void setProjectBaseInfo(ProjectBaseInfo projectBaseInfo) {
		this.projectBaseInfo = projectBaseInfo;
	}
	public ProjectRepaymentSplitRecord() {
		super();
	}

	public ProjectRepaymentSplitRecord(String id){
		super(id);
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getRepaymentUserId() {
		return repaymentUserId;
	}

	public void setRepaymentUserId(Long repaymentUserId) {
		this.repaymentUserId = repaymentUserId;
	}
	
	public Long getInvestmentRecordId() {
		return investmentRecordId;
	}

	public void setInvestmentRecordId(Long investmentRecordId) {
		this.investmentRecordId = investmentRecordId;
	}
	
	@Length(min=0, max=200, message="还款人账号长度必须介于 0 和 200 之间")
	public String getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(String repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	
	public Long getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(Long payeeUserId) {
		this.payeeUserId = payeeUserId;
	}
	
	@Length(min=0, max=200, message="收款人账号长度必须介于 0 和 200 之间")
	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	
	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}
	
	public Double getRemainedPrincipal() {
		return remainedPrincipal;
	}

	public void setRemainedPrincipal(Double remainedPrincipal) {
		this.remainedPrincipal = remainedPrincipal;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepaymentDt() {
		return repaymentDt;
	}

	public void setRepaymentDt(Date repaymentDt) {
		this.repaymentDt = repaymentDt;
	}
	
	@Length(min=0, max=200, message="分配第三方编号长度必须介于 0 和 200 之间")
	public String getThirdPartyOrder() {
		return thirdPartyOrder;
	}

	public void setThirdPartyOrder(String thirdPartyOrder) {
		this.thirdPartyOrder = thirdPartyOrder;
	}
	
	@Length(min=0, max=200, message="分配返回码长度必须介于 0 和 200 之间")
	public String getRepayResult() {
		return repayResult;
	}

	public void setRepayResult(String repayResult) {
		this.repayResult = repayResult;
	}
	
	@Length(min=0, max=2, message="分配状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}
	
	@Length(min=0, max=1000, message="修改备注长度必须介于 0 和 1000 之间")
	public String getModifyRemark() {
		return modifyRemark;
	}

	public void setModifyRemark(String modifyRemark) {
		this.modifyRemark = modifyRemark;
	}
	
	public Double getPrePenaltyMoney() {
		return prePenaltyMoney;
	}

	public void setPrePenaltyMoney(Double prePenaltyMoney) {
		this.prePenaltyMoney = prePenaltyMoney;
	}

	public Double getLatePenaltyMoney() {
		return latePenaltyMoney;
	}

	public void setLatePenaltyMoney(Double latePenaltyMoney) {
		this.latePenaltyMoney = latePenaltyMoney;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getActualRepaymentDt() {
		return actualRepaymentDt;
	}

	public void setActualRepaymentDt(Date actualRepaymentDt) {
		this.actualRepaymentDt = actualRepaymentDt;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public Double getSumReceivedProfit() {
		return sumReceivedProfit;
	}

	public void setSumReceivedProfit(Double sumReceivedProfit) {
		this.sumReceivedProfit = sumReceivedProfit;
	}

	public Double getSumWillProfit() {
		return sumWillProfit;
	}

	public void setSumWillProfit(Double sumWillProfit) {
		this.sumWillProfit = sumWillProfit;
	}

	public Date getLastRepaymentDt() {
		return LastRepaymentDt;
	}

	public void setLastRepaymentDt(Date lastRepaymentDt) {
		LastRepaymentDt = lastRepaymentDt;
	}

	public Date getFirstRepaymentDt() {
		return firstRepaymentDt;
	}

	public void setFirstRepaymentDt(Date firstRepaymentDt) {
		this.firstRepaymentDt = firstRepaymentDt;
	}

	public int getReceivedPeriods() {
		return receivedPeriods;
	}

	public void setReceivedPeriods(int receivedPeriods) {
		this.receivedPeriods = receivedPeriods;
	}

	public int getTotalPeriods() {
		return totalPeriods;
	}

	public void setTotalPeriods(int totalPeriods) {
		this.totalPeriods = totalPeriods;
	}

	private Long queryProjectId; //查询项目编号
	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}


}