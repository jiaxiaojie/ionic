/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员加息券清单Entity
 * @author ydt
 * @version 2016-04-05
 */
public class CustomerRateTicket extends DataEntity<CustomerRateTicket> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Long ticketTypeId;		// 加息券类型编号
	private Date getDt;		// 获得时间
	private Date invalidDt;		// 失效时间
	private String getRemark;		// 获得备注
	private Date useDt;		// 使用时间
	private String useRemark;		// 使用备注
	private Long useProjectId;		// 使用项目
	private String status;		// 状态
	
	private RateTicketType rateTicketType;	//加息券类型Entity
	
	//在某次投资中是否可用
	private Boolean enabled = true;
	
	public CustomerRateTicket() {
		super();
	}

	public CustomerRateTicket(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Long getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(Long ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGetDt() {
		return getDt;
	}

	public void setGetDt(Date getDt) {
		this.getDt = getDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvalidDt() {
		return invalidDt;
	}

	public void setInvalidDt(Date invalidDt) {
		this.invalidDt = invalidDt;
	}
	
	@Length(min=0, max=200, message="获得备注长度必须介于 0 和 200 之间")
	public String getGetRemark() {
		return getRemark;
	}

	public void setGetRemark(String getRemark) {
		this.getRemark = getRemark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUseDt() {
		return useDt;
	}

	public void setUseDt(Date useDt) {
		this.useDt = useDt;
	}
	
	@Length(min=0, max=200, message="使用备注长度必须介于 0 和 200 之间")
	public String getUseRemark() {
		return useRemark;
	}

	public void setUseRemark(String useRemark) {
		this.useRemark = useRemark;
	}
	
	public Long getUseProjectId() {
		return useProjectId;
	}

	public void setUseProjectId(Long useProjectId) {
		this.useProjectId = useProjectId;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RateTicketType getRateTicketType() {
		return rateTicketType;
	}

	public void setRateTicketType(RateTicketType rateTicketType) {
		this.rateTicketType = rateTicketType;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
}