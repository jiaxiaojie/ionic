/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员可抵用投资金额变更流水Entity
 * @author ydt
 * @version 2015-07-30
 */
public class CustomerInvestmentMoneyHis extends DataEntity<CustomerInvestmentMoneyHis> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Double changeVal;		// 变更值
	private String changeTypeCode;		// 变更类型
	private Date getDt;		// 获取时间
	private Long recordId;		// 投资时关联投资记录
	private Date useDt;		// 使用时间
	
	public CustomerInvestmentMoneyHis() {
		super();
	}

	public CustomerInvestmentMoneyHis(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Double getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(Double changeVal) {
		this.changeVal = changeVal;
	}
	
	@Length(min=0, max=2, message="变更类型长度必须介于 0 和 2 之间")
	public String getChangeTypeCode() {
		return changeTypeCode;
	}

	public void setChangeTypeCode(String changeTypeCode) {
		this.changeTypeCode = changeTypeCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGetDt() {
		return getDt;
	}

	public void setGetDt(Date getDt) {
		this.getDt = getDt;
	}
	
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUseDt() {
		return useDt;
	}

	public void setUseDt(Date useDt) {
		this.useDt = useDt;
	}
	
}