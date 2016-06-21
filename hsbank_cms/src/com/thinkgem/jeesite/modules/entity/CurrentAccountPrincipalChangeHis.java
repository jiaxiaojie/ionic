/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期账户本金变更历史Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentAccountPrincipalChangeHis extends DataEntity<CurrentAccountPrincipalChangeHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账户编号
	private Long projectId;		// 项目流水号
	private Double changeValue;		// 变更值
	private String changeType;		// 变更类型
	private String changeReason;		// 变更原因
	private String status;			//状态
	private String opTerm;		// 操作终端
	private Date opDt;		// 操作时间
	private String thirdAccountRequestNo;		// 第三方日志流水号
	private Double beginChangeValue;		// 开始 变更值
	private Double endChangeValue;		// 结束 变更值
	private CustomerBase cb; //投资会员信息
	private CustomerAccount ca;	//投资账户信息
	private CurrentProjectInfo pi; //活期项目信息
	
	public CurrentAccountPrincipalChangeHis() {
		super();
	}

	public CurrentAccountPrincipalChangeHis(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Double getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(Double changeValue) {
		this.changeValue = changeValue;
	}
	
	@Length(min=0, max=2, message="变更类型长度必须介于 0 和 2 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@Length(min=0, max=50, message="变更原因长度必须介于 0 和 50 之间")
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	@Length(min=0, max=2, message="操作终端长度必须介于 0 和 2 之间")
	public String getOpTerm() {
		return opTerm;
	}

	public void setOpTerm(String opTerm) {
		this.opTerm = opTerm;
	}

	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=50, message="第三方日志流水号长度必须介于 0 和 50 之间")
	public String getThirdAccountRequestNo() {
		return thirdAccountRequestNo;
	}

	public void setThirdAccountRequestNo(String thirdAccountRequestNo) {
		this.thirdAccountRequestNo = thirdAccountRequestNo;
	}
	
	public Double getBeginChangeValue() {
		return beginChangeValue;
	}

	public void setBeginChangeValue(Double beginChangeValue) {
		this.beginChangeValue = beginChangeValue;
	}
	
	public Double getEndChangeValue() {
		return endChangeValue;
	}

	public void setEndChangeValue(Double endChangeValue) {
		this.endChangeValue = endChangeValue;
	}

	public CustomerBase getCb() {
		return cb;
	}

	public void setCb(CustomerBase cb) {
		this.cb = cb;
	}

	public CurrentProjectInfo getPi() {
		return pi;
	}

	public void setPi(CurrentProjectInfo pi) {
		this.pi = pi;
	}

	public CustomerAccount getCa() {
		return ca;
	}

	public void setCa(CustomerAccount ca) {
		this.ca = ca;
	}
	
	
		
}