/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员联保人Entity
 * @author ydt
 * @version 2015-06-23
 */
public class CustomerCosurety extends DataEntity<CustomerCosurety> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String cosuretyName;		// 联保人
	private String cosuretyMobile;		// 联保人电话
	private String cosuretyRel;		// 联保人关系
	private String cosuretyCertNum;		// 联保人身份证号
	
	private String accountId;	//会员账号信息
	private String accountType;	//账号类型

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public CustomerCosurety() {
		super();
	}

	public CustomerCosurety(String id){
		super(id);
	}

	@NotNull(message="会员编号不能为空")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=200, message="联保人长度必须介于 0 和 200 之间")
	public String getCosuretyName() {
		return cosuretyName;
	}

	public void setCosuretyName(String cosuretyName) {
		this.cosuretyName = cosuretyName;
	}
	
	@Length(min=0, max=20, message="联保人电话长度必须介于 0 和 20 之间")
	public String getCosuretyMobile() {
		return cosuretyMobile;
	}

	public void setCosuretyMobile(String cosuretyMobile) {
		this.cosuretyMobile = cosuretyMobile;
	}
	
	@Length(min=0, max=2, message="联保人关系长度必须介于 0 和 2 之间")
	public String getCosuretyRel() {
		return cosuretyRel;
	}

	public void setCosuretyRel(String cosuretyRel) {
		this.cosuretyRel = cosuretyRel;
	}
	
	@Length(min=0, max=20, message="联保人身份证号长度必须介于 0 和 20 之间")
	public String getCosuretyCertNum() {
		return cosuretyCertNum;
	}

	public void setCosuretyCertNum(String cosuretyCertNum) {
		this.cosuretyCertNum = cosuretyCertNum;
	}
	
}