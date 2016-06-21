/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 营销活动Entity
 * @author lizibo
 * @version 2015-09-09
 */
public class MarketingActivityInfo extends DataEntity<MarketingActivityInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long acticityId;		// 活动编号
	private String name;		// 活动名称
	private Date beginDate;		// 活动开始时间
	private Date endDate;		// 活动结束时间
	private String beginTime;		// 活动有效开始时间段
	private String endTime;		// 活动有效结束时间段
	private String bizClassName;		// 关联实现类名
	private String introduction;		// 活动说明
	private Long createUserId;		// 活动创建人
	private Date createDt;		// 创建时间
	private Long reviewUserId;		// 审批人
	private Date reviewDt;		// 审批时间
	private String status;		// 状态
	private String beginBeginTime;		// 开始 活动有效开始时间段
	private String endBeginTime;		// 结束 活动有效开始时间段
	private String beginEndTime;		// 开始 活动有效结束时间段
	private String endEndTime;		// 结束 活动有效结束时间段
	private List<String> channelIdList;		//渠道列表
	private List<String> actionTypeList;	//行为列表
	private List<MarketingActivityChannelLimit> channelLimitList;
	private List<MarketingActivityUserBehaviorLimit> behaviorLimitList;
	private List<String> channelLimitLists = new ArrayList<String>();;
	private List<String> behaviorLimitLists = new ArrayList<String>();
	
	
	public MarketingActivityInfo() {
		super();
	}

	public MarketingActivityInfo(String id){
		super(id);
	}

	public Long getActicityId() {
		return acticityId;
	}

	public void setActicityId(Long acticityId) {
		this.acticityId = acticityId;
	}
	
	@Length(min=0, max=200, message="活动名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Length(min=0, max=200, message="关联实现类名长度必须介于 0 和 200 之间")
	public String getBizClassName() {
		return bizClassName;
	}

	public void setBizClassName(String bizClassName) {
		this.bizClassName = bizClassName;
	}
	
	@Length(min=0, max=1000, message="活动说明长度必须介于 0 和 1000 之间")
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBeginBeginTime() {
		return beginBeginTime;
	}

	public void setBeginBeginTime(String beginBeginTime) {
		this.beginBeginTime = beginBeginTime;
	}
	
	public String getEndBeginTime() {
		return endBeginTime;
	}

	public void setEndBeginTime(String endBeginTime) {
		this.endBeginTime = endBeginTime;
	}
		
	public String getBeginEndTime() {
		return beginEndTime;
	}

	public void setBeginEndTime(String beginEndTime) {
		this.beginEndTime = beginEndTime;
	}
	
	public String getEndEndTime() {
		return endEndTime;
	}

	public void setEndEndTime(String endEndTime) {
		this.endEndTime = endEndTime;
	}

	public List<String> getChannelIdList() {
		return channelIdList;
	}

	public void setChannelIdList(List<String> channelIdList) {
		this.channelIdList = channelIdList;
	}

	public List<String> getActionTypeList() {
		return actionTypeList;
	}

	public void setActionTypeList(List<String> actionTypeList) {
		this.actionTypeList = actionTypeList;
	}
	public List<MarketingActivityChannelLimit> getChannelLimitList() {
		return channelLimitList;
	}

	public void setChannelLimitList(
			List<MarketingActivityChannelLimit> channelLimitList) {
		this.channelLimitList = channelLimitList;
		for(MarketingActivityChannelLimit channelLimit :channelLimitList){
			channelLimitLists.add(channelLimit.getChannelId()+"");
		}
	
	}

	public List<MarketingActivityUserBehaviorLimit> getBehaviorLimitList() {
		return behaviorLimitList;
	}

	public void setBehaviorLimitList(
			List<MarketingActivityUserBehaviorLimit> behaviorLimitList) {
		this.behaviorLimitList = behaviorLimitList;
		for(MarketingActivityUserBehaviorLimit behaviorLimit : behaviorLimitList){
			behaviorLimitLists.add(behaviorLimit.getActionType());
		}
		
	}
	
	
	public List<String> getChannelLimitLists() {
		return channelLimitLists;
	}

	public void setChannelLimitLists(List<String> channelLimitLists) {
		this.channelLimitLists = channelLimitLists;
	}

	public List<String> getBehaviorLimitLists() {
		return behaviorLimitLists;
	}

	public void setBehaviorLimitLists(List<String> behaviorLimitLists) {
		this.behaviorLimitLists = behaviorLimitLists;
	}

	/**
	 * 检查活动行为及渠道是否可用
	 * @param behaviorCode
	 * @param channelId
	 * @return
	 */
	public boolean checkIsUse(String behaviorCode,String channelId){
		if(channelLimitLists != null && channelLimitLists.contains(channelId)){
			if(behaviorLimitLists != null && behaviorLimitLists.contains(behaviorCode)){
				return true;
			}
		}
		return false;
	}
		
}