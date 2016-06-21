/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.RateTicketType;

/**
 * 加息券类型DAO接口
 * @author ydt
 * @version 2016-04-05
 */
@MyBatisDao
public interface RateTicketTypeDao extends CrudDao<RateTicketType> {
	
}