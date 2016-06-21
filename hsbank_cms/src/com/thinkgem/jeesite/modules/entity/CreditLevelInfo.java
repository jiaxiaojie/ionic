/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 信用等级信息Entity
 * @author ydt
 * @version 2015-08-04
 */
public class CreditLevelInfo extends DataEntity<CreditLevelInfo> {
	
	private static final long serialVersionUID = 1L;
	private Double minScore;		// 得分范围最小值
	private Double maxScore;		// 得分范围最大值
	private String creditLevel;		// 信用等级
	private Double creditLimit;		// 信用额度
	
	public CreditLevelInfo() {
		super();
	}

	public CreditLevelInfo(String id){
		super(id);
	}

	public Double getMinScore() {
		return minScore;
	}

	public void setMinScore(Double minScore) {
		this.minScore = minScore;
	}
	
	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}
	
	@Length(min=0, max=20, message="信用等级长度必须介于 0 和 20 之间")
	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	
	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
}