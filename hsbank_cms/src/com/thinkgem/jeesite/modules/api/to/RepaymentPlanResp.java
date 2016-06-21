package com.thinkgem.jeesite.modules.api.to;


public class RepaymentPlanResp {

	private String planDate;		// 还款日期
	private String planMoney;		// 还款金额
	private String principal;		// 本金
	private String interest;		// 利息
	private String remainingPrincipal;		// 剩余本金
	private String status;		//状态
	private String statusName;		//状态名称
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(String planMoney) {
		this.planMoney = planMoney;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getRemainingPrincipal() {
		return remainingPrincipal;
	}
	public void setRemainingPrincipal(String remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	

}
