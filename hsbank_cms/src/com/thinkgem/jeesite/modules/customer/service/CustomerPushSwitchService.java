/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.customer.dao.CustomerPushSwitchDao;
import com.thinkgem.jeesite.modules.entity.CustomerPushSwitch;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 用户接收push消息开关Service
 * @author lzb
 * @version 2016-02-25
 */
@Service
@Transactional(readOnly = true)
public class CustomerPushSwitchService extends CrudService<CustomerPushSwitchDao, CustomerPushSwitch> {

	public CustomerPushSwitch get(String id) {
		return super.get(id);
	}
	
	public List<CustomerPushSwitch> findList(CustomerPushSwitch customerPushSwitch) {
		return super.findList(customerPushSwitch);
	}
	
	public Page<CustomerPushSwitch> findPage(Page<CustomerPushSwitch> page, CustomerPushSwitch customerPushSwitch) {
		return super.findPage(page, customerPushSwitch);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerPushSwitch customerPushSwitch) {
		super.save(customerPushSwitch);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerPushSwitch customerPushSwitch) {
		super.delete(customerPushSwitch);
	}
	
	/**
	 * 根据账号、渠道获取push消息开关
	 * @param accountId
	 * @param pushChannel
	 * @return
	 */
	public CustomerPushSwitch getCustomerPushSwitch(long accountId, String pushChannel) {
		return dao.getCustomerPushSwitch(accountId, pushChannel);
	}
	
	/**
	 * 更新是否接受push
	 * @param customerPushSwitch
	 */
	@Transactional(readOnly = false)
	public void updateIsReceive(long accountId, String opTerm,
			String isReceive) {
		if (ProjectConstant.OP_TERM_DICT_ANDROID.equals(opTerm)
				|| ProjectConstant.OP_TERM_DICT_IOS.equals(opTerm)) {
			String pushChannel = ApiUtil.getPushChannel(opTerm);
			CustomerPushSwitch customerPushSwitch = dao.getCustomerPushSwitch(
					accountId, pushChannel);
			if (customerPushSwitch != null) {
				dao.updateIsReceive(accountId, pushChannel, isReceive);
			} else {
				customerPushSwitch = new CustomerPushSwitch();
				customerPushSwitch.setAccountId(accountId);
				customerPushSwitch.setPushChannel(pushChannel);
				customerPushSwitch.setIsReceive(isReceive);
				dao.insert(customerPushSwitch);
			}
		}
	}
	

	
	
}