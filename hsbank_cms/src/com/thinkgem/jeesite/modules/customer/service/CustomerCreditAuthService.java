/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerCreditAuthDao;
import com.thinkgem.jeesite.modules.entity.CreditLevelInfo;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerCar;
import com.thinkgem.jeesite.modules.entity.CustomerCreditAuth;
import com.thinkgem.jeesite.modules.entity.CustomerHousing;
import com.thinkgem.jeesite.modules.entity.CustomerWork;
import com.thinkgem.jeesite.modules.sys.dao.CreditLevelInfoDao;

/**
 * 会员信用认证信息Service
 * @author ydt
 * @version 2015-07-13
 */
@Service
@Transactional(readOnly = true)
public class CustomerCreditAuthService extends CrudService<CustomerCreditAuthDao, CustomerCreditAuth> {
	@Autowired
	private CreditLevelInfoDao creditLevelInfoDao;
	
	public CustomerCreditAuth get(String id) {
		return super.get(id);
	}
	
	public List<CustomerCreditAuth> findList(CustomerCreditAuth customerCreditAuth) {
		return super.findList(customerCreditAuth);
	}
	
	public Page<CustomerCreditAuth> findPage(Page<CustomerCreditAuth> page, CustomerCreditAuth customerCreditAuth) {
		return super.findPage(page, customerCreditAuth);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerCreditAuth customerCreditAuth) {
		super.save(customerCreditAuth);
		Double score = customerCreditAuth.getTotalScore();
		CreditLevelInfo creditLevelInfo = creditLevelInfoDao.getByScore(score);
		String creditLevel = creditLevelInfo.getCreditLevel();
		Double creditLimit = creditLevelInfo.getCreditLimit();
		customerCreditAuth.setCreditScore(score);
		customerCreditAuth.setCreditLevel(creditLevel);
		customerCreditAuth.setCreditLimit(creditLimit);
		super.save(customerCreditAuth);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerCreditAuth customerCreditAuth) {
		super.delete(customerCreditAuth);
	}

	public CustomerCreditAuth getByAccountId(Long accountId) {
		return dao.getByAccountId(accountId);
	}
	
	@Transactional(readOnly = false)
	public void authBaseInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setPersonCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authIdentityInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setIdentityCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authIncomeInfo(CustomerWork customerWork) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerWork.getAccountId());
		customerCreditAuth.setIncomeCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authcreditCardInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authHousingInfo(CustomerHousing customerHousing) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerHousing.getAccountId());
		customerCreditAuth.setHousingCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authCarInfo(CustomerCar customerCar) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerCar.getAccountId());
		customerCreditAuth.setCarCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authAddressInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setAddressCreditAuthCode("1");
		save(customerCreditAuth);
	}

	@Transactional(readOnly = false)
	public void authEducationInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setEducationCreditAuthCode("1");
		save(customerCreditAuth);
	}
	
}