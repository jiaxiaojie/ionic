/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期产品审批历史Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectReviewHis extends DataEntity<CurrentProjectReviewHis> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Date reviewDt;		// 审核时间
	private String reviewRemark;		// 审核意见
	private Long reviewUserId;		// 审核人
	private String reviewResult;		// 审核结果
	
	public CurrentProjectReviewHis() {
		super();
	}

	public CurrentProjectReviewHis(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}
	
	@Length(min=0, max=500, message="审核意见长度必须介于 0 和 500 之间")
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	@Length(min=0, max=2, message="审核结果长度必须介于 0 和 2 之间")
	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}
	
}