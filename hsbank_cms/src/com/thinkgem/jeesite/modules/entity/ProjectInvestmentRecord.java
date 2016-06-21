/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 投资记录Entity
 * @author yangtao
 * @version 2015-06-24
 */
public class ProjectInvestmentRecord extends DataEntity<ProjectInvestmentRecord> {
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Long transferProjectId; //转让项目编号
	private Long investmentUserId;		// 投资人
	private Double amount;		// 投资金额
	private String investmentType;		// 投资方式
	private String opTerm;		// 操作终端
	private Date opDt;		// 投资时间
	private String status;		// 投资状态
	private String thirdPartyOrder;		// 投资交易第三方流水编号
	private Date beginOpDt;		// 开始 投资时间
	private Date endOpDt;		// 结束 投资时间
	private Long queryProjectId; //查询项目编号
	private Double actualAmount;//实际投资金额
	private Double ticketAmount;//抵用券金额
	private String ticketIds; //抵用券编号列表，逗号分隔
	private String rateTicketIds;	//加息券ID
	private Double willProfit; //预计收益
	private Double realProfit; //实际收益
	private Double willReceivePrincipal;//待收本金
	private Double willReceiveInterest;//待收利息
	private Double platformAmount;	//平台垫付金额（使用的抵用金额）
	private Double toPlatformMoney;	//给平台金额
	private Double toBorrowersUserMoney;	//给融资人金额
	private Double sumWillReceive; //待收本息
	private Double hasReceive;//已收本息
	private Double upToPlatformMoney;//上家应付平台服务费
	private Double downToPlatformMoney;//下家应付平台服务费
	
	private Double sumProfit;//逾期总收益 预期收益加本金 暂时未用
	
	
	private ProjectBaseInfo projectBaseInfo ;//对应项目信息
	private ProjectExecuteSnapshot pes;//执行快照
	private boolean canTransferFlag; //能否转让标志
	private ProjectBaseInfo pb; //借贷合同明细
	
	
	public ProjectBaseInfo getPb() {
		return pb;
	}

	public void setPb(ProjectBaseInfo pb) {
		this.pb = pb;
	}

	private Date finishDate;
	
	
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Double getHasReceive() {
		return hasReceive;
	}

	public void setHasReceive(Double hasReceive) {
		this.hasReceive = hasReceive;
	}

	
	public boolean isCanTransferFlag() {
		return canTransferFlag;
	}

	public void setCanTransferFlag(boolean canTransferFlag) {
		this.canTransferFlag = canTransferFlag;
	}

	public Double getSumWillReceive() {
		return sumWillReceive;
	}

	public void setSumWillReceive(Double sumWillReceive) {
		this.sumWillReceive = sumWillReceive;
	}
	public Double getSumProfit() {
		return sumProfit;
	}

	public void setSumProfit(Double sumProfit) {
		this.sumProfit = sumProfit;
	}

	
	
	public ProjectExecuteSnapshot getPes() {
		return pes;
	}

	public void setPes(ProjectExecuteSnapshot pes) {
		this.pes = pes;
	}

	public ProjectBaseInfo getProjectBaseInfo() {
		return projectBaseInfo;
	}

	public void setProjectBaseInfo(ProjectBaseInfo projectBaseInfo) {
		this.projectBaseInfo = projectBaseInfo;
	}
	public Long getTransferProjectId() {
		return transferProjectId;
	}

	public void setTransferProjectId(Long transferProjectId) {
		this.transferProjectId = transferProjectId;
	}

	public Double getAmount() {
		return amount;
	}

	public Double getWillProfit() {
		return willProfit;
	}

	public void setWillProfit(Double willProfit) {
		this.willProfit = willProfit;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Double getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(Double ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public String getTicketIds() {
		return ticketIds;
	}

	public void setTicketIds(String ticketIds) {
		this.ticketIds = ticketIds;
	}
	
	

	public String getRateTicketIds() {
		return rateTicketIds;
	}

	public void setRateTicketIds(String rateTicketIds) {
		this.rateTicketIds = rateTicketIds;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	private CustomerBase cb; //投资会员信息
	

	private CustomerAccount ca; //投资账户信息
	
	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

	public ProjectInvestmentRecord() {
		super();
	}

	public ProjectInvestmentRecord(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getInvestmentUserId() {
		return investmentUserId;
	}

	public void setInvestmentUserId(Long investmentUserId) {
		this.investmentUserId = investmentUserId;
	}
	
	
	
	@Length(min=0, max=11, message="投资方式长度必须介于 0 和 11 之间")
	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}
	
	@Length(min=0, max=11, message="操作终端长度必须介于 0 和 11 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=11, message="投资状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=200, message="投资交易第三方流水编号长度必须介于 0 和 200 之间")
	public String getThirdPartyOrder() {
		return thirdPartyOrder;
	}

	public void setThirdPartyOrder(String thirdPartyOrder) {
		this.thirdPartyOrder = thirdPartyOrder;
	}
	
	public Date getBeginOpDt() {
		return beginOpDt;
	}

	public void setBeginOpDt(Date beginOpDt) {
		this.beginOpDt = beginOpDt;
	}
	
	public Date getEndOpDt() {
		return endOpDt;
	}

	public void setEndOpDt(Date endOpDt) {
		this.endOpDt = endOpDt;
	}
	public CustomerBase getCb() {
		return cb;
	}

	public void setCb(CustomerBase cb) {
		this.cb = cb;
	}

	public CustomerAccount getCa() {
		return ca;
	}

	public void setCa(CustomerAccount ca) {
		this.ca = ca;
	}
		
	public Double getRealProfit() {
		return realProfit;
	}

	public void setRealProfit(Double realProfit) {
		this.realProfit = realProfit;
	}

	public Double getPlatformAmount() {
		return platformAmount;
	}

	public void setPlatformAmount(Double platformAmount) {
		this.platformAmount = platformAmount;
	}

	public Double getWillReceivePrincipal() {
		return willReceivePrincipal;
	}

	public void setWillReceivePrincipal(Double willReceivePrincipal) {
		this.willReceivePrincipal = willReceivePrincipal;
	}

	public Double getWillReceiveInterest() {
		return willReceiveInterest;
	}

	public void setWillReceiveInterest(Double willReceiveInterest) {
		this.willReceiveInterest = willReceiveInterest;
	}

	public Double getToPlatformMoney() {
		return toPlatformMoney;
	}

	public void setToPlatformMoney(Double toPlatformMoney) {
		this.toPlatformMoney = toPlatformMoney;
	}

	public Double getToBorrowersUserMoney() {
		return toBorrowersUserMoney;
	}

	public void setToBorrowersUserMoney(Double toBorrowersUserMoney) {
		this.toBorrowersUserMoney = toBorrowersUserMoney;
	}

	public Double getUpToPlatformMoney() {
		return upToPlatformMoney;
	}

	public void setUpToPlatformMoney(Double upToPlatformMoney) {
		this.upToPlatformMoney = upToPlatformMoney;
	}

	public Double getDownToPlatformMoney() {
		return downToPlatformMoney;
	}

	public void setDownToPlatformMoney(Double downToPlatformMoney) {
		this.downToPlatformMoney = downToPlatformMoney;
	}


}