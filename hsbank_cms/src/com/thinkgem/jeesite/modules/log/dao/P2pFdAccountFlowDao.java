/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.P2pFdAccountFlow;

/**
 * 平台账号流水DAO接口
 * @author yangtao
 * @version 2015-08-12
 */
@MyBatisDao
public interface P2pFdAccountFlowDao extends CrudDao<P2pFdAccountFlow> {
	
}