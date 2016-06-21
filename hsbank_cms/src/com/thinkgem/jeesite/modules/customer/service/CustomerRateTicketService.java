/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.ext.beans.HashAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerRateTicketDao;
import com.thinkgem.jeesite.modules.entity.CustomerRateTicket;

/**
 * 会员加息券清单Service
 * @author ydt
 * @version 2016-04-05
 */
@Service
@Transactional(readOnly = true)
public class CustomerRateTicketService extends CrudService<CustomerRateTicketDao, CustomerRateTicket> {

	public CustomerRateTicket get(String id) {
		return super.get(id);
	}
	
	public List<CustomerRateTicket> findList(CustomerRateTicket customerRateTicket) {
		return super.findList(customerRateTicket);
	}
	
	public Page<CustomerRateTicket> findPage(Page<CustomerRateTicket> page, CustomerRateTicket customerRateTicket) {
		return super.findPage(page, customerRateTicket);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerRateTicket customerRateTicket) {
		super.save(customerRateTicket);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerRateTicket customerRateTicket) {
		super.delete(customerRateTicket);
	}

	public Page<CustomerRateTicket> getPage(Long accountId, String status, Page<CustomerRateTicket> page) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		map.put("status", status);
		page.setList(dao.findListByMap(map));
		return page;
	}
	
	/**
	 * 获取加息券列表
	 * @param accountId
	 * @param status
	 * @return
	 */
	public List<CustomerRateTicket> getRateTicketList(Long accountId, String status){
		return dao.getListByAccountId(accountId, status);
	}

	/**
	 * 将过期会员加息券状态设置为已过期
	 */
	@Transactional(readOnly = false)
	public void expiredTicket() {
		dao.expiredTicket();
	}

	/**
	 * 统计加息券总利息及张数
	 * @param accountId
	 * @param status
     * @return
     */
	public Map<String,Object> getRateTicketStatistics(Long accountId, String status){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("status", status);
		return dao.getRateTicketStatistics(map);
	}
	
}