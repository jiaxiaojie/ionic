/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 日期信息Entity
 * @author ydt
 * @version 2015-12-09
 */
public class DateInfo extends DataEntity<DateInfo> {
	
	private static final long serialVersionUID = 1L;
	private Date date;		// 日期
	private String isWorkday;		// 是否工作日
	
	public DateInfo() {
		super();
	}

	public DateInfo(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Length(min=0, max=2, message="是否工作日长度必须介于 0 和 2 之间")
	public String getIsWorkday() {
		return isWorkday;
	}

	public void setIsWorkday(String isWorkday) {
		this.isWorkday = isWorkday;
	}
	
}