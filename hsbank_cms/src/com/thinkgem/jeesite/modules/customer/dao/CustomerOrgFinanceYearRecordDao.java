/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerOrgFinanceYearRecord;

/**
 * 企业会员财务年表DAO接口
 * @author ydt
 * @version 2015-06-30
 */
@MyBatisDao
public interface CustomerOrgFinanceYearRecordDao extends CrudDao<CustomerOrgFinanceYearRecord> {
	
}