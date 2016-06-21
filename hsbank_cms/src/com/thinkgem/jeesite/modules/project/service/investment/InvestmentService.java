package com.thinkgem.jeesite.modules.project.service.investment;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.investment.handler.InvestFailedHandler;
import com.thinkgem.jeesite.modules.project.service.investment.handler.InvestRecordHandler;
import com.thinkgem.jeesite.modules.project.service.investment.handler.InvestSuccessHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.BalanceHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.PlatformAmountHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.ProjectProcessHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.RepaymentPlanHandler;
import com.thinkgem.jeesite.modules.project.service.util.handler.TicketHandler;
import com.hsbank.util.type.NumberUtil;

@Service
public class InvestmentService implements IInvestmentService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private TicketHandler ticketHandler;
	@Autowired
	private BalanceHandler balanceHandler;
	@Autowired
	private PlatformAmountHandler platformAmountHandler;
	@Autowired
	private InvestRecordHandler investmentRecordHandler;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private ProjectProcessHandler projectProcessHandler;
	@Autowired
	private InvestSuccessHandler investSuccessHandler;
	@Autowired
	private InvestFailedHandler investFailedHandler;
	@Autowired
	private RateTicketHandler rateTicketHandler;

	@Override
	@Transactional(readOnly = false)
	public void beforeInvest(ProjectBaseInfo projectInfo, String opTerm, String requestNo,
			Long accountId, String ticketIds, Double amount, Double ticketAmount, 
			Double platformAmount, String rateTicketIds, Date beginInterestDate) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】beforeInvest start...");
		//项目流水号
		String projectId = projectInfo.getProjectId();
		//==================================参数校验======================================
		this.beforeCheck(projectInfo, opTerm, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds);
		//==================================数据入库======================================
		//<1>.生成投资记录
		ProjectInvestmentRecord investmentRecord = investmentRecordHandler.addRecord(projectInfo, opTerm, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds, beginInterestDate);
		//<2>.生成投资记录对应的还款计划
		repaymentPlanHandler.generateForInvestment(projectInfo, investmentRecord);
		//<3>.更新投资券状态
		ticketHandler.updateStatus(accountId, ticketIds);
		//<4>.更新加息券信息
		rateTicketHandler.updateRateTicket(rateTicketIds, ProjectConstant.TICKET_DICT_USED, "投资项目使用", NumberUtil.toLong(projectId, 0L), new Date());
		//<5>.更新平台垫付金额
		platformAmountHandler.updatePlatformAmount(accountId, platformAmount);
		//<6>.新增【平台垫付金额】使用记录
		platformAmountHandler.addPlatformAmountRecord(accountId, investmentRecord.getId(), platformAmount);
		//<7>.更新冻结金额
		Double goldBalance = balanceHandler.updateCongealValForInvest(accountId, investmentRecord.getActualAmount());
		//<8>.新增冻结金额变更记录(投资冻结)
		String changeReason = "投资项目：" + projectInfo.getProjectName() + "【" + investmentRecord.getId() + "】投资(冻结金额：" + investmentRecord.getActualAmount() + "元)";
		balanceHandler.addBalanceRecord(accountId, opTerm, NumberUtil.toLong(projectId, 0L), changeReason, investmentRecord.getId(), -investmentRecord.getActualAmount(), goldBalance);
		//<9>.更新进度处理
		projectProcessHandler.handler(NumberUtil.toLong(projectId, 0L), 0L, amount, ticketAmount, platformAmount, investmentRecord.getToPlatformMoney());
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】beforeInvest end...");
		logger.info("beforeInvest total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}
	
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> invest(Long accountId, Long projectId, Double money, Double ticketMoney, String ticketIds, Double platformAmount) {
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void afterInvest(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord, boolean result) {
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】afterInvest start...");
		//判断，易宝返回是否成功
		if(result){
			//<1>.投资成功处理
			investSuccessHandler.handler(projectInfo, investmentRecord);
		}else{
			//<2>.投资失败处理
			investFailedHandler.handler(projectInfo, investmentRecord);
		}
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】afterInvest end...");
		logger.info("afterInvest total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}
	
	/**
	 * 检查用户是否可投借贷项目
	 * @param projectInfo
	 * @param accountId
	 */
	public void checkVoteForInvest(ProjectBaseInfo projectInfo, Long accountId){
		//<0>.用户不能自己投自己
		String agentId = projectInfo.getAgentUser() != null ? projectInfo.getAgentUser().longValue() + "" : "";
		String customerId = projectInfo.getBorrowersUser() != null ? projectInfo.getBorrowersUser().longValue() + "" : "";
		String opCustomerId=accountId.longValue()+"";
		if ((agentId != null) && (!agentId.equals(""))
				&& (!agentId.equals("0"))) {
			if(agentId.equals(opCustomerId)){
				throw new ServiceException("用户不能投资自己的借贷项目");
			}
		}
		if(customerId.equals(opCustomerId)){
			throw new ServiceException("用户不能投资自己的借贷项目");
		}
	}
	
	/**
	 * 参数检查
	 * @param projectInfo
	 * @param requestNo
	 * @param accountId
	 * @param ticketIds
	 * @param amount
	 * @param ticketAmount
	 * @param platformAmount
	 */
	public void beforeCheck(ProjectBaseInfo projectInfo, String opTerm, String requestNo, Long accountId, String ticketIds, Double amount, Double ticketAmount, Double platformAmount, String rateTicketIds){
		Date startDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】beforeCheck start...");
		//<0>.检查用户是否可投
		this.checkVoteForInvest(projectInfo, accountId);
		//<1>.投资券校验
		ticketHandler.check(projectInfo.getIsNewUser(), accountId, ticketIds, amount, ticketAmount);
		//<2>.加息券校验
		rateTicketHandler.check(projectInfo.getCanUseRateTicket(), accountId, rateTicketIds);
		//<3>.平台垫付金额校验
		platformAmountHandler.check(accountId, platformAmount);
		//<4>.可用余额校验
		balanceHandler.check(accountId, amount, ticketAmount, platformAmount, 0.00);
		//<5>.投资金额校验
		projectProcessHandler.check(projectInfo, 0L, amount);
		//<6>.可投资项目状态校验
		projectProcessHandler.checkProjectStatus(projectInfo.getProjectId(), 0L, ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT);
		//<7>.项目可操作终端校验
		projectProcessHandler.checkProjectTerm(opTerm, projectInfo.getProjectId());
		//<8>.新手项目，检查是否新手
		platformAmountHandler.checkIsNewCustomer(accountId, projectInfo);
		Date endDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTime)
				+ "】beforeCheck end...");
		logger.info("beforeCheck total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
	}
}
