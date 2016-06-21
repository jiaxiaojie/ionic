/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员基本信息变更历史Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerBaseHis extends DataEntity<CustomerBaseHis> {
	
	private static final long serialVersionUID = 1L;
	private CustomerBase customerBase;
	private Date createDt;		// 创建时间
	
	private String accountName;	//登录名
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public CustomerBaseHis() {
		super();
	}

	public CustomerBaseHis(String id){
		super(id);
	}

	public CustomerBase getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBase customerBase) {
		this.customerBase = customerBase;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
}