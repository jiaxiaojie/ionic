/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.customer.dao.CustomerWithdrawHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerWithdrawHis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerFreeWithdrawCountHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerFreeWithdrawCountHis;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员账户余额汇总Service
 * @author ydt
 * @version 2015-06-25
 */
@Service
@Transactional(readOnly = true)
public class CustomerBalanceService extends CrudService<CustomerBalanceDao, CustomerBalance> {
	
	@Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
	@Autowired
	private CustomerFreeWithdrawCountHisDao customerFreeWithdrawCountHisDao;
	@Autowired
	private CustomerBalanceHandler customerBalanceHandler;
	@Autowired
	private CustomerWithdrawHisDao customerWithdrawHisDao;
	
	public CustomerBalance get(String id) {
		return super.get(id);
	}
	
	public CustomerBalance getByExtendNo(String extendNo) {
		return dao.getByExtendNo(extendNo);
	}
	
	public List<CustomerBalance> findList(CustomerBalance customerBalance) {
		return super.findList(customerBalance);
	}
	
	public Page<CustomerBalance> findPage(Page<CustomerBalance> page, CustomerBalance customerBalance) {
		return super.findPage(page, customerBalance);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBalance customerBalance) {
		super.save(customerBalance);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBalance customerBalance) {
		super.delete(customerBalance);
	}
	@Transactional(readOnly = false)
	public void createInitCustomerBalance(Long accountId){
		CustomerBalance customerBalance=new CustomerBalance();
		customerBalance.setAccountId(accountId);
		customerBalance.setGoldBalance(new Double("0"));
		customerBalance.setCongealVal(new Double("0"));
		customerBalance.setWillLoan(new Double("0"));
		customerBalance.setSumLoan(new Double("0"));
		customerBalance.setRepaymentMoney(new Double("0"));
		customerBalance.setRepaymentPrincipal(new Double("0"));
		customerBalance.setRepaymentLateMoney(new Double("0"));
		customerBalance.setRepaymentPreMoney(new Double("0"));
		customerBalance.setRepayment30dWill(new Double("0"));
		customerBalance.setWillProfit(new Double("0"));
		customerBalance.setSumProfit(new Double("0"));
		customerBalance.setSumInvestment(new Double("0"));
		customerBalance.setReceiveMoney(new Double("0"));
		customerBalance.setReceivePrincipal(new Double("0"));
		customerBalance.setReceiveLateMoney(new Double("0"));
		customerBalance.setReceivePreMoney(new Double("0"));
		customerBalance.setReceiveTransferMoney(new Double("0"));
		customerBalance.setNetAssets(new Double("0"));
		customerBalance.setFinancialAssets(new Double("0"));
		customerBalance.setSumRecharge(new Double("0"));
		customerBalance.setSumWithdraw(new Double("0"));
		customerBalance.setRechargeCount(new Integer("0"));
		customerBalance.setWithdrawCount(new Integer("0"));
		customerBalance.setInvestmentCount(new Integer("0"));
		customerBalance.setCancelCount(new Integer("0"));
		customerBalance.setTransferCount(new Integer("0"));
		customerBalance.setAcceptCount(new Integer("0"));
		customerBalance.setFirstGetDt(null);
		customerBalance.setLastChangeDt(null);
		save(customerBalance);
	}

	/**
	 * 充值成功后数据更新
	 * 		1.更新customerBalance表
	 * 		2.新增customerBalanceHis流水记录
	 * @param platformUserNo
	 * @param amount
	 * @param fee
	 * @param payProduct 
	 */
	@Transactional(readOnly = false)
	public void updateWithRechargeNotify(String platformUserNo, Double amount, Double fee, String payProduct) {
		CustomerBalance customerBalance = dao.getByPlatformUserNo(platformUserNo);
		dao.updateByRecharge(customerBalance.getAccountId(), amount - fee, customerBalance.getFirstGetDt() == null ? new Date() : customerBalance.getFirstGetDt());
		customerBalance = dao.get(customerBalance.getAccountId() + "");
		
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(customerBalance.getAccountId());
		customerBalanceHis.setChangeVal(amount);
		customerBalanceHis.setBalance(customerBalance.getGoldBalance());
		customerBalanceHis.setChangeType(payProduct);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(ProjectConstant.DICT_DEFAULT_VALUE);
		customerBalanceHisDao.insert(customerBalanceHis);
	}

