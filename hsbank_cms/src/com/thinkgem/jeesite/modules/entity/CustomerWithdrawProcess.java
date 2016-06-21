/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员提现审批Entity
 * @author yangtao
 * @version 2015-07-22
 */
public class CustomerWithdrawProcess extends DataEntity<CustomerWithdrawProcess> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Date createDt;		// 提现提交时间
	private String amount;		// 提现金额
	private String serviceMoney;		// 手续费
	private String ticketAmount;		// 用券金额
	private Date applyDt;		// 审批时间
	private String applyStatus;		// 审批状态
	private Long applyUserId;		// 审批人员
	private String thirdPartyReq;		// 提现执行流水号
	private String thirdPartyResult;		// 提现执行结果
	private Date beginCreateDt;		// 开始 提现提交时间
	private Date endCreateDt;		// 结束 提现提交时间
	
	public CustomerWithdrawProcess() {
		super();
	}

	public CustomerWithdrawProcess(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(String serviceMoney) {
		this.serviceMoney = serviceMoney;
	}
	
	public String getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(String ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDt() {
		return applyDt;
	}

	public void setApplyDt(Date applyDt) {
		this.applyDt = applyDt;
	}
	
	@Length(min=0, max=2, message="审批状态长度必须介于 0 和 2 之间")
	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	public Long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	@Length(min=0, max=50, message="提现执行流水号长度必须介于 0 和 50 之间")
	public String getThirdPartyReq() {
		return thirdPartyReq;
	}

	public void setThirdPartyReq(String thirdPartyReq) {
		this.thirdPartyReq = thirdPartyReq;
	}
	
	@Length(min=0, max=10, message="提现执行结果长度必须介于 0 和 10 之间")
	public String getThirdPartyResult() {
		return thirdPartyResult;
	}

	public void setThirdPartyResult(String thirdPartyResult) {
		this.thirdPartyResult = thirdPartyResult;
	}
	
	public Date getBeginCreateDt() {
		return beginCreateDt;
	}

	public void setBeginCreateDt(Date beginCreateDt) {
		this.beginCreateDt = beginCreateDt;
	}
	
	public Date getEndCreateDt() {
		return endCreateDt;
	}

	public void setEndCreateDt(Date endCreateDt) {
		this.endCreateDt = endCreateDt;
	}
		
}