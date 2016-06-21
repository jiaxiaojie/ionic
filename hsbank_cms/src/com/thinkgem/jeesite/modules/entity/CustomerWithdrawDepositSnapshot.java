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
 * 会员提现额汇总Entity
 * @author yangtao
 * @version 2015-07-23
 */
public class CustomerWithdrawDepositSnapshot extends DataEntity<CustomerWithdrawDepositSnapshot> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String depositBalance;		// 当前提现额
	private Date firstGetDt;		// 第一笔花生豆获得时间
	private Date lastChangeDt;		// 最后一笔花生豆变更时间
	
	public CustomerWithdrawDepositSnapshot() {
		super();
	}

	public CustomerWithdrawDepositSnapshot(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=11, message="当前提现额长度必须介于 0 和 11 之间")
	public String getDepositBalance() {
		return depositBalance;
	}

	public void setDepositBalance(String depositBalance) {
		this.depositBalance = depositBalance;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFirstGetDt() {
		return firstGetDt;
	}

	public void setFirstGetDt(Date firstGetDt) {
		this.firstGetDt = firstGetDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastChangeDt() {
		return lastChangeDt;
	}

	public void setLastChangeDt(Date lastChangeDt) {
		this.lastChangeDt = lastChangeDt;
	}
	
}