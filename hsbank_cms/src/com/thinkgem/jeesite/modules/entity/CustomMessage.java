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
 * 自定义消息Entity
 * @author ydt
 * @version 2016-01-14
 */
public class CustomMessage extends DataEntity<CustomMessage> {
	
	private static final long serialVersionUID = 1L;
	private String	 id;
	private String title;		// 标题
	private String content;		// 内容
	private String receiverType;		// 接收用户类型
	private String receiverData;		// 接收用户
	private String type;		// 消息类型
	private String label;		// 标签
	private String isUrgent;    //是否紧急
	private String targetType;  //目标类型
	private String target;     //目标参数
	private String isClick;    //是否可点击
	private Date sendDt;		// 发送时间
	private String status;		// 状态
	private Date createDt;		// 创建时间
	private Long creator;		// 创建人
	private Date reviewDt;		// 审批时间
	private String reviewRemark;		// 审批意见
	private Long reviewer;		// 审批人
	private List<String> messageChannelList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<String> getMessageChannelList() {
		return messageChannelList;
	}

	public void setMessageChannelList(List<String> messageChannelList) {
		this.messageChannelList = messageChannelList;
	}

	public CustomMessage() {
		super();
	}

	public CustomMessage(String id){
		super(id);
	}

	@Length(min=0, max=20, message="标题长度必须介于 0 和 20 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=280, message="内容长度必须介于 0 和 280 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2, message="接收用户类型长度必须介于 0 和 2 之间")
	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	
	@Length(min=0, max=200, message="接收用户长度必须介于 0 和 200 之间")
	public String getReceiverData() {
		return receiverData;
	}

	public void setReceiverData(String receiverData) {
		this.receiverData = receiverData;
	}
	
	@Length(min=0, max=2, message="消息类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=20, message="标签长度必须介于 0 和 30 之间")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	public String getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIsClick() {
		return isClick;
	}
	public void setIsClick(String isClick) {
		this.isClick = isClick;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDt() {
		return sendDt;
	}
    
	public void setSendDt(Date sendDt) {
		this.sendDt = sendDt;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	@Length(min=0, max=100, message="审批意见长度必须介于 0 和 100 之间")
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	
	public Long getReviewer() {
		return reviewer;
	}

	public void setReviewer(Long reviewer) {
		this.reviewer = reviewer;
	}
	
}