/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员花生豆变更流水Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerIntegralHis extends DataEntity<CustomerIntegralHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Integer changeVal;		// 变更分值
	private String changeType;		// 变更类型
	private String changeReason;		// 变更原因
	private String relActivity;		// 关联活动
	private String exchangeGoods;		// 兑换物品
	private Date opDt;		// 操作时间
	private String opTermType;		// 操作终端
	private Integer beginChangeVal;		// 开始 变更分值
	private Integer endChangeVal;		// 结束 变更分值

	private Date beginOpDate;
	private Date endOpDate;
	private String receiveOrUsed;// 1：查询获得的流水，2：查询使用的流水

	public String getReceiveOrUsed() {
		return receiveOrUsed;
	}

	public void setReceiveOrUsed(String receiveOrUsed) {
		this.receiveOrUsed = receiveOrUsed;
	}

	public Date getBeginOpDate() {
		return beginOpDate;
	}

	public void setBeginOpDate(Date beginOpDate) {
		this.beginOpDate = beginOpDate;
	}

	public Date getEndOpDate() {
		return endOpDate;
	}

	public void setEndOpDate(Date endOpDate) {
		this.endOpDate = endOpDate;
	}



	public CustomerIntegralHis() {
		super();
	}

	public CustomerIntegralHis(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Integer getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(Integer changeVal) {
		this.changeVal = changeVal;
	}
	
	@Length(min=0, max=500, message="变更原因长度必须介于 0 和 500 之间")
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	@Length(min=0, max=200, message="关联活动长度必须介于 0 和 200 之间")
	public String getRelActivity() {
		return relActivity;
	}

	public void setRelActivity(String relActivity) {
		this.relActivity = relActivity;
	}
	
	@Length(min=0, max=200, message="兑换物品长度必须介于 0 和 200 之间")
	public String getExchangeGoods() {
		return exchangeGoods;
	}

	public void setExchangeGoods(String exchangeGoods) {
		this.exchangeGoods = exchangeGoods;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpDt() {
		return opDt;
	}

	public void setOpDt(Date opDt) {
		this.opDt = opDt;
	}
	
	@Length(min=0, max=2, message="操作终端长度必须介于 0 和 2 之间")
	public String getOpTermType() {
		return opTermType;
	}

	public void setOpTermType(String opTermType) {
		this.opTermType = opTermType;
	}
	
	public Integer getBeginChangeVal() {
		return beginChangeVal;
	}

	public void setBeginChangeVal(Integer beginChangeVal) {
		this.beginChangeVal = beginChangeVal;
	}
	
	public Integer getEndChangeVal() {
		return endChangeVal;
	}

	public void setEndChangeVal(Integer endChangeVal) {
		this.endChangeVal = endChangeVal;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
		
}