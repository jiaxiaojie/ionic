/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CreditMachineAccount;

/**
 * 债权台账DAO接口
 * @author wanduanrui
 * @version 2016-03-30
 */
@MyBatisDao
public interface CreditMachineAccountDao extends CrudDao<CreditMachineAccount> {

	CreditMachineAccount getByEntity(CreditMachineAccount creditMachineAccount);
	
}