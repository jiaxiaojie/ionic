package com.thinkgem.jeesite.modules.api.to;


public class CarouselResp {

	private String imageUrl;		// 图片URL（绝对路径）
	public String description;     //描述
	private String title;		// 标题
	private Integer type;		// 类型（1--活动，点击后打开url；2--项目，点击后跳到项目详情
	private String target;		// 目标参数
	private String activity_period;
	private String  activityPeriod;  //活动周期(yyyy.MM.dd-yyyy.MM.dd)"
	private String status;	//状态(0:进行中、1：已结束)
	private String statusName;	//状态名称(进行中、已结束)
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivityPeriod() {
		return activityPeriod;
	}

	public void setActivityPeriod(String activityPeriod) {
		this.activityPeriod = activityPeriod;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getActivity_period() {
		return activity_period;
	}
	public void setActivity_period(String activity_period) {
		this.activity_period = activity_period;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}




}
