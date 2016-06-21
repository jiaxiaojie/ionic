/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CreditBaseInfo;

/**
 * 债券基本信息DAO接口
 * @author wanduanrui
 * @version 2016-03-29
 */
@MyBatisDao
public interface CreditBaseInfoDao extends CrudDao<CreditBaseInfo> {

	CreditBaseInfo getByEntity(CreditBaseInfo creditBaseInfo);

	

	void updateStatus(HashMap<String, Object> params);



	void addRaisedMoneyOnLine(@Param("creditId") Long creditId, @Param("endFinanceMoney") Double endFinanceMoney);
	
	void addRaisedMoneyBelowLine(@Param("creditId") Long creditId, @Param("endFinanceMoney") Double endFinanceMoney);



	void addRaisingMoney(@Param("creditId") Long creditId, @Param("financeMoney") Double financeMoney);



	void addToRaiseMoney(@Param("creditId") Long creditId, @Param("money") Double money);



	void updateStatusByIdAndStatus(@Param("creditId") Long creditId, @Param("status") String status);
	
}