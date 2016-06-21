/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBlanceAlignmentDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerBlanceAlignment;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员账户余额对齐Service
 * @author lzb
 * @version 2015-11-03
 */
@Service
@Transactional(readOnly = true)
public class CustomerBlanceAlignmentService extends CrudService<CustomerBlanceAlignmentDao, CustomerBlanceAlignment> {
    @Autowired
    private CustomerBalanceDao customerBalanceDao;
    @Autowired
	private CustomerBalanceHisDao customerBalanceHisDao;
    
	public CustomerBlanceAlignment get(String id) {
		return super.get(id);
	}
	
	public List<CustomerBlanceAlignment> findList(CustomerBlanceAlignment customerBlanceAlignment) {
		return super.findList(customerBlanceAlignment);
	}
	
	public Page<CustomerBlanceAlignment> findPage(Page<CustomerBlanceAlignment> page, CustomerBlanceAlignment customerBlanceAlignment) {
		return super.findPage(page, customerBlanceAlignment);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerBlanceAlignment customerBlanceAlignment) {
		super.save(customerBlanceAlignment);
	}
	
	@Transactional(readOnly = false)
	public String alignment(CustomerBlanceAlignment customerBlanceAlignment, CustomerAccount account, AccountInfoResp accountInfoResp) {
		String message = "";
		if(!account.getCustomerBalance().getGoldBalance().equals(NumberUtils.toDouble(accountInfoResp.getBalance()))){
			//同步易宝账户余额
			customerBalanceDao.updateGoldBalanceByAlignment(customerBlanceAlignment.getAccountId(), NumberUtils.toDouble(accountInfoResp.getBalance()));
			Double actualMoney = LoanUtil.formatAmount(NumberUtils.toDouble(accountInfoResp.getBalance()) - account.getCustomerBalance().getGoldBalance());
			this.addBalanceRecord(account.getAccountId(), ProjectConstant.OP_TERM_DICT_PC, MarketConstant.CUSTOMER_BLANCE_ALIGNMENT_REMARK, actualMoney, NumberUtils.toDouble(accountInfoResp.getBalance()));
			message = "余额对齐成功";
		}else{
			message = "易宝账户余额：" + accountInfoResp.getBalance() + " 等于平台账户余额：" + account.getCustomerBalance().getGoldBalance();
		}
		CustomerBlanceAlignment alignment = new CustomerBlanceAlignment();
		alignment.setId(customerBlanceAlignment.getId());
		alignment.setNewGoldBalance(account.getCustomerBalance().getGoldBalance());
		alignment.setNewYeepayBalance(NumberUtils.toDouble(accountInfoResp.getBalance()));
		alignment.setStatus("1");
		alignment.setModifyDt(new Date());
		dao.updateSelected(alignment);
		return message;
	}
	
	/**
	 * 余额变更记录
	 * @param accountId
	 * @param opTerm
	 * @param changeReason
	 * @param actualMoney
	 * @param goldBalance
	 */
	public void addBalanceRecord(Long accountId, String opTerm, String changeReason, Double actualMoney, Double goldBalance){
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addBalanceRecord start...");
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		customerBalanceHis.setAccountId(accountId);
		customerBalanceHis.setChangeVal(actualMoney);
		customerBalanceHis.setBalance(goldBalance);
		customerBalanceHis.setChangeType(ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT);
		customerBalanceHis.setChangeReason(changeReason);
		customerBalanceHis.setOpDt(new Date());
		customerBalanceHis.setOpTermType(opTerm);
		customerBalanceHisDao.insert(customerBalanceHis);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":addBalanceRecord end...");
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBlanceAlignment customerBlanceAlignment) {
		super.delete(customerBlanceAlignment);
	}

	@Transactional(readOnly = false)
	public void insert(CustomerBlanceAlignment cAlignment) {
		dao.insert(cAlignment);
	}

	/**
	 * 删除days天以前的数据
	 * @param days
	 */
	@Transactional(readOnly = false)
	public void deleteSomeDaysAgoData(int days) {
		dao.deleteSomeDaysAgoData(days);
	}
	
}