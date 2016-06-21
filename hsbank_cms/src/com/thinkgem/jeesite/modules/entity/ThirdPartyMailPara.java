/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 邮件发送参数Entity
 * @author yangtao
 * @version 2015-08-13
 */
public class ThirdPartyMailPara extends DataEntity<ThirdPartyMailPara> {
	
	private static final long serialVersionUID = 1L;
	private String mailServerHost;		// 邮件服务器
	private String mailSmtpAuth;		// 发送验证
	private String mailSmtpTimeout;		// 发送超时
	private String mailUserName;		// 邮件账号
	private String mailPassword;		// 邮箱密码
	
	public ThirdPartyMailPara() {
		super();
	}

	public ThirdPartyMailPara(String id){
		super(id);
	}

	@Length(min=0, max=50, message="邮件服务器长度必须介于 0 和 50 之间")
	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}
	
	@Length(min=0, max=50, message="发送验证长度必须介于 0 和 50 之间")
	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}
	
	@Length(min=0, max=50, message="发送超时长度必须介于 0 和 50 之间")
	public String getMailSmtpTimeout() {
		return mailSmtpTimeout;
	}

	public void setMailSmtpTimeout(String mailSmtpTimeout) {
		this.mailSmtpTimeout = mailSmtpTimeout;
	}
	
	@Length(min=0, max=50, message="邮件账号长度必须介于 0 和 50 之间")
	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}
	
	@Length(min=0, max=50, message="邮箱密码长度必须介于 0 和 50 之间")
	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	
}