/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawProcess;

/**
 * 会员提现审批DAO接口
 * @author yangtao
 * @version 2015-07-22
 */
@MyBatisDao
public interface CustomerWithdrawProcessDao extends CrudDao<CustomerWithdrawProcess> {
	
}