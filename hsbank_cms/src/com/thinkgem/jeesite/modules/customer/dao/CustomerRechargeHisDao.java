/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerRechargeHis;

/**
 * 会员充值记录DAO接口
 * @author yangtao
 * @version 2015-07-23
 */
@MyBatisDao
public interface CustomerRechargeHisDao extends CrudDao<CustomerRechargeHis> {
	
}