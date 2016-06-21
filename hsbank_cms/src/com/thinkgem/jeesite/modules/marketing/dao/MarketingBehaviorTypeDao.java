/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingBehaviorType;

/**
 * 会员行为编码DAO接口
 * @author lizibo
 * @version 2015-09-10
 */
@MyBatisDao
public interface MarketingBehaviorTypeDao extends CrudDao<MarketingBehaviorType> {
	
}