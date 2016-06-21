package com.thinkgem.jeesite.modules.entity.front;

import com.thinkgem.jeesite.common.utils.DateUtils;

import java.util.Date;

import static com.hsbank.util.type.NumberUtil.toInt;

/**
 * 我的账户分页列表查询bean
 * @author ydt
 *
 */
public class PageSearchBean {
	private Date startDateTime;
	private Date endDateTime;
	private String pageNo;
	public Date getStartDateTime() {
		return DateUtils.dateFormate(startDateTime);
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Date getEndDateTime() {
		return DateUtils.dateFormateDayOfTheLastTime(endDateTime);
	}
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	public int getPageNo() {
		return toInt(pageNo, 1);
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 设置默认时间范围，单位为天
	 * 		若days为负数，表示时间段为：当前时间-|days|至当前时间
	 * 		若days为正数 ，表示时间段为：当前时间至当前时间+|days|
	 */
	public void setDefaultDateRangeWithDays(int days) {
		if(startDateTime == null || endDateTime == null) {
			if(days < 0) {
				endDateTime = new Date();
				startDateTime = DateUtils.dateAddDay(endDateTime, days);
			} else {
				startDateTime = new Date();
				endDateTime = DateUtils.dateAddDay(startDateTime, days);
			}
		}
	}
	
	/**
	 * 设置默认时间范围，单位为月
	 * 		若months为负数，表示时间段为：当前时间-|months|至当前时间
	 * 		若months为正数 ，表示时间段为：当前时间至当前时间+|months|
	 */
	public void setDefaultDateRangeWithMonths(int months) {
		if(startDateTime == null || endDateTime == null) {
			if(months < 0) {
				endDateTime = new Date();
				startDateTime = DateUtils.dateAddMonth(endDateTime, months);
			} else {
				startDateTime = new Date();
				endDateTime = DateUtils.dateAddMonth(startDateTime, months);
			}
		}
	}
}
