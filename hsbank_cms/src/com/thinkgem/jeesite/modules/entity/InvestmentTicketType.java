/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 投资券类型管理Entity
 * @author yangtao
 * @version 2015-07-21
 */
public class InvestmentTicketType extends DataEntity<InvestmentTicketType> {
	
	private static final long serialVersionUID = 1L;
	private String ticketTypeName;		// 投资券名称
	private Integer denomination;		// 面值
	private String useInfo;		// 使用说明
	private Integer useLimit;		// 使用限制
	private Integer termOfValidity;		// 有效期限制
	private String status;		// 状态
	private Long createUser;		// 创建人
	private Date createDt;		// 创建时间
	private Long lastModifyUser;		// 最后一次修改人
	private Date lastModifyDt;		// 修改时间
	private String beginDenomination;		// 开始 面值
	private String endDenomination;		// 结束 面值
	
	private User cUser;
	private User mUser;
	
	public InvestmentTicketType() {
		super();
	}

	public InvestmentTicketType(String id){
		super(id);
	}

	@Length(min=0, max=20, message="投资券名称长度必须介于 0 和 20 之间")
	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String certTypeName) {
		this.ticketTypeName = certTypeName;
	}
	
	public Integer getDenomination() {
		return denomination;
	}

	public void setDenomination(Integer denomination) {
		this.denomination = denomination;
	}
	
	@Length(min=0, max=500, message="使用说明长度必须介于 0 和 500 之间")
	public String getUseInfo() {
		return useInfo;
	}

	public void setUseInfo(String useInfo) {
		this.useInfo = useInfo;
	}
	
	public Integer getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(Integer useLimit) {
		this.useLimit = useLimit;
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
	
	public String getBeginDenomination() {
		return beginDenomination;
	}

	public void setBeginDenomination(String beginDenomination) {
		this.beginDenomination = beginDenomination;
	}
	
	public String getEndDenomination() {
		return endDenomination;
	}

	public void setEndDenomination(String endDenomination) {
		this.endDenomination = endDenomination;
	}
	public User getcUser() {
		return cUser;
	}

	public void setcUser(User cUser) {
		this.cUser = cUser;
	}

	public User getmUser() {
		return mUser;
	}

	public void setmUser(User mUser) {
		this.mUser = mUser;
	}
		
}