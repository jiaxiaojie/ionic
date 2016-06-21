/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBaseDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerWorkDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBaseHandler;
import com.thinkgem.jeesite.modules.customer.handler.CustomerCreditAuthHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerWork;
import com.thinkgem.jeesite.modules.entity.front.CustomerChangeInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员基本信息Service
 * @author ydt
 * @version 2015-06-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerBaseService extends CrudService<CustomerBaseDao, CustomerBase> {

	@Autowired
	private CustomerCreditAuthHandler customerCreditAuthHandler;
	@Autowired
	private CustomerBaseHandler customerBaseHandler;
	@Autowired
	private CustomerWorkDao customerWorkDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	public CustomerBase get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据手机号查询
	 * @param mobile
	 * @return
	 */
	public CustomerBase getByMobile(String mobile){
		return dao.getByMobile(mobile);
	}
	
	public List<CustomerBase> findList(CustomerBase customerBase) {
		return super.findList(customerBase);
	}
	
	public Page<CustomerBase> findPage(Page<CustomerBase> page, CustomerBase customerBase) {
		return super.findPage(page, customerBase);
	}
	
	/**
	 * 保存用户信息
	 */
	@Transactional(readOnly = false)
	public void save(CustomerBase customerBase) {
		customerBaseHandler.save(customerBase);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerBase customerBase) {
		super.delete(customerBase);
	}

	public CustomerBase getByAccountId(Long accountId) {
		return dao.getByAccountId(accountId);
	}

	@Transactional(readOnly = false)
	public void authBaseInfo(CustomerBase customerBase) {
		save(customerBase);
		customerCreditAuthHandler.authBaseInfo(customerBase);
	}

	@Transactional(readOnly = false)
	public void authIdentityInfo(CustomerBase customerBase) {
		save(customerBase);
		customerCreditAuthHandler.authIdentityInfo(customerBase);
	}

	@Transactional(readOnly = false)
	public void authcreditCardInfo(CustomerBase customerBase) {
		save(customerBase);
		customerCreditAuthHandler.authcreditCardInfo(customerBase);
	}

	@Transactional(readOnly = false)
	public void authAddressInfo(CustomerBase customerBase) {
		save(customerBase);
		customerCreditAuthHandler.authAddressInfo(customerBase);
	}

	@Transactional(readOnly = false)
	public void authEducationInfo(CustomerBase customerBase) {
		save(customerBase);
		customerCreditAuthHandler.authEducationInfo(customerBase);
	}

	/**
	 * 检查手机号是否存在
	 * @param mobile
	 * @return
	 */
	public boolean checkMobileExist(String mobile) {
		return dao.getByMobile(mobile) == null ? false : true;
	}
	
	/**
	 * 检查手机号是否可用，若未重复则可以使用
	 * @param customerBase
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean checkMobileCanUse(CustomerBase customerBase) {
		CustomerBase cb = dao.getByMobile(customerBase.getMobile());
		if(cb == null || cb.getAccountId() == customerBase.getAccountId()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查邮箱是否可用，若未重复则可以使用
	 * @param customerBase
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean checkEmailCanUse(CustomerBase customerBase) {
		CustomerBase cb = dao.getByEmail(customerBase.getEmail());
		//如果未使用过或者邮箱属于这个用户则表示可用
		if(cb == null || cb.getAccountId().longValue() == customerBase.getAccountId().longValue()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据accountId更新用户名与身份证信息
	 * @param customerBase
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateNameAndCertNum(CustomerBase customerBase) {
		dao.updateNameAndCertNum(customerBase);
	}

	/**
	 * 根据accountId获取用户姓名
	 * @param accountId
	 * @return
	 */
	public String getCustomerNameByAccountId(Long accountId) {
		return dao.getCustomerNameByAccountId(accountId);
	}

	/**
	 * 检查邮箱是否通过验证
	 * @param email
	 * @return
	 */
	public boolean checkEmailHasAuthed(String email) {
		CustomerBase customerBase = dao.getByEmail(email);
		return (customerBase != null && ProjectConstant.HAS_AUTHED.equals(customerBase.getEmailAuthCode())) ? true : false;
	}

	/**
	 * 根据email得到customerBase信息
	 * @param eamil
	 * @return
	 */
	public CustomerBase getByEmail(String eamil) {
		return dao.getByEmail(eamil);
	}

	/**
	 * 用户修改其信息
	 * 		1.修改customerBase记录
	 * 		2.修改customerWork记录
	 * 			若记录不存在，则添加新纪录
	 * 			否则，更新记录
	 * @param customerAccount 
	 * @param customerChangeInfo
	 */
	@Transactional(readOnly = false)
	public void customerChangeHisInfo(long customerId, CustomerChangeInfo customerChangeInfo) {
		CustomerBase customerBase = new CustomerBase();
		customerBase.setCustomerId(customerId);
		customerBase.setGenderCode(customerChangeInfo.getGenderCode());
		customerBase.setMarriageCode(customerChangeInfo.getMarriageCode());
		customerBase.setAddress(customerChangeInfo.getAddress());
		customerBase.setEducationCode(customerChangeInfo.getEducationCode());
		customerBase.setEducationSchool(customerChangeInfo.getEducationSchool());
		dao.customerChangeHisInfo(customerBase);
		
		CustomerWork customerWork = customerWorkDao.getCustomerWorkByCustomerId(customerId);
		if(customerWork == null) {
			customerWork = new CustomerWork();
			customerWork.setCustomerId(customerId);
			customerWork.setCompanySizeCode(customerChangeInfo.getCompanySizeCode());
			customerWork.setCompanyTypeCode(customerChangeInfo.getCompanyTypeCode());
			customerWork.setPosition(customerChangeInfo.getPosition());
			customerWork.setIncomeCode(customerChangeInfo.getIncomeCode());
			customerWork.setCreateDt(new Date());
			customerWork.setLastModifyDt(new Date());
			customerWorkDao.insert(customerWork);
		}else {
			customerWork.setCustomerId(customerId);
			customerWork.setCompanySizeCode(customerChangeInfo.getCompanySizeCode());
			customerWork.setCompanyTypeCode(customerChangeInfo.getCompanyTypeCode());
			customerWork.setPosition(customerChangeInfo.getPosition());
			customerWork.setIncomeCode(customerChangeInfo.getIncomeCode());
			customerWork.setLastModifyDt(new Date());
			customerWorkDao.customerChangeHisInfo(customerWork);
		}
	}

	/**
	 * 修改用户手机号
	 * @param platformUserNo
	 * @param mobile
	 */
	@Transactional(readOnly = false)
	public void updateWithResetMobileNotify(String platformUserNo, String mobile) {
		customerAccountDao.updateStatusToLockedByMobile(mobile);
		dao.updateMobileByPlatformUserNo(platformUserNo, mobile);
	}
	/**
	 * 手机号查询及导出
	 * @param customerBase
	 * @param customerAccount
	 * @param beginRegisterDt
	 * @param beginOpDt
	 * @param endOpDt
	 * @param endRegisterDt
	 * @param userStatus
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Map<String, Object>> selectCoustmerMoboileNoPage( CustomerBase customerBase,
			CustomerAccount customerAccount, Date beginRegisterDt, Date beginOpDt, Date endOpDt, Date endRegisterDt,
			String userStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginRegisterDt", beginRegisterDt);
		map.put("endRegisterDt", endRegisterDt);
		map.put("userStatus", userStatus);
		map.put("beginOpDt",beginOpDt);
		map.put("endOpDt",endOpDt);
		return 	dao.selectCoustmerMoboile(map);
	}

	public List<CustomerBase> findMobileList(Long accountId) {
		return dao.findMobileList(accountId);
	}


	}
