/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 私人订制项目Entity
 * @author yubin
 * @version 2016-05-18
 */
public class PersonalTailor extends DataEntity<PersonalTailor> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目名称
	private String code;		// 项目编号
	private String type;		// 项目类型
	private String amount;		// 项目金额
	private String duration;		// 项目期限
	private String descPic;		// 项目描述图片
	private String state;		// 状态
	private String rate;		// 最低年化利率
	private Date deadline;		// 投标截止日期
	private Date publishTime;		// 发布时间
	private String startingAmount;		// 起投金额
	
	public PersonalTailor() {
		super();
	}

	public PersonalTailor(String id){
		super(id);
	}

	@Length(min=0, max=128, message="项目名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="项目编号长度必须介于 0 和 32 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=1, message="项目类型长度必须介于 0 和 20之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=11, message="项目期限长度必须介于 0 和 20之间")
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getDescPic() {
		return HtmlUtils.htmlUnescape(descPic);
	}

	public void setDescPic(String descPic) {
		this.descPic = descPic;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	 
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	public String getStartingAmount() {
		return startingAmount;
	}

	public void setStartingAmount(String startingAmount) {
		this.startingAmount = startingAmount;
	}
	
}