/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员客户端访问流水Entity
 * @author ydt
 * @version 2015-06-23
 */
public class CustomerLoginLog extends DataEntity<CustomerLoginLog> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Date loginDt;		// 登录时间
	private String termCode;		// 登录终端类型
	private String remark;		// 登录备注信息
	private String equipmentSpecification;		// 终端规格
	
	public CustomerLoginLog() {
		super();
	}

	public CustomerLoginLog(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDt() {
		return loginDt;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}
	
	@Length(min=0, max=2, message="登录终端类型长度必须介于 0 和 2 之间")
	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
	@Length(min=0, max=500, message="登录备注信息长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=500, message="终端规格长度必须介于 0 和 500 之间")
	public String getEquipmentSpecification() {
		return equipmentSpecification;
	}

	public void setEquipmentSpecification(String equipmentSpecification) {
		this.equipmentSpecification = equipmentSpecification;
	}
	
}