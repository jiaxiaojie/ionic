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
 * 消息实例Entity
 * @author ydt
 * @version 2016-01-14
 */
public class MessageInstance extends DataEntity<MessageInstance> {
	
	private static final long serialVersionUID = 1L;
	private Long messageId;		// 消息编号
	private String messageChannel;		// 渠道
	private Date createDt;		// 创建时间
	private Date sendDt;		// 发送时间
	private Date readDt;		// 阅读时间
	private Date deleteDt;		// 删除时间
	private String status;		// 状态
	private Date beginOpDt;		// 发送时间
	private Date endOpDt;		// 发送时间 
	private Long accountId;
	private String title;
	private String content;
	private Date planSendDt;
	private String type;
	private String label;
	private String fromType;
	private Long fromId;
	private String mobile;		//手机号
	private String customerName;	//会员名称
	private CustomerBase customerBase;	//用户基本信息
	
	private List<Long> messageIdList;	//消息checkBox
	
	public CustomerBase getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBase customerBase) {
		this.customerBase = customerBase;
	}

	public Date getBeginOpDt() {
		return beginOpDt;
	}

	public void setBeginOpDt(Date beginOpDt) {
		this.beginOpDt = beginOpDt;
	}

	public Date getEndOpDt() {
		return endOpDt;
	}

	public void setEndOpDt(Date endOpDt) {
		this.endOpDt = endOpDt;
	}

	public MessageInstance() {
		super();
	}

	public MessageInstance(String id){
		super(id);
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	@Length(min=0, max=2, message="渠道长度必须介于 0 和 2 之间")
	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDt() {
		return sendDt;
	}

	public void setSendDt(Date sendDt) {
		this.sendDt = sendDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDt() {
		return readDt;
	}

	public void setReadDt(Date readDt) {
		this.readDt = readDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeleteDt() {
		return deleteDt;
	}

	public void setDeleteDt(Date deleteDt) {
		this.deleteDt = deleteDt;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPlanSendDt() {
		return planSendDt;
	}

	public void setPlanSendDt(Date planSendDt) {
		this.planSendDt = planSendDt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<Long> getMessageIdList() {
		return messageIdList;
	}

	public void setMessageIdList(List<Long> messageIdList) {
		this.messageIdList = messageIdList;
	}


	
	
	
}