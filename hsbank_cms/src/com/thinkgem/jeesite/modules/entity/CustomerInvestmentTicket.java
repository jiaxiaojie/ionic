/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员投资券清单Entity
 * @author yangtao
 * @version 2015-07-21
 */
public class CustomerInvestmentTicket extends DataEntity<CustomerInvestmentTicket> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;			// 账号编号
	private Long ticketTypeId;		// 投资券类型编号
	private Date getDt;				// 获得时间
	private String getRemark;		// 获得备注
	private Date useDt;				// 使用时间
	private String useRemark;		// 使用备注
	private String status;			// 状态
	private Date beginGetDt;		// 开始 获得时间
	private Date endGetDt;			// 结束 获得时间
	private Date beginUseDt;		// 开始 使用时间
	private Date endUseDt;			// 结束 使用时间
	private Date invalidDt; 		// 失效时间
	
	//在某次投资中是否可用
	private Boolean enabled = false;
	
	private InvestmentTicketType investmentTicketType;
	
	public Date getInvalidDt() {
		return invalidDt;
	}

	public void setInvalidDt(Date invalidDt) {
		this.invalidDt = invalidDt;
	}

	public CustomerInvestmentTicket() {
		super();
	}

	public CustomerInvestmentTicket(String id){
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
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginGetDt() {
		return beginGetDt;
	}

	public void setBeginGetDt(Date beginGetDt) {
		this.beginGetDt = beginGetDt;
	}
	
	public Date getEndGetDt() {
		return endGetDt;
	}

	public void setEndGetDt(Date endGetDt) {
		this.endGetDt = endGetDt;
	}
		
	public Date getBeginUseDt() {
		return beginUseDt;
	}

	public void setBeginUseDt(Date beginUseDt) {
		this.beginUseDt = beginUseDt;
	}
	
	public Date getEndUseDt() {
		return endUseDt;
	}

	public void setEndUseDt(Date endUseDt) {
		this.endUseDt = endUseDt;
	}

	public InvestmentTicketType getInvestmentTicketType() {
		return investmentTicketType;
	}

	public void setInvestmentTicketType(InvestmentTicketType investmentTicketType) {
		this.investmentTicketType = investmentTicketType;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
		
}