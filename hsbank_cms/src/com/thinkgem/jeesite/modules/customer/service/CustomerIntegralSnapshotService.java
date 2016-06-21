/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.constant.DatetimeField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralHisDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralSnapshotDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralSnapshot;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员花生豆汇总Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerIntegralSnapshotService extends CrudService<CustomerIntegralSnapshotDao, CustomerIntegralSnapshot> {

	@Autowired
	private CustomerIntegralHisDao customerIntegralHisDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	
	public CustomerIntegralSnapshot get(String id) {
		return super.get(id);
	}
	
	public List<CustomerIntegralSnapshot> findList(CustomerIntegralSnapshot customerIntegralSnapshot) {
		return super.findList(customerIntegralSnapshot);
	}
	
	public Page<CustomerIntegralSnapshot> findPage(Page<CustomerIntegralSnapshot> page, CustomerIntegralSnapshot customerIntegralSnapshot) {
		return super.findPage(page, customerIntegralSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerIntegralSnapshot customerIntegralSnapshot) {
		super.save(customerIntegralSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerIntegralSnapshot customerIntegralSnapshot) {
		super.delete(customerIntegralSnapshot);
	}

	public CustomerIntegralSnapshot getByAccountId(Long accountId) {
		return dao.getByAccountId(accountId);
	}

	/**
	 * 签到操作
	 * 		1.修改customerIntegralSnapshot表
	 * 		2.新增customerIntegralHis表签到记录
	 * @param accountId
	 */
	@Transactional(readOnly = false)
	public Map<String,Object> sign(Long accountId, String opTerm) {
		if(!canSign(accountId)) {
			throw new ServiceException("sign failed, today signed.");
		}
		int consecutiveSignDays = 0;
		int signIntegral = ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_VALUE_SIGN_FIRST;
		/*CustomerIntegralSnapshot snapshot = getByAccountId(accountId); //暂时注释掉等待移动端
		//如果昨天签到
		if(yesterdayIsSign(accountId)){
			consecutiveSignDays = snapshot.getConsecutiveDays() !=null ? snapshot.getConsecutiveDays() : 0;
		}
		consecutiveSignDays += 1;
		logger.info("the accountId " + accountId + " sign consecutiveDays:" + consecutiveSignDays);
		signIntegral = signIntegral * consecutiveSignDays;
		signIntegral = signIntegral > ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_VALUE_SIGN_MAX ? ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_VALUE_SIGN_MAX : signIntegral;
		logger.info("the accountId " + accountId + " sign giveIntegral:" + signIntegral);
		//签到送花生豆
		String changeReason = "连续第" + consecutiveSignDays + "天签到";
		//签到送现金券
		this.signGiveCustomerTickets(accountId, consecutiveSignDays);*/
		String changeReason = "签到";
		customerIntegralSnapshotHandler.customerSign(accountId, signIntegral,consecutiveSignDays, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_SIGN, changeReason, opTerm);

        Map<String,Object> map = new HashMap<String,Object>();
		map.put("signIntegral",signIntegral);
		map.put("consecutiveSignDays",consecutiveSignDays);
		return map;
	}

	/**
	 *连续签到赠送现金券
	 * @param accountId
	 * @param consecutiveSignDays
     */
	public void signGiveCustomerTickets(Long accountId, int consecutiveSignDays){
		boolean isGiveTickets = false;
		int[] denominations = new int[0];
		String remark = "连续签到赠送";
		//除以30天的余数
		int day_30_remainder = consecutiveSignDays % ProjectConstant.CUSTOMER_CONSECUTIVE_SIGN_30_DAYS;
		int day_20_remainder = day_30_remainder % ProjectConstant.CUSTOMER_CONSECUTIVE_SIGN_20_DAYS;
		//连续签到第20天
		if(day_30_remainder > 0 && day_20_remainder ==0){
			isGiveTickets = true;
			denominations = ProjectConstant.CUSTOMER_CONSECUTIVE_SIGN_20_DAY_GIVE_INVESTMENT_TICKET;
		}else if(day_30_remainder ==0){
			isGiveTickets = true;
			denominations = ProjectConstant.CUSTOMER_CONSECUTIVE_SIGN_30_DAY_GIVE_INVESTMENT_TICKET;
		}
		if(isGiveTickets){
			logger.info("the accountId " + accountId + " giveTickets denominations:" + denominations[0]);
			customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
		}
	}

	/**
	 * 赠送积分
	 * 		1.修改customerIntegralSnapshot表
	 * 		2.新增customerIntegralHis表记录
	 * @param accountId
	 */
	@Transactional(readOnly = false)
	public void giveIntegral(Long accountId, int changeValue, String changeType, String changeReason, String opTerm) {
		customerIntegralSnapshotHandler.changeIntegralValue(accountId, changeValue, changeType, changeReason, opTerm);
	}

	/**
	 * 检查用户今日是否可进行签到操作，若已签到过则返回false，否则返回true
	 * @param accountId
	 * @return
	 */
	public boolean canSign(Long accountId) {
		if(accountId == null) {
			return false;
		}
		return !customerIntegralHisDao.hasSigned(accountId, DateUtils.dateFormate(new Date()), new Date());
	}

	/**
	 * 检测昨天是否签到，若已签到则返回true，否则返回false
	 * @param accountId
	 * @return
     */
	public boolean yesterdayIsSign(Long accountId){
		if(accountId == null){
			return false;
		}
		return customerIntegralHisDao.hasSigned(accountId, DateUtils.dateFormateDayOfTheBeginTime(DatetimeUtil.getDate(new Date(), DatetimeField.DAY, -1)), DateUtils.dateFormateDayOfTheLastTime(DatetimeUtil.getDate(new Date(), DatetimeField.DAY, -1)));
	}
}