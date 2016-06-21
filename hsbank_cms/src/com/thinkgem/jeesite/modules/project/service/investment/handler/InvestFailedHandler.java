package com.thinkgem.jeesite.modules.project.service.investment.handler;

import java.util.Date;

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
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;

/**
 * 投资失败处理
 * <p/>
 * @author zibo.li
 * CreateDate 2015-07-30
 */
@Component("investFailedHandler")
public class InvestFailedHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao investmentRecordDao;
	@Autowired
	private ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerInvestmentTicketDao investmentTicketDao;
	@Autowired
	private ProjectExecuteSnapshotDao snapshotDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private CustomerInvestmentMoneyHisDao customerInvestmentMoneyHisDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;
	
	/**
     * 处理器
	 * <1>.更新投资记录状态（已撤销）
	 * <2>.更新还款计划状态（已撤销）
	 * <3>.回滚账户冻结金额
	 * <4>.新增冻结金额变更记录(取消投资冻结)
	 * <5>.回滚优惠劵
	 * <6>.回滚可抵扣金额
	 * <7>.删除【平台垫付金额】使用记录
	 * <8>.回滚项目已融资金额、已冻结平台垫付金额、已冻结优惠券金额、已冻结服务费
	 * @param projectInfo				项目信息
	 * @param investmentRecord		投资记录
	 */
	public void handler(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】Investment Data rollback start...");
		//<1>.更新投资记录状态（已撤销）
		investmentRecordDao.updateStatus(String.valueOf(investmentRecord.getId()), ProjectConstant.PROJECT_INVESTMENT_STATUS_REPEAL);
		//<2>.更新还款计划状态（已撤销）
		repaymentSplitRecordDao.updateStatusByInvestmentRecordId(String.valueOf(investmentRecord.getId()), ProjectConstant.PROJECT_REPAY_STATUS_REPEAL);
		//实际投资额
		Double actualMoney = investmentRecord.getActualAmount();	
		//<3>.回滚账户冻结金额
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":Investment updateCongealVal accountId :" + investmentRecord.getInvestmentUserId() + " 【congealVal:" + -actualMoney + "】 start.");
		customerBalanceDao.updateCongealVal(investmentRecord.getInvestmentUserId(), - actualMoney);
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":Investment updateCongealVal accountId :" + investmentRecord.getInvestmentUserId() + " 【congealVal:" + -actualMoney + "】 end.");
		//<4>.新增冻结金额变更记录(取消投资冻结)
		this.cancelBalanceRecord(investmentRecord.getInvestmentUserId(), investmentRecord.getOpTerm(), NumberUtil.toLong(projectInfo.getProjectId(),0L), projectInfo.getProjectName(), investmentRecord.getId(), actualMoney);
		//<5>.回滚优惠劵
		String ticketIds = StringUtil.dealString(investmentRecord.getTicketIds());
		if(!"".equals(ticketIds)){
			logger.debug("the ticketIds:" + ticketIds);
			investmentTicketDao.batchUpdateStatus(StringUtils.surroundSymbol(ticketIds,",","'"), ProjectConstant.TICKET_DICT_NORMAL);
		}
		//<6>.回滚加息券
		rateTicketHandler.updateRateTicket(investmentRecord.getRateTicketIds(), ProjectConstant.TICKET_DICT_NORMAL, "", null, null);
		Double platformAmount = investmentRecord.getPlatformAmount() !=null ? investmentRecord.getPlatformAmount() : 0.00;
		if(platformAmount.compareTo(0.00) > 0){
			//<7>.回滚平台垫付金额
			customerBalanceDao.updatePlatformAmount(investmentRecord.getInvestmentUserId(), - platformAmount);
			//<8>.删除【平台垫付金额】使用记录
			customerInvestmentMoneyHisDao.deleteByRecordIdAndAccountId(investmentRecord.getInvestmentUserId(), investmentRecord.getId());
		}
		//<9>.回滚项目已融资金额、已冻结平台垫付金额、已冻结优惠券金额、已冻结服务费
		logger.debug("---------" + DateUtils.formatDateTime(new Date())
				+ ":Investment Rollback some amount data : 【endFinanceMoney:"
				+ -investmentRecord.getAmount() + "】,【sumPlatformAmount:"
				+ -investmentRecord.getTicketAmount() + "】,【sumTicketMoney:"
				+ -investmentRecord.getTicketAmount() + "】,【sumServiceCharge:"
				+ -investmentRecord.getToPlatformMoney() + "】  start.");
		snapshotDao.updateAmount(NumberUtil.toLong(projectInfo.getProjectId(), 0L), 0L, LoanUtil.formatAmount(- investmentRecord.getAmount()), - investmentRecord.getTicketAmount(), - platformAmount, - investmentRecord.getToPlatformMoney());
		ProjectExecuteSnapshot snapshot = snapshotDao.getTransferSnapshot(NumberUtil.toLong(projectInfo.getProjectId(),0L), 0L);
		Double endFinanceMoney = snapshot.getEndFinanceMoney() !=null ? snapshot.getEndFinanceMoney() : 0.0;
		if(endFinanceMoney.compareTo(0.0) < 0){
			throw new ServiceException("项目已融资金额小于0：已融资金额 =【" + endFinanceMoney + "】");
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":Investment Rollback some amount data end.");
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】Investment Data rollback end...");
		logger.info("Investment Data rollback total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
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
				+ ":Investment cancelBalanceRecord accountId :" + accountId + " start.");
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		Double goldBalance = balance.getGoldBalance();
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(actualMoney);
		customerBalanceHis.setBalance(goldBalance);
		customerBalanceHis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CANCEL_FREEZE);
		customerBalanceHis.setChangeReason("投资项目：" + projectName + "【" + recordId + "】(取消冻结金额：" + actualMoney + "元)");
		customerBalanceHis.setRelProject(String.valueOf(projectId));
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":Investment cancelBalanceRecord accountId :" + accountId + " end.");
	}
}
