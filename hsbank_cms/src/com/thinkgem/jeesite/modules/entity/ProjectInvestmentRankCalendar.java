/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 投资排行日历Entity
 * @author lizibo
 * @version 2015-11-25
 */
public class ProjectInvestmentRankCalendar extends DataEntity<ProjectInvestmentRankCalendar> {
	
	private static final long serialVersionUID = 1L;
	private String year;		// 年份
	private String month;		//
	private int week;		// 周数
	private String weekName;	//周描述
	private Date beginDt;		// 开始时间
	private Date endDt;		// 结束时间
	private String status;		// 状态
	
	public ProjectInvestmentRankCalendar() {
		super();
	}

	public ProjectInvestmentRankCalendar(String id){
		super(id);
	}

	@Length(min=0, max=6, message="年份长度必须介于 0 和 6 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
	
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
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
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
}