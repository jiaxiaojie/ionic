/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ReceiveMessageSwitch;

/**
 * 用户接收消息开关DAO接口
 * @author ydt
 * @version 2016-01-15
 */
@MyBatisDao
public interface ReceiveMessageSwitchDao extends CrudDao<ReceiveMessageSwitch> {

	

	/**
	 * 获取isReceive字段
	 * @param accountId
	 * @param messageType
	 * @param messageChannel
	 * @return
	 */
	String getIsReceive(@Param("accountId") Long accountId, @Param("messageType") String messageType, @Param("messageChannel") String messageChannel);
	List<Map<String,Object>> findCustomerSwitchList(HashMap<String, Object> params);

	/**
	 * 删除消息设置
	 * @param accountId
	 * @param messageType
	 * @param messageChannel
	 */
	void deleteSwitch(@Param("accountId") Long accountId, @Param("messageType") String messageType, @Param("messageChannel") String messageChannel);
}