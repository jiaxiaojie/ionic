/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;

/**
 * 消息产生规则DAO接口
 * @author ydt
 * @version 2016-01-14
 */
@MyBatisDao
public interface MessageCreateRuleDao extends CrudDao<MessageCreateRule> {

	/**
	 * 获取状态为status的消息产生规则列表，若dateTime不为null 则对列表时间进行过滤（dateTime在startDate endDate，startTime endTime内）
	 * @param status
	 * @param dateTime
	 * @return
	 */
	List<MessageCreateRule> findListByStatus(@Param("status") String status, @Param("dateTime") Date dateTime);

	List<MessageCreateRule> querySimpleList(MessageCreateRule qa);

	/**
	 * 审批消息产生规则
	 * @param id
	 * @param status
	 * @param reviewer
	 */
	void review(@Param("id") String id, @Param("status") String status, @Param("reviewer") long reviewer);
	
	/**
	 * 根据className获取消息产生规则
	 * @param className
	 * @return
	 */
	MessageCreateRule getByClassName(@Param("className") String className);

	MessageCreateRule getByMessageId(@Param("messageId") Long messageId);
}