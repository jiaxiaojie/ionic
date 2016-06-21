package com.thinkgem.jeesite.common.loan.util.bean;

import java.util.Date;


/**
 * 还款计划_bean
 * @author Arthur.Xie
 * 2015-07-27
 */
public class RepaymentPlanItem {
	/**当前期数*/
	private int index = 1;
	/**开始日期*/
	private Date beginDate = null;
	/**截止日期*/
	private Date endDate = null;
	/**应还本金+利息: Principal and Interest*/
	private double principalAndInterest = 0L;
	/**应还本金*/
	private double principal = 0L;
	/**剩余应还本金: Principal Remaining*/
	private double principalRemaining = 0L;
	/**应还利息（产品利息 + 加息券利息）*/
	private double interest = 0L;
	/**加息券利息*/
	private double rateTicketInterest = 0L;
	/**累计应还本金: Sum Principal*/
	private double sumPrincipal = 0L;
	/**累计应还利息: Sum Interest*/
	private double sumInterest = 0L;
	/**剩余应还本金 + 利息: Principal and Interest Remaining*/
	private double principalAndInterestRemaining = 0L;
	

	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getPrincipalAndInterest() {
		return principalAndInterest;
	}

	public void setPrincipalAndInterest(double principalAndInterest) {
		this.principalAndInterest = principalAndInterest;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	
	
	public double getRateTicketInterest() {
		return rateTicketInterest;
	}

	public void setRateTicketInterest(double rateTicketInterest) {
		this.rateTicketInterest = rateTicketInterest;
	}

	public double getPrincipalRemaining() {
		return principalRemaining;
	}

	public void setPrincipalRemaining(double principalRemaining) {
		this.principalRemaining = principalRemaining;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	public double getSumPrincipal() {
		return sumPrincipal;
	}

	public void setSumPrincipal(double sumPrincipal) {
		this.sumPrincipal = sumPrincipal;
	}

	public double getSumInterest() {
		return sumInterest;
	}

	public void setSumInterest(double sumInterest) {
		this.sumInterest = sumInterest;
	}

	public double getPrincipalAndInterestRemaining() {
		return principalAndInterestRemaining;
	}

	public void setPrincipalAndInterestRemaining(double principalAndInterestRemaining) {
		this.principalAndInterestRemaining = principalAndInterestRemaining;
	}
}
