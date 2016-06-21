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
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.entity.CreditInvestUserInfo;
import com.thinkgem.jeesite.modules.entity.CreditMachineAccount;
import com.thinkgem.jeesite.modules.credit.dao.CreditInvestUserInfoDao;
import com.thinkgem.jeesite.modules.credit.dao.CreditMachineAccountDao;

/**
 * 债权投资用户信息Service
 * @author wanduanrui
 * @version 2016-03-30
 */
@Service
@Transactional(readOnly = true)
public class CreditInvestUserInfoService extends CrudService<CreditInvestUserInfoDao, CreditInvestUserInfo> {
	@Autowired
	private CreditMachineAccountDao creditMachineAccountDao;
	
	@Autowired
	private CreditInvestUserInfoDao creditInvestUserInfoDao;
	public CreditInvestUserInfo get(String id) {
		return super.get(id);
	}
	
	public List<CreditInvestUserInfo> findList(CreditInvestUserInfo creditInvestUserInfo) {
		return super.findList(creditInvestUserInfo);
	}
	
	public Page<CreditInvestUserInfo> findPage(Page<CreditInvestUserInfo> page, CreditInvestUserInfo creditInvestUserInfo) {
		page.setOrderBy("a.create_date desc");
		return super.findPage(page, creditInvestUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CreditInvestUserInfo creditInvestUserInfo) {
		super.save(creditInvestUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreditInvestUserInfo creditInvestUserInfo) {
		CreditMachineAccount creditMachineAccount = new CreditMachineAccount();
		creditMachineAccount.setCreditInvestUserId(Long.parseLong(creditInvestUserInfo.getId()));
		creditMachineAccount.setPage(new Page<CreditMachineAccount>(1, 1));
		List<CreditMachineAccount> creditMachineAccounts = creditMachineAccountDao.findList(creditMachineAccount);
		
		if(creditMachineAccounts != null && creditMachineAccounts.size() > 0){
			throw new ServiceException("此用户已经关联台账，不能被删除！");
		}
		super.delete(creditInvestUserInfo);
	}

	public Page<CreditInvestUserInfo> birthdayRemindPage(Page<CreditInvestUserInfo> page,CreditInvestUserInfo creditInvestUserInfo) {
		creditInvestUserInfo.setPage(page);
		
		return page.setList(dao.birthdayRemindList(creditInvestUserInfo));
	}

	public Page<CreditInvestUserInfo> repaymentRemindPage(Page<CreditInvestUserInfo> page,CreditInvestUserInfo creditInvestUserInfo) {
		creditInvestUserInfo.setPage(page);
		return page.setList(dao.repaymentRemindList(creditInvestUserInfo));	
	}

	public CreditInvestUserInfo getCreditInvestUserInfoByInfo(CreditInvestUserInfo creditInvestUserInfo) {
		return creditInvestUserInfoDao.getByEntity(creditInvestUserInfo);
	}
	
}