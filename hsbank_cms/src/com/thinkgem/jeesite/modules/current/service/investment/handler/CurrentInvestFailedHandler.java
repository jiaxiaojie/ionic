package com.thinkgem.jeesite.modules.current.service.investment.handler;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.handler.CurrentBalanceHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentProjectHandler;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 活期产品投资失败处理
 * <p/>
 * @author lzb
 * @version 2015-12-10
 */
@Component
public class CurrentInvestFailedHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentBalanceHandler currentBalanceHandler;
	@Autowired
	private CurrentProjectHandler currentProjectHandler;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	
	
    /**
     * 投资失败处理
     * @param cInfo
     * @param changeHis
     */
	public void handlerFailed(CurrentProjectInfo cInfo, CurrentAccountPrincipalChangeHis changeHis){
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】failHandler start...");
		Date endDateTime = new Date();
		Long projectId = NumberUtil.toLong(cInfo.getId(),0L);
		Double amount = changeHis.getChangeValue();
		//<1>.更新投资记录状态(已撤销)
		currentAccountPrincipalChangeHisDao.updateStatus(changeHis.getId(), CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_CANCEL);
		//<2>.回滚账户冻结金额
		customerBalanceDao.updateCongealVal(changeHis.getAccountId(), -amount);
		//<3>.新增冻结金额变更记录(取消投资冻结)
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(changeHis.getAccountId()));
		Double goldBalance = balance.getGoldBalance();
		String changeType = ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CANCEL_FREEZE;
		String changeReason = "活期项目：" + cInfo.getName() + "【" + changeHis.getId() + "】(取消冻结金额：" + amount + "元)";
		currentBalanceHandler.addBalanceHisRecord(changeHis.getAccountId(), changeHis.getOpTerm(), projectId,
				changeType, changeReason, changeHis.getId(), amount, goldBalance);
		//<4>.回滚已融资金额、当前实际本金
		currentProjectHandler.cancelProjectSnapshotAmount(projectId, -amount);;
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】failHandler end...");
		logger.info("failHandler total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}
}
