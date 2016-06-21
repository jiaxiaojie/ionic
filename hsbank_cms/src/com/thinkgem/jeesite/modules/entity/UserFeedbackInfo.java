/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户意见反馈Entity
 * @author lizibo
 * @version 2015-09-07
 */
public class UserFeedbackInfo extends DataEntity<UserFeedbackInfo> {
	
	private static final long serialVersionUID = 1L;
	private String channelId;		// 渠道
	private String content;		// 反馈内容
	private Date createDt;		// 反馈时间
	private Long accountId;		// 用户编号
	private String returnContent;		// 处理意见
	private String result;		// 处理结果
	private Date beginCreateDt;		// 开始 反馈时间
	private Date endCreateDt;		// 结束 反馈时间
	
	public UserFeedbackInfo() {
		super();
	}

	public UserFeedbackInfo(String id){
		super(id);
	}

	@Length(min=0, max=2, message="渠道长度必须介于 0 和 2 之间")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@NotBlank(message="反馈内容不能为空！")
	@Length(min=0, max=1000, message="反馈内容长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=500, message="处理意见长度必须介于 0 和 500 之间")
	public String getReturnContent() {
		return returnContent;
	}

	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}
	
	@Length(min=0, max=500, message="处理结果长度必须介于 0 和 500 之间")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Date getBeginCreateDt() {
		return beginCreateDt;
	}

	public void setBeginCreateDt(Date beginCreateDt) {
		this.beginCreateDt = beginCreateDt;
	}
	
	public Date getEndCreateDt() {
		return endCreateDt;
	}

	public void setEndCreateDt(Date endCreateDt) {
		this.endCreateDt = endCreateDt;
	}
		
}