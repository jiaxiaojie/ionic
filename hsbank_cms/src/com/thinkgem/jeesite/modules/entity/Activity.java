/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动Entity
 * @author wanduanrui
 * @version 2015-11-24
 */
public class Activity extends DataEntity<Activity> {
	
	private static final long serialVersionUID = 1L;
	private String title;                   //活动标题
	private String activityDescription;		// 活动描述
	private String activityJoin;		// 活动链接
	private String type;
	private String target;
	private String activityCover;		// 活动封面
	private String activityStatus;		// 活动状态
	private Date startDt;             //活动开始时间
	private Date endDt;               //活动结束时间
	private String createUserId;    //创建人id
	private Date createDt;         //创建日期
	private String reviewUserId;  //审核人id
	private Date reviewDt;       //审核日期
	private Integer sort; 	// 排序
	
	private String[] termCodes;
	
	public Activity() {
		super();
	}

	public Activity(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String[] getTermCodes() {
		return termCodes;
	}

	public void setTermCodes(String[] termCodes) {
		this.termCodes = termCodes;
	}
	
	public String getTermCode() {
		return termCodes==null?null:termCodes[0];
	}

	@Length(min=0, max=255, message="活动描述长度必须介于 0 和 255 之间")
	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	
	@Length(min=0, max=255, message="活动链接长度必须介于 0 和 255 之间")
	public String getActivityJoin() {
		return activityJoin;
	}

	public void setActivityJoin(String activityJoin) {
		this.activityJoin = activityJoin;
	}
	
	@Length(min=0, max=255, message="活动封面长度必须介于 0 和 255 之间")
	public String getActivityCover() {
		return activityCover;
	}

	public void setActivityCover(String activityCover) {
		this.activityCover = activityCover;
	}
	
	@Length(min=0, max=2, message="活动状态长度必须介于 0 和 2 之间")
	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	private String actStatus;

	public String getActStatus() {
		return actStatus;
	}

	public void setActStatus(String actStatus) {
		this.actStatus = actStatus;
	}
	

	
}