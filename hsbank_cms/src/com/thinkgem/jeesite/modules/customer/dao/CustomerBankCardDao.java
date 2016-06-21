/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;

/**
 * 会员银行卡信息DAO接口
 * @author ydt
 * @version 2015-06-25
 */
@MyBatisDao
public interface CustomerBankCardDao extends CrudDao<CustomerBankCard> {

	CustomerBankCard getByAccountId(Long accountId);

	/**
	 * 判断指定accountId的记录是否存在
	 * @param accountId
	 * @return
	 */
	boolean recordIsExistWithAccountId(Long accountId);

	/**
	 * 绑定银行卡易宝notify处理，根据requestNo更新信息
	 * @param customerBankCard
	 */
	void updateInfoWithRequestNo(CustomerBankCard customerBankCard);

	CustomerBankCard getByRequestNo(String requestNo);

	String selectetCardNoIsnull(Long accountId);

}