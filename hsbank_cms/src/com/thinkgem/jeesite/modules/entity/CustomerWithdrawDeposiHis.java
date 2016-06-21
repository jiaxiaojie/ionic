/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员提现额流水Entity
 * @author yangtao
 * @version 2015-07-23
 */
public class CustomerWithdrawDeposiHis extends DataEntity<CustomerWithdrawDeposiHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String changeVal;		// 变更分值
	private String changeReason;		// 变更原因
	private String relActivity;		// 关联活动
	private String thirdPartyOrder;		// 提现流水号
	private Date opDt;		// 操作时间
	private String opTermType;		// 操作终端
	private Date beginOpDt;		// 开始 操作时间
	private Date endOpDt;		// 结束 操作时间
	
	public CustomerWithdrawDeposiHis() {
		super();
	}

	public CustomerWithdrawDeposiHis(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=11, message="变更分值长度必须介于 0 和 11 之间")
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
	
	@Length(min=0, max=50, message="提现流水号长度必须介于 0 和 50 之间")
	public String getThirdPartyOrder() {
		return thirdPartyOrder;
	}

	public void setThirdPartyOrder(String thirdPartyOrder) {
		this.thirdPartyOrder = thirdPartyOrder;
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
		
}