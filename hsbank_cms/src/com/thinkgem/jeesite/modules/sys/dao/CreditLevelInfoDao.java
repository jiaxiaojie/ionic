/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CreditLevelInfo;

/**
 * 信用等级信息DAO接口
 * @author ydt
 * @version 2015-08-04
 */
@MyBatisDao
public interface CreditLevelInfoDao extends CrudDao<CreditLevelInfo> {
	
	/**
	 * 查询score所在范围内的信用等级信息
	 * @param score
	 * @return
	 */
	CreditLevelInfo getByScore(Double score);
	
}