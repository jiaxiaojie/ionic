/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 大转盘中奖记录Entity
 * @author ydt
 * @version 2015-11-24
 */
public class MarketingWheelPrizeInfo extends DataEntity<MarketingWheelPrizeInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 奖品名称
	private String label;		// 奖品标签
	private String type;		// 奖品类型
	private String value;		// 奖品值
	private String getTips;		// 获奖提示
	private String description;		// 奖品描述
	private String logo;		// 奖品logo
	private Integer number;		// 奖品数量
	private Double rotate;		// 中奖角度
	private Long activityId;		//活动编号
	private String activityName;		//活动名称
	private String isDefault;		//是否为默认奖品
	
	public MarketingWheelPrizeInfo() {
		super();
	}

	public MarketingWheelPrizeInfo(String id){
		super(id);
	}

	@Length(min=0, max=20, message="奖品名称长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="奖品标签长度必须介于 0 和 20 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=0, max=2, message="奖品类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=20, message="奖品值长度必须介于 0 和 20 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=100, message="奖品描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=100, message="奖品logo长度必须介于 0 和 100 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Double getRotate() {
		return rotate;
	}

	public void setRotate(Double rotate) {
		this.rotate = rotate;
	}

	@Length(min=0, max=50, message="获奖提示长度必须介于 0 和 50 之间")
	public String getGetTips() {
		return getTips;
	}

	public void setGetTips(String getTips) {
		this.getTips = getTips;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}