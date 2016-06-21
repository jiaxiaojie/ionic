package com.thinkgem.jeesite.modules.project.service.assignment.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationReq;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.entity.P2pFdAccountFlow;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;
import com.thinkgem.jeesite.modules.log.dao.P2pFdAccountFlowDao;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransferInfoDao;

/**
 * 债权转让成功处理
 * <p/>
 * @author zibo.li
 * CreateDate 2015-07-30
 */
@Component("assignSuccessHandler")
public class AssignSuccessHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao investmentRecordDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	@Autowired
	private ProjectTransferInfoDao projectTransferInfoDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	P2pFdAccountFlowDao p2pFdAccountFlowDao;
	@Autowired
	LogThirdPartyDao logThirdPartyDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private DirectReqUtils directReqUtils;
	
	/**
	 * 处理器
	 * <1>.更新转让部分的投资记录为【成功】状态
	 * <2>.更新转让剩余部分的投资记录为【成功】状态
	 * <3>.更新转让人的余额
	 * <4>.债权是否结束 ? 债权转让结束处理流程 : 不做任何操作
	 * @return
	 */
	public boolean handler(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":AssignSuccessHandler start.");
		//<1>.更新转让部分的投资记录为【成功】状态
		investmentRecordDao.updateStatus(recordAssignment.getId(), ProjectConstant.PROJECT_INVESTMENT_STATUS_NORMAL);
		//<2>.更新转让剩余部分的投资记录为【成功】状态
		investmentRecordDao.updateStatus(recordRemaining.getId(), ProjectConstant.PROJECT_INVESTMENT_STATUS_NORMAL);
		//<3>.债权投资确认(受转让人)，更新账户余额、冻结金额、代收收益、理财资产、投资次数、受转让次数
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "updateAssignInformationSelective status start.");
		customerBalanceDao.updateAssignInformationSelective(recordAssignment.getInvestmentUserId(), recordAssignment.getActualAmount(), recordAssignment.getActualAmount(), recordAssignment.getWillProfit(), recordAssignment.getAmount());
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "updateAssignInformationSelective status end.");
		//<4>.新增余额变更记录(受转让人)
		String projectName = projectTransferInfo.getProjectBaseInfo() != null ?  projectTransferInfo.getProjectBaseInfo().getProjectName() : "";
		String changeReason = "投资债权：" + projectName + "【" + recordAssignment.getId() + "】投资(确认金额：" + recordAssignment.getActualAmount() + "元)";
		this.addConfirmBalanceRecord(-recordAssignment.getActualAmount(), recordAssignment.getOpTerm(), recordAssignment.getInvestmentUserId(), changeReason, String.valueOf(projectTransferInfo.getProjectId()));
		//<5>.债权投资确认(转让人)，更新账户余额、代收收益、收款总额、收款本息、理财资产
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "updateBalanceForTransferor status start.");
		customerBalanceDao.updateBalanceForTransferor(projectTransferInfo.getTransferor(), recordAssignment.getToBorrowersUserMoney(), recordAssignment.getWillProfit(), recordAssignment.getToBorrowersUserMoney(), recordAssignment.getToBorrowersUserMoney(), recordAssignment.getAmount());
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "updateBalanceForTransferor status end.");
		//<6>.新增余额变更记录(转让人)
		String changeReasonForTransferor = "债权收款：" + projectName + "【" + recordAssignment.getId() + "】投资(确认金额：" + recordAssignment.getToBorrowersUserMoney() + "元)";
		this.addConfirmBalanceRecord(recordAssignment.getToBorrowersUserMoney(), recordAssignment.getOpTerm(), projectTransferInfo.getTransferor(), changeReasonForTransferor, String.valueOf(projectTransferInfo.getProjectId()));
		//<7> 平台垫付金额转账
		platformAmountHandler(projectTransferInfo, recordAssignment);
		//<8>.债权是否结束 ? 债权转让结束处理流程 : 不做任何操作
		finishedHandler(projectTransferInfo);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":AssignSuccessHandler end.");
		//营销活动债权投资
		marketFacadeCreditAssgnment(projectTransferInfo,recordAssignment);
		return false;
	}
	
	/**
	 * 营销活动债权投资
	 * @param projectInfo
	 * @param investmentRecord
	 */
	public void marketFacadeCreditAssgnment(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment){
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, recordAssignment.getInvestmentUserId());
		para.put(MarketConstant.AMOUNT_PARA, recordAssignment.getAmount());
		para.put(MarketConstant.PROJECT_ID_PARA, projectTransferInfo.getProjectId());
		para.put(MarketConstant.RECORD_ID_PARA, recordAssignment.getId());
		para.put(MarketConstant.CHANNEL_PARA, recordAssignment.getOpTerm());
		para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_CREDIT_ASSIGNMENT);
		marketFacadeService.investmentCreditAssgnment(para);
	}
	
	/**
	 * 债券转让投资确认
	 * @param projectTransferInfo
	 * @param recordAssignment
	 * @return
	 */
	public CompleteTranscationResp confirmAssignInvestmentRecord(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment){
		logger.info("---------" + DateUtils.formatDate(new Date())
				+ ":confirmAssignInvestmentRecord start.");
		// 获得项目对应的借款人
		Long agentId = projectTransferInfo.getProjectBaseInfo().getAgentUser();
		Long customerId = projectTransferInfo.getProjectBaseInfo().getBorrowersUser();
		if ((agentId != null) && (!agentId.equals(""))
				&& (agentId.longValue()!=0)) {
			customerId = agentId;
		}
		CustomerAccount ca = customerAccountDao.get(new Long(customerId));
		String borrowUserNo = ca.getPlatformUserNo();
		String thirdPartyNo = recordAssignment.getThirdPartyOrder();
		CompleteTranscationResp completeResp = this.confirmInvestmentRecord(String.valueOf(projectTransferInfo.getProjectId()), projectTransferInfo.getProjectBaseInfo(), borrowUserNo, recordAssignment, thirdPartyNo, "CONFIRM");
		logger.info("---------" + DateUtils.formatDate(new Date())
				+ ":confirmAssignInvestmentRecord end.");
		return completeResp;
	}
	
	/**
	 * 投资记录转账确认
	 * @param projectId
	 * @param pbi
	 * @param borrowUserNo
	 * @param item
	 * @param thirdPartyNo
	 * @param mode
	 * @return
	 */
	public CompleteTranscationResp confirmInvestmentRecord(String projectId, ProjectBaseInfo pbi,
			String borrowUserNo, ProjectInvestmentRecord item,
			String thirdPartyNo,String mode) {
		Date startDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(startDateTime) + "confirmInvestmentRecord borrowUserNO : " + borrowUserNo +" start request yeepay");
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
		CompleteTranscationResp completeResp = JaxbMapper.fromXml(
				completeXml, CompleteTranscationResp.class);
		// 记录第三方交互流水
		saveThirdPartLog(
				thirdPartyNo,
				YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ,
				completeReqXml, startDateTime, completeXml,
				completeResp.getCode(), new Date());
		// 添加平台账号流水（平台收取的费用）
		double changeVal=0;
		changeVal=item.getToPlatformMoney();
		saveP2pAccountFlow(changeVal+"", projectId, "0",
				item.getId(), item.getInvestmentUserId(), "项目："+pbi.getProjectName()+" 投资人："+item.getInvestmentUserId().longValue()+" 投资编号："+item.getId()+" 平台收到："+changeVal, thirdPartyNo,
				completeXml, completeResp.getCode());
		Date endDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(endDateTime) + "confirmInvestmentRecord borrowUserNO : " + borrowUserNo +" end total time consuming:" + (endDateTime.getTime() - startDateTime.getTime())/1000 + "s.");
		return completeResp;
	}
	
	/**
	 * (平台垫付金额+抵用券金额)转账给债权转出人
	 * @param projectTransferInfo
	 * @param recordAssignment
	 */
	private void platformAmountHandler(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment) {
		Date startDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(startDateTime) + "platformAmountHandler start request yeepay");
		double willPayMoney = recordAssignment.getPlatformAmount() + recordAssignment.getTicketAmount();
		if (willPayMoney <= 0.00) {
			return;
		}
		String borrowUserAccount = projectTransferInfo.getPir().getInvestmentUserId().longValue()+"";
		String borrowUserNo = customerAccountDao.get(borrowUserAccount).getPlatformUserNo();
		String projectId = projectTransferInfo.getProjectId().longValue()+"";
		
		Date startDate;
		Date endDate;
		String requestNo;
		DirectTranscationReq req = new DirectTranscationReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		requestNo = YeepayUtils
				.getSequenceNumber(
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
		startDate = new Date();
		String callBackContent = directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
						reqXml,
						YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE);
		endDate = new Date();
		DirectTranscationResp resp = JaxbMapper.fromXml(callBackContent,
				DirectTranscationResp.class);
		if(!resp.getCode().equals("1")){
			logger.error("投资债券 直接转账 to "+borrowUserAccount +" 金额："+willPayMoney +" 失败");
		}else{
			//更新账户余额  += 平台垫付金额 + 抵用券金额
			customerBalanceDao.updateGoldBalance(projectTransferInfo.getPir().getInvestmentUserId().longValue(), -willPayMoney);
			String changeReason = "债权转账：" + projectTransferInfo.getProjectBaseInfo().getProjectName() + "【" + recordAssignment.getId() + "】转账(确认金额：" + willPayMoney + "元)";
			this.addConfirmBalanceRecord(willPayMoney, projectTransferInfo.getPir().getOpTerm(), projectTransferInfo.getPir().getInvestmentUserId().longValue(), changeReason, projectId);
		}
		// 记录第三方交互流水
		saveThirdPartLog(
				requestNo,
				YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
				reqXml, startDate, callBackContent, resp.getCode(), endDate);
		
		// 添加平台账号流水
		saveP2pAccountFlow((0-willPayMoney)+"", projectId, "0",
				"0", new Long(borrowUserAccount), "项目："+ projectTransferInfo.getProjectBaseInfo().getProjectName()+" 放款,抵用额度和投资券额度为："+willPayMoney, requestNo,
				callBackContent, resp.getCode());
		Date endDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(endDateTime) + "platformAmountHandler borrowUserNO : " + borrowUserNo +" end total time consuming:" + (endDateTime.getTime() - startDateTime.getTime())/1000 + "s.");
	}
	
	/**
	 * 债权是否结束 ? 债权转让结束处理流程 : 不做任何操作
	 * 【是否到了转让截止时间】或者【是否已转让全部金额】
	 */
	public boolean isFinished(ProjectTransferInfo projectTransferInfo) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":isFinished start.");
		if (System.currentTimeMillis() > projectTransferInfo.getDiscountDate().getTime()) {
			//转让截止日期已过
			return true;
		}
		//<1>.该项目的投资记录有没有【冻结中】状态 ? 没有结束 : 下一步判断
		List<ProjectInvestmentRecord> list = projectInvestmentRecordDao.findListByProjectId(String.valueOf(projectTransferInfo.getProjectId()), ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE);
		logger.info("InvestmentRecord list size : "+ list != null ? list.size() : 0 );
		if (list.size() <= 0 ) {
			//<2>.该项转让目的已募集金额 >= 募集金额 ? 满标 : 没有满标
			ProjectExecuteSnapshot transSnapshot = projectTransferInfo.getProjectExecuteSnapshot(); 
			logger.info("transSnapshot 【endFinanceMoney:" + transSnapshot.getEndFinanceMoney() + "】,【financeMoney:" + transSnapshot.getFinanceMoney() + "】" );
			if (transSnapshot.getEndFinanceMoney().compareTo(transSnapshot.getFinanceMoney()) >= 0) {
				return true;
			}
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":isFinished end.");
		return false;
	}
	
	/**
	 * 债权转让结束处理流程
	 * <1>.更新项目状态为【投标结束】
	 */
	public void finishedHandler(ProjectTransferInfo projectTransferInfo) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":AssignfinishedHandler start.");
		if (isFinished(projectTransferInfo)) {
			//更新转让结束时间
			String transferId=projectTransferInfo.getTransferProjectId().longValue()+"";
			logger.info("update projectTransferInfo transferId" + transferId);
			projectTransferInfo =projectTransferInfoDao.get(transferId);
			projectTransferInfo.setCloseDate(new Date());
			projectTransferInfo.setId(transferId);
			projectTransferInfo.setStatus(ProjectConstant.PROJECT_TRANSFER_STATUS_ALLEND);
			projectTransferInfoDao.update(projectTransferInfo);
			//更新项目执行快照过程状态
			projectExecuteSnapshotDao.updateStatus(projectTransferInfo.getProjectId(), projectTransferInfo.getTransferProjectId(), ProjectConstant.PROJECT_TRANSFER_STATUS_ALLEND);
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":AssignfinishedHandler end.");
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
	 * 新增余额变更记录(购买债权投资确认)
	 * @param actualAmount
	 * @param accountId
	 * @param projectName
	 * @param projectId
	 */
	public void addConfirmBalanceRecord(Double actualAmount, String opTerm, Long accountId, String changeReason, String projectId){
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "addConfirmBalanceRecord status start.");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(actualAmount);
		customerBalanceHis.setBalance(balance.getGoldBalance());
		customerBalanceHis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CONFIRM);
		customerBalanceHis.setChangeReason(changeReason);
		customerBalanceHis.setRelProject(projectId);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(new Date()) + "addConfirmBalanceRecord status end.");
	}
}
