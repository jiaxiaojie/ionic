/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentProjectParameter;

/**
 * 活期产品参数DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentProjectParameterDao extends CrudDao<CurrentProjectParameter> {
	
}