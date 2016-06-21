/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentMoneyHis;

/**
 * 会员可抵用投资金额变更流水DAO接口
 * @author ydt
 * @version 2015-07-30
 */
@MyBatisDao
public interface CustomerInvestmentMoneyHisDao extends CrudDao<CustomerInvestmentMoneyHis> {
	/**
	 * 根据会员账号及投资记录删除
	 * @param accountId
	 * @param recordId
	 * @return
	 */
	public int deleteByRecordIdAndAccountId(@Param("accountId") Long accountId, @Param("recordId") String recordId);
}