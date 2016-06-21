/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户接收消息开关Entity
 * @author ydt
 * @version 2016-01-15
 */
public class ReceiveMessageSwitch extends DataEntity<ReceiveMessageSwitch> {
	
	private static final long serialVersionUID = 1L;
	private String messageChannel;		// 渠道
	private Long accountId;		// 账户编号
	private String isReceive;		// 是否接收
	private String messageType;
	
	private String[] messageChannels;//查询参数，消息渠道编号列表，用,号分割
	
	
	


	public String[] getMessageChannels() {
		return messageChannels;
	}

	public void setMessageChannels(String[] messageChannels) {
		this.messageChannels = messageChannels;
	}

	public ReceiveMessageSwitch() {
		super();
	}

	public ReceiveMessageSwitch(String id){
		super(id);
	}

	
	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Length(min=0, max=2, message="渠道长度必须介于 0 和 2 之间")
	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
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