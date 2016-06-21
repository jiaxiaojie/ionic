/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MessageCreateRuleChannel;

/**
 * 消息产生规则发送渠道DAO接口
 * @author ydt
 * @version 2016-01-14
 */
@MyBatisDao
public interface MessageCreateRuleChannelDao extends CrudDao<MessageCreateRuleChannel> {

	/**
	 * 根据ruleId获取消息产生规则发送渠道列表
	 * @param ruleId
	 * @return
	 */
	List<String> findMessageChannelListByRuleId(@Param("ruleId") long ruleId);

	/**
	 * 根据ruleId删除消息产生规则发送渠道
	 * @param ruleId
	 */
	void deleteByRuleId(@Param("ruleId") long ruleId);
	
}