/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户接收push消息开关Entity
 * @author lzb
 * @version 2016-02-25
 */
public class CustomerPushSwitch extends DataEntity<CustomerPushSwitch> {
	
	private static final long serialVersionUID = 1L;
	private String pushChannel;		// 渠道
	private Long accountId;		// 账户编号
	private String isReceive;		// 是否接收
	
	public CustomerPushSwitch() {
		super();
	}

	public CustomerPushSwitch(String id){
		super(id);
	}

	@Length(min=0, max=2, message="渠道长度必须介于 0 和 2 之间")
	public String getPushChannel() {
		return pushChannel;
	}

	public void setPushChannel(String pushChannel) {
		this.pushChannel = pushChannel;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=2, message="是否接收长度必须介于 0 和 2 之间")
	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}
	
}