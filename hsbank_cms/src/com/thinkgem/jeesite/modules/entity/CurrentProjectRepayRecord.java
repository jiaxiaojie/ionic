/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期产品付款记录Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectRepayRecord extends DataEntity<CurrentProjectRepayRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Double principal;		// 本金
	private Double interest;		// 利息
	private Date repayDt;		// 付款日期
	private Date beginRepayDt;		// 开始 付款日期
	private Date endRepayDt;		// 结束 付款日期
	
	public CurrentProjectRepayRecord() {
		super();
	}

	public CurrentProjectRepayRecord(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepayDt() {
		return repayDt;
	}

	public void setRepayDt(Date repayDt) {
		this.repayDt = repayDt;
	}
	
	public Date getBeginRepayDt() {
		return beginRepayDt;
	}

	public void setBeginRepayDt(Date beginRepayDt) {
		this.beginRepayDt = beginRepayDt;
	}
	
	public Date getEndRepayDt() {
		return endRepayDt;
	}

	public void setEndRepayDt(Date endRepayDt) {
		this.endRepayDt = endRepayDt;
	}
		
}