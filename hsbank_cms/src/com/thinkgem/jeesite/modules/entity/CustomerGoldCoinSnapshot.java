/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员代金币汇总Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerGoldCoinSnapshot extends DataEntity<CustomerGoldCoinSnapshot> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Integer goldBalance;		// 当前代币值
	private Integer outtimeVal;		// 即将过期代币值
	private Date firstGetDt;		// 第一笔代币获得时间
	private Date lastChangeDt;		// 最后一笔代币变更时间
	private Integer beginGoldBalance;		// 开始 当前代币值
	private Integer endGoldBalance;		// 结束 当前代币值
	
	public CustomerGoldCoinSnapshot() {
		super();
	}

	public CustomerGoldCoinSnapshot(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Integer getGoldBalance() {
		return goldBalance;
	}

	public void setGoldBalance(Integer goldBalance) {
		this.goldBalance = goldBalance;
	}
	
	public Integer getOuttimeVal() {
		return outtimeVal;
	}

	public void setOuttimeVal(Integer outtimeVal) {
		this.outtimeVal = outtimeVal;
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
	
	public Integer getBeginGoldBalance() {
		return beginGoldBalance;
	}

	public void setBeginGoldBalance(Integer beginGoldBalance) {
		this.beginGoldBalance = beginGoldBalance;
	}
	
	public Integer getEndGoldBalance() {
		return endGoldBalance;
	}

	public void setEndGoldBalance(Integer endGoldBalance) {
		this.endGoldBalance = endGoldBalance;
	}
		
}