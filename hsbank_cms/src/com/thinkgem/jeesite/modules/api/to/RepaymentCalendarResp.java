package com.thinkgem.jeesite.modules.api.to;

import java.util.List;

/**
 * 还款日历信息
 * @author lizibo
 *
 */
public class RepaymentCalendarResp {

	private int day;		// 会员账号编号
	private Double money;		// 还款额
	private Integer status;
	public List<Object> recordList;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Object> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<Object> recordList) {
		this.recordList = recordList;
	}
	
	
	
}
