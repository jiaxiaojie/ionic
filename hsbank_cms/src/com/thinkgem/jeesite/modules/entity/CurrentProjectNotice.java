/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期产品公告Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectNotice extends DataEntity<CurrentProjectNotice> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目编号
	private String title;		// 标题
	private String content;		// 内容
	private Date publishDt;		// 发布时间
	private String status;		// 状态
	private Date beginPublishDt;		// 开始 发布时间
	private Date endPublishDt;		// 结束 发布时间
	private String queryParas; //下拉列表框查询选择
	
	private CurrentProjectInfo currentProjectInfo;
	
	
	
	public CurrentProjectInfo getCurrentProjectInfo() {
		return currentProjectInfo;
	}

	public void setCurrentProjectInfo(CurrentProjectInfo currentProjectInfo) {
		this.currentProjectInfo = currentProjectInfo;
	}

	public CurrentProjectNotice() {
		super();
	}

	public CurrentProjectNotice(String id){
		super(id);
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=50, message="标题长度必须介于 0 和 50 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDt() {
		return publishDt;
	}

	public void setPublishDt(Date publishDt) {
		this.publishDt = publishDt;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginPublishDt() {
		return beginPublishDt;
	}

	public void setBeginPublishDt(Date beginPublishDt) {
		this.beginPublishDt = beginPublishDt;
	}
	
	public Date getEndPublishDt() {
		return endPublishDt;
	}

	public void setEndPublishDt(Date endPublishDt) {
		this.endPublishDt = endPublishDt;
	}

	public String getQueryParas() {
		return queryParas;
	}

	public void setQueryParas(String queryParas) {
		this.queryParas = queryParas;
	}

	
		
}