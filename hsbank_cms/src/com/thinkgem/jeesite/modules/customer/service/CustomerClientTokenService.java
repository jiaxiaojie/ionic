/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.customer.dao.CustomerClientTokenDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerPushSwitchDao;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.CustomerPushSwitch;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;

/**
 * 客户端缓存信息Service
 * @author lzb
 * @version 2015-10-14
 */
@Service
@Transactional(readOnly = true)
public class CustomerClientTokenService extends CrudService<CustomerClientTokenDao, CustomerClientToken> {
	@Autowired
	private CustomerClientTokenDao customerClientTokenDao;
	@Autowired
	private CustomerPushSwitchDao customerPushSwitchDao;

	public CustomerClientToken get(String id) {
		return super.get(id);
	}
	
	public List<CustomerClientToken> findList(CustomerClientToken customerClientToken) {
		return super.findList(customerClientToken);
	}
	
	public Page<CustomerClientToken> findPage(Page<CustomerClientToken> page, CustomerClientToken customerClientToken) {
		return super.findPage(page, customerClientToken);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerClientToken customerClientToken) {
		super.save(customerClientToken);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerClientToken customerClientToken) {
		super.delete(customerClientToken);
	}
	
	/**
	 * 更新
	 * @param customerClientToken
	 */
	@Transactional(readOnly = false)
	public int update(CustomerClientToken customerClientToken) {
		return customerClientTokenDao.update(customerClientToken);
	}
	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	public CustomerClientToken getByToken(String token) {
		return customerClientTokenDao.getByToken(token);
	}
	
	/**
	 * 登录操作缓存信息
	 * @param customerClientToken
	 */
	@Transactional(readOnly = false)
	public String operaCustomerClientToken(CustomerClientToken customerClientToken, String opTerm) {
		CustomerClientToken customerClient = CustomerClientUtils.getByCustomerId(customerClientToken.getCustomerId(), customerClientToken.getTermType());
		if(customerClient == null ){
			customerClient = customerClientTokenDao.getClientTokenByCustomerIdAndTermType(String.valueOf(customerClientToken.getCustomerId()),customerClientToken.getTermType());
		}
		if(customerClient == null ){
			customerClientTokenDao.insert(customerClientToken);
			CustomerClientUtils.addCache(customerClientToken);
		}else{
			customerClient.setLastDt(new Date());
			customerClientTokenDao.update(customerClient);
			CustomerClientUtils.refreshCache(customerClient);
			customerClientToken.setToken(customerClient.getToken());
		}
		//初始化push消息开关
		initPushSwitch(customerClientToken.getCustomerId(), opTerm);
		String token = customerClientToken.getToken();
		return token;
	}
	/**
	 * 登录操作缓存信息Pc端
	 * @param customerClientToken
	 */
	@Transactional(readOnly = false)
	public String operaCustomerClientTokenByPc(CustomerClientToken customerClientToken, String opTerm) {
		CustomerClientToken customerClient = CustomerClientUtils.getByCustomerId(customerClientToken.getCustomerId(), customerClientToken.getTermType());
		if(customerClient == null ){
			customerClient = customerClientTokenDao.getClientTokenByCustomerIdAndTermType(String.valueOf(customerClientToken.getCustomerId()),customerClientToken.getTermType());
		}
		if(customerClient == null ){
			customerClientTokenDao.insert(customerClientToken);
			CustomerClientUtils.addCache(customerClientToken);
		}else{
			customerClient.setLastDt(new Date());
			customerClientTokenDao.update(customerClient);
			CustomerClientUtils.refreshCache(customerClient);
			customerClientToken.setToken(customerClient.getToken());
		}
		return customerClientToken.getToken();
	}
	
	/**
	 * 初始化push消息开关
	 * @param accountId
	 * @param pushChannel
	 */
	public void initPushSwitch(long accountId, String opTerm) {
		if (ProjectConstant.OP_TERM_DICT_ANDROID.equals(opTerm)
				|| ProjectConstant.OP_TERM_DICT_IOS.equals(opTerm)) {
			String pushChannel = ApiUtil.getPushChannel(opTerm);
			CustomerPushSwitch pushSwitch = customerPushSwitchDao
					.getCustomerPushSwitch(accountId, pushChannel);
			if (pushSwitch == null) {
				logger.info("the accountId:"+accountId+" initPushSwitch...");
				CustomerPushSwitch customerPushSwitch = new CustomerPushSwitch();
				customerPushSwitch.setAccountId(accountId);
				customerPushSwitch.setPushChannel(pushChannel);
				customerPushSwitch.setIsReceive(ApiConstant.PUSH_SWITCH_YES);
				customerPushSwitchDao.insert(customerPushSwitch);
			}
		}
	}
	
	
	
}