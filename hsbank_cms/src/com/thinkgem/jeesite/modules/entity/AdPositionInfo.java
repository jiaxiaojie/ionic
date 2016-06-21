/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 广告位显示信息Entity
 * @author huangyuchen
 * @version 2016-05-17
 */
public class AdPositionInfo extends DataEntity<AdPositionInfo> {

	private static final long serialVersionUID = 1L;
	private String code;		// 代码
	private String name;		// 名称
	private String image;		// 图片
	private String canClick;		// 是否可点击
	private String type;		// 类型
	private String target;		// 目标参数
	private String description;		// 描述
	private Date startDt;		// 开始时间
	private Date endDt;		// 结束时间
	private String version;		// 版本
	private String status;		// 状态
	private Long createUserId;		// 创建人
	private Date createDt;		// 创建时间
	private Long reviewUserId;		// 审批人
	private Date reviewDt;		// 审批时间
	private String termCode; //终端
	private AdPositionShowTerm adPositionShowTerm;

	public AdPositionShowTerm getAdPositionShowTerm() {
		return adPositionShowTerm;
	}

	public void setAdPositionShowTerm(AdPositionShowTerm adPositionShowTerm) {
		this.adPositionShowTerm = adPositionShowTerm;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public AdPositionInfo() {
		super();
	}

	public AdPositionInfo(String id){
		super(id);
	}

	@Length(min=0, max=20, message="代码长度必须介于 0 和 20 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=500, message="图片长度必须介于 0 和 500 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=0, max=2, message="是否可点击长度必须介于 0 和 2 之间")
	public String getCanClick() {
		return canClick;
	}

	public void setCanClick(String canClick) {
		this.canClick = canClick;
	}
	
	@Length(min=0, max=2, message="类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=300, message="目标参数长度必须介于 0 和 300 之间")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Length(min=0, max=500, message="描述长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	@Length(min=0, max=20, message="版本长度必须介于 0 和 20 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
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
	
}