/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceAlert;

/**
 * 账户余额警戒DAO接口
 * @author huangyuchen
 * @version 2016-04-14
 */
@MyBatisDao
public interface CustomerBalanceAlertDao extends CrudDao<CustomerBalanceAlert> {
	/**
	 * 查询出账户余额提醒相关的数据
	 * @param title
	 * @return
	 */
	CustomerBalanceAlert selectCustomerBalaneceAlertBytitle(String title);
	
}