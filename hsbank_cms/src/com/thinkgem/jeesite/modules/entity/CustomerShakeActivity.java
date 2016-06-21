/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 摇一摇活动Entity
 * @author ydt
 * @version 2015-09-10
 */
public class CustomerShakeActivity extends DataEntity<CustomerShakeActivity> {
	
	private static final long serialVersionUID = 1L;
	private String shakeId;		// 摇奖编号
	private String mobile;		// 手机号
	private String customerName;		// 姓名
	private Integer denomination;		// 面额
	private String hasGived;		// 是否已赠送
	private String ip;		// ip
	private Date shakeDate;		// 摇奖时间
	private Date beginShakeDate;		// 开始 摇奖时间
	private Date endShakeDate;		// 结束 摇奖时间
	
	public CustomerShakeActivity() {
		super();
	}

	public CustomerShakeActivity(String id){
		super(id);
	}

	@Length(min=0, max=20, message="摇奖编号长度必须介于 0 和 20 之间")
	public String getShakeId() {
		return shakeId;
	}

	public void setShakeId(String shakeId) {
		this.shakeId = shakeId;
	}
	
	@Length(min=0, max=20, message="手机号长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Integer getDenomination() {
		return denomination;
	}

	public void setDenomination(Integer denomination) {
		this.denomination = denomination;
	}
	
	@Length(min=0, max=2, message="是否已赠送长度必须介于 0 和 2 之间")
	public String getHasGived() {
		return hasGived;
	}

	public void setHasGived(String hasGived) {
		this.hasGived = hasGived;
	}
	
	@Length(min=0, max=20, message="ip长度必须介于 0 和 20 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShakeDate() {
		return shakeDate;
	}

	public void setShakeDate(Date shakeDate) {
		this.shakeDate = shakeDate;
	}
	
	public Date getBeginShakeDate() {
		return beginShakeDate;
	}

	public void setBeginShakeDate(Date beginShakeDate) {
		this.beginShakeDate = beginShakeDate;
	}
	
	public Date getEndShakeDate() {
		return endShakeDate;
	}

	public void setEndShakeDate(Date endShakeDate) {
		this.endShakeDate = endShakeDate;
	}
		
}