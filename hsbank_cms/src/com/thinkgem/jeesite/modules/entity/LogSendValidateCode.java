/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 验证码发送日志Entity
 * @author ydt
 * @version 2015-08-13
 */
public class LogSendValidateCode extends DataEntity<LogSendValidateCode> {
	
	private static final long serialVersionUID = 1L;
	private String mobile;		// 目标手机
	private String email;		// 目标邮箱地址
	private String serviceTypeCode;		// 操作类型
	private String validateCode;		// 验证码
	private Date opDt;		// 发送时间
	private String ip;		// Ip地址
	
	public LogSendValidateCode() {
		super();
	}

	public LogSendValidateCode(String id){
		super(id);
	}

	@Length(min=0, max=20, message="目标手机长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=50, message="目标邮箱地址长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=2, message="操作类型长度必须介于 0 和 2 之间")
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}
	
	@Length(min=0, max=20, message="验证码长度必须介于 0 和 20 之间")
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=50, message="Ip地址长度必须介于 0 和 50 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}