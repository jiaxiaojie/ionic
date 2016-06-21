package com.thinkgem.jeesite.modules.project.service.util.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.customer.service.CustomerBlanceAlignmentService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBlanceAlignment;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

public class AlignmentBlanceHandler implements Runnable{
	Logger logger = Logger.getLogger(this.getClass());
	
	private CustomerBlanceAlignmentService customerBlanceAlignmentService;
	
	private YeepayCommonHandler yeepayCommonHandler;
	
	private LogTimerTaskService logTimerTaskService;
	
	private List<CustomerAccount> accountList;
	public AlignmentBlanceHandler(List<CustomerAccount> accountList,YeepayCommonHandler yeepayCommonHandler,
			CustomerBlanceAlignmentService customerBlanceAlignmentService,LogTimerTaskService logTimerTaskService) {
		this.accountList=accountList;
		this.customerBlanceAlignmentService = customerBlanceAlignmentService;
		this.yeepayCommonHandler = yeepayCommonHandler;
		this.logTimerTaskService = logTimerTaskService;
	}

	@Override
	public void run(){
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】alignmentBlanceHandler start...");
		String status = "1";
		String remark = "autoAlignmentBlanceTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		//logger.info("the thread is :" + Thread.currentThread());
		for(CustomerAccount account: accountList){
			try {
				AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(account.getPlatformUserNo());
				Double platformGoldBalance = LoanUtil.formatAmount(account.getCustomerBalance().getGoldBalance());
				Double yeepayBalance = accountInfoResp.getBalance() !=null ? LoanUtil.formatAmount(NumberUtils.toDouble(accountInfoResp.getBalance())) : 0;
				if(accountInfoResp != null && accountInfoResp.getBalance() != null 
						&& platformGoldBalance.compareTo(yeepayBalance) != 0){
					logger.debug("the accountId：" + account.getAccountId() + " ,platform goldBalance： " + platformGoldBalance + " ,yeepay  balance:" + yeepayBalance);
					CustomerBlanceAlignment cAlignment = new CustomerBlanceAlignment();
					cAlignment.setAccountId(account.getAccountId());
					cAlignment.setCustomerName(account.getCustomerBase().getCustomerName());
					cAlignment.setTaskGoldBalance(account.getCustomerBalance().getGoldBalance());
					cAlignment.setTaskYeepayBalance(NumberUtils.toDouble(accountInfoResp.getBalance()));
					cAlignment.setStatus("0");
					cAlignment.setCreateDt(new Date());
					customerBlanceAlignmentService.insert(cAlignment);
				}
			} catch (Exception e) {
				e.printStackTrace();
				customerBlanceException(account);
				status = "0";
				failBuilder.append("autoAlignmentBlanceTask fail record id is " + account.getAccountId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoAlignmentBlanceTask investment record id is "
						+ account.getAccountId() + " run err mes is "+e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		Date endTime = new Date();
		logTimerTaskService.logTimer(accountList, "autoAlignmentBlanceTask", startTime, endTime, status, remark);
		logger.info("alignmentBlanceHandler total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
	}
	
	/**
	 * 异常数据处理
	 * @param account
	 * @param accountInfoResp
	 */
	public void customerBlanceException(CustomerAccount account){
		CustomerBlanceAlignment cAlignment = new CustomerBlanceAlignment();
		cAlignment.setAccountId(account.getAccountId());
		cAlignment.setCustomerName(account.getCustomerBase().getCustomerName());
		cAlignment.setTaskGoldBalance(account.getCustomerBalance().getGoldBalance());
		cAlignment.setTaskYeepayBalance(-1.0);
		cAlignment.setStatus("0");
		cAlignment.setCreateDt(new Date());
		customerBlanceAlignmentService.insert(cAlignment);
	}

}
