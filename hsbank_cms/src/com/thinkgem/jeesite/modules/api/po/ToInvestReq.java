package com.thinkgem.jeesite.modules.api.po;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ToInvestReq extends DataEntity<ToInvestReq>{
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目流水号
	private String transferProjectId; //转让项目编号
	private String amount;		// 投资金额
	private String ticketIds; //抵用券编号列表，逗号分隔
	private String rateTicketIds;	//加息券ids
	private String ticketAmount;//抵用券金额
	private String platformAmount;	//平台垫付金额
	private String type;	//直投（"1"）、债权转让（"2"）
	
	@NotBlank(message="项目流水号不能为空")
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTransferProjectId() {
		return transferProjectId;
	}
	public void setTransferProjectId(String transferProjectId) {
		this.transferProjectId = transferProjectId;
	}
	@NotBlank(message="投资金额不能为空")
	@Length(min=0, max=13, message="投资金额长度必须介于 0 和 13 之间")
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTicketIds() {
		return ticketIds;
	}
	public void setTicketIds(String ticketIds) {
		this.ticketIds = ticketIds;
	}
	public String getTicketAmount() {
		return ticketAmount;
	}
	public void setTicketAmount(String ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	public String getPlatformAmount() {
		return platformAmount;
	}
	public void setPlatformAmount(String platformAmount) {
		this.platformAmount = platformAmount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRateTicketIds() {
		return rateTicketIds;
	}
	public void setRateTicketIds(String rateTicketIds) {
		this.rateTicketIds = rateTicketIds;
	}

	
}
