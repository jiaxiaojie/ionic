package com.thinkgem.jeesite.modules.current.handler;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CurrentAccountHandler {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;

	/**
	 * 校验用户是否可投活期项目
	 * @param cInfo
	 * @param accountId
	 */
	public void checkIsInvestForAccount(CurrentProjectInfo cInfo,
			Long accountId){
		//<0>.用户不能自己投自己
		String customerId = cInfo.getBorrowerAccountId() != null ? cInfo.getBorrowerAccountId().longValue() + "" : "";
		String opAccountId=accountId.longValue()+"";
		if(customerId.equals(opAccountId)){
			throw new ServiceException("用户不能投资自己的活期项目");
		}
		
	}
	
	/**
	 * 持有本金限制
	 * @param accountId
	 * @param amount
	 */
	public void checkMaxHoldMoney(Long accountId, Double amount){
		double maxPrincipal = CurrentProjectConstant.MAX_INVESTMENT_MONEY;
		//已持有本金
		CurrentAccountSummary summary = currentAccountSummaryDao.getByAccountId(accountId);
		Double holdPrincipal = summary.getCurrentPrincipal() !=null ? summary.getCurrentPrincipal() :0.00;
		Double frozenAmount = currentAccountPrincipalChangeHisDao.getSumCurrentPrincipal(accountId, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE);
		frozenAmount = frozenAmount !=null ? frozenAmount : 0.00;
		//累计持有本金 = 本次投资金额 + 已持有本金 + 投资冻结资金
		Double totalPrincipal = LoanUtil.formatAmount(holdPrincipal + amount + frozenAmount);
		//差值 = 最大持有本金 - 已持有本金 - 投资冻结资金
		Double differPrincipal = LoanUtil.formatAmount(maxPrincipal - holdPrincipal - frozenAmount);
		if(holdPrincipal.compareTo(maxPrincipal) == 0){
			throw new ServiceException("持有本金【" + holdPrincipal + "】已达到最大限额");
		}else if(totalPrincipal.compareTo(maxPrincipal) > 0){
			throw new ServiceException("本次投资金额【" + amount + "】 加上已持有本金【" + holdPrincipal + "】大于最大限额，您本次最多能投资【" + differPrincipal + "】");
		}
	}
	
	/**
	 * 活期账户总览操作
	 * @param accountId
	 * @param amount
	 */
	public void summaryHandler(Long accountId, Double amount){
		CurrentAccountSummary summary = currentAccountSummaryDao.getByAccountId(accountId);
		//判断是否存在 是 ? 更新 : 插入
		if(summary != null){
			currentAccountSummaryDao.updateByInvestment(accountId, amount);
		}else{
			CurrentAccountSummary cSummary = new CurrentAccountSummary();
			cSummary.setAccountId(accountId);
			cSummary.setDefaultValue();
			cSummary.setTotalInvestmentMoney(amount);
			cSummary.setCurrentPrincipal(amount);
			cSummary.setCreateDt(new Date());
		}
	}

}
