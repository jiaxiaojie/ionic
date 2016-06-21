package com.thinkgem.jeesite.modules.api.to;
/**
 * 还款日历详细信息
 * @author lizibo
 *
 */
public class RepaymentCalendarDetailResp {

	private String projectName;		// 项目名称
	private String planDate;		// 还款日期
	private Double planMoney;		// 还款金额
	private Double principal;		// 应还本金
	private Double interest;		// 应还利息
	private Double remainingPrincipal;		// 剩余应还本金
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public Double getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(Double planMoney) {
		this.planMoney = planMoney;
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
	public Double getRemainingPrincipal() {
		return remainingPrincipal;
	}
	public void setRemainingPrincipal(Double remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}
	
	
}
