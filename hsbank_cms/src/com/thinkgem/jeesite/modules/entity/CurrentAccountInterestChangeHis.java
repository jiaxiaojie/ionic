/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期账户利息变更历史Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentAccountInterestChangeHis extends DataEntity<CurrentAccountInterestChangeHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账户编号
	private Long projectId;		// 项目流水号
	private Double changeValue;		// 变更值
	private String changeType;		// 变更类型
	private String changeReason;		// 变更原因
	private String opTerm;		// 操作终端
	private Date opDt;		// 操作时间
	private String thirdAccountRequestNo;		// 第三方日志流水号
	private Date beginOpDt;		// 开始 操作时间
	private Date endOpDt;		// 结束 操作时间
	
	private CurrentProjectInfo currentProjectInfo; //活期项目信息
	private CustomerBase cb; //投资会员信息
	
	
	
	public CustomerBase getCb() {
		return cb;
	}

	public void setCb(CustomerBase cb) {
		this.cb = cb;
	}

	public CurrentProjectInfo getCurrentProjectInfo() {
		return currentProjectInfo;
	}

	public void setCurrentProjectInfo(CurrentProjectInfo currentProjectInfo) {
		this.currentProjectInfo = currentProjectInfo;
	}

	public CurrentAccountInterestChangeHis() {
		super();
	}

	public CurrentAccountInterestChangeHis(String id){
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
	
	public Date getBeginOpDt() {
		return beginOpDt;
	}

	public void setBeginOpDt(Date beginOpDt) {
		this.beginOpDt = beginOpDt;
	}
	
	public Date getEndOpDt() {
		return endOpDt;
	}

	public void setEndOpDt(Date endOpDt) {
		this.endOpDt = endOpDt;
	}
		
}