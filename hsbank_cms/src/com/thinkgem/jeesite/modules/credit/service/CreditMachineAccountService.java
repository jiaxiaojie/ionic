/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.credit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CreditMachineAccount;
import com.thinkgem.jeesite.modules.credit.dao.CreditMachineAccountDao;

/**
 * 债权台账Service
 * @author wanduanrui
 * @version 2016-03-30
 */
@Service
@Transactional(readOnly = true)
public class CreditMachineAccountService extends CrudService<CreditMachineAccountDao, CreditMachineAccount> {
	@Autowired
	private CreditMachineAccountDao creditMachineAccountDao;
	@Autowired
	private CreditBaseInfoService creditBaseInfoService;
	
	public CreditMachineAccount get(String id) {
		return super.get(id);
	}
	
	public List<CreditMachineAccount> findList(CreditMachineAccount creditMachineAccount) {
		return super.findList(creditMachineAccount);
	}
	
	public Page<CreditMachineAccount> findPage(Page<CreditMachineAccount> page, CreditMachineAccount creditMachineAccount) {
		page.setOrderBy("a.create_date desc");
		return super.findPage(page, creditMachineAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(CreditMachineAccount creditMachineAccount) {
		super.save(creditMachineAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreditMachineAccount creditMachineAccount) {
		super.delete(creditMachineAccount);
	}

	public CreditMachineAccount getCreditMachineAccountByInfo(CreditMachineAccount creditMachineAccount) {
		
		return creditMachineAccountDao.getByEntity(creditMachineAccount);
	}

	@Transactional(readOnly = false)
	public void saveAndUpdateCreditRaisedMoney(CreditMachineAccount creditMachineAccount) {
		
		//1.更新债权线下已经募集金额
		Long currentCreditId = creditMachineAccount.getCreditId();
		Double currentInvestMoney  = creditMachineAccount.getInvestMoney();
		if (!creditMachineAccount.getIsNewRecord()){
			//删除减去之前已经募集金额中的金额
			CreditMachineAccount  tempCreditMacAcc  = creditMachineAccountDao.get(creditMachineAccount.getId());
			Long beforeCreditId =  tempCreditMacAcc.getCreditId();
			Double beforeInvestMoney = tempCreditMacAcc.getInvestMoney();
			
			if(beforeCreditId == currentCreditId){
				currentInvestMoney -=  tempCreditMacAcc.getInvestMoney();
			}
			else{
				creditBaseInfoService.addRaisedMoneyBelowLine(beforeCreditId, -1 * beforeInvestMoney);
			}
		}
		
		creditBaseInfoService.addRaisedMoneyBelowLine(currentCreditId, currentInvestMoney);
		
		//2.保存债权台账
		super.save(creditMachineAccount);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}