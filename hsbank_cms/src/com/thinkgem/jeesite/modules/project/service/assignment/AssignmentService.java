package com.thinkgem.jeesite.modules.project.service.assignment;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.service.assignment.handler.AssignFailedHandler;
import com.thinkgem.jeesite.modules.project.service.assignment.handler.AssignRecordHandler;
import com.thinkgem.jeesite.modules.project.service.assignment.handler.AssignSuccessHandler;
import com.thinkgem.jeesite.modules.project.service.assignment.handler.ProjectTransferHandler;
import com.thinkgem.jeesite.modules.project.service.assignment.handler.RepaymentPlanHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.BalanceHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.PlatformAmountHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.ProjectProcessHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.TicketHandler;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.constant.DatetimeField;

@Service
public class AssignmentService implements IAssignmentService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private TicketHandler ticketHandler;
	@Autowired
	private BalanceHandler balanceHandler;
	@Autowired
	private PlatformAmountHandler platformAmountHandler;
	@Autowired
	private AssignRecordHandler assignRecordHandler;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private ProjectTransferHandler projectTransferHandler;
	@Autowired
	private ProjectProcessHandler projectProcessHandler;
	@Autowired
	private AssignSuccessHandler assignSuccessHandler;
	@Autowired
	private AssignFailedHandler assignFailedHandler;
	@Autowired
	CustomerAccountDao customerAccountDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;

	@Override
	public boolean isAssignable(ProjectBaseInfo projectInfo,
			ProjectInvestmentRecord investmentRecord) {

		// <1>.非正常状态不允许转让
		if (!ProjectConstant.PROJECT_EXECUTE_SNAPSHOT_STATUS_NORMAL
				.equals(projectInfo.getPes().getStatus())) {
			return false;
		}
		// <2>.距下一个还款日期 N 天内的不允许转让
		if (repaymentPlanHandler.checkPlanEndDate(investmentRecord.getId())) {
			return false;
		}
		// <3>.事先约定不能转让的项目，债权不允许转让
		Long transferCode = projectInfo.getTransferCode();
		if (transferCode == ProjectConstant.PROJECT_TRANSFER_CODE_WITHOUT_PERMISSION) {
			return false;
		}
		// <4>.债权持有时间少于约定时间的，不允许转让
		if (DatetimeUtil.getDate(investmentRecord.getOpDt(), DatetimeField.DAY,
				transferCode.intValue()).getTime() > System.currentTimeMillis()) {
			return false;
		}
		return true;
	}

	@Override
	public void applyAssign(ProjectBaseInfo projectInfo,
			ProjectInvestmentRecord investmentRecord) {
		// <1>.债权是否可转让 ? 【下一步】：【返回】
		if (!isAssignable(projectInfo, investmentRecord)) {
			return;
		}
		// <2>.生成债权转让合同
		projectTransferHandler.addTransferInfo(projectInfo, investmentRecord);
	}

	@Override
	@Transactional(readOnly = false)
	public void beforeAssign(ProjectTransferInfo projectTransferInfo, String opTerm,
			String requestNo, Long accountId, String ticketIds, Double amount,
			Double ticketAmount, Double platformAmount, String rateTicketIds, Date beginInterestDate) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】beforeAssign start...");
		// ==================================参数校验======================================
		this.beforeAssignCheck(projectTransferInfo, opTerm, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds);
		// ==================================数据入库======================================
		// <1>.生成转让部分的投资记录
		ProjectInvestmentRecord recordAssignment = assignRecordHandler
				.addRecord_assignment(projectTransferInfo, opTerm, requestNo,
						accountId, ticketIds, amount, ticketAmount,
						platformAmount, rateTicketIds, beginInterestDate);
		// <2>.生成转让剩余部分的投资记录
		// 剩余债权
		// 项目可投金额 = 借款金额 - 已融资金额
		Double investAmount = projectTransferInfo.getProjectExecuteSnapshot()
				.getFinanceMoney()
				- projectTransferInfo.getProjectExecuteSnapshot()
						.getEndFinanceMoney();
		Double remainderCreditor = LoanUtil.formatAmount(investAmount - amount);
		ProjectInvestmentRecord recordRemaining = assignRecordHandler
				.addRecord_remaining(projectTransferInfo, opTerm, requestNo,
						remainderCreditor, rateTicketIds, beginInterestDate);
		// <3>.更新原投资记录的状态为【已转让】
		assignRecordHandler.updateStatus(projectTransferInfo.getPir().getId(),
				ProjectConstant.PROJECT_INVESTMENT_STATUS_TRANSFER);
		// <4>.生成转让的两条投资记录对应的的还款计划，并将原标的的待还款计划状态更新为【已转让】
		repaymentPlanHandler.handler(projectTransferInfo, recordAssignment, recordRemaining);
		// <5>.更新投资券状态
		ticketHandler.updateStatus(accountId, ticketIds);
		// <6>.更新加息券信息
		rateTicketHandler.updateRateTicket(rateTicketIds, ProjectConstant.TICKET_DICT_USED, "投资债权使用", projectTransferInfo.getProjectId(), new Date());
		// <7>.更新平台垫付金额
		platformAmountHandler.updatePlatformAmount(accountId, platformAmount);
		// <8>.新增【平台垫付金额】使用记录
		platformAmountHandler.addPlatformAmountRecord(accountId,
				recordAssignment.getId(), platformAmount);
		// <9>.更新冻结金额
		Double goldBalance = balanceHandler.updateCongealValForInvest(accountId,recordAssignment.getActualAmount());
		// <10>.新增冻结金额变更记录(投资冻结)
		String projectName = projectTransferInfo.getProjectBaseInfo() != null ? projectTransferInfo
				.getProjectBaseInfo().getProjectName() : "";
		String changeReason = "投资债权：" + projectName + "【" + recordAssignment.getId() + "】投资(冻结金额：" + recordAssignment.getActualAmount() + "元)";		
		balanceHandler.addBalanceRecord(accountId, opTerm,
				projectTransferInfo.getProjectId(), changeReason,
				recordAssignment.getId(), -recordAssignment.getActualAmount(),
				goldBalance);
		// <11>.更新进度处理
		projectProcessHandler.handler(projectTransferInfo.getProjectId(),
				projectTransferInfo.getTransferProjectId(), amount,
				ticketAmount, platformAmount, recordAssignment.getToPlatformMoney());
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】beforeAssign end...");
		logger.info("beforeAssign total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}

	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> assign(Long accountId, Long projectId,
			Double money, Double ticketMoney, String ticketIds,
			Double platformAmount) {
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void afterAssign(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining, boolean isSuccess) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem) + "】afterAssign start...");
		if (isSuccess) {
			if (confirmAssignInvestmentRecord(projectTransferInfo, recordAssignment)) {
				logger.info("afterAssign is success");
				// <1>.债权转让成功处理
				assignSuccessHandler.handler(projectTransferInfo, recordAssignment, recordRemaining);
			} else {
				// <2>.债权转让失败处理
				assignFailedHandler.handler(projectTransferInfo, recordAssignment, recordRemaining);
			}
		} else {
			// <2>.债权转让失败处理
			assignFailedHandler.handler(projectTransferInfo, recordAssignment, recordRemaining);
		}
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime) + "】afterAssign end...");
		logger.info("afterAssign total time consuming：【" + (endDateTime.getTime() - startDateTiem.getTime()) / 1000 + "s】");
	}
	
	/**
	 * 债权转让投资确认(易宝3.7接口)
	 * @param projectTransferInfo
	 * @param recordAssignment
	 * @return
	 */
	private boolean confirmAssignInvestmentRecord(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment) {
		CompleteTranscationResp completeResp = assignSuccessHandler.confirmAssignInvestmentRecord(projectTransferInfo, recordAssignment);
		return "1".equals(completeResp.getCode());
	}
	
	/**
	 * 检查债权转让项目用户是否可投
	 * @param projectTransferInfo
	 * @param accountId
	 */
	public void checkVoteForAssign(ProjectTransferInfo projectTransferInfo, Long accountId){
		// <0>.用户不能自己投自己
		ProjectBaseInfo projectInfo=projectTransferInfo.getProjectBaseInfo();
		String agentId = projectInfo.getAgentUser() != null ? projectInfo.getAgentUser().longValue() + "" : "";
		String customerId = projectInfo.getBorrowersUser() != null ? projectInfo.getBorrowersUser().longValue() + "" : "";
		String opCustomerId = accountId.longValue() + "";
		String transferor = projectTransferInfo.getTransferor().longValue() + "";
		if ((agentId != null) && (!agentId.equals(""))
				&& (!agentId.equals("0"))) {
			if (agentId.equals(opCustomerId)) {
				throw new ServiceException("用户不能投资自己的借贷项目");
			}
		}
		if (customerId.equals(opCustomerId)) {
			throw new ServiceException("用户不能投资自己的借贷项目");
		}
		if(transferor.equals(opCustomerId)){
			throw new ServiceException("用户不能投资自己的借贷项目");
		}
	}
	
	/**
	 * 参数检查
	 * @param projectTransferInfo
	 * @param requestNo
	 * @param accountId
	 * @param ticketIds
	 * @param amount
	 * @param ticketAmount
	 * @param platformAmount
	 */
	public void beforeAssignCheck(ProjectTransferInfo projectTransferInfo,String opTerm,
			String requestNo, Long accountId, String ticketIds, Double amount,
			Double ticketAmount, Double platformAmount, String rateTicketIds){
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】beforeAssignCheck start...");
		//<0>.检查用户是否可投
		this.checkVoteForAssign(projectTransferInfo, accountId);
		//<1>.项目校验: if (不可转让) {抛出异常，终止操作}
		if (!isAssignable(projectTransferInfo.getProjectBaseInfo(),
				projectTransferInfo.getPir())) {
			throw new ServiceException("项目【"
					+ projectTransferInfo.getProjectBaseInfo().getProjectId()
					+ "】不可转让");
		}
		//<2>.投资券校验
		ticketHandler.check(projectTransferInfo.getProjectBaseInfo().getIsNewUser(),accountId, ticketIds, amount, ticketAmount);
		//<3>.加息券校验
		rateTicketHandler.check(projectTransferInfo.getProjectBaseInfo().getCanUseRateTicket(), accountId, rateTicketIds);
		//<4>.平台垫付金额校验
		platformAmountHandler.check(accountId, platformAmount);
		//<5>.余额校验
		balanceHandler.check(accountId, amount, ticketAmount, platformAmount, ProjectUtil.getDownServiceCharge(amount));
		//<6>.投资金额校验
		projectProcessHandler.check(projectTransferInfo.getProjectBaseInfo(), projectTransferInfo.getTransferProjectId(), amount);
		//<7>.可投资项目状态校验
		projectProcessHandler.checkProjectStatus(projectTransferInfo.getProjectBaseInfo().getProjectId(), projectTransferInfo.getTransferProjectId(), ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT);
		//<8>.项目可操作终端校验
		projectProcessHandler.checkProjectTerm(opTerm, String.valueOf(projectTransferInfo.getProjectId()));
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】beforeAssignCheck end...");
		logger.info("beforeAssignCheck total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}
}
