package com.thinkgem.jeesite.modules.customer.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.customer.dao.CustomerCreditAuthDao;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerCar;
import com.thinkgem.jeesite.modules.entity.CustomerCreditAuth;
import com.thinkgem.jeesite.modules.entity.CustomerHousing;
import com.thinkgem.jeesite.modules.entity.CustomerWork;

@Component
public class CustomerCreditAuthHandler {
	@Autowired
	private CustomerCreditAuthDao customerCreditAuthDao;
	
	/**
	 * 认证个人基本信息
	 * 		1.获取认证信息
	 * 		2.将个人基本信息认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authBaseInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setPersonCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证身份信息
	 * 		1.获取认证信息
	 * 		2.将身份信息认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authIdentityInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setIdentityCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证身份信息
	 * 		1.获取认证信息
	 * 		2.将身份信息认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authIncomeInfo(CustomerWork customerWork) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerWork.getAccountId());
		customerCreditAuth.setIncomeCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证信用卡信息
	 * 		1.获取认证信息
	 * 		2.将信用卡信息认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authcreditCardInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证房产信息
	 * 		1.获取认证信息
	 * 		2.将房产认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authHousingInfo(CustomerHousing customerHousing) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerHousing.getAccountId());
		customerCreditAuth.setHousingCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证车产信息
	 * 		1.获取认证信息
	 * 		2.将车产认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authCarInfo(CustomerCar customerCar) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerCar.getAccountId());
		customerCreditAuth.setCarCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证家庭情况信息
	 * 		1.获取认证信息
	 * 		2.将家庭情况信息认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authAddressInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setAddressCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 认证学历信息
	 * 		1.获取认证信息
	 * 		2.将学历认证状态置为提交审核状态（'1'）
	 * @param customerBase
	 */
	public void authEducationInfo(CustomerBase customerBase) {
		CustomerCreditAuth customerCreditAuth = customerCreditAuthDao.getByAccountId(customerBase.getAccountId());
		customerCreditAuth.setEducationCreditAuthCode("1");
		save(customerCreditAuth);
	}

	/**
	 * 保存认证信息
	 * 		1.更新认证信息得分总分为各项分数之和
	 * 		2.保存记录：若为新纪录则添加，否则更新
	 * @param customerCreditAuth
	 */
	public void save(CustomerCreditAuth customerCreditAuth) {
		customerCreditAuth.setCreditScore(customerCreditAuth.getTotalScore());
		if(customerCreditAuth.getIsNewRecord()) {
			customerCreditAuthDao.insert(customerCreditAuth);
		} else {
			customerCreditAuthDao.update(customerCreditAuth);
		}
	}
	
}
