package com.thinkgem.jeesite.modules.yeepay.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoReq;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTranscationReq;
import com.thinkgem.jeesite.common.yeepay.autoTranscation.AutoTranscationResp;
import com.thinkgem.jeesite.common.yeepay.common.Property;
import com.thinkgem.jeesite.common.yeepay.completeTranscation.CompleteTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;
import com.thinkgem.jeesite.common.yeepay.query.QueryReq;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectInfoDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 与易宝交互的公用方法handler
 * 
 * @author ydt 2015-09-16
 */
@Component("yeepayCommonHandler")
public class YeepayCommonHandler {
	Logger logger = Logger.getLogger(this.getClass());
	public static String platformNo = YeepayConstant.YEEPAY_PLATFORM_NO;

	@Autowired
	private LogThirdPartyDao logThirdPartyDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private CustomerBalanceHandler customerBalanceHandler;
	@Autowired
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Autowired
	private DirectReqUtils directReqUtils;

	/**
	 * 平台转账给用户 若转账金额大于0且用户开通了第三方账号
	 * 1.发送直连转账请求给易宝，并插入logThirdParty新纪录
	 * 2.接收易宝callback数据，并更新logThirdParty表callback相关数据
	 * 3.	若成功
	 * 			更新customerBalance表数据，并插入customerBalanceHis表新记录
	 * 		若不成功
	 * 			抛出转账失败异常
	 * 4.后续操作：易宝notify回来时更新logThirdParty表notify相关数据
	 * 
	 * @param customerAccount
	 * @param amount
	 * @param balanceChangeType
	 * @param balanceChangeReason
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String transferToCustomerFromPlatform(long accountId, double amount,
			String balanceChangeType, String balanceChangeReason, String ext1) {
		CustomerAccount customerAccount = customerAccountDao.get(accountId);
		try {
			if (amount > 0 && ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount())) {
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ, customerAccount.getPlatformUserNo());
				logger.info("=====transfer to customer start. accountId:" + accountId + ", amount:" + amount + ", requestNo:" + requestNo + "=====");
				// 生成直接转账请求
				DirectTranscationReq req = generateDirectTranscationReq(customerAccount.getPlatformUserNo(), amount, requestNo);
				String reqXml = req.toReq();
				// 1.发送直连转账请求给易宝，并插入logThirdParty新纪录
				// 2.接收易宝callback数据，并更新logThirdParty表callback相关数据
				String callBackContent = directReqUtils
						.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
								reqXml,
								YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE);
				insertIntoLogThirdParty(requestNo, reqXml);
				DirectTranscationResp resp = JaxbMapper.fromXml(
						callBackContent, DirectTranscationResp.class);
				String respCode = resp.getCode();
				logger.info("response code:" + respCode + ",response content:"
						+ callBackContent + ", requestNo:" + requestNo);
				// 插入logThirdParty表转账请求纪录
				updateLogThirdPartyWithCallback(requestNo, callBackContent,
						respCode);
				if (!respCode.equals("1")) {
					// 抛出转账失败异常
					logger.error("直接转账 to " + customerAccount.getAccountId()
							+ " 金额：" + amount + " 失败");
					throw new ServiceException(
							"platform transfer to customer faild. customerAccountId:"
									+ customerAccount.getAccountId()
									+ ", amount:" + amount
									+ ", yeepay give description:"
									+ resp.getDescription());
				} else {
					// 更新customerBalance表数据，并插入customerBalanceHis表新记录
					logger.info("update customerBalance by transferToCustomerByPlatform start. customer accountId:" + accountId + ", amount:" + amount);
					customerBalanceHandler.upDateByTransferFromPlatform(accountId, amount, balanceChangeType, balanceChangeReason, ext1);
					logger.info("update customerBalance by transferToCustomerByPlatform end");
				}
				logger.info("-----------"
						+ DateUtils.formatDateTime(new Date())
						+ ":transfer to customer end.-----------");
				return requestNo;
			} else if (!ProjectConstant.HASOPENED.equals(customerAccount
					.getHasOpenThirdAccount())) {
				logger.info("customer has not open third account, transfer failed: accountId:"
						+ customerAccount.getAccountId() + ", amount:" + amount);
			} else if (amount == 0) {
				logger.info("customer will give 0, transfer failed: accountId:"
						+ customerAccount.getAccountId() + ", amount:" + amount);
			}
		} catch (Exception e) {
			logger.info("transfer money err:" + customerAccount.getAccountId()
					+ ",amount: " + amount + ",balanceChangeType:"
					+ balanceChangeType + ",balanceChangeReason:"
					+ balanceChangeReason + ",ext:" + ext1);
		}
		return "";
	}

	/**
	 * 生成直接转账请求
	 * 
	 * @param toPlatformUserNo
	 * @param amount
	 * @param requestNo
	 * @return
	 */
	private DirectTranscationReq generateDirectTranscationReq(
			String toPlatformUserNo, double amount, String requestNo) {
		DirectTranscationReq req = new DirectTranscationReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setRequestNo(requestNo);
		req.setPlatformUserNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setUserType("MERCHANT");
		req.setBizType("TRANSFER");
		req.setNotifyUrl(YeepayConstant.YEEPAY_DIRECT_NOTIFY_URL_PREFIX
				+ "directTransaction");
		MoneyDetail moneyDetail = new MoneyDetail();
		moneyDetail.setAmount(amount + "");
		moneyDetail.setBizType("TRANSFER");
		moneyDetail.setTargetPlatformUserNo(toPlatformUserNo);
		moneyDetail.setTargetUserType("MEMBER");
		List<MoneyDetail> list = new ArrayList<MoneyDetail>();
		list.add(moneyDetail);
		req.setDetail(list);
		return req;
	}