	/**
	 * 提现成功后数据更新
	 * 		1.更新customerBalance表
	 * 		2.新增customerBalanceHis流水记录
	 * 		3.若使用了可免费提现次数（即feeMode为：收取商户手续费），则添加customerFreeWithdrawCountHis表新记录
	 * @param platformUserNo
	 * @param amount
	 * @param fee
	 * @param withdarwType
	 * @param requestNo
	 */
	@Transactional(readOnly = false)
	public void updateWithWithdrawNotify(String platformUserNo, Double amount, String feeMode, Double fee, String withdarwType, String requestNo,String bankCardNo, String bank) {
		CustomerBalance customerBalance = dao.getByPlatformUserNo(platformUserNo);
		double realReduceAmount = amount + fee;
		int freeWithdrawCountChangeValue = 0;
		if(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM.equals(feeMode)) {
			realReduceAmount = amount;
			freeWithdrawCountChangeValue = -1;
		}
		dao.updateByWithdraw(customerBalance.getAccountId(), realReduceAmount, freeWithdrawCountChangeValue);
		customerBalance = dao.get(customerBalance.getAccountId() + "");

		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(customerBalance.getAccountId());
		customerBalanceHis.setChangeVal(-realReduceAmount);
		customerBalanceHis.setBalance(customerBalance.getGoldBalance());
		customerBalanceHis.setChangeType(withdarwType);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(ProjectConstant.DICT_DEFAULT_VALUE);
		customerBalanceHisDao.insert(customerBalanceHis);
		//添加提现记录
		addWithdrawHis(customerBalance,amount,feeMode,withdarwType,requestNo,bankCardNo,bank);
		
		if(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM.equals(feeMode)) {
			CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis = new CustomerFreeWithdrawCountHis();
			customerFreeWithdrawCountHis.setAccountId(customerBalance.getAccountId());
			customerFreeWithdrawCountHis.setChangeVal(-1);
			customerFreeWithdrawCountHis.setChangeTypeCode(ProjectConstant.FREE_WITHDRAW_COUNT_CHANGE_TYPE_WITHDRAW_USE);
			customerFreeWithdrawCountHis.setRequestNo(requestNo);
			customerFreeWithdrawCountHis.setUseDt(new Date());
			customerFreeWithdrawCountHisDao.insert(customerFreeWithdrawCountHis);
		}
	}

	/**
	 * 更新用户免费提现次数
	 * @param accountId
	 * @param changeValue
	 * @param changeType
	 */
	@Transactional(readOnly = false)
	public void updateFreeWithdrawCount(long accountId, int changeValue, String changeType) {
		customerBalanceHandler.updateFreeWithCount(accountId, changeValue, changeType);
	}

	/**
	 * 添加提现记录
	 * @param customerBalance
	 * @param amount
	 * @param feeMode
	 * @param withdarwType
	 * @param requestNo
	 * @param bankCardNo
     * @param bank
     */
	public  void addWithdrawHis(CustomerBalance customerBalance,Double amount, String feeMode,
								String withdarwType, String requestNo, String bankCardNo, String bank){
		CustomerWithdrawHis withdrawHis = new CustomerWithdrawHis();
		withdrawHis.setAccountId(customerBalance.getAccountId());
		withdrawHis.setAmount(amount);
		withdrawHis.setBankCardNo(bankCardNo);
		withdrawHis.setBankCode(bank);
		withdrawHis.setFeeMode(feeMode);
		withdrawHis.setOpTermType(ProjectConstant.DICT_DEFAULT_VALUE);
		withdrawHis.setThirdPartyReq(requestNo);
		withdrawHis.setWithdrawType(withdarwType);
		withdrawHis.setOpDt(new Date());
		withdrawHis.setRemark("用户提现");
		customerWithdrawHisDao.insert(withdrawHis);
	}
}