/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 借贷产品审核记录Entity
 * @author yangtao
 * @version 2015-06-24
 */
public class ProjectReviewRecord extends DataEntity<ProjectReviewRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Long reviewUserId;		// 审核人员
	private Long reviewResult;		// 审核结果
	private Date reviewDt;		// 审核日期
	private String reviewRemark; //审核记录
	private User user; //审核人员信息
	private Long queryProjectId; //查询项目编号
	
	

	public ProjectReviewRecord() {
		super();
	}

	public ProjectReviewRecord(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	public Long getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(Long reviewResult) {
		this.reviewResult = reviewResult;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}
	
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

}