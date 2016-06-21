package com.thinkgem.jeesite.modules.project.bean;

/**
 * 一次投资相关的金额
 * @author Administrator
 * 2015-08-11
 */
public class InvestAmountBean {
	/**投资金额*/
	Double amount = 0.00;
	/**选中的优惠券总金额（多张则相加）*/
	Double sumTicketAmount = 0.00;
	/**使用的平台垫付金额*/
	Double platformAmount = 0.00;
	/**应付金额*/
	Double payableAmount = 0.00;
	/**下家的手续费*/
	Double downServiceCharge = 0.00;
	/**当前投资用户余额*/
	Double balance = 0.00;
	/**差额=应付金额-当前投资用户余额*/
	Double differenceAmount = 0.00;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getSumTicketAmount() {
		return sumTicketAmount;
	}
	public void setSumTicketAmount(Double sumTicketAmount) {
		this.sumTicketAmount = sumTicketAmount;
	}
	public Double getPlatformAmount() {
		return platformAmount;
	}
	public void setPlatformAmount(Double platformAmount) {
		this.platformAmount = platformAmount;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getDifferenceAmount() {
		return differenceAmount;
	}
	public void setDifferenceAmount(Double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}
	public Double getDownServiceCharge() {
		return downServiceCharge;
	}
	public void setDownServiceCharge(Double downServiceCharge) {
		this.downServiceCharge = downServiceCharge;
	}
}
