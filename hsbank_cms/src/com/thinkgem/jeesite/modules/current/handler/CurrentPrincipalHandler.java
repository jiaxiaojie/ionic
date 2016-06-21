package com.thinkgem.jeesite.modules.current.handler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;

/**
 * 活期产品本金处理
 * <p/>
 * @author lzb
 * @version 2015-12-10
 */
@Component
public class CurrentPrincipalHandler {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Autowired
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	
	/**
	 * 活期日收益计算
	 * @param amount
	 * @param days
	 * @param rate
	 * @return
	 */
	public static Double calculateDayProfit(Double amount, Double rate){
		Double profit = NumberUtils.formatWithScale(NumberUtils.div(
				NumberUtils.mul(rate, amount),
				new Double(CurrentProjectConstant.DAYS_IN_YEAR)), 2);
		return profit;
	}
	
	/**
	 * 活期产品持有信息操作
	 * @param accountId
	 * @param projectId
	 * @param amount
	 */
	public void holdHandler(Long accountId, Long projectId, Double amount){
		CurrentProjectHoldInfo cHoldInfo = currentProjectHoldInfoDao.getByAccountIdAndProjectId(accountId, projectId,CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		//判断是否存在 是 ? 更新 : 插入
		if(cHoldInfo != null){
			currentProjectHoldInfoDao.updatePrincipal(projectId, accountId, amount);
		}else{
			CurrentProjectHoldInfo holdInfo = new CurrentProjectHoldInfo();
			holdInfo.setProjectId(projectId);
			holdInfo.setAccountId(accountId);
			holdInfo.setDefaultValue();
			holdInfo.setPrincipal(amount);
			holdInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
			currentProjectHoldInfoDao.insert(holdInfo);
		}
	}
	
	/**
	 * 添加活期账户本金变更信息
	 * @param projectId
	 * @param opTerm
	 * @param requestNo
	 * @param accountId
	 * @param amount
	 * @param changeType
	 * @param changeReason
	 * @param status
	 * @return
	 */
	public CurrentAccountPrincipalChangeHis addPrincipal(Long projectId,
			String opTerm, String requestNo, Long accountId, Double amount,
			String changeType, String changeReason, String status) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addPrincipal start...");
		CurrentAccountPrincipalChangeHis cPrincipalChangeHis = new CurrentAccountPrincipalChangeHis();
		cPrincipalChangeHis.setAccountId(accountId);
		cPrincipalChangeHis.setProjectId(projectId);
		cPrincipalChangeHis.setChangeValue(amount);
		cPrincipalChangeHis.setChangeType(changeType);
		cPrincipalChangeHis.setChangeReason(changeReason);
		cPrincipalChangeHis.setStatus(status);
		cPrincipalChangeHis.setOpTerm(opTerm);
		cPrincipalChangeHis.setOpDt(new Date());
		cPrincipalChangeHis.setThirdAccountRequestNo(requestNo);
		currentAccountPrincipalChangeHisDao.insert(cPrincipalChangeHis);
		cPrincipalChangeHis.setId(cPrincipalChangeHis.getId());
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addPrincipal end...");
		return cPrincipalChangeHis;
	}

}
