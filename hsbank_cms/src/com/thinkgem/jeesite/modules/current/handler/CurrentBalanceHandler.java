package com.thinkgem.jeesite.modules.current.handler;

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

/**
 * 账户资金处理
 * <p/>
 * @author lzb
 * @version 2015-12-10
 */
@Component
public class CurrentBalanceHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	
	/**
	 * 检查账户的可用余额
	 * @param accountId
	 * @param amount
	 */
	public void checkAvailableBalance(Long accountId, Double amount){
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":checkAvailableBalance 【" + accountId + "】  availableBalance start...");
		CustomerBalance customerBalance = customerBalanceDao.get(String.valueOf(accountId));
		if(customerBalance == null){
			customerBalance = new CustomerBalance();
		}
		Double goldBalance = customerBalance.getGoldBalance() != null ?customerBalance.getGoldBalance() : 0;
		Double congealVal = customerBalance.getCongealVal() != null ? customerBalance.getCongealVal() : 0;
		Double availableBalance = goldBalance - congealVal < 0 ? 0 : goldBalance - congealVal;
		availableBalance = LoanUtil.formatAmount(availableBalance);
		logger.debug("The Account info: 【goldBalance:" + goldBalance
				+ "】,【congealVal:" + congealVal + "】,【availableBalance:"
				+ availableBalance + "】");
		//判断，【实际投资额】<=【当前账户可用余额】
		if(amount != null && amount.compareTo(availableBalance) > 0){
			throw new ServiceException("当前用户的可用余额不足：可用余额 =【" + availableBalance + "】，实际投资额 =【" + amount + "】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":checkAvailableBalance 【" + accountId + "】  availableBalance end...");
	}
	
	/**
	 * 数据入库：购买活期产品时，更新当前用户的冻结金额【当前用户的冻结金额】+=购买金额
	 * @param accountId
	 * @param amount
	 * @return
	 */
	public Double updateCongealValForCurrentInvest(Long accountId, Double amount) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":updateCongealValForCurrentInvest start...");
		logger.debug("updateCongealValForCurrentInvest the Parameter: 【amount:" + amount + "】");
		customerBalanceDao.updateCongealVal(accountId, amount);
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		Double goldBalance = balance.getGoldBalance();
		Double congealVal = balance.getCongealVal();
		//可用余额
		Double availableBalance = LoanUtil.formatAmount(goldBalance - congealVal);
		logger.debug("updateCongealValForCurrentInvest the Account info: 【goldBalance:" + goldBalance
				+ "】,【congealVal:" + congealVal + "】,【availableBalance:"
				+ availableBalance + "】");
		if(availableBalance.compareTo(0.0) < 0){
			throw new ServiceException("当前用户的可用余额小于0：可用余额 =【" + availableBalance + "】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":updateCongealValForCurrentInvest end...");
		return goldBalance;
	}
	
	
	/**
	 * 数据入库：新增账户余额变更记录
	 * @param accountId
	 * @param opTerm
	 * @param projectId
	 * @param changeType
	 * @param changeReason
	 * @param recordId
	 * @param actualMoney
	 * @param goldBalance
	 */
	public void addBalanceHisRecord(Long accountId, String opTerm,
			Long projectId, String changeType, String changeReason,
			String recordId, Double amount, Double goldBalance) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addBalanceHisRecord start...");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(amount);
		customerBalanceHis.setBalance(goldBalance);
		customerBalanceHis.setChangeType(changeType);
		customerBalanceHis.setChangeReason(changeReason);
		customerBalanceHis.setRelProject(String.valueOf(projectId));
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addBalanceHisRecord end...");
	}

}
