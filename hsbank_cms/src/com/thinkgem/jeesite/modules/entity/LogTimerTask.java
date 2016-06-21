/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 定时任务日志Entity
 * @author lizibo
 * @version 2015-08-14
 */
public class LogTimerTask extends DataEntity<LogTimerTask> {
	
	private static final long serialVersionUID = 1L;
	private String taskName;		// 任务名称
	private Date beginTime;      //用于查询
	private Date beginDt;		// 任务开始时间
	private Date endDt;		// 任务结束时间
	private Date endTime;      //用于查询
	private String status;		// 任务执行状态
	private String remark;		// 任务运行信息
	
	public LogTimerTask() {
		super();
	}

	public LogTimerTask(String id){
		super(id);
	}
	
	
	
	

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Length(min=0, max=50, message="任务名称长度必须介于 0 和 50 之间")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
	@Length(min=0, max=2, message="任务执行状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}