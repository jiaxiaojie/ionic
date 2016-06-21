package com.thinkgem.jeesite.modules.api.to;

/**
 * 我的信息
 * @author hyc
 *
 */
public class AdPositionResp {
	private String adCode;   //广告位代码
	private String imageUrl; //广告位图片路径
	private Long isClick;  //是否可点击(0:否,1:是)
	private Long type;     //类型（1:活动，点击后打开url, 2:项目，点击后跳到项目详情
	private String  target;  //目标参数
	private String description;  //描述

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getIsClick() {
		return isClick;
	}

	public void setIsClick(Long isClick) {
		this.isClick = isClick;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
