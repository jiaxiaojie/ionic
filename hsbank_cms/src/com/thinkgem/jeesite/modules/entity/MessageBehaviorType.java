/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 行为编码Entity
 * @author ydt
 * @version 2016-01-19
 */
public class MessageBehaviorType extends DataEntity<MessageBehaviorType> {
	
	private static final long serialVersionUID = 1L;
	private String behaviorCode;		// 行为编号
	private String behaviorName;		// 行为名称
	
	public MessageBehaviorType() {
		super();
	}

	public MessageBehaviorType(String id){
		super(id);
	}

	@Length(min=0, max=6, message="行为编号长度必须介于 0 和 6 之间")
	public String getBehaviorCode() {
		return behaviorCode;
	}

	public void setBehaviorCode(String behaviorCode) {
		this.behaviorCode = behaviorCode;
	}
	
	@Length(min=0, max=200, message="行为名称长度必须介于 0 和 200 之间")
	public String getBehaviorName() {
		return behaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName;
	}
	
}