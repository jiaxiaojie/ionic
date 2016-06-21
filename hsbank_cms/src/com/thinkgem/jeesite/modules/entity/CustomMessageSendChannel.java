/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;


import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自定义消息发送渠道Entity
 * @author ydt
 * @version 2016-01-14
 */
public class CustomMessageSendChannel extends DataEntity<CustomMessageSendChannel> {
	
	private static final long serialVersionUID = 1L;
	private Long customerMessageId;		// 自定义消息编号
	private String messageChannel;		// 渠道
	
	public CustomMessageSendChannel() {
		super();
	}

	public CustomMessageSendChannel(String id){
		super(id);
	}

	public Long getCustomerMessageId() {
		return customerMessageId;
	}

	public void setCustomerMessageId(Long customerMessageId) {
		this.customerMessageId = customerMessageId;
	}

	@Length(min=0, max=2, message="渠道长度必须介于 0 和 2 之间")
	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}
	
}