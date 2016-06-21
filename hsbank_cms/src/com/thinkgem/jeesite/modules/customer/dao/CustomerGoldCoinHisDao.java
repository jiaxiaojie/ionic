/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerGoldCoinHis;

/**
 * 会员代币变更流水DAO接口
 * @author ydt
 * @version 2015-06-26
 */
@MyBatisDao
public interface CustomerGoldCoinHisDao extends CrudDao<CustomerGoldCoinHis> {
	
}