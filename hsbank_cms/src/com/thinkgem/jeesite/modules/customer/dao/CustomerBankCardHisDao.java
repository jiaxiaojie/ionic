/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBankCardHis;

/**
 * 会员银行卡历史变更DAO接口
 * @author ydt
 * @version 2015-06-26
 */
@MyBatisDao
public interface CustomerBankCardHisDao extends CrudDao<CustomerBankCardHis> {

	/**
	 * 绑定银行卡易宝notify处理，根据requestNo更新信息
	 * @param customerBankCard
	 */
	void updateInfoWithRequestNo(CustomerBankCardHis customerBankCardHis);
	
}