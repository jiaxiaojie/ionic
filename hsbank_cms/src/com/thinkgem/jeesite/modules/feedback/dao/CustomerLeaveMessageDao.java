/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerLeaveMessage;

/**
 * 客户留言DAO接口
 * @author ydt
 * @version 2016-02-22
 */
@MyBatisDao
public interface CustomerLeaveMessageDao extends CrudDao<CustomerLeaveMessage> {
	
}