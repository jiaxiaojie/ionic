/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;

/**
 * 活期账户总览DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentAccountSummaryDao extends CrudDao<CurrentAccountSummary> {

	/**
	 * 还利息时做的更新操作
	 * @param accountId
	 * @param getInterest
	 * @return
	 */
	Long updateByRepay(@Param("accountId") long accountId, @Param("getInterest") double getInterest);

	/**
	 * 将用户申请赎回的本金从平台打款给用户时的更新操作
	 * @param accountId
	 * @param redeemPrincipal
	 */
	long updateByPayRedeem(@Param("accountId") Long accountId, @Param("redeemPrincipal") Double redeemPrincipal);

	/**
	 * 提取利息时做的更新操作
	 * @param accountId
	 * @param pollInterest
	 * @return
	 */
	long updateByPollInterest(@Param("accountId") long accountId, @Param("pollInterest") double pollInterest);

	/**
	 * 清盘时做的更新操作
	 * @param accountId
	 * @param principal
	 * @param interest
	 * @return
	 */
	long updateByWindingUp(@Param("accountId") long accountId, @Param("principal") double principal, @Param("interest") double interest);

	/**
	 * 根据accountId获取其账户总览信息
	 * @param accountId
	 * @return
	 */
	CurrentAccountSummary getByAccountId(@Param("accountId") long accountId);
	
	/**
	 * 累计当前持有本金、累计获取利息汇总
	 * @param map
	 * @return
	 */
	public Map<String,Object> getStatCurrentPrincipalAndInterestMoney();
	
	/**
	 * 按账户统计当前持有本金
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getStatCurrentPrincipalListByAccountId(Map<String, Object> map);
	
	/**
	 * 投资更新累计投资金额、当前持有本金
	 * @param accountId
	 * @param amount
	 * @return
	 */
	long updateByInvestment(@Param("accountId") long accountId, @Param("amount") double amount);
}