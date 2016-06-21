/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.current.dao.CurrentProjectRedemptionApplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerFreeWithdrawCountHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerFreeWithdrawCountHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员可免费提现次数变更流水Service
 * @author ydt
 * @version 2015-08-15
 */
@Service
@Transactional(readOnly = true)
public class CustomerFreeWithdrawCountHisService extends CrudService<CustomerFreeWithdrawCountHisDao, CustomerFreeWithdrawCountHis> {

	@Autowired
	private CustomerFreeWithdrawCountHisDao customerFreeWithdrawCountHisDao;

	public CustomerFreeWithdrawCountHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerFreeWithdrawCountHis> findList(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis) {
		return super.findList(customerFreeWithdrawCountHis);
	}
	
	public Page<CustomerFreeWithdrawCountHis> findPage(Page<CustomerFreeWithdrawCountHis> page, CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis) {
		return super.findPage(page, customerFreeWithdrawCountHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis) {
		super.save(customerFreeWithdrawCountHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis) {
		super.delete(customerFreeWithdrawCountHis);
	}

	/**
	 * 查询列表
	 * @param map
	 * @return
	 */
	public Page<CustomerFreeWithdrawCountHis> searchList(Long accountId, PageSearchBean pageSearchBean) {
		Page<CustomerFreeWithdrawCountHis> page = new Page<CustomerFreeWithdrawCountHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		page.setOrderBy("");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("startDate", pageSearchBean.getStartDateTime());
		map.put("endDate", pageSearchBean.getEndDateTime());
		map.put("page", page);
		page.setList(dao.searchList(map));
		return page;
	}

	/**
	 * 提现券统计信息
	 * @param accountId
	 * @return
     */
	public Map<String,Object> statistics(Long accountId) {
		return customerFreeWithdrawCountHisDao.statistics(accountId);
	}


	
	
}