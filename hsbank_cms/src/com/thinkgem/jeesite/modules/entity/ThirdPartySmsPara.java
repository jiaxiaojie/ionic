/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 短信通道参数Entity
 * @author yangtao
 * @version 2015-08-03
 */
public class ThirdPartySmsPara extends DataEntity<ThirdPartySmsPara> {
	
	private static final long serialVersionUID = 1L;
	private String smsApiUrl;		// 短信通道地址
	private String smsApiKey;		// 短信通道账号
	private String smsSecretKey;		// 短信通道密码
	
	public ThirdPartySmsPara() {
		super();
	}

	public ThirdPartySmsPara(String id){
		super(id);
	}

	@Length(min=0, max=500, message="短信通道地址长度必须介于 0 和 500 之间")
	public String getSmsApiUrl() {
		return smsApiUrl;
	}

	public void setSmsApiUrl(String smsApiUrl) {
		this.smsApiUrl = smsApiUrl;
	}
	
	@Length(min=0, max=500, message="短信通道账号长度必须介于 0 和 500 之间")
	public String getSmsApiKey() {
		return smsApiKey;
	}

	public void setSmsApiKey(String smsApiKey) {
		this.smsApiKey = smsApiKey;
	}
	
	@Length(min=0, max=500, message="短信通道密码长度必须介于 0 和 500 之间")
	public String getSmsSecretKey() {
		return smsSecretKey;
	}

	public void setSmsSecretKey(String smsSecretKey) {
		this.smsSecretKey = smsSecretKey;
	}
	
}