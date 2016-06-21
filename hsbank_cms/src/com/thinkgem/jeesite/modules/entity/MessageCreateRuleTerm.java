/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 消息产生规则适用终端Entity
 * @author ydt
 * @version 2016-01-15
 */
public class MessageCreateRuleTerm extends DataEntity<MessageCreateRuleTerm> {
	
	private static final long serialVersionUID = 1L;
	private Long ruleId;		// 消息产生规则编号
	private String opTerm;		// 终端
	
	public MessageCreateRuleTerm() {
		super();
	}

	public MessageCreateRuleTerm(String id){
		super(id);
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=0, max=2, message="终端长度必须介于 0 和 2 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
}