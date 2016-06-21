/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.DirectReqUtils;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationReq;
import com.thinkgem.jeesite.common.yeepay.directTranscation.DirectTranscationResp;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerDoubleElevenActivityDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerDoubleElevenActivity;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.log.dao.LogThirdPartyDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 双11活动手动送现金Service
 * @author lzb
 * @version 2015-11-03
 */
@Service
@Transactional(readOnly = true)
public class CustomerDoubleElevenActivityService extends CrudService<CustomerDoubleElevenActivityDao, CustomerDoubleElevenActivity> {
    Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private LogThirdPartyDao logThirdPartyDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private DirectReqUtils directReqUtils;
	
	public CustomerDoubleElevenActivity get(String id) {
		return super.get(id);
	}
	
	public List<CustomerDoubleElevenActivity> findList(CustomerDoubleElevenActivity customerDoubleElevenActivity) {
		return super.findList(customerDoubleElevenActivity);
	}
	
	public Page<CustomerDoubleElevenActivity> findPage(Page<CustomerDoubleElevenActivity> page, CustomerDoubleElevenActivity customerDoubleElevenActivity) {
		return super.findPage(page, customerDoubleElevenActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerDoubleElevenActivity customerDoubleElevenActivity) {
		super.save(customerDoubleElevenActivity);
	}
	
	
	
	
	
	/**
	 * 赠送现金
	 * @param customerDoubleElevenActivity
	 */
	@Transactional(readOnly = false)
	public void give(String id) {
		CustomerDoubleElevenActivity activity = super.get(id);
		CustomerAccount customerAccount = customerAccountDao.get(activity.getAccountId());
		this.transferToCustomerFromPlatform(customerAccount, activity.getAmount(), ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, activity.getChangeReason());
		activity.setStatus("1");
		activity.setOpDt(new Date());
		dao.updateSelected(activity);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerDoubleElevenActivity customerDoubleElevenActivity) {
		super.delete(customerDoubleElevenActivity);
	}
	
	/**
	 * 直接转账
	 * @param customerAccount
	 * @param amount
	 * @param balanceChangeType
	 * @param changeReason
	 */
	private void transferToCustomerFromPlatform(CustomerAccount customerAccount, double amount, String balanceChangeType, String changeReason) {
		if(amount > 0 && ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount())) {
			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ, customerAccount.getPlatformUserNo());
			logger.info("-----------" + DateUtils.formatDateTime(new Date()) + ": start give money to customer, accountId:"
					+ customerAccount.getAccountId() + ", amount:" + amount + ", requestNo:" + requestNo + "-----------");
			//生成直接转账请求
			DirectTranscationReq req = generateDirectTranscationReq(customerAccount.getPlatformUserNo(), amount, requestNo);
			String reqXml = req.toReq();
			//1.发送直连转账请求给易宝，并插入logThirdParty新纪录
			//2.接收易宝callback数据，并插入logThirdParty表转账请求纪录
			String callBackContent = directReqUtils.dirReq(YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ, reqXml, YeepayConstant.PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE);
			insertIntoLogThirdParty(requestNo, reqXml);
			DirectTranscationResp resp = JaxbMapper.fromXml(callBackContent, DirectTranscationResp.class);
			String respCode = resp.getCode();
			logger.info("response code:" + respCode + ",response content:" + callBackContent + ", requestNo:" + requestNo);
			//更新logThirdParty表callback相关数据
			updateLogThirdPartyWithCallback(requestNo, callBackContent, respCode);
			if(!respCode.equals("1")) {
				//抛出转账失败异常
				logger.error("直接转账 to "+ customerAccount.getAccountId() +" 金额："+ amount +" 失败");
				throw new ServiceException("直接转账失败：" + resp.getDescription());
			}else {
				//更新customerBalance表数据，并插入customerBalanceHis表新记录
				logger.info("update customerBalance by transferToCustomerByPlatform: customer accountId:" + customerAccount.getAccountId() + ", amount:" + amount);
				customerBalanceDao.updateBalance(customerAccount.getAccountId(), amount);
				CustomerBalance customerBalance = customerBalanceDao.get(customerAccount.getAccountId() + "");
				logger.info("update customerBalance finished");
				
				logger.info("insert into customerBalanceHis by transferToCustomerByPlatform: customer accountId:" + customerAccount.getAccountId() + ", change amount:" + amount);
				CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
				customerBalanceHis.setAccountId(customerAccount.getAccountId());
				customerBalanceHis.setChangeVal(amount);
				customerBalanceHis.setBalance(customerBalance.getGoldBalance());
				customerBalanceHis.setChangeType(balanceChangeType);
				customerBalanceHis.setChangeReason(changeReason);
				customerBalanceHis.setOpDt(new Date());
				customerBalanceHis.setOpTermType(ProjectConstant.OP_TERM_DICT_PC);
				customerBalanceHisDao.insert(customerBalanceHis);
				logger.info("insert into customerBalanceHis finished");
			}
			logger.info("-----------" + DateUtils.formatDateTime(new Date()) + ":transfer to customer end.-----------");
		} else if(!ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount())) {
			logger.info("customer has not open third account, transfer failed: accountId:" + customerAccount.getAccountId() + ", amount:" + amount);
			throw new ServiceException("直接转账失败：accountId：" + customerAccount.getAccountId() + "还没有开通第三方账号");
		}else if(amount==0){
			logger.info("customer will give 0, transfer failed: accountId:" + customerAccount.getAccountId() + ", amount:" + amount);
			throw new ServiceException("直接转账失败，转账金额amount：" + amount);
		}
	}
	
	@Transactional
	public void transferToCustomerFromPlatform(long accountId, double amount, String balanceChangeType, String balanceChangeReason, String ext1) {
		yeepayCommonHandler.transferToCustomerFromPlatform(accountId, amount, balanceChangeType, balanceChangeReason, ext1);
	}
	
	/**
	 * 生成直接转账请求
	 * @param toPlatformUserNo
	 * @param amount
	 * @param requestNo
	 * @return
	 */
	private DirectTranscationReq generateDirectTranscationReq(String toPlatformUserNo, double amount, String requestNo) {
		DirectTranscationReq req = new DirectTranscationReq();
		req.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setRequestNo(requestNo);
		req.setPlatformUserNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		req.setUserType("MERCHANT");
		req.setBizType("TRANSFER");
		req.setNotifyUrl(YeepayConstant.YEEPAY_DIRECT_NOTIFY_URL_PREFIX + "directTransaction");
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
	 * 更新logThirdParty表callback相关数据
	 * @param requestNo
	 * @param respContent
	 * @param respCode
	 */
	private void updateLogThirdPartyWithCallback(String requestNo, String respContent, String respCode) {
		logThirdPartyDao.updateWithCallback(requestNo, respContent, respCode, new Date());
	}
}