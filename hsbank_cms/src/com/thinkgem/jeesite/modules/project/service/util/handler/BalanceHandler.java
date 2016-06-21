package com.thinkgem.jeesite.modules.project.service.util.handler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;

/**
 * 余额处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("balanceHandler")
public class BalanceHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	
	
	/**
	 * 参数校验
	 * <1>.计算实际投资额：投资总金额 - 用券金额 - 平台垫付金额   + 下家服务费(债权转让项目)
	 * <2>.if(【实际投资额】>【当前用户余额】) {抛出异常;} 
	 * @param accountId 		当前用户账户Id
	 * @param amount 			投资总金额
	 * @param ticketAmount 		用券金额
	 * @param platformAmount 	平台垫付金额
	 * @param downServiceCharge	下家服务费(债权转让项目 ? downServiceCharge : 0.00)
	 */
	public void check(Long accountId, Double amount, Double ticketAmount, Double platformAmount, Double downServiceCharge) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":check Account 【" + accountId + "】  goldBalance start...");
		//计算实际投资额  = 投资金额 - 投资券金额 - 抵用金额 + 下家服务费(债权转让项目)
		Double actualAmount = LoanUtil.formatAmount(amount - ticketAmount - platformAmount + downServiceCharge);
		logger.debug("The Parameter: 【amount:" + amount
				+ "】,【ticketAmount:" + ticketAmount + "】,【platformAmount:"
				+ platformAmount + "】");
		//查询会员账户汇总信息
		CustomerBalance customerBalance = customerBalanceDao.get(String.valueOf(accountId));
		if(customerBalance == null){
			customerBalance = new CustomerBalance();
		}
		Double goldBalance = customerBalance.getGoldBalance() != null ?customerBalance.getGoldBalance() : 0;
		Double congealVal = customerBalance.getCongealVal() != null ? customerBalance.getCongealVal() : 0;
		Double accountBalance = goldBalance - congealVal < 0 ? 0 : goldBalance - congealVal;
		accountBalance = LoanUtil.formatAmount(accountBalance);
		logger.debug("The Account info: 【goldBalance:" + goldBalance
				+ "】,【congealVal:" + congealVal + "】,【accountBalance:"
				+ accountBalance + "】");
		//判断，【实际投资额】<=【当前账户可用余额】
		if(actualAmount != null && actualAmount.compareTo(accountBalance) > 0){
			throw new ServiceException("当前用户的可用余额不足：可用余额 =【" + accountBalance + "】，实际投资额 =【" + actualAmount + "】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":check Account 【" + accountId + "】  goldBalance start...");
	}
	
	/**
	 * 数据入库：投资时，更新当前用户的冻结金额【当前用户的冻结金额】+=实际支付金额
	 * @param accountId
	 * @param actualAmount
	 * @return
	 */
	public Double updateCongealValForInvest(Long accountId, Double actualAmount) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":updateBalanceForInvest start...");
		logger.debug("updateBalanceForInvest the Parameter: 【actualAmount:" + actualAmount + "】");
		customerBalanceDao.updateCongealVal(accountId, actualAmount);
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		Double goldBalance = balance.getGoldBalance();
		Double congealVal = balance.getCongealVal();
		//可用余额
		Double availableBalance = goldBalance - congealVal;
		logger.debug("updateBalanceForInvest the Account info: 【goldBalance:" + goldBalance
				+ "】,【congealVal:" + congealVal + "】,【availableBalance:"
				+ availableBalance + "】");
		if(goldBalance.compareTo(congealVal) < 0){
			throw new ServiceException("当前用户的可用余额小于0：可用余额 =【" + availableBalance + "】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":updateBalanceForInvest end...");
		return goldBalance;
	}
	
	/**
	 * 数据入库：债权转让成功时，更新转让人的余额 【转让人的余额】 += （【转让金额】-【上家手续费】）
	 * @param accountId 			转让人账户Id
	 * @param amount 				转让金额
	 */
	public Double updateBalanceForAssign(Long accountId, Double amount) {
		//上家手续费
		Double tempAmount = amount + ProjectUtil.getUpServiceCharge(amount);
		customerBalanceDao.updateGoldBalance(accountId, tempAmount);
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		Double goldBalance = balance.getGoldBalance();
		if(goldBalance < 0){
			throw new ServiceException("当前用户的余额小于0：余额 =【" + goldBalance + "】");
		}
		return goldBalance;
	}
	
	/**
	 * 数据入库：新增余额变更记录(投资冻结)
	 * <p/>
	 * @param accountId			当前用户账户Id
	 * @param projectId			项目流水号
	 * @param recordId			投资记录Id
	 * @param actualMoney		实际投资额
	 * @param goldBalance		账户余额
	 */
	public void addBalanceRecord(Long accountId, String opTerm, Long projectId, String changeReason, String recordId, Double actualMoney, Double goldBalance){
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addBalanceRecord start...");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(actualMoney);
		customerBalanceHis.setBalance(goldBalance);
		customerBalanceHis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_FREEZE);
		customerBalanceHis.setChangeReason(changeReason);
		customerBalanceHis.setRelProject(String.valueOf(projectId));
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addBalanceRecord end...");
	}
}
