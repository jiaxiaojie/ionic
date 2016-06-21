/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomMessage;
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;

/**
 * 自定义消息DAO接口
 * @author ydt
 * @version 2016-01-14
 */
@MyBatisDao
public interface CustomMessageDao extends CrudDao<CustomMessage> {
    /**
     * 查询标题是否存在
     * @param customMessage
     * @return
     */
	CustomMessage getCustomMessage(CustomMessage customMessage);

	List<CustomMessageSendChannel> findListById(Long id);

	String findStatusFromCustomMessageByAccountId(String id);

	CustomMessage selectCustomMessageList(String id);

	void changeStatus(String id);

	CustomMessage getByMessageId(@Param("messageId") Long messageId);

	void updateIsUrgent(CustomMessage customMessage);
	
}