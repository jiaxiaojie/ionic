package com.thinkgem.jeesite.modules.current.service.investment;

import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;

public interface ICurrentInvestmentService {
	
	/**
	 * 投资校验
	 * @param cInfo
	 * @param opTerm
	 * @param requestNo
	 * @param accountId
	 * @param amount
	 */
	public void checkCurrentInvest(CurrentProjectInfo cInfo, Long accountId, Double amount, boolean isCheck);
    
	/**
	 * 投资前置服务
	 * @param currentProjectInfo
	 * @param opTerm
	 * @param requestNo
	 * @param accountId
	 * @param amount
	 */
	public void beforeCurrentInvest(CurrentProjectInfo cInfo, String opTerm, String requestNo, Long accountId, Double amount, boolean isCheck);
	
	/**
	 * 投资后置服务
	 * @param cInfo
	 * @param cHis
	 * @param result
	 */
	public void alterCurrentInvest(CurrentProjectInfo cInfo, CurrentAccountPrincipalChangeHis cHis, boolean result);
}
