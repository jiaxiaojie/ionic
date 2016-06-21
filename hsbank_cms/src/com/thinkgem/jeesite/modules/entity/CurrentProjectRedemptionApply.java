/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期产品赎回申请Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectRedemptionApply extends DataEntity<CurrentProjectRedemptionApply> {
	
	private static final long serialVersionUID = 1L;
	private Long holdId;		// 活期产品持有流水号
	private Double redeemPrincipal;		// 赎回本金
	private Double redeemInterest;		// 赎回利息
	private String applyTerm;		// 申请终端
	private Date applyDt;		// 申请时间
	private String status;		// 状态
	private Date reviewDt;		// 审批时间
	private Long reviewUserId;		// 审批人
	private Date finishDt;		// 完成时间
	private Date beginApplyDt;		// 开始 申请时间
	private Date endApplyDt;		// 结束 申请时间
	
	private Long projectId;
	private Long accountId;		// 账户编号
	
	private CurrentProjectHoldInfo currentProjectHoldInfo;
	
	
	
	public CurrentProjectHoldInfo getCurrentProjectHoldInfo() {
		return currentProjectHoldInfo;
	}

	public void setCurrentProjectHoldInfo(CurrentProjectHoldInfo currentProjectHoldInfo) {
		this.currentProjectHoldInfo = currentProjectHoldInfo;
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

	public CurrentProjectRedemptionApply() {
		super();
	}

	public CurrentProjectRedemptionApply(String id){
		super(id);
	}

	public Long getHoldId() {
		return holdId;
	}

	public void setHoldId(Long holdId) {
		this.holdId = holdId;
	}
	
	public Double getRedeemPrincipal() {
		return redeemPrincipal;
	}

	public void setRedeemPrincipal(Double redeemPrincipal) {
		this.redeemPrincipal = redeemPrincipal;
	}
	
	public Double getRedeemInterest() {
		return redeemInterest;
	}

	public void setRedeemInterest(Double redeemInterest) {
		this.redeemInterest = redeemInterest;
	}
	
	@Length(min=0, max=2, message="申请终端长度必须介于 0 和 2 之间")
	public String getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(String applyTerm) {
		this.applyTerm = applyTerm;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDt() {
		return applyDt;
	}

	public void setApplyDt(Date applyDt) {
		this.applyDt = applyDt;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishDt() {
		return finishDt;
	}

	public void setFinishDt(Date finishDt) {
		this.finishDt = finishDt;
	}
	
	public Date getBeginApplyDt() {
		return beginApplyDt;
	}

	public void setBeginApplyDt(Date beginApplyDt) {
		this.beginApplyDt = beginApplyDt;
	}
	
	public Date getEndApplyDt() {
		return endApplyDt;
	}

	public void setEndApplyDt(Date endApplyDt) {
		this.endApplyDt = endApplyDt;
	}
		
}