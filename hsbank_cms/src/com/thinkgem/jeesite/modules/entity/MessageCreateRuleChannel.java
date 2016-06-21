/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;


import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 消息产生规则发送渠道Entity
 * @author ydt
 * @version 2016-01-14
 */
public class MessageCreateRuleChannel extends DataEntity<MessageCreateRuleChannel> {
	
	private static final long serialVersionUID = 1L;
	private Long ruleId;		// 消息产生规则编号
	private String messageChannel;		// 渠道
	
	public MessageCreateRuleChannel() {
		super();
	}

	public MessageCreateRuleChannel(String id){
		super(id);
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=0, max=2, message="渠道长度必须介于 0 和 2 之间")
	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}
	
}