	/**
	 * 插入logThirdParty新纪录
	 * 
	 * @param requestNo
	 * @param reqContent
	 */
	private void insertIntoLogThirdParty(String requestNo, String reqContent) {
		LogThirdParty logThirdParty = new LogThirdParty();
		logThirdParty.setRequestNo(requestNo);
		logThirdParty.setService(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ);
		logThirdParty.setReqContent(reqContent);
		logThirdParty.setReqDt(new Date());
		logThirdPartyDao.insert(logThirdParty);
	}

	/**
	 * 插入logThirdParty新纪录
	 * 
	 * @param requestNo
	 * @param reqContent
	 */
	private void insertIntoLogThirdParty(String requestNo, String reqContent, String service) {
		LogThirdParty logThirdParty = new LogThirdParty();
		logThirdParty.setRequestNo(requestNo);
		logThirdParty.setService(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ);
		logThirdParty.setReqContent(reqContent);
		logThirdParty.setReqDt(new Date());
		logThirdPartyDao.insert(logThirdParty);
	}

	/**
	 * 更新logThirdParty表callback相关数据
	 * 
	 * @param requestNo
	 * @param respContent
	 * @param respCode
	 */
	private void updateLogThirdPartyWithCallback(String requestNo,
			String respContent, String respCode) {
		logThirdPartyDao.updateWithCallback(requestNo, respContent, respCode,
				new Date());
	}
	
