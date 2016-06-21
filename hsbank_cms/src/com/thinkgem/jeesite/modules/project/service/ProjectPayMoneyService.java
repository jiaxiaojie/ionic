/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationReq;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;
import com.thinkgem.jeesite.modules.credit.service.CreditBaseInfoService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.entity.P2pFdAccountFlow;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectWillLoan;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;
import com.thinkgem.jeesite.modules.log.dao.P2pFdAccountFlowDao;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentPlanDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectWillLoanDao;

/**
 * @author yangtao
 *
 */
@Service
public class ProjectPayMoneyService {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	P2pFdAccountFlowDao p2pFdAccountFlowDao;
	@Autowired
	LogThirdPartyDao logThirdPartyDao;
	@Autowired
	CustomerAccountDao customerAccountDao;
	@Autowired
	ProjectWillLoanDao projectWillLoanDao;
	@Autowired
	private CustomerInvestmentTicketDao investmentTicketDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private ProjectRepaymentPlanDao projectRepaymentPlanDao;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private CreditBaseInfoService creditBaseInfoService;
	@Autowired
	private DirectReqUtils directReqUtils;

	/**
	 * 付款确认
	 * 
	 * @param projectId
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean confirm(String projectId) {
		Date startDateTiem = new Date();
		String dateTime=(new Date()).getTime()+"";
		logger.info("【" + DateUtils.formatDateTime(startDateTiem)
				+ "】 confirm start..."+dateTime);
		// 1)校验项目状态信息
		ProjectBaseInfo pbi = projectBaseInfoDao.get(projectId);
		if (!pbi.getProjectStatus().equals(
				ProjectConstant.PROJECT_STATUS_INVESTMENT_FINISHED)) {
			throw new ServiceException("项目状态不等于" + ProjectConstant.PROJECT_STATUS_INVESTMENT_FINISHED);
		}
		// 获得项目对应的借款人
		Long agentId = pbi.getAgentUser();
		Long customerId = pbi.getBorrowersUser();
		if ((agentId != null) && (!agentId.equals(""))
				&& (agentId.longValue() != 0)) {
			customerId = agentId;
		}
		String projectType = pbi.getProjectTypeCode().longValue() + "";
		Long applyId = pbi.getApplySrcId();
		CustomerAccount ca = customerAccountDao.get(new Long(customerId));
		String borrowUserNo = ca.getPlatformUserNo();
		ProjectExecuteSnapshot pes = projectExecuteSnapshotDao
				.getTransferSnapshot(new Long(projectId), 0L);
		// 2)根据项目编号获得正常状态的投资记录列表
		List<ProjectInvestmentRecord> investmentList = projectInvestmentRecordDao
				.findListByProjectIdAndTransferProjectId(projectId, 0L, "0");
		if ((investmentList == null) || (investmentList.size() == 0)) {
			throw new ServiceException("投资记录列表不存在");
		}
		logger.info("---------" + dateTime + ":investmentList size:" + investmentList.size());

		// 3)循环完成投资确认
		Date tempDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(tempDateTime)
				+ ":start confirmInvestmentRecord. ");
		
		Double realAmountSum = 0d;
		for (ProjectInvestmentRecord item : investmentList) {
			realAmountSum += item.getAmount();
			String thirdPartyNo = item.getThirdPartyOrder();
			confirmInvestmentRecord(projectId, pbi, borrowUserNo, item,
					thirdPartyNo, "CONFIRM");
			// 放款更新账户余额、冻结金额、待收收益、理财资产、投资次数
			logger.info("---------" + dateTime
					+ ":updateLoanInformationSelective start 【amount:"
					+ item.getAmount() + "】,  【actualAmount:"
					+ item.getActualAmount() + "】,【willProfit:"
					+ item.getWillProfit() + "】");
			customerBalanceDao.updateLoanInformationSelective(
					item.getInvestmentUserId(), item.getActualAmount(),
					item.getActualAmount(), item.getWillProfit(),item.getWillReceivePrincipal(),
					item.getAmount());
			logger.info("---------" + dateTime
					+ ":updateLoanInformationSelective end...");
			// 新增余额变更记录(投资确认)
			String changeReason = "投资项目：" + pbi.getProjectName() + "【" + item.getId() + "】投资(确认金额："+ item.getActualAmount() + "元)";
			this.addConfirmBalanceRecord(-item.getActualAmount(),
					item.getInvestmentUserId(), changeReason, projectId);
			//营销活动投标完成
			marketFacadeTenderOver(item);
		}
		Date endDateTime = new Date();
		logger.info("---------" + dateTime
				+ ":all confirmInvestmentRecord end total time consuming: "
				+ (endDateTime.getTime() - tempDateTime.getTime()) / 1000);

		// 4)更新借款人余额、添加余额变更流水
		logger.info("---------" + dateTime
				+ ":update borrower:" + customerId + " balance start.");
		CustomerBalance customerBalance = customerBalanceDao.get(customerId
				+ "");
		double changeBalance = pes.getEndFinanceMoney()
				- pes.getSumServiceCharge();
		customerBalance.setGoldBalance(customerBalance.getGoldBalance()
				+ changeBalance);
		customerBalance.setSumLoan(customerBalance.getSumLoan()
				+ pes.getFinanceMoney());
		customerBalance.setNetAssets(customerBalance.getNetAssets()
				+ changeBalance);
		customerBalance.setLastChangeDt(new Date());
		customerBalance.setWillLoan(customerBalance.getWillLoan()
				+ projectInvestmentRecordDao.getSumWillProfit(projectId, "0",
						ProjectConstant.PROJECT_INVESTMENT_STATUS_NORMAL));
		customerBalance.setRepayment30dWill(customerBalance
				.getRepayment30dWill()
				+ projectRepaymentPlanDao.getRepayment30Will(projectId));
		customerBalanceDao.update(customerBalance);
		CustomerBalance cb = customerBalanceDao.get(customerId + "");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(customerId);
		customerBalanceHis.setChangeVal(changeBalance);
		customerBalanceHis.setBalance(cb.getGoldBalance());
		customerBalanceHis
				.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CONFIRM);
		customerBalanceHis.setChangeReason("融资收款：" + pbi.getProjectName());
		customerBalanceHis.setRelProject(projectId);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(ProjectConstant.DICT_DEFAULT_VALUE);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + dateTime
				+ ":update borrower:" + customerId + " balance end.");
		// 5)根据项目编号获得平台垫付资金数量，平台支付
		double willPayMoney = pes.getSumPlatformAmount()
				+ pes.getSumTicketMoney();
		if (willPayMoney < 0) {
			throw new ServiceException("平台垫付资金小于0 ");
		}
		logger.info("---------" + dateTime + ":platform willPayMoney:" + willPayMoney);
		if (willPayMoney > 0) {
			// 平台垫付资金转拨
			platformPayMoney(projectId, pbi, customerId, borrowUserNo,
					willPayMoney);
		} else {
			logger.info("--------- " + dateTime + "project " + projectId + " willPayMoney==0");
		}
		// 6)如果是个人信用贷，则更新对应的资金申请状态为已放款
		if (projectType.equals("2")) {
			if ((applyId == null) || (applyId.longValue() == 0)) {

			} else {
				logger.info("---------" + dateTime
						+ ":update willLoan status start.");
				ProjectWillLoan projectWillLoan = projectWillLoanDao
						.get(applyId.longValue() + "");
				projectWillLoan.setStatus("3");
				projectWillLoanDao.update(projectWillLoan);
				logger.info("---------" + dateTime
						+ ":update willLoan status end.");
			}
		}

		// 7)更新项目的状态为还款中
		logger.info("---------" + dateTime
				+ ":update project status:repaymenting start.");
		projectBaseInfoDao.updateProjectStatus(new Long(projectId),
				ProjectConstant.PROJECT_STATUS_REPAYMENTING);
		logger.info("---------" + dateTime
				+ ":update project status:repaymenting end.");
		
		
		
		
		// 8)更新募集金额
		logger.info("---------" + dateTime + ":update credit Raise money start.");
		creditBaseInfoService.updateRaiseMoney(pbi.getCreditId(),pbi.getFinanceMoney(),realAmountSum);
		logger.info("---------" + dateTime + ":update credit Raise money end.");
		
		
		
		endDateTime = new Date();
		logger.info("【" + dateTime
				+ "】 confirm end...");
		logger.info("confirm total time consuming：【"
				+ (endDateTime.getTime() - startDateTiem.getTime()) / 1000
				+ "s】");
		return true;
	}
	
	/**
	 * 营销活动投标完成
	 * @param projectInfo
	 * @param investmentRecord
	 */
	public void marketFacadeTenderOver(ProjectInvestmentRecord investmentRecord){
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, investmentRecord.getInvestmentUserId());
		para.put(MarketConstant.AMOUNT_PARA, investmentRecord.getAmount());
		para.put(MarketConstant.PROJECT_ID_PARA, investmentRecord.getProjectId());
		para.put(MarketConstant.RECORD_ID_PARA, investmentRecord.getId());
		para.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ": marketActivity TenderOver para：【" + para.toString() + "】 start.");
		marketFacadeService.investmentTenderOver(para);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ": marketActivity TenderOver para：【" + para.toString() + "】 end.");
	}

	/**
	 * 新增余额变更记录(投资确认)
	 * 
	 * @param actualAmount
	 * @param accountId
	 * @param projectName
	 * @param projectId
	 */
	@Transactional(readOnly=false)
	public void addConfirmBalanceRecord(Double actualAmount, Long accountId,
			String changeReason, String projectId) {
		Date startDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(startDateTime)
				+ ":start addConfirmBalanceRecord. ");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		CustomerBalance balance = customerBalanceDao.get(String
				.valueOf(accountId));
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(actualAmount);
		customerBalanceHis.setBalance(balance.getGoldBalance());
		customerBalanceHis
				.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CONFIRM);
		customerBalanceHis.setChangeReason(changeReason);
		customerBalanceHis.setRelProject(projectId);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(ProjectConstant.OP_TERM_DICT_PC);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(startDateTime)
				+ ":end addConfirmBalanceRecord. ");
	}

	/**
	 * @param projectId
	 * @param pbi
	 * @param customerId
	 * @param borrowUserNo
	 * @param willPayMoney
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	private void platformPayMoney(String projectId, ProjectBaseInfo pbi,
			Long customerId, String borrowUserNo, double willPayMoney) {
		Date startDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(startDateTime)
				+ " platformPayMoney start request yeepay.");
		Date endDate;
		String requestNo;
		DirectTranscationReq req = new DirectTranscationReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		requestNo = YeepayUtils.getSequenceNumber(
				YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
				borrowUserNo);
		req.setRequestNo(requestNo);
		req.setPlatformUserNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setUserType("MERCHANT");
		req.setBizType("TRANSFER");
		req.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX
				+ "directTransaction");
		MoneyDetail md = new MoneyDetail();
		md.setAmount(willPayMoney + "");
		md.setBizType("TRANSFER");
		md.setTargetPlatformUserNo(borrowUserNo);
		md.setTargetUserType("MEMBER");
		List<MoneyDetail> list = new ArrayList<MoneyDetail>();
		list.add(md);
		req.setDetail(list);
		String reqXml = req.toReq();
		String callBackContent = directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
						reqXml,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE);
		endDate = new Date();
		DirectTranscationResp resp = JaxbMapper.fromXml(callBackContent,
				DirectTranscationResp.class);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ " platformPayMoney received response.");
		// 记录第三方交互流水
		saveThirdPartLog(requestNo,
				YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
				reqXml, new Date(), callBackContent, resp.getCode(), endDate);

		// 添加平台账号流水
		saveP2pAccountFlow((0 - willPayMoney) + "", projectId, "0", "0",
				customerId, "项目：" + pbi.getProjectName() + " 放款,抵用额度和投资券额度为："
						+ willPayMoney, requestNo, callBackContent,
				resp.getCode());
		Date endDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(endDateTime)
				+ " platformPayMoney end. total time consuming:"
				+ (endDateTime.getTime() - startDateTime.getTime()) / 1000
				+ "s.");
	}

	/**
	 * 3.7 投资记录转账确认
	 * 
	 * @param projectId
	 * @param pbi
	 * @param borrowUserNo
	 * @param item
	 * @param thirdPartyNo
	 * @param mode
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void confirmInvestmentRecord(String projectId, ProjectBaseInfo pbi,
			String borrowUserNo, ProjectInvestmentRecord item,
			String thirdPartyNo, String mode) {
		Date startDateTime = new Date();
		logger.info("----------------"
				+ DateUtils.formatDateTime(startDateTime)
				+ "confirmInvestmentRecord borrowUserNO : " + borrowUserNo
				+ " start request yeepay");
		// 进行确认
		CompleteTranscationReq completeReq = new CompleteTranscationReq();
		completeReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		completeReq.setRequestNo(thirdPartyNo);
		completeReq.setMode(mode);
		completeReq.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX
				+ "completeTransaction");
		String completeReqXml = completeReq.toReq();
		String completeXml = directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ,
						completeReqXml,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_SERVICE);
		CompleteTranscationResp completeResp =JaxbMapper.fromXml(
				completeXml, CompleteTranscationResp.class);
		// 记录第三方交互流水
		saveThirdPartLog(
				thirdPartyNo,
				YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ,
				completeReqXml, startDateTime, completeXml,
				completeResp.getCode(), new Date());
		// 添加平台账号流水（平台收取的费用）
		double changeVal = 0;
		changeVal = item.getToPlatformMoney();
		saveP2pAccountFlow(changeVal + "", projectId, "0", item.getId(),
				item.getInvestmentUserId(), "项目：" + pbi.getProjectName()
						+ " 投资人：" + item.getInvestmentUserId().longValue()
						+ " 投资编号：" + item.getId() + " 平台收到：" + changeVal,
				thirdPartyNo, completeXml, completeResp.getCode());
		Date endDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(endDateTime)
				+ "confirmInvestmentRecord borrowUserNO : " + borrowUserNo
				+ " end total time consuming:"
				+ (endDateTime.getTime() - startDateTime.getTime()) / 1000
				+ "s.");
	}

	/**
	 * 保存第三方交互记录
	 * 
	 * @param requestNo
	 * @param service
	 * @param req_content
	 * @param req_dt
	 * @param resp_content
	 * @param resp_code
	 * @param resp_dt
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveThirdPartLog(String requestNo, String service,
			String reqContent, Date reqDt, String respContent, String respCode,
			Date respDt) {
		LogThirdParty ltp = new LogThirdParty();
		ltp.setRequestNo(requestNo);
		ltp.setService(service);
		ltp.setReqContent(reqContent);
		ltp.setReqDt(reqDt);
		ltp.setRespContent(respContent);
		ltp.setRespCode(respCode);
		ltp.setRespDt(respDt);
		logThirdPartyDao.insert(ltp);
	}

	/**
	 * 保存平台账号变动流水
	 * 
	 * @param requestNo
	 * @param service
	 * @param req_content
	 * @param req_dt
	 * @param resp_content
	 * @param resp_code
	 * @param resp_dt
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveP2pAccountFlow(String changeVal, String projectId,
			String transferProjectId, String investmentId, Long accountId,
			String detail, String thirdPartySeq, String thirdPartyResult,
			String result) {
		P2pFdAccountFlow pf = new P2pFdAccountFlow();
		pf.setChangeVal(changeVal);
		pf.setProjectId(new Long(projectId));
		pf.setTransferProjectId(new Long(transferProjectId));
		pf.setInvestmentId(new Long(investmentId));
		pf.setAccountId(accountId);
		pf.setDetail(detail);
		pf.setCreateDt(new Date());
		pf.setThirdPartySeq(thirdPartySeq);
		pf.setThirdPartyResult(thirdPartyResult);
		pf.setResult(result);
		p2pFdAccountFlowDao.insert(pf);
	}

	/**
	 * 流标处理 <1>.流标回滚数据 <2>.调用3.7接口进行取消转账 <3>.设置项目状态为流标
	 * 
	 * @param projectId
	 * @return
	 */
	public boolean failedProject(String projectId) {
		// 根据项目编号查询项目基本信息
		ProjectBaseInfo pInfo = projectBaseInfoDao.get(projectId);
		// 获得项目对应的借款人
		Long agentId = pInfo.getAgentUser();
		Long customerId = pInfo.getBorrowersUser();
		if ((agentId != null) && (!agentId.equals(""))
				&& (!agentId.equals("0"))) {
			customerId = agentId;
		}
		CustomerAccount cAccount = customerAccountDao.get(customerId);
		String borrowUserNo = cAccount.getPlatformUserNo();
		// 根据项目编号查询对应的正常状态的投资记录
		List<ProjectInvestmentRecord> investmentList = projectInvestmentRecordDao
				.findListByProjectIdAndTransferProjectId(projectId, 0L, "0");
		for (ProjectInvestmentRecord investment : investmentList) {
			// 流标回滚数据
			this.rollBackFlowData(investment);
			String thirdPartyNo = investment.getThirdPartyOrder();
			// 调用3.7接口进行取消转账
			this.confirmInvestmentRecord(projectId, pInfo, borrowUserNo,
					investment, thirdPartyNo, "CANCEL");
		}
		// 更新项目状态为流标
		projectBaseInfoDao.updateProjectStatus(new Long(projectId),
				ProjectConstant.PROJECT_STATUS_FAILED);
		return true;
	}

	/**
	 * 流标回滚数据 <1>.更新投资记录状态（已撤销） <2>.回滚优惠劵 <3>.回滚账户余额及可抵扣金额
	 * 
	 * @param investment
	 */
	public void rollBackFlowData(ProjectInvestmentRecord investment) {
		// 更新投资记录状态（已撤销）
		projectInvestmentRecordDao.updateStatus(
				String.valueOf(investment.getId()),
				ProjectConstant.PROJECT_INVESTMENT_STATUS_REPEAL);
		// 回滚优惠劵
		String ticketIds = investment.getTicketIds();
		if (ticketIds != null && !"".equals(ticketIds)) {
			investmentTicketDao.batchUpdateStatus(
					StringUtils.surroundSymbol(ticketIds, ",", "'"),
					ProjectConstant.TICKET_DICT_NORMAL);
		}
		// 投资金额
		Double amount = investment.getAmount();
		// 抵用优惠券金额
		Double ticketAmount = investment.getTicketAmount();
		// 平台垫付金额
		Double platformAmount = investment.getPlatformAmount();
		// 实际支付金额
		Double actualAmount = amount - ticketAmount - platformAmount;
		Long accountId = investment.getInvestmentUserId();
		// 回滚账户余额及可抵扣金额
		customerBalanceDao.updateGoldBalanceAndPlatformAmount(accountId,
				actualAmount, platformAmount);
	}

}
