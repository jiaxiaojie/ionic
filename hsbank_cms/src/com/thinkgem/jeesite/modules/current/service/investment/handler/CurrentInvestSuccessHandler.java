package com.thinkgem.jeesite.modules.current.service.investment.handler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationReq;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationResp;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.handler.CurrentAccountHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentBalanceHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentPrincipalHandler;
import com.thinkgem.jeesite.modules.current.handler.CurrentProjectHandler;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.entity.P2pFdAccountFlow;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;
import com.thinkgem.jeesite.modules.log.dao.P2pFdAccountFlowDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.NumberUtil;

/**
 * 活期产品投资成功处理
 * <p/>
 * @author lzb
 * @version 2015-12-10
 */
@Component
public class CurrentInvestSuccessHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentProjectHandler currentProjectHandler;
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Autowired
	private LogThirdPartyDao logThirdPartyDao;
	@Autowired
	private P2pFdAccountFlowDao p2pFdAccountFlowDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CurrentBalanceHandler currentBalanceHandler;
	@Autowired
	private CurrentPrincipalHandler currentPrincipalHandler;
	@Autowired
	private CurrentAccountHandler currentAccountHandler;
	@Autowired
	private DirectReqUtils directReqUtils;
	
	/**
	 * 投资成功处理
	 * @param cInfo
	 * @param cHis
	 */
	public void handerSuccess(CurrentProjectInfo cInfo, CurrentAccountPrincipalChangeHis cHis){
		Double amount = cHis.getChangeValue();
		//<1>.更新投资记录状态(正常)
		currentAccountPrincipalChangeHisDao.updateStatus(cHis.getId(), CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
		//<2>.维护活期产品持有信息
		currentPrincipalHandler.holdHandler(cHis.getAccountId(), cHis.getProjectId(), cHis.getChangeValue());
		//<3>.维护活期账户总览操作
		currentAccountHandler.summaryHandler(cHis.getAccountId(), amount);
		//<4>.更新投资人账户信息
		customerBalanceDao.updateCurrentInvestSelective(cHis.getAccountId(), amount, amount);
		//<5>.新增余额变更记录(投资人)
		String changeReasonInvest = "活期投资：" + cInfo.getName() + "【" + cHis.getId() + "】投资(确认金额：" + amount + "元)";
		operaBalanceHisRecord(cHis.getAccountId(), changeReasonInvest, -amount, cInfo, cHis);
		//<6>.更新融资人账户信息
		customerBalanceDao.updateCurrentReceiveSelective(cInfo.getBorrowerAccountId(), amount);
		//<7>.新增余额变更记录(融资人)
		String changeReasonReceive = "活期收款：" + cInfo.getName() + "【" + cHis.getId() + "】投资(确认金额：" + amount + "元)";
		operaBalanceHisRecord(cInfo.getBorrowerAccountId(), changeReasonReceive, amount, cInfo, cHis);
		//<8>.是否满标处理
		currentProjectHandler.finishedCurrentProject(cInfo);
	}
	
	
	/**
	 * 操作投资记录
	 * @param accountId
	 * @param opTerm
	 * @param projectId
	 * @param changeType
	 * @param changeReason
	 * @param recordId
	 * @param amount
	 */
	public void operaBalanceHisRecord(Long accountId, String changeReason, Double amount, CurrentProjectInfo cInfo, CurrentAccountPrincipalChangeHis cHis){
		CustomerBalance balance = customerBalanceDao.get(String.valueOf(accountId));
		String changeType = ProjectConstant.CHANGE_TYPE_BALANCE_INVEST_CONFIRM;
		Long projectId = NumberUtil.toLong(cInfo.getId(), 0L);
		String recordId = cHis.getId();
		String opTerm = cHis.getOpTerm();
		currentBalanceHandler.addBalanceHisRecord(accountId, opTerm, projectId, changeType, changeReason, recordId, amount, balance.getGoldBalance());
	}
	
	/**
	 * 活期产品投资确认(易宝3.7接口)
	 * @param cInfo
	 * @param cHis
	 * @return
	 */
	public boolean confirmCurrentInvest(CurrentProjectInfo cInfo,CurrentAccountPrincipalChangeHis cHis){
		//借款人信息
		Long customerId = cInfo.getBorrowerAccountId();
		CustomerAccount ca = customerAccountDao.get(customerId);
		String borrowUserNo = ca.getPlatformUserNo();
		String thirdPartyNo = cHis.getThirdAccountRequestNo();
		CompleteTranscationResp completeResp = confirmCurrentInvestmentRecord(cInfo, borrowUserNo, cHis, thirdPartyNo, "CONFIRM");
		return "1".equals(completeResp.getCode());
	}
	
	
	/**
	 * 确认操作
	 * @param projectId
	 * @param cInfo
	 * @param borrowUserNo
	 * @param cHis
	 * @param thirdPartyNo
	 * @param mode
	 * @return
	 */
	public CompleteTranscationResp confirmCurrentInvestmentRecord(CurrentProjectInfo cInfo,
			String borrowUserNo, CurrentAccountPrincipalChangeHis cHis,
			String thirdPartyNo,String mode) {
		Date startDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(startDateTime) + "confirmCurrentInvestmentRecord borrowUserNO : " + borrowUserNo +" start request yeepay");
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
		/*double changeVal=0;
		String projectId = cInfo.getId();
		saveP2pAccountFlow(changeVal+"", projectId, "0",
				cHis.getId(), cHis.getAccountId(), "活期项目："+cInfo.getName()+" 投资人："+cHis.getAccountId().longValue()+" 投资编号："+cHis.getId()+" 平台收到："+changeVal, thirdPartyNo,
				completeXml, completeResp.getCode());*/
		Date endDateTime = new Date();
		logger.info("----------------" + DateUtils.formatDateTime(endDateTime) + "confirmCurrentInvestmentRecord borrowUserNO : " + borrowUserNo +" end total time consuming:" + (endDateTime.getTime() - startDateTime.getTime())/1000 + "s.");
		return completeResp;
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
	
}
