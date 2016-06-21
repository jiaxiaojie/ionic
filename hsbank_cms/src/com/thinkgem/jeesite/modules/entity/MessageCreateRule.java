/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 消息产生规则Entity
 * @author ydt
 * @version 2016-01-14
 */
public class MessageCreateRule extends DataEntity<MessageCreateRule> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String behaviorCode;		// 行为编号
	private String messageTitle;		// 消息标题
	private String messageContent;		// 消息内容
	private String messageType;		// 消息类型
	private String label;		// 标签
	private String isUrgent;	//是否紧急
	private String targetType;	//目标类型
	private String target;		//目标参数
	private String isClick;		//是否可点击
	private String status;		// 状态
	private String className;		// 实现类名
	private Date startDate;		// 开始日期
	private Date endDate;		// 结束日期
	private String startTime;		// 开始时间段
	private String endTime;		// 结束时间段
	private Date createDt;		// 创建时间
	private Long creator;		// 创建人
	private Date reviewDt;		// 审批时间
	private Long reviewer;		// 审批人

	private List<String> messageChannelList;	// 消息产生规则发送渠道
	private List<String> termList;	// 消息产生规则适用终端
	
	private String queryParas;
	
	
	
	public String getQueryParas() {
		return queryParas;
	}

	public void setQueryParas(String queryParas) {
		this.queryParas = queryParas;
	}

	public MessageCreateRule() {
		super();
	}

	public MessageCreateRule(String id){
		super(id);
	}

	@Length(min=0, max=30, message="名称长度必须介于 0 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=5, message="行为编号长度必须介于 0 和 5 之间")
	public String getBehaviorCode() {
		return behaviorCode;
	}

	public void setBehaviorCode(String behaviorCode) {
		this.behaviorCode = behaviorCode;
	}
	
	@Length(min=0, max=20, message="消息标题长度必须介于 0 和 20 之间")
	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	@Length(min=0, max=280, message="消息内容长度必须介于 0 和 280 之间")
	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	@Length(min=0, max=2, message="消息类型长度必须介于 0 和 2 之间")
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Length(min=0, max=30, message="标签长度必须介于 0 和 30 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min=0, max=2, message="是否紧急长度必须介于 0 和 2 之间")
	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	@Length(min=0, max=2, message="目标类型长度必须介于 0 和 2 之间")
	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	@Length(min=0, max=200, message="目标参数长度必须介于 0 和 200 之间")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Length(min=0, max=2, message="是否可点击长度必须介于 0 和 2 之间")
	public String getIsClick() {
		return isClick;
	}

	public void setIsClick(String isClick) {
		this.isClick = isClick;
	}

	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=40, message="实现类名长度必须介于 0 和 40 之间")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}
	
	public Long getReviewer() {
		return reviewer;
	}

	public void setReviewer(Long reviewer) {
		this.reviewer = reviewer;
	}

	public List<String> getMessageChannelList() {
		return messageChannelList;
	}

	public void setMessageChannelList(List<String> messageChannelList) {
		this.messageChannelList = messageChannelList;
	}

	public List<String> getTermList() {
		return termList;
	}

	public void setTermList(List<String> termList) {
		this.termList = termList;
	}

	/**
	 * 是否应该处理此规则
	 * @param behaviorCode
	 * @param opTerm
	 * @return
	 */
	public boolean shouldHandler(String behaviorCode, String opTerm) {
		return this.behaviorCode.equals(behaviorCode) && termList != null && termList.contains(opTerm);
	}
	
}