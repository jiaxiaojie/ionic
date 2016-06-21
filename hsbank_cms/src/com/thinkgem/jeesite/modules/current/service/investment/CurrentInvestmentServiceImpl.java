package com.thinkgem.jeesite.modules.current.service.investment;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.handler.CurrentAccountHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentBalanceHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentPrincipalHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentProjectHandler;
import com.thinkgem.jeesite.modules.current.service.investment.handler.CurrentInvestFailedHandler;
import com.thinkgem.jeesite.modules.current.service.investment.handler.CurrentInvestSuccessHandler;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CurrentInvestmentServiceImpl implements ICurrentInvestmentService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentAccountHandler currentAccountHandler;
	@Autowired
	private CurrentBalanceHandler currentBalanceHandler;
	@Autowired
	private CurrentPrincipalHandler currentPrincipalHandler;
	@Autowired
	private CurrentProjectHandler currentProjectHandler;
	@Autowired
	private CurrentInvestFailedHandler currentInvestFailedHandler;
	@Autowired
	private CurrentInvestSuccessHandler currentInvestSuccessHandler;

	@Override
	public void checkCurrentInvest(CurrentProjectInfo cInfo,Long accountId, Double amount, boolean isCheck) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】checkCurrentInvest start...");
		//<1>.用户是否可投校验
		currentAccountHandler.checkIsInvestForAccount(cInfo, accountId);
		//<2>.持有本金校验
		currentAccountHandler.checkMaxHoldMoney(accountId, amount);
		//<2>.可用余额校验
		currentBalanceHandler.checkAvailableBalance(accountId, amount);
		//<3>.项目状态校验
		currentProjectHandler.checkProjectStatus(cInfo.getId());
		//<4>.项目自动还款授权校验
		currentProjectHandler.checkProjectAutoRepay(cInfo.getId(), isCheck);
		//<5>.投资金额校验
		currentProjectHandler.checkProjectAmount(cInfo, amount);
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】checkCurrentInvest end...");
		logger.info("checkCurrentInvest total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}

	@Override
	@Transactional(readOnly = false)
	public void beforeCurrentInvest(CurrentProjectInfo cInfo, String opTerm,
			String requestNo, Long accountId, Double amount, boolean isCheck) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】beforeCurrentInvest start...");
		//----------------------------------参数校验--------------------------------------
		checkCurrentInvest(cInfo, accountId, amount, isCheck);
		//----------------------------------数据入库--------------------------------------
		Long projectId = NumberUtil.toLong(cInfo.getId(), 0L);
		//<1>.插入投资记录
		String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT;
		String changeReason = "活期投资";
		String status = CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE;
		CurrentAccountPrincipalChangeHis changeHis = currentPrincipalHandler
				.addPrincipal(projectId, opTerm, requestNo, accountId, amount,
						changeType, changeReason, status);
		//<2>.更新冻结金额
		Double goldBalance = currentBalanceHandler.updateCongealValForCurrentInvest(accountId, amount);
		//<3>.新增冻结金额变更记录
		String changeTypeHis = ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_FREEZE; 
		String changeReasonHis = "活期项目：" + cInfo.getName() + "【" + changeHis.getId() + "】投资(冻结金额：" + changeHis.getChangeValue() + "元)";; 
		currentBalanceHandler.addBalanceHisRecord(accountId, opTerm, projectId,
				changeTypeHis, changeReasonHis, changeHis.getId(), -amount, goldBalance);
		//<4>.更新活期项目执行快照
		currentProjectHandler.updateProjectSnapshotAmount(cInfo, amount);
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】beforeCurrentInvest end...");
		logger.info("beforeCurrentInvest total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
		
	}
	
	@Override
	@Transactional(readOnly = false)
	public void alterCurrentInvest(CurrentProjectInfo cInfo,
			CurrentAccountPrincipalChangeHis cHis, boolean result) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】beforeCurrentInvest start...");
		//判断，易宝返回是否成功
		if(result){
			//投资确认
			boolean confirmResult = currentInvestSuccessHandler.confirmCurrentInvest(cInfo, cHis);
			if(confirmResult){
				//<1>.投资成功处理
				currentInvestSuccessHandler.handerSuccess(cInfo, cHis);
			}else{
				//<2>.投资失败处理
				currentInvestFailedHandler.handlerFailed(cInfo, cHis);
			}
		}else{
			//<2>.投资失败处理
			currentInvestFailedHandler.handlerFailed(cInfo, cHis);
		}
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】beforeCurrentInvest end...");
		logger.info("beforeCurrentInvest total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
		
	}

}
