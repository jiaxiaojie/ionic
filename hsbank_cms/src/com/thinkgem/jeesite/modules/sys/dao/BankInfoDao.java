/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.BankInfo;

/**
 * 银行信息DAO接口
 * @author wanduanrui
 * @version 2015-11-17
 */
@MyBatisDao
public interface BankInfoDao extends CrudDao<BankInfo> {
	/**
	 * 根据银行代码查询信息
	 * @param bankCode
	 * @return
	 */
	public BankInfo getBankInfoByBankCode(@Param("bankCode") String bankCode);
}