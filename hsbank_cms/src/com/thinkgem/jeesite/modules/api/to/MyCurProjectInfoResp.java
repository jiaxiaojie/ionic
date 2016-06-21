package com.thinkgem.jeesite.modules.api.to;

/**
 * 我的活期项目信息
 * @author lizibo
 *
 */
public class MyCurProjectInfoResp {
	private Long projectId;		//项目id
	private String projectName;		//项目名称
	private Double annualizedRate;	//年化利率
	private Double amount;		//投资金额(持有本金)
	private Double applyRedeemPrincipal; 		//申请赎回金额
	private Double receivedProfit;		//累计收益
	private Double interest;		//可提取收益
	private Double interestOf10000Yuan;		//每万元日收益
	private String startDate;		//收益起始日
	private Double yesterdayProfit;		//昨日收益
	private int status;	//状态(3-投标中，4--投标结束，5-还款中，6--还款结束，7-清算结束
	private String statusName;	//状态名称

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getAnnualizedRate() {
		return annualizedRate;
	}

	public void setAnnualizedRate(Double annualizedRate) {
		this.annualizedRate = annualizedRate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getApplyRedeemPrincipal() {
		return applyRedeemPrincipal;
	}

	public void setApplyRedeemPrincipal(Double applyRedeemPrincipal) {
		this.applyRedeemPrincipal = applyRedeemPrincipal;
	}

	public Double getReceivedProfit() {
		return receivedProfit;
	}

	public void setReceivedProfit(Double receivedProfit) {
		this.receivedProfit = receivedProfit;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getInterestOf10000Yuan() {
		return interestOf10000Yuan;
	}

	public void setInterestOf10000Yuan(Double interestOf10000Yuan) {
		this.interestOf10000Yuan = interestOf10000Yuan;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Double getYesterdayProfit() {
		return yesterdayProfit;
	}

	public void setYesterdayProfit(Double yesterdayProfit) {
		this.yesterdayProfit = yesterdayProfit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
