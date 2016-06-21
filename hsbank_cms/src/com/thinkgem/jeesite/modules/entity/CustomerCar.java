/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员车辆信息Entity
 * @author ydt
 * @version 2015-07-08
 */
public class CustomerCar extends DataEntity<CustomerCar> {
	
	private static final long serialVersionUID = 1L;
	private Long customerId;		// 会员编号
	private String carType;		// 车辆型号
	private Integer loanValue;	//未还贷款（单位：元）
	private Integer distance;		// 行驶路程（单位：千米）
	private String buyYear;			//购买年份
	private String carFile;		//人车合照
	
	private Long accountId;
	private String accountType;	//账号类型
	
	public CustomerCar() {
		super();
	}

	public CustomerCar(String id){
		super(id);
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=20, message="车辆型号长度必须介于 0 和 20 之间")
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	@Length(min=0, max=4, message="购买年份长度必须介于 0 和 4 之间")
	public String getBuyYear() {
		return buyYear;
	}

	public void setBuyYear(String buyYear) {
		this.buyYear = buyYear;
	}
	
	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
	public Integer getLoanValue() {
		return loanValue;
	}

	public void setLoanValue(Integer loanValue) {
		this.loanValue = loanValue;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Length(min=0, max=500, message="人车合照长度必须介于 0 和 500 之间")
	public String getCarFile() {
		return carFile;
	}

	public void setCarFile(String carFile) {
		this.carFile = carFile;
	}
	
}