/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 欧洲杯射手榜Entity
 * @author lzb
 * @version 2016-06-13
 */
public class EuropeanCupTopScorer extends DataEntity<EuropeanCupTopScorer> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 用户编号
	private Long activityId;		// 活动编号
	private Integer totalGoals;		// 累计进球总数
	private Integer usedScratchTimes;		// 已刮奖次数
	private Date opDt;		// 操作时间
	private String mobile;		//手机号
	
	public EuropeanCupTopScorer() {
		super();
	}

	public EuropeanCupTopScorer(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Integer getTotalGoals() {
		return totalGoals;
	}

	public void setTotalGoals(Integer totalGoals) {
		this.totalGoals = totalGoals;
	}

	public Integer getUsedScratchTimes() {
		return usedScratchTimes;
	}

	public void setUsedScratchTimes(Integer usedScratchTimes) {
		this.usedScratchTimes = usedScratchTimes;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}