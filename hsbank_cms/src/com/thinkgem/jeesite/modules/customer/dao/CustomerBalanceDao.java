/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;

/**
 * 会员账户余额汇总DAO接口
 * @author ydt
 * @version 2015-06-26
 */
@MyBatisDao
public interface CustomerBalanceDao extends CrudDao<CustomerBalance> {

	/**
	 * 更新投资人的余额
	 * @param accountId 投资账户id
	 * @param money 投资总金额
	 * @param ticketMoney 使用投资券金额
	 */
	void updateInvestorBalance(@Param("accountId") Long accountId, @Param("money") Double money, @Param("ticketMoney") Double ticketMoney);
	
	/**
	 * 更新会员账户可抵用投资金额
	 * @param accountId
	 * @param platformAmount
	 * @return
	 */
	public int updatePlatformAmount(@Param("accountId") Long accountId, @Param("platformAmount") Double platformAmount);
	
	/**
	 * 更新当前账户余额
	 * @param accountId
	 * @param actualMoney
	 * @return
	 */
	public int updateGoldBalance(@Param("accountId") Long accountId, @Param("actualAmount") Double actualAmount);
	
	/**
	 * 更新账户余额
	 * @param accountId
	 * @param amount
	 * @return
	 */
	public int updateGoldBalanceByAlignment(@Param("accountId") Long accountId, @Param("amount") Double amount);
	
	/**
	 * 更新当前账户冻结金额
	 * @param accountId
	 * @param actualAmount
	 * @return
	 */
	public int updateCongealVal(@Param("accountId") Long accountId, @Param("actualAmount") Double actualAmount);
	
	/**
	 * 更新账户余额及平台垫付金额
	 * @param accountId
	 * @param actualAmount
	 * @param platformAmount
	 * @return
	 */
	public int updateGoldBalanceAndPlatformAmount(@Param("accountId") Long accountId, @Param("actualAmount") Double actualAmount, @Param("platformAmount") Double platformAmount);
	
	/**
	 * 还款记录收到钱后，更新收款人汇总信息
	 * @param accountId
	 * @param money
	 * @param prePenaltyMoney
	 * @param latePenaltyMoney
	 * @param principal
	 * @param interest
	 * @return
	 */
	public int updateReciveMoney(@Param("accountId") Long accountId, @Param("money") Double money, @Param("prePenaltyMoney") Double prePenaltyMoney, @Param("latePenaltyMoney") Double latePenaltyMoney, @Param("principal") Double principal, @Param("interest") Double interest);
	/**
	 * 还款发生后，更新借款人汇总信息
	 * @param accountId
	 * @param sumMoney
	 * @param principal
	 * @param interest
	 * @param prePenaltyMoney
	 * @param latePenaltyMoney
	 * @param oldMoney
	 * @return
	 */
	public int updateRepayMoney(@Param("accountId") Long accountId, @Param("sumMoney") Double sumMoney, @Param("principal") Double principal, @Param("interest") Double interest, @Param("prePenaltyMoney") Double prePenaltyMoney, @Param("latePenaltyMoney") Double latePenaltyMoney, @Param("oldMoney") Double oldMoney);
	/**
	 * 追加30天将还贷款金额
	 * @param accountId
	 * @param planMoney
	 * @return
	 */
	public int update30DayWillRepayMoney(@Param("accountId") Long accountId, @Param("planMoney") Double planMoney);

	/**
	 * 根据platformUserNo获取记录
	 * @param platformUserNo
	 * @return
	 */
	CustomerBalance getByPlatformUserNo(@Param("platformUserNo") String platformUserNo);
	
	/**
	 * 放款更新账户余额、冻结金额、代收收益、理财资产、投资次数
	 * @param accountId
	 * @param actualAmount
	 * @param frozenAmount
	 * @param willProfit
	 * @return
	 */
	public int updateLoanInformationSelective(@Param("accountId") Long accountId, @Param("actualAmount") Double actualAmount, @Param("frozenAmount") Double frozenAmount, @Param("willProfit") Double willProfit, @Param("willPrincipal") Double willPrincipal, @Param("amount") Double amount);
	
	/**
	 * 活期产品：更新投资人账户信息
	 * @param accountId
	 * @param actualAmount
	 * @param frozenAmount
	 * @return
	 */
	public int updateCurrentInvestSelective(@Param("accountId") Long accountId, @Param("actualAmount") Double actualAmount, @Param("frozenAmount") Double frozenAmount);
	
	/**
	 * 活期产品：融资人活期收款
	 * @param accountId
	 * @param amount
	 * @return
	 */
	public int updateCurrentReceiveSelective(@Param("accountId") Long accountId, @Param("amount") Double amount);
	
	/**
	 * 债权投资确认，更新账户余额、冻结金额、代收收益、理财资产、投资次数、受转让次数
	 * @param accountId
	 * @param actualAmount
	 * @param frozenAmount
	 * @param willProfit
	 * @param financialAssets
	 * @return
	 */
	public int updateAssignInformationSelective(@Param("accountId") Long accountId, @Param("actualAmount") Double actualAmount, @Param("frozenAmount") Double frozenAmount, @Param("willProfit") Double willProfit, @Param("financialAssets") Double financialAssets);
	
	/**
	 * 债权投资确认，更新转让人的账户余额、代收收益、收款总额、收款本息、理财资产
	 * @param accountId
	 * @param goldBalance
	 * @param willProfit
	 * @param receiveMoney
	 * @param receivePrincipal
	 * @param financialAssets
	 * @return
	 */
	public int updateBalanceForTransferor(@Param("accountId") Long accountId, @Param("goldBalance") Double goldBalance, @Param("willProfit") Double willProfit, @Param("receiveMoney") Double receiveMoney, @Param("receivePrincipal") Double receivePrincipal, @Param("financialAssets") Double financialAssets);

	/**
	 * 充值时更新用户余额信息
	 * @param accountId
	 * @param amount		充值到账金额
	 * @param firstGetDt	首次充值时间
	 */
	void updateByRecharge(@Param("accountId") Long accountId, @Param("amount") double amount, @Param("firstGetDt") Date firstGetDt);

	/**
	 * 提现时更新用户余额信息
	 * @param accountId
	 * @param amount							提现实扣金额
	 * @param freeWithdrawCountChangeValue		免费提现次数变化数，使用为负数
	 */
	void updateByWithdraw(@Param("accountId") Long accountId, @Param("amount") Double amount, @Param("freeWithdrawCountChangeValue") int freeWithdrawCountChangeValue);

	/**
	 * 更新用户免费提现次数
	 * @param freeWithdrawCountChangeValue
	 */
	void updateFreeWithdrawCount(@Param("accountId") Long accountId, @Param("freeWithdrawCountChangeValue") int freeWithdrawCountChangeValue);

	/**
	 * 更新用户余额信息
	 * @param accountId
	 * @param amount
	 */
	long updateBalance(@Param("accountId") Long accountId, @Param("amount") double amount);
	
	/**
	 * 根据外部编号查询信息
	 * @param extendNo
	 * @return
	 */
	CustomerBalance getByExtendNo(@Param("extendNo") String extendNo);
}