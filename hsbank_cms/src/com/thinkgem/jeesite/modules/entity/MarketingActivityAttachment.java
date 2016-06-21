/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活动关联附件Entity
 * @author lizibo
 * @version 2015-09-09
 */
public class MarketingActivityAttachment extends DataEntity<MarketingActivityAttachment> {
	
	private static final long serialVersionUID = 1L;
	private Long activityCode;		// 活动编号
	private String attaName;		// 附件名称
	private String attaSize;		// 附件大小
	private String attsSize;		// 附件路径
	private Date createTime;		// 上传时间
	
	public MarketingActivityAttachment() {
		super();
	}

	public MarketingActivityAttachment(String id){
		super(id);
	}

	public Long getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Long activityCode) {
		this.activityCode = activityCode;
	}
	
	@Length(min=0, max=200, message="附件名称长度必须介于 0 和 200 之间")
	public String getAttaName() {
		return attaName;
	}

	public void setAttaName(String attaName) {
		this.attaName = attaName;
	}
	
	@Length(min=0, max=11, message="附件大小长度必须介于 0 和 11 之间")
	public String getAttaSize() {
		return attaSize;
	}

	public void setAttaSize(String attaSize) {
		this.attaSize = attaSize;
	}
	
	@Length(min=0, max=200, message="附件路径长度必须介于 0 和 200 之间")
	public String getAttsSize() {
		return attsSize;
	}

	public void setAttsSize(String attsSize) {
		this.attsSize = attsSize;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}