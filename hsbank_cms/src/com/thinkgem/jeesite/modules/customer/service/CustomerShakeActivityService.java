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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerShakeActivityDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.entity.CustomerShakeActivity;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.dao.InvestmentTicketTypeDao;

/**
 * 摇一摇活动Service
 * @author ydt
 * @version 2015-09-10
 */
@Service
@Transactional(readOnly = true)
public class CustomerShakeActivityService extends CrudService<CustomerShakeActivityDao, CustomerShakeActivity> {
	@Autowired
	private InvestmentTicketTypeDao investmentTicketTypeDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private CustomerInvestmentTicketDao customerInvestmentTicketDao;

	public CustomerShakeActivity get(String id) {
		return super.get(id);
	}
	
	public List<CustomerShakeActivity> findList(CustomerShakeActivity customerShakeActivity) {
		return super.findList(customerShakeActivity);
	}
	
	public Page<CustomerShakeActivity> findPage(Page<CustomerShakeActivity> page, CustomerShakeActivity customerShakeActivity) {
		return super.findPage(page, customerShakeActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerShakeActivity customerShakeActivity) {
		super.save(customerShakeActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerShakeActivity customerShakeActivity) {
		super.delete(customerShakeActivity);
	}
	public Page<CustomerShakeActivity> findSuperPage(Page<CustomerShakeActivity> page, CustomerShakeActivity customerShakeActivity) {
		page.setList(dao.findSuperList());
		return page;
	}
	/**
	 * 根据摇一摇活动赠送投资券
	 * 		1.添加customerInvestmentTicket表新纪录
	 * 		2.更新customerShakeActivity表hasGived状态为已赠送（"1"）
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public String give(long id) {
		String message = "";
		String remark = "微信摇一摇";
		CustomerShakeActivity customerShakeActivity = dao.get(id + "");
		if("1".equals(customerShakeActivity.getHasGived())) {
			message = "赠送投资券失败，投资券已赠送不可再次赠送。";
			return message;
		}
		String mobile = customerShakeActivity.getMobile();
		InvestmentTicketType investmentTicketType = investmentTicketTypeDao.getByDenomination(customerShakeActivity.getDenomination());
		if(investmentTicketType == null) {
			message = "赠送投资券失败，投资券面额错误。";
			return message;
		}
		CustomerAccount customerAccount = customerAccountDao.getByMobile(mobile);
		if(customerAccount == null) {
			message = "赠送投资券失败，手机号未注册。";
			return message;
		}
		
		CustomerInvestmentTicket customerInvestmentTicket = new CustomerInvestmentTicket();
		customerInvestmentTicket.setAccountId(customerAccount.getAccountId());
		customerInvestmentTicket.setTicketTypeId(new Long(investmentTicketType.getId()));
		customerInvestmentTicket.setGetDt(customerShakeActivity.getShakeDate());
		customerInvestmentTicket.setInvalidDt(DateUtils.dateAddDay(DateUtils.dateFormate(customerShakeActivity.getShakeDate()), investmentTicketType.getTermOfValidity()));
		customerInvestmentTicket.setGetRemark(remark);
		customerInvestmentTicket.setStatus(ProjectConstant.CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL);
		customerInvestmentTicketDao.insert(customerInvestmentTicket);
		
		customerShakeActivity.setHasGived("1");
		save(customerShakeActivity);
		
		message = "赠送投资券成功。";
		return message;
	}
	
}