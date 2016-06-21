/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员花生豆汇总Entity
 * @author ydt
 * @version 2015-06-26
 */
public class CustomerIntegralSnapshot extends DataEntity<CustomerIntegralSnapshot> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private Integer integralBalance;		// 当前花生豆值
	private Integer outtimeVal;		// 即将过期花生豆值
	private Integer consecutiveDays;		//连续签到天数
	private Date firstGetDt;		// 第一笔花生豆获得时间
	private Date lastChangeDt;		// 最后一笔花生豆变更时间
	private Integer beginIntegralBalance;		// 开始 当前花生豆值
	private Integer endIntegralBalance;		// 结束 当前花生豆值
	
	public CustomerIntegralSnapshot() {
		super();
	}

	public CustomerIntegralSnapshot(String id){
		super(id);
	}

	@NotNull(message="账号编号不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Integer getIntegralBalance() {
		return integralBalance;
	}

	public void setIntegralBalance(Integer integralBalance) {
		this.integralBalance = integralBalance;
	}
	
	public Integer getOuttimeVal() {
		return outtimeVal;
	}

	public void setOuttimeVal(Integer outtimeVal) {
		this.outtimeVal = outtimeVal;
	}

	public Integer getConsecutiveDays() {
		return consecutiveDays;
	}

	public void setConsecutiveDays(Integer consecutiveDays) {
		this.consecutiveDays = consecutiveDays;
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
	
	public Integer getBeginIntegralBalance() {
		return beginIntegralBalance;
	}

	public void setBeginIntegralBalance(Integer beginIntegralBalance) {
		this.beginIntegralBalance = beginIntegralBalance;
	}
	
	public Integer getEndIntegralBalance() {
		return endIntegralBalance;
	}

	public void setEndIntegralBalance(Integer endIntegralBalance) {
		this.endIntegralBalance = endIntegralBalance;
	}

	/**
	 * 设置默认值
	 */
	public void setDefaultValue() {
		if(integralBalance == null) {
			integralBalance = 0;
		}
		if(outtimeVal == null) {
			outtimeVal = 0;
		}
		if(consecutiveDays == null){
			consecutiveDays = 0;
		}
	}
		
}