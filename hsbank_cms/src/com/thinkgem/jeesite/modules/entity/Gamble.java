/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 竞猜Entity
 * @author ydt
 * @version 2016-04-20
 */
public class Gamble extends DataEntity<Gamble> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 用户
	private Long activityId;		// 活动编号
	private String choiceSide;		// 选择队伍
	private Date opDt;		// 竞猜时间
	private String  opTerm;   //竞猜终端
	private String rightSide;		// 比赛结果
	private Date awardDt;		// 领奖时间
	
	public Gamble() {
		super();
	}

	public Gamble(String id){
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
	
	@Length(min=0, max=20, message="选择队伍长度必须介于 0 和 20 之间")
	public String getChoiceSide() {
		return choiceSide;
	}

	public void setChoiceSide(String choiceSide) {
		this.choiceSide = choiceSide;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}

	@Length(min=0, max=20, message="比赛结果长度必须介于 0 和 20 之间")
	public String getRightSide() {
		return rightSide;
	}

	public void setRightSide(String rightSide) {
		this.rightSide = rightSide;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAwardDt() {
		return awardDt;
	}

	public void setAwardDt(Date awardDt) {
		this.awardDt = awardDt;
	}
	
}