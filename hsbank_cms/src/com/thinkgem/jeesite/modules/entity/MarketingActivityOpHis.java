/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 营销活动操作流水Entity
 * @author lizibo
 * @version 2015-09-09
 */
public class MarketingActivityOpHis extends DataEntity<MarketingActivityOpHis> {
	
	private static final long serialVersionUID = 1L;
	private Long acticityId;		// 活动编号
	private String behaviorCode;		// 行为编号
	private Long accountId;		// 用户账号
	private String channelId;		// 渠道编号
	private Date opDt;		// 操作时间
	private String inPara;		// 入参
	private String outPara;		// 出参
	private String resultCode;		// 操作结果
	private Date beginOpDt;		// 开始 操作时间
	private Date endOpDt;		// 结束 操作时间
	private String behaviorName;	//行为名称
	private String customerName; //用户名 
	
	public MarketingActivityOpHis() {
		super();
	}

	public MarketingActivityOpHis(String id){
		super(id);
	}

	public Long getActicityId() {
		return acticityId;
	}

	public void setActicityId(Long acticityId) {
		this.acticityId = acticityId;
	}
	
	@Length(min=0, max=6, message="行为编号长度必须介于 0 和 6 之间")
	public String getBehaviorCode() {
		return behaviorCode;
	}

	public void setBehaviorCode(String behaviorCode) {
		this.behaviorCode = behaviorCode;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=11, message="渠道编号长度必须介于 0 和 11 之间")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=500, message="入参长度必须介于 0 和 500 之间")
	public String getInPara() {
		return inPara;
	}

	public void setInPara(String inPara) {
		this.inPara = inPara;
	}
	
	@Length(min=0, max=500, message="出参长度必须介于 0 和 500 之间")
	public String getOutPara() {
		return outPara;
	}

	public void setOutPara(String outPara) {
		this.outPara = outPara;
	}
	
	@Length(min=0, max=2, message="操作结果长度必须介于 0 和 2 之间")
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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

	public String getBehaviorName() {
		return behaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
		
}