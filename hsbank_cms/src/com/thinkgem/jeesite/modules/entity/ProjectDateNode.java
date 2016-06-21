/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目时间节点信息Entity
 * @author ydt
 * @version 2015-07-30
 */
public class ProjectDateNode extends DataEntity<ProjectDateNode> {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;		// 项目流水号
	private Date onLineDt;		// 上线时间
	private Date startFundingDt;		// 开始募资时间
	private Date endFundingDt;		// 募资结束时间
	private Date finishRepayDt;		// 还款结束时间
	
	public ProjectDateNode() {
		super();
	}

	public ProjectDateNode(String id){
		super(id);
	}

	@NotNull(message="项目流水号不能为空")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOnLineDt() {
		return onLineDt;
	}

	public void setOnLineDt(Date onLineDt) {
		this.onLineDt = onLineDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartFundingDt() {
		return startFundingDt;
	}

	public void setStartFundingDt(Date startFundingDt) {
		this.startFundingDt = startFundingDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndFundingDt() {
		return endFundingDt;
	}

	public void setEndFundingDt(Date endFundingDt) {
		this.endFundingDt = endFundingDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishRepayDt() {
		return finishRepayDt;
	}

	public void setFinishRepayDt(Date finishRepayDt) {
		this.finishRepayDt = finishRepayDt;
	}
	
	private Long queryProjectId; //查询项目编号
	public Long getQueryProjectId() {
		return queryProjectId;
	}

	public void setQueryProjectId(Long queryProjectId) {
		this.queryProjectId = queryProjectId;
	}

}