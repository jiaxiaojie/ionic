/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 大转盘中奖记录Entity
 * @author ydt
 * @version 2015-11-24
 */
public class MarketingWheelGetPrizeRecord extends DataEntity<MarketingWheelGetPrizeRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long prizeInstanceId;		// 奖品详情编号
	private String status;		// 状态
	private String token;		// 中奖token
	private Date getDt;		// 中奖时间
	private String opTerm;		// 操作终端
	private Date invalidDt;		// 失效时间
	private Long accountId;		// 中奖用户编号
	private Date giveDt;		// 奖品赠送时间
	private Date beginGetDt;		// 开始 中奖时间
	private Date endGetDt;		// 结束 中奖时间
	
	private String mobile;		//中奖用户手机号
	private String customerName;//中奖用户名
	private String prizeName;	//奖品名称
	private String prizeType;	//奖品类型
	private String prizeValue;	//奖品值
	
	private Long activityId;	//活动编号
	
	public MarketingWheelGetPrizeRecord() {
		super();
	}

	public MarketingWheelGetPrizeRecord(String id){
		super(id);
	}

	public Long getPrizeInstanceId() {
		return prizeInstanceId;
	}

	public void setPrizeInstanceId(Long prizeInstanceId) {
		this.prizeInstanceId = prizeInstanceId;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="中奖token长度必须介于 0 和 50 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGetDt() {
		return getDt;
	}

	public void setGetDt(Date getDt) {
		this.getDt = getDt;
	}
	
	@Length(min=0, max=2, message="操作终端长度必须介于 0 和 2 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvalidDt() {
		return invalidDt;
	}

	public void setInvalidDt(Date invalidDt) {
		this.invalidDt = invalidDt;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGiveDt() {
		return giveDt;
	}

	public void setGiveDt(Date giveDt) {
		this.giveDt = giveDt;
	}
	
	public Date getBeginGetDt() {
		return beginGetDt;
	}

	public void setBeginGetDt(Date beginGetDt) {
		this.beginGetDt = beginGetDt;
	}
	
	public Date getEndGetDt() {
		return endGetDt;
	}

	public void setEndGetDt(Date endGetDt) {
		this.endGetDt = endGetDt;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
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

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
}