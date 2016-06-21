/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 轮播图管理Entity
 * @author hyc
 * @version 2015-11-11
 */
public class CarouselInfo extends DataEntity<CarouselInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long carouselId;    //轮播图编号
	private String title;		// 标题
	private String pictureBig;		// 大图
	private String pictureMedium;		// 中图
	private String pictureSmall;		// 小图
	private Date activityTime;        //活动开始时间
	private String typeCode;		// 类型
	private String isNewWebsite;	//是否新网站使用
	private String target;		// 目标
	private String sort;		// 排序
	private Date startDt;		// 开始时间
	private Date endDt;		// 结束时间
	private String status;		// 状态
	private String createUserId;		// 创建人
	private Date createDt;		// 创建时间
	private Long reviewUserId;		// 审批人
	private Date reviewDt;		// 审批时间
	private String dispaly = "0";		//是否显示倒计时
	private CarouselInfo carouselInfo;
	private List<String> carouselLists = new ArrayList<String>();

	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	public List<String> getCarouselLists() {
		return carouselLists;
	}

	public void setCarouselLists(List<String> carouselLists) {
		this.carouselLists = carouselLists;
	}

	public CarouselInfo getCarouselInfo() {
		return carouselInfo;
	}

	public void setCarouselInfo(CarouselInfo carouselInfo) {
		this.carouselInfo = carouselInfo;
	}

	private List<String> termCodeList;
	private List<CarouselShowTerm> carouselShowTermList;

	public List<CarouselShowTerm> getCarouselShowTermList() {
		return carouselShowTermList;
	}

	public void setCarouselShowTermList(List<CarouselShowTerm> carouselShowTermList) {
		this.carouselShowTermList = carouselShowTermList;
	}

	public List<String> getTermCodeList() {
		return termCodeList;
	}

	public void setTermCodeList(List<String> termCodeList) {
		this.termCodeList = termCodeList;
	}

	public CarouselInfo() {
		super();
	}

	public CarouselInfo(String id){
		super(id);
	}

	@Length(min=0, max=100, message="标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=500, message="大图长度必须介于 0 和 500 之间")
	public String getPictureBig() {
		return pictureBig;
	}

	public void setPictureBig(String pictureBig) {
		this.pictureBig = pictureBig;
	}
	
	@Length(min=0, max=500, message="中图长度必须介于 0 和 500 之间")
	public String getPictureMedium() {
		return pictureMedium;
	}

	public void setPictureMedium(String pictureMedium) {
		this.pictureMedium = pictureMedium;
	}
	
	@Length(min=0, max=500, message="小图长度必须介于 0 和 500 之间")
	public String getPictureSmall() {
		return pictureSmall;
	}

	public void setPictureSmall(String pictureSmall) {
		this.pictureSmall = pictureSmall;
	}
	
	@Length(min=0, max=2, message="类型长度必须介于 0 和 2 之间")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Length(min=0, max=500, message="目标长度必须介于 0 和 500 之间")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
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
	

	
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}

	public Long getCarouselId() {
		return carouselId;
	}

	public void setCarouselId(Long carouselId) {
		this.carouselId = carouselId;
	}

	public String getDispaly() {
		return dispaly;
	}

	public void setDispaly(String dispaly) {
		this.dispaly = dispaly;
	}

	public String getIsNewWebsite() {
		return isNewWebsite;
	}

	public void setIsNewWebsite(String isNewWebsite) {
		this.isNewWebsite = isNewWebsite;
	}
}