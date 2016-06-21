/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ReceiveSmsMessageTime;
import com.thinkgem.jeesite.modules.message.MessageConstant;
import com.thinkgem.jeesite.modules.message.dao.ReceiveSmsMessageTimeDao;

/**
 * 用户接收短信时间段Service
 * @author ydt
 * @version 2016-01-14
 */
@Service
@Transactional(readOnly = true)
public class ReceiveSmsMessageTimeService extends CrudService<ReceiveSmsMessageTimeDao, ReceiveSmsMessageTime> {

	public ReceiveSmsMessageTime get(String id) {
		return super.get(id);
	}
	
	public List<ReceiveSmsMessageTime> findList(ReceiveSmsMessageTime receiveSmsMessageTime) {
		return super.findList(receiveSmsMessageTime);
	}
	
	public Page<ReceiveSmsMessageTime> findPage(Page<ReceiveSmsMessageTime> page, ReceiveSmsMessageTime receiveSmsMessageTime) {
		return super.findPage(page, receiveSmsMessageTime);
	}
	
	@Transactional(readOnly = false)
	public void save(ReceiveSmsMessageTime receiveSmsMessageTime) {
		super.save(receiveSmsMessageTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReceiveSmsMessageTime receiveSmsMessageTime) {
		super.delete(receiveSmsMessageTime);
	}

	/**
	 * 判断用户在此时刻是否接收短信消息
	 * @param accountId
	 * @param date
	 * @return
	 */
	public boolean isReceive(Long accountId, Date date) {
		ReceiveSmsMessageTime receiveSmsMessageTime = dao.getByAccountId(accountId);
		if(receiveSmsMessageTime != null) {
			return isInDuringTime(DateUtils.FormatDate(date, "HH:mm:ss"), receiveSmsMessageTime.getStartTime(), receiveSmsMessageTime.getEndTime());
		} else {
			return isInDuringTime(DateUtils.FormatDate(date, "HH:mm:ss"), MessageConstant.SMS_MESSAGE_START_TIME, MessageConstant.SMS_MESSAGE_END_TIME);
		}
	}
	
	/**
	 * time must format with "HH:mm:ss"
	 * @param time
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private boolean isInDuringTime(String time, String startTime, String endTime) {
		return time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0;
	}

	public ReceiveSmsMessageTime getByAccountId(String accountId) {
		
		return this.dao.getByAccountId(Long.parseLong(accountId));
	}

	public ReceiveSmsMessageTime getSettingTimeByAccountId(Long accountId) {
		ReceiveSmsMessageTime rst = dao.getByAccountId(accountId);
		if(rst==null && (accountId != null && !"".equals(accountId))){
			rst = new ReceiveSmsMessageTime();
			rst.setAccountId(accountId);
			rst.setStartTime(MessageConstant.SMS_MESSAGE_START_TIME);
			rst.setEndTime(MessageConstant.SMS_MESSAGE_END_TIME);
		}
		return rst;
	}
	
}