/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MessageCreateRuleTerm;

/**
 * 消息产生规则适用终端DAO接口
 * @author ydt
 * @version 2016-01-15
 */
@MyBatisDao
public interface MessageCreateRuleTermDao extends CrudDao<MessageCreateRuleTerm> {

	/**
	 * 根据ruleId获取消息产生规则适用终端列表
	 * @param ruleId
	 * @return
	 */
	List<String> findTermListByRuleId(@Param("ruleId") long ruleId);

	/**
	 * 根据ruleId删除消息产生规则适用终端
	 * @param ruleId
	 */
	void deleteByRuleId(long ruleId);


	
}