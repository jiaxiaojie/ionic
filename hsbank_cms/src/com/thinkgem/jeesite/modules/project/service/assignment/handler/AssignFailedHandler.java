package com.thinkgem.jeesite.modules.project.service.assignment.handler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentMoneyHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;
import com.hsbank.util.type.StringUtil;

/**
 * 债权转让失败处理
 * @author Arthur.Xie
 * 2015-07-30
 */
@Component("assignFailedHandler")
public class AssignFailedHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao investmentRecordDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerInvestmentTicketDao investmentTicketDao;
	@Autowired
	private ProjectExecuteSnapshotDao snapshotDao;
	@Autowired
	private CustomerInvestmentMoneyHisDao customerInvestmentMoneyHisDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;
	
	/**
	 * 处理器
	 * <1>.回滚投资记录状态
	 * <2>.回滚还款计划状态
	 * <3>.回滚账户冻结金额
	 * <4>.新增冻结金额变更记录(取消投资冻结)
	 * <5>.回滚优惠劵
	 * <6>.回滚平台垫付金额
	 * <7>.删除【平台垫付金额】使用记录
	 * <8>.回滚项目已融资金额、已冻结平台垫付金额、已冻结优惠券金额、已冻结服务费
	 * @param projectTransferInfo
	 * @param recordAssignment
	 */
	public void handler(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem) + "】Assignment Data rollback start...");
		//<1>.回滚投资记录状态
		investmentRecordHandler(projectTransferInfo, recordAssignment, recordRemaining);
		//<2>.回滚还款计划状态
		repaymentPlanHandler(projectTransferInfo, recordAssignment, recordRemaining);
		//<3>.回滚账户冻结金额
		Double actualMoney = recordAssignment.getActualAmount();
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":Assignment updateCongealVal accountId :" + recordAssignment.getInvestmentUserId() + " 【congealVal:" + -actualMoney + "】 start.");
		customerBalanceDao.updateCongealVal(recordAssignment.getInvestmentUserId(),  - actualMoney);
		//<5>.新增冻结金额变更记录(取消投资冻结)
		Long projectId = projectTransferInfo.getProjectId();
		String projectName = projectTransferInfo.getProjectBaseInfo() != null ?  projectTransferInfo.getProjectBaseInfo().getProjectName() : "";
		this.cancelBalanceRecord(recordAssignment.getInvestmentUserId(), recordAssignment.getOpTerm(), projectId, projectName, recordAssignment.getId(), actualMoney);
		//<6>.回滚优惠劵
		String ticketIds = StringUtil.dealString(recordAssignment.getTicketIds());
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":Assignment Rollback ticketIds :" + ticketIds + " start.");
		if(!"".equals(ticketIds)){
			investmentTicketDao.batchUpdateStatus(StringUtils.surroundSymbol(ticketIds,",","'"), ProjectConstant.TICKET_DICT_NORMAL);
		}
		//<7>.回滚加息券信息
		rateTicketHandler.updateRateTicket(recordAssignment.getRateTicketIds(), ProjectConstant.TICKET_DICT_NORMAL, "", null, null);
		Double platformAmount = recordAssignment.getPlatformAmount() !=null ? recordAssignment.getPlatformAmount() : 0.00;
		if(platformAmount.compareTo(0.00) > 0){
			//<7>.回滚平台垫付金额
			customerBalanceDao.updatePlatformAmount(recordAssignment.getInvestmentUserId(), - platformAmount);
			//<8>.删除【平台垫付金额】使用记录
			customerInvestmentMoneyHisDao.deleteByRecordIdAndAccountId(recordAssignment.getInvestmentUserId(), recordAssignment.getId());
		}
		//<9>.回滚项目已融资金额、已冻结平台垫付金额、已冻结优惠券金额、已冻结服务费
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":Assignment Rollback some amount data : 【endFinanceMoney:"
				+ -recordAssignment.getAmount() + "】,【sumPlatformAmount:"
				+ -recordAssignment.getTicketAmount() + "】,【sumTicketMoney:"
				+ -recordAssignment.getTicketAmount() + "】,【sumServiceCharge:"
				+ -recordAssignment.getToPlatformMoney() + "】  start.");
		snapshotDao.updateAmount(projectTransferInfo.getProjectId(), projectTransferInfo.getTransferProjectId(), LoanUtil.formatAmount(- recordAssignment.getAmount()), - recordAssignment.getTicketAmount(), - recordAssignment.getPlatformAmount(), - recordAssignment.getToPlatformMoney());
		ProjectExecuteSnapshot snapshot = snapshotDao.getTransferSnapshot(projectTransferInfo.getProjectId(), projectTransferInfo.getTransferProjectId());
		Double endFinanceMoney = snapshot.getEndFinanceMoney() !=null ? snapshot.getEndFinanceMoney() : 0.0;
		if(endFinanceMoney.compareTo(0.0) < 0){
			throw new ServiceException("项目已融资金额小于0：已融资金额 =【" + endFinanceMoney + "】");
		}
		logger.info("---------" + DateUtils.formatDate(new Date())
				+ ":Assignment Rollback some amount data end.");
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】Assignment Data rollback end...");
		logger.info("Assignment Data rollback total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}
	
	/**
	 * 回滚投资记录状态
	 * <1>.更新转让债权的投资记录为【已撤销】状态
	 * <2>.更新剩余债权的投资记录为【已撤销】状态
	 * <3>.更新原投资记录为【正常】状态
	 */
	private void investmentRecordHandler(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining) {
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "investmentRecordHandler status start.");
		//<1>.更新转让部分的投资记录为【已撤销】状态
		investmentRecordDao.updateStatus(recordAssignment.getId(), ProjectConstant.PROJECT_INVESTMENT_STATUS_REPEAL);
		//<2>.更新剩余债权的投资记录为【已撤销】状态
		investmentRecordDao.updateStatus(recordRemaining.getId(), ProjectConstant.PROJECT_INVESTMENT_STATUS_REPEAL);
		//<3>.更新原投资记录的状态为【正常】
		investmentRecordDao.updateStatus(String.valueOf(projectTransferInfo.getInvestmentRecordId()), ProjectConstant.PROJECT_INVESTMENT_STATUS_NORMAL);
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "investmentRecordHandler status end.");
	}
	
	/**
	 * 回滚还款计划状态
	 * <1>.更新转让债权的还款计划为【已撤销】状态
	 * <2>.更新剩余债权的还款计划为【已撤销】状态
	 * <3>.更新原投资记录的还款计划为【预算】状态
	 */
	private void repaymentPlanHandler(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining) {
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "repaymentPlanHandler status start.");
		//<1>.更新转让债权的还款计划为【已撤销】状态
		repaymentSplitRecordDao.updateStatusByInvestmentRecordId(String.valueOf(recordAssignment.getId()), ProjectConstant.PROJECT_REPAY_STATUS_REPEAL);
		//<2>.更新剩余债权的还款计划为【已撤销】状态
		repaymentSplitRecordDao.updateStatusByInvestmentRecordId(String.valueOf(recordRemaining.getId()), ProjectConstant.PROJECT_REPAY_STATUS_REPEAL);
		//<3>.更新原投资记录的还款计划为【正常】状态
		//原标的待还款计划列表
		List<ProjectRepaymentSplitRecord> willRepaymentList = repaymentSplitRecordDao.getRepaymentListByInvestmentRecord(projectTransferInfo.getPir().getId(), ProjectConstant.PROJECT_REPAY_STATUS_TRANSFER);
		for (ProjectRepaymentSplitRecord record : willRepaymentList) {
			repaymentSplitRecordDao.updateStatusById(String.valueOf(record.getSplitRecordId()), ProjectConstant.PROJECT_REPAY_STATUS_BUDGET);
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "repaymentPlanHandler status end.");
	}
	
	
    /**
	 * 数据入库：新增余额变更记录(取消投资冻结)
	 * <p/>
	 * @param accountId			当前用户账户Id
	 * @param projectId			项目流水号
	 * @param recordId			投资记录Id
	 * @param actualMoney		实际投资额
	 * @param goldBalance		账户余额
	 */
	public void cancelBalanceRecord(Long accountId, String opTerm, Long projectId, String projectName, String recordId, Double actualMoney){
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":cancelBalanceRecord accountId :" + accountId + " start.");
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		Double goldBalance = balance.getGoldBalance();
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(actualMoney);
		customerBalanceHis.setBalance(goldBalance);
		customerBalanceHis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CANCEL_FREEZE);
		customerBalanceHis.setChangeReason("投资项目：" + projectName + "(取消冻结金额：" + actualMoney + "元)");
		customerBalanceHis.setRelProject(String.valueOf(projectId));
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":cancelBalanceRecord accountId :" + accountId + " end.");
	}
}