	/**
	 * 活期产品还款给用户（提取利息、赎回本金、清盘）
	 * 		1.插入logThirdParty请求
	 * 		2.发起请求 并更新logThridParty callBack信息(后续操作：更新notify信息)
	 * 		3.更改fromCustomerAccount余额信息
	 * 		4.添加fromCustomerAccount余额变化流水
	 * 		5.更改targetCustomerAccount余额信息
	 * 		6.更改targetCustomerAccount余额变化流水
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String currentProjectRepayMoney(Long currentProjectId, Long targetAccountId, Double amount, String balanceChangeType, String remark, String opTerm) {
		logger.info("=====transcation start.=====");
		CurrentProjectInfo currentProjectInfo = currentProjectInfoDao.get(String.valueOf(currentProjectId));
		if(currentProjectInfo == null || !"1".equals(currentProjectInfo.getIsAutoRepay())) {
			throw new ServiceException("transaction failed. project is null or is not autoRepay status. currentProjectId:" + currentProjectId);
		}
		CustomerAccount fromCustomerAccount = customerAccountDao.get(currentProjectInfo.getBorrowerAccountId());
		CustomerAccount targetCustomerAccount = customerAccountDao.get(targetAccountId);
		if(targetCustomerAccount == null || !ProjectConstant.HASOPENED.equals(targetCustomerAccount.getHasOpenThirdAccount())) {
			throw new ServiceException("transaction failed. customerAccount has not open third account."
					+ "currentProjectId:" + currentProjectId + ", targetAccountId:" + targetAccountId + ", amount:" + amount + ".");
		}
		if(amount.doubleValue() <= 0) {
			throw new ServiceException("transaction failed. amount can not be negative."
					+ "currentProjectId:" + currentProjectId + ", targetAccountId:" + targetAccountId + ", amount:" + amount + ".");
		}
		
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ,
				fromCustomerAccount.getPlatformUserNo());
		AutoTranscationReq request = generateAutoTranscationReq(requestNo, fromCustomerAccount.getPlatformUserNo(),
				targetCustomerAccount.getPlatformUserNo(), amount, currentProjectId);
		insertIntoLogThirdParty(requestNo, request.toReq(), YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ);
		String callBackContent = directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ, request.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_SERVICE);
		AutoTranscationResp autoRespObj = JaxbMapper.fromXml(callBackContent, AutoTranscationResp.class);
		updateLogThirdPartyWithCallback(requestNo, callBackContent, autoRespObj.getCode());
		if (!autoRespObj.getCode().equals("1")) {
			throw new ServiceException("transaction failed. message:" + autoRespObj.getDescription()
					+ "currentProjectId:" + currentProjectId + ", targetAccountId:" + targetAccountId + ", amount:" + amount + ".");
		}
		//转账确认
		confirmTranscation(requestNo);
		updateCustomerBalanceData(fromCustomerAccount.getAccountId(), -amount, balanceChangeType, "活期还款：" + remark, ProjectConstant.OP_TERM_DICT_PC);
		updateCustomerBalanceData(targetCustomerAccount.getAccountId(), amount, balanceChangeType, remark, opTerm);
		logger.info("=====transcation end.=====");
		return requestNo;
	}
	
	/**
	 * 转账确认
	 * @param requestNo
	 */
	private void confirmTranscation(String requestNo) {
		try {
			logger.info("confirmTranscation start, comfirm transcation requestNo:" + requestNo);
			CompleteTranscationReq completeReq = new CompleteTranscationReq();
			completeReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
			completeReq.setRequestNo(requestNo);
			completeReq.setMode("CONFIRM");
			completeReq.setNotifyUrl(YeepayConstant.YEEPAY_NOTIFY_URL_PREFIX + "completeTransaction");
			String completeReqXml = completeReq.toReq();
			String response = directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ,completeReqXml,
							YeepayConstant.PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_SERVICE);
			logger.info("confirmTranscation end, comfirm transcation requestNo:" + requestNo + ", response:" + response + ".");
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("confirmTranscation exception, comfirm transcation requestNo:" + requestNo);
		}
	}

	/**
	 * 更新用户余额相关数据
	 * 		1.更改余额值
	 * 		2.插入余额变化记录
	 * @param accountId
	 * @param amount
	 * @param remark
	 */
	private void updateCustomerBalanceData(Long accountId, Double amount, String balanceChangeType, String remark, String opTerm) {
		customerBalanceDao.updateBalance(accountId, amount);
		
		CustomerBalance customerBalance = customerBalanceDao.get(accountId + "");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(amount);
		customerBalanceHis.setBalance(customerBalance.getGoldBalance());
		customerBalanceHis.setChangeType(balanceChangeType);
		customerBalanceHis.setChangeReason(remark);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
	}

	/**
	 * 生成请求
	 * @param requestNo
	 * @param platformUserNo
	 * @param targetPlatformUserNo
	 * @param amount
	 * @param currentProjectId
	 * @return
	 */
	private AutoTranscationReq generateAutoTranscationReq( String requestNo, String platformUserNo, String targetPlatformUserNo, Double amount, Long currentProjectId) {
		AutoTranscationReq request = new AutoTranscationReq();
		request.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		request.setUserType("MEMBER");
		request.setBizType("REPAYMENT");
		request.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "current/autoTranscation");
		request.setPlatformUserNo(platformUserNo);
		request.setRequestNo(requestNo);

		List<MoneyDetail> details = new ArrayList<MoneyDetail>();
		MoneyDetail detail = new MoneyDetail();
		detail.setAmount(String.valueOf(amount));
		detail.setBizType("REPAYMENT");
		detail.setTargetPlatformUserNo(targetPlatformUserNo);
		detail.setTargetUserType("MEMBER");
		details.add(detail);
		request.setDetail(details);
		
		List<Property> extend = new ArrayList<Property>();
		Property property = new Property();
		property.setName("tenderOrderNo");
		property.setValue(YeepayConstant.YEEPAY_CURRENT_TENDERORDERNO_PREFIX + currentProjectId);
		extend.add(property);
		request.setExtend(extend);
		return request;
	}
	
	/**
	 * 接口文档3.1章节 账户查询
	 * @param platformUserNo
	 * @return
	 */
	public AccountInfoResp accountInfo(String platformUserNo) {
		AccountInfoReq req = new AccountInfoReq();
		req.setPlatformNo(platformNo);
		req.setPlatformUserNo(platformUserNo);
		String resp = directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ ,req.toReq(),
				YeepayConstant.PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_SERVICE);
		return JaxbMapper.fromXml(resp, AccountInfoResp.class);
	}
	
	/**
	 * 接口文档3.6章节 解绑记录业务查询
	 * @param requestNo
	 * @return
	 */
	public UnbindRecordResp queryUnbindRecord(String requestNo){
		String respStr = query(requestNo,"UNBIND_RECORD");
		UnbindRecordResp unbindRecordResp = JaxbMapper.fromXml(
				respStr, UnbindRecordResp.class);
		return unbindRecordResp;
	}
	
	/**
	 * 接口文档3.6章节 单笔业务查询
	 * @param requestNo
	 * @param mode
	 * @return
	 */
	public String query(String requestNo,String mode) {
		QueryReq req = new QueryReq();
		req.setPlatformNo(platformNo);
		req.setRequestNo(requestNo);
		req.setMode(mode);
		return directReqUtils
				.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_REQ,req.toReq(),
						YeepayConstant.PROJECT_INTERFACE_DIRECT_QUERY_SERVICE);
	}
}
