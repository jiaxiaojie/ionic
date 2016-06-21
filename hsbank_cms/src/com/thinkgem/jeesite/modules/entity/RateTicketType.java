/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 加息券类型Entity
 * @author ydt
 * @version 2016-04-05
 */
public class RateTicketType extends DataEntity<RateTicketType> {
	
	private static final long serialVersionUID = 1L;
	private String ticketTypeName;		// 加息券名称
	private Double rate;		// 加息
	private Integer rateDuration;		// 加息期限（天）
	private Double maxAmount;		// 额度上限（元）
	private Integer termOfValidity;		// 有效期限制（天）
	private String status;		// 状态
	private String useDescription;		// 使用说明
	private Long createUser;		// 创建人
	private Date createDt;		// 创建时间
	private Long lastModifyUser;		// 最后一次修改人
	private Date lastModifyDt;		// 修改时间
	
	public RateTicketType() {
		super();
	}

	public RateTicketType(String id){
		super(id);
	}

	@Length(min=0, max=20, message="加息券名称长度必须介于 0 和 20 之间")
	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public Integer getRateDuration() {
		return rateDuration;
	}

	public void setRateDuration(Integer rateDuration) {
		this.rateDuration = rateDuration;
	}
	
	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	public Integer getTermOfValidity() {
		return termOfValidity;
	}

	public void setTermOfValidity(Integer termOfValidity) {
		this.termOfValidity = termOfValidity;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1000, message="使用说明长度必须介于 0 和 1000 之间")
	public String getUseDescription() {
		return useDescription;
	}

	public void setUseDescription(String useDescription) {
		this.useDescription = useDescription;
	}
	
	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(Long lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastModifyDt() {
		return lastModifyDt;
	}

	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}
	
}