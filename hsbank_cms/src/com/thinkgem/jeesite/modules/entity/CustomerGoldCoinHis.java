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
 * 会员代币变更流水Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerGoldCoinHis extends DataEntity<CustomerGoldCoinHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String changeVal;		// 变更币值
	private String changeReason;		// 变更原因
	private String relActivity;		// 关联活动
	private String exchangeGoods;		// 兑换物品
	private Date opDt;		// 操作时间
	private String opTermType;		// 操作终端
	private String beginChangeVal;		// 开始 变更币值
	private String endChangeVal;		// 结束 变更币值
	
	public CustomerGoldCoinHis() {
		super();
	}

	public CustomerGoldCoinHis(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=11, message="变更币值长度必须介于 0 和 11 之间")
	public String getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(String changeVal) {
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
	
	public String getBeginChangeVal() {
		return beginChangeVal;
	}

	public void setBeginChangeVal(String beginChangeVal) {
		this.beginChangeVal = beginChangeVal;
	}
	
	public String getEndChangeVal() {
		return endChangeVal;
	}

	public void setEndChangeVal(String endChangeVal) {
		this.endChangeVal = endChangeVal;
	}
		
}