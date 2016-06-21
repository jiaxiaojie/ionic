/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 营销活动分享记录Entity
 * @author lzb
 * @version 2016-02-26
 */
public class MarketingShareRecord extends DataEntity<MarketingShareRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long activityId;		// 活动编号
	private String channelId;		// 渠道编号
	private Long accountId;		// 分享用户
	private Date shareDt;		// 分享时间
	private String shareReason;		// 分享说明
	private String acticityCode;		// 活动代码
	private Date beginShareDt;		// 开始 分享时间
	private Date endShareDt;		// 结束 分享时间
	
	public MarketingShareRecord() {
		super();
	}

	public MarketingShareRecord(String id){
		super(id);
	}


	
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Length(min=0, max=10, message="渠道编号长度必须介于 0 和 10 之间")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShareDt() {
		return shareDt;
	}

	public void setShareDt(Date shareDt) {
		this.shareDt = shareDt;
	}
	
	@Length(min=0, max=100, message="分享说明长度必须介于 0 和 100 之间")
	public String getShareReason() {
		return shareReason;
	}

	public void setShareReason(String shareReason) {
		this.shareReason = shareReason;
	}
	
	@Length(min=0, max=50, message="活动代码长度必须介于 0 和 50 之间")
	public String getActicityCode() {
		return acticityCode;
	}

	public void setActicityCode(String acticityCode) {
		this.acticityCode = acticityCode;
	}
	
	public Date getBeginShareDt() {
		return beginShareDt;
	}

	public void setBeginShareDt(Date beginShareDt) {
		this.beginShareDt = beginShareDt;
	}
	
	public Date getEndShareDt() {
		return endShareDt;
	}

	public void setEndShareDt(Date endShareDt) {
		this.endShareDt = endShareDt;
	}
		
}