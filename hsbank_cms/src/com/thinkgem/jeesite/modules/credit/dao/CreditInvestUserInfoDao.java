/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CreditInvestUserInfo;

/**
 * 债权投资用户信息DAO接口
 * @author wanduanrui
 * @version 2016-03-30
 */
@MyBatisDao
public interface CreditInvestUserInfoDao extends CrudDao<CreditInvestUserInfo> {

	List<CreditInvestUserInfo> birthdayRemindList(CreditInvestUserInfo creditInvestUserInfo);

	List<CreditInvestUserInfo> repaymentRemindList(CreditInvestUserInfo creditInvestUserInfo);

	CreditInvestUserInfo getByEntity(CreditInvestUserInfo creditInvestUserInfo);
	
}