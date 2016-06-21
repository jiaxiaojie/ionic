/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户端消息推送记录Entity
 * @author ydt
 * @version 2016-02-19
 */
public class AppMessagePushRecord extends DataEntity<AppMessagePushRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long messageInstanceId;		// 消息实例编号
	private Date pushDt;		// 推送时间
	private String resultData;		// 推送结果
	
	public AppMessagePushRecord() {
		super();
	}

	public AppMessagePushRecord(String id){
		super(id);
	}

	public Long getMessageInstanceId() {
		return messageInstanceId;
	}

	public void setMessageInstanceId(Long messageInstanceId) {
		this.messageInstanceId = messageInstanceId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPushDt() {
		return pushDt;
	}

	public void setPushDt(Date pushDt) {
		this.pushDt = pushDt;
	}
	
	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	
}