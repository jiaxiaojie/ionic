/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 大转盘奖品实例Entity
 * @author ydt
 * @version 2015-11-24
 */
public class MarketingWheelPrizeInstance extends DataEntity<MarketingWheelPrizeInstance> {
	
	private static final long serialVersionUID = 1L;
	private Long prizeId;		// 奖品编号
	private String status;		// 状态
	private String token;

	private String prizeType;		//奖品类型
	private String prizeValue;		//奖励值
	
	public MarketingWheelPrizeInstance() {
		super();
	}

	public MarketingWheelPrizeInstance(String id){
		super(id);
	}

	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizeValue() {
		return prizeValue;
	}

	public void setPrizeValue(String prizeValue) {
		this.prizeValue = prizeValue;
	}
}