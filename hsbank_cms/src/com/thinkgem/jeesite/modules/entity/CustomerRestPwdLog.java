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
 * 密码重置记录Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerRestPwdLog extends DataEntity<CustomerRestPwdLog> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Date resetDt;		// 重置时间
	private String resetType;		// 重置方式
	private String status;		// 状态
	
	private String accountName;	//登录名
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public CustomerRestPwdLog() {
		super();
	}

	public CustomerRestPwdLog(String id){
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
	public Date getResetDt() {
		return resetDt;
	}

	public void setResetDt(Date resetDt) {
		this.resetDt = resetDt;
	}
	
	@Length(min=0, max=2, message="重置方式长度必须介于 0 和 2 之间")
	public String getResetType() {
		return resetType;
	}

	public void setResetType(String resetType) {
		this.resetType = resetType;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}