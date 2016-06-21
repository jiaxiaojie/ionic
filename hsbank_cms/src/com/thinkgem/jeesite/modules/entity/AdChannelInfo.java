/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 推广渠道信息Entity
 * @author wanduanrui
 * @version 2015-11-17
 */
public class AdChannelInfo extends DataEntity<AdChannelInfo> {
	
	private static final long serialVersionUID = 1L;
	private String channel;		// 渠道编号
	private String channelName;		// 渠道名称
	private Date startDt;		// 开始时间
	private Date endDt;		// 结束时间
	private String status;		// 状态
	
	public AdChannelInfo() {
		super();
		this.status = DEL_FLAG_NORMAL;
	}

	public AdChannelInfo(String id){
		super(id);
	}

	@Length(min=1, max=50, message="渠道编号长度必须介于 1 和 50 之间")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Length(min=1, max=100, message="渠道名称长度必须介于 1 和 100 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
	
}