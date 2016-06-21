/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ReceiveSmsMessageTime;

/**
 * 用户接收短信时间段DAO接口
 * @author ydt
 * @version 2016-01-14
 */
@MyBatisDao
public interface ReceiveSmsMessageTimeDao extends CrudDao<ReceiveSmsMessageTime> {

	/**
	 * 根据accountId获取用户设置的信息
	 * @param accountId
	 */
	ReceiveSmsMessageTime getByAccountId(Long accountId);

	ReceiveSmsMessageTime getSettingTimeByAccountId(Map<String, Object> params); 
	
}