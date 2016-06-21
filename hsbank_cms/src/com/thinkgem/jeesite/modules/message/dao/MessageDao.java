/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.Message;

/**
 * 消息DAO接口
 * @author ydt
 * @version 2016-01-14
 */
@MyBatisDao
public interface MessageDao extends CrudDao<Message> {

	Message getByAccountIdAndFromId(@Param("accountId") long accountId, @Param("fromId") long fromId, @Param("fromType") String fromType);
	
}