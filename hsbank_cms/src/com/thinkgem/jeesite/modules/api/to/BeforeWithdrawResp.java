package com.thinkgem.jeesite.modules.api.to;

public class BeforeWithdrawResp {

	private String cardNo;		// 绑定的卡号
	private String status;	//银行卡绑定状态【VERIFYING（认证中），VERIFIED（已认证)】
	private String statusName;//银行卡绑定状态【VERIFYING（认证中），VERIFIED（已认证)】
	private String bankCode;	//银行卡所属银行
	private String bankName;	//银行卡所属银行名称
	private String bankLogo;	//银行Logo的URL
	private Double quota;	//每次限额
	private Double dayQuota;		//日限额
	private Double amount;		//可提现金额
	private Integer ticketCount; //提现券张数
	private String appointmentDate; //解绑提示时间
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankLogo() {
		return bankLogo;
	}
	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
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
	public Double getQuota() {
		return quota;
	}
	public void setQuota(Double quota) {
		this.quota = quota;
	}
	public Double getDayQuota() {
		return dayQuota;
	}
	public void setDayQuota(Double dayQuota) {
		this.dayQuota = dayQuota;
	}


	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}
