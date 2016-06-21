package com.thinkgem.jeesite.modules;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 与易宝交互测试的公用方法handler
 * 
 * @author ydt 2015-09-16
 */
@Component("yeepayCommonHandlerTest")
public class YeepayCommonHandlerTest extends YeepayCommonHandler {
	Logger logger = Logger.getLogger(this.getClass());

	
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private CustomerBalanceHandler customerBalanceHandler;

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
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String transferToCustomerFromPlatform(long accountId, double amount,
			String balanceChangeType, String balanceChangeReason, String ext1) {
		CustomerAccount customerAccount = customerAccountDao.get(accountId);
		try {
			if (amount > 0 && ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount())) {
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ, customerAccount.getPlatformUserNo());
				logger.info("=====transfer to customer start. accountId:" + accountId + ", amount:" + amount + ", requestNo:" + requestNo + "=====");
				// 生成直接转账请求
//				DirectTranscationReq req = generateDirectTranscationReq(customerAccount.getPlatformUserNo(), amount, requestNo);
//				String reqXml = req.toReq();
				// 1.发送直连转账请求给易宝，并插入logThirdParty新纪录
				// 2.接收易宝callback数据，并更新logThirdParty表callback相关数据
//				String callBackContent = DirectReqUtils
//						.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ,
//								reqXml,
//								YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE);
//				insertIntoLogThirdParty(requestNo, reqXml);
//				DirectTranscationResp resp = JaxbMapper.fromXml(
//						callBackContent, DirectTranscationResp.class);
//				String respCode = resp.getCode();
//				logger.info("response code:" + respCode + ",response content:"
//						+ callBackContent + ", requestNo:" + requestNo);
				// 插入logThirdParty表转账请求纪录
//				updateLogThirdPartyWithCallback(requestNo, callBackContent,
//						respCode);
				
				DirectTranscationResp resp = new DirectTranscationResp();
				String respCode = "1";
				
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
}
