/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目显示终端Entity
 * @author ydt
 * @version 2015-11-16
 */
public class ProjectShowTerm extends DataEntity<ProjectShowTerm> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目编号
	private String termCode;		// 终端
	
	public ProjectShowTerm() {
		super();
	}

	public ProjectShowTerm(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=2, message="终端长度必须介于 0 和 2 之间")
	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
}