/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawDeposiHis;

/**
 * 会员提现额流水DAO接口
 * @author yangtao
 * @version 2015-07-23
 */
@MyBatisDao
public interface CustomerWithdrawDeposiHisDao extends CrudDao<CustomerWithdrawDeposiHis> {

	List<CustomerWithdrawDeposiHis> findListDuringDate(@Param("accountId") Long accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
}