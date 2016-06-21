/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawHis;

/**
 * 会员体现记录表DAO接口
 * @author lzb
 * @version 2016-05-10
 */
@MyBatisDao
public interface CustomerWithdrawHisDao extends CrudDao<CustomerWithdrawHis> {
	
}