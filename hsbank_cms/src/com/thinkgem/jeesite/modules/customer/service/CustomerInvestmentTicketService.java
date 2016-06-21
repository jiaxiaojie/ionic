/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;
import com.thinkgem.jeesite.common.utils.ArrayUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.to.MyTicketsResp;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerInvestmentTicketHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.sys.dao.InvestmentTicketTypeDao;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 会员投资券清单Service
 * @author yangtao
 * @version 2015-07-21
 */
@Service
@Transactional(readOnly = true)
public class CustomerInvestmentTicketService extends CrudService<CustomerInvestmentTicketDao, CustomerInvestmentTicket> {
	public static Logger logger = Logger.getLogger(CustomerInvestmentTicketService.class);
	
	@Autowired
	private InvestmentTicketTypeDao investmentTicketTypeDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private CustomerInvestmentTicketDao customerInvestmentTicketDao;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private CustomerInvestmentTicketHandler customerInvestmentTicketHandler;
	
	public CustomerInvestmentTicket get(String id) {
		return super.get(id);
	}
	
	public List<CustomerInvestmentTicket> findList(CustomerInvestmentTicket CustomerInvestmentTicket) {
		return super.findList(CustomerInvestmentTicket);
	}
	
	public Page<CustomerInvestmentTicket> findPage(Page<CustomerInvestmentTicket> page, CustomerInvestmentTicket CustomerInvestmentTicket) {
		return super.findPage(page, CustomerInvestmentTicket);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerInvestmentTicket CustomerInvestmentTicket) {
		super.save(CustomerInvestmentTicket);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerInvestmentTicket CustomerInvestmentTicket) {
		super.delete(CustomerInvestmentTicket);
	}

	public List<CustomerInvestmentTicket> findCanUseListByAccountId(Long accountId) {
		return dao.findCanUseListByAccountId(accountId);
	}

	/**
	 * 获取现金券数量
	 * @param accountId
	 * @param status
     * @return
     */
	public int getCountByAccountId(Long accountId, String status){
		return dao.getCountByAccountId(accountId, status);
	}

	/**
	 * 获取智能返回结果
	 * @param accountId
	 * @param resultList
     * @return
     */
	public List<MyTicketsResp> getIntelligenceTicketList(Long accountId, List<Long> resultList){
		List<MyTicketsResp> dataList = new ArrayList<MyTicketsResp>();
		List<MyTicketsResp> unselectedList = new ArrayList<MyTicketsResp>();
		List<CustomerInvestmentTicket> list = dao.findCanUseListByAccountId(accountId);
        for(CustomerInvestmentTicket ticket : list){
			MyTicketsResp mResp = new MyTicketsResp();
			mResp.setTicketId(NumberUtils.toLong(ticket.getId(), 0L));
			mResp.setType(ticket.getTicketTypeId());
			mResp.setTypeName(ticket.getInvestmentTicketType().getTicketTypeName());
			mResp.setUseInfo(ticket.getInvestmentTicketType().getUseInfo());
			mResp.setGetRemark(ticket.getGetRemark());
			mResp.setUseLimit(ticket.getInvestmentTicketType().getUseLimit());
			mResp.setAmount(ticket.getInvestmentTicketType().getDenomination());
			mResp.setStatus(NumberUtils.toLong(ticket.getStatus(), 0));
			mResp.setStatusName(DictUtils.getDictLabel(ticket.getStatus(), "customer_investment_ticket_dict", ""));
			mResp.setBeginValidDate(DateUtils.formatDate(ticket.getGetDt()));
			mResp.setEndValidDate(DateUtils.formatDate(ticket.getInvalidDt()));
			mResp.setApplyProject("");
			if(resultList.contains(NumberUtil.toLong(ticket.getId(),0L))){
				mResp.setIsSelect(Global.YES);
				dataList.add(mResp);
			}else{
				mResp.setIsSelect(Global.NO);
				unselectedList.add(mResp);
			}
		}
		dataList.addAll(unselectedList);
        return dataList;
	}
	
	/**
	 * 分页查询账户可用优惠卷列表
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public Page<CustomerInvestmentTicket> findCanUsePageByAccountId(Long accountId, String pageNo) {
		Page<CustomerInvestmentTicket> page = new Page<CustomerInvestmentTicket>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(dao.findCanUsePageByAccountId(map));
		return page;
	}
	
	/**
	 * 根据账户及投资金额得到可用优惠劵列表
	 * @param accountId
	 * @param amount
	 * @return
	 */
	public List<CustomerInvestmentTicket> findCanUseListByAccountIdAndAmount(Long accountId, Double amount) {
		//<1>.得到指定账户可用的优惠券列表
		List<CustomerInvestmentTicket> resultValue = dao.findCanUseListByAccountId(accountId);
		//<2>.判断每张优惠券在本次投资中是否可用
		Double maxTicketAmount = LoanUtil.formatAmount(amount * ProjectConfig.getInstance().getTicketAmountRate());
		for (CustomerInvestmentTicket item : resultValue) {
			if (item.getInvestmentTicketType().getDenomination() <= maxTicketAmount) {
				item.setEnabled(true);
			} else {
				item.setEnabled(false);
			}
		}
		return resultValue;
	} 
	
	/**
	 * 获取可以选择的现金券
	 * @param accountId
	 * @param amount
	 * @return
	 */
	public List<CashTicketBean> findChooseListByAccountId(Long accountId, Double amount){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("amount", LoanUtil.formatAmount(amount * ProjectConfig.getInstance().getTicketAmountRate()));
		List<CustomerInvestmentTicket> resultValue = dao.findCanChooseListByAccountId(map);
		List<CashTicketBean> list = new ArrayList<CashTicketBean>();
		for(CustomerInvestmentTicket ticket: resultValue){
			CashTicketBean ticketBean = new CashTicketBean();
			ticketBean.setTicketId(NumberUtil.toLong(ticket.getId(), 0L));
			ticketBean.setDenomination(Double.valueOf(ticket.getInvestmentTicketType().getDenomination()));
			list.add(ticketBean);
		}
		return list;
	}
	
	public Page<CustomerInvestmentTicket> findUsedListByAccountId(Long accountId, String pageNo) {
		Page<CustomerInvestmentTicket> page = new Page<CustomerInvestmentTicket>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(dao.findUsedListByAccountId(map));
		return page;
	}

	public Page<CustomerInvestmentTicket> findExpiredListByAccountId(
			Long accountId, String pageNo) {
		Page<CustomerInvestmentTicket> page = new Page<CustomerInvestmentTicket>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		page.setList(dao.findExpiredListByAccountId(map));
		return page;
	}

	/**
	 * 将到期的投资券自动设置为已过期状态
	 */
	@Transactional(readOnly = false)
	public void autoChangeStatusToExpired() {
		dao.autoChangeStatusToExpired();
	}

	/**
	 * 根据手机号（多个以“;”分隔），赠送200元投资券礼包
	 * @param mobiles
	 */
	@Transactional(readOnly = false)
	public String giveTicketByFirstInvestment(String mobiles) {
		logger.info("------------" + DateUtils.formatDateTime(new Date()) + ": start give first investment gift bag, mobiles:" + mobiles + ".");
		StringBuffer sb = new StringBuffer();
		//两张5元、两张10元、一张20元、一张50元、一张100元投资券
		int[] ticketDenominations = {5, 5, 10, 10, 20, 50, 100};
		String remark = "首次投资赠送200元红包";
		if(mobiles != null) {
			for(String mobile : mobiles.replace("\n", "").split(";")) {
				CustomerAccount customerAccount = customerAccountDao.getByMobile(mobile);
				if(customerAccount == null) {
					logger.info("give to " + mobile + " investment ticket failed because it is not register user.");
					continue;
				}
				long firstInvestmentTicketCount = dao.getTicketCountByAccountIdAndGetRemark(remark, customerAccount.getAccountId());
				//如果已经赠送则不再赠送
				if(firstInvestmentTicketCount > 0) {
					logger.info("give to " + mobile + " investment ticket failed because has gived.");
					continue;
				}
				int investCount = projectInvestmentRecordDao.getInvestCountByAccountId(customerAccount.getAccountId());
				//如果没有成功的投资记录不赠送
				if(investCount <= 0) {
					logger.info("give to " + mobile + " investment ticket failed because he hasn't invested.");
					continue;
				}
				for(int ticketDenomination : ticketDenominations) {
					InvestmentTicketType investmentTicketType = investmentTicketTypeDao.getByDenomination(ticketDenomination);
					CustomerInvestmentTicket customerInvestmentTicket = new CustomerInvestmentTicket();
					customerInvestmentTicket.setAccountId(customerAccount.getAccountId());
					customerInvestmentTicket.setTicketTypeId(new Long(investmentTicketType.getId()));
					customerInvestmentTicket.setGetDt(new Date());
					customerInvestmentTicket.setInvalidDt(DateUtils.dateAddDay(DateUtils.dateFormate(new Date()), investmentTicketType.getTermOfValidity()));
					customerInvestmentTicket.setGetRemark(remark);
					customerInvestmentTicket.setStatus(ProjectConstant.CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL);
					customerInvestmentTicketDao.insert(customerInvestmentTicket);
				}
				sb.append(mobile).append(";");
				logger.info("give to " + mobile + " investment ticket success.");
			}
		}
		logger.info("------------" + DateUtils.formatDateTime(new Date()) + ": give first investment gift bag end.");
		return sb.toString();
	}
	
	/**
	 * 检查是否有可用投资卷
	 * @param accountId
	 * @return
	 */
	public  boolean hasRemindOfTicket(Long accountId){
		if(accountId == null){
			return false;
		}
		List<CustomerInvestmentTicket> ticketList = customerInvestmentTicketDao.findCanUseListByAccountId(accountId);
		if(ticketList != null && ticketList.size() > 0){
			return true;
		}
		return false;
	}

	/**
	 * 根据accountId获得备注获取用户投资券列表
	 * @param accountId
	 * @param getRemark
	 * @return
	 */
	public long getTicketCountByAccountIdAndGetRemark(Long accountId, String getRemark) {
		return dao.getTicketCountByAccountIdAndGetRemark(getRemark, accountId);
	}

	/**
	 * 获取用户可用投资券价值总额
	 * @param accountId
	 * @return
	 */
	public double getCustomerCanUseTicketTotalDenomination(Long accountId) {
		return dao.getCustomerCanUseTicketTotalDenomination(accountId);
	}

	public Map<String, Object> getTicketStatistics(Long accountId, String ticketDictNormal) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("accountId", accountId);
		params.put("ticketDictNormal", ticketDictNormal);

		params.put("currentDate", new Date());
		return customerInvestmentTicketDao.getTicketStatistics(params);
	}
	
	/**
	 * 赠送用户投资券
	 * @param accountId
	 * @param denominations
	 * @param remark
	 */
	@Transactional(readOnly = false)
	public void giveCustomerTickets(long accountId, int[] denominations, String remark) {
		customerInvestmentTicketHandler.giveCustomerTickets(accountId, denominations, remark);
	}

	@Transactional(readOnly = false)
	public void clearTicketByAccountId(String accountId) {
		//HashMap<String, Integer> result = new HashMap<String, Integer>();
		//List<CustomerInvestmentTicket> canUseInvestmentTicketList = customerInvestmentTicketDao.findCanUseListByAccountId(Long.parseLong(accountId));
		
		//获得需要整理的总金额
		Integer sum = customerInvestmentTicketDao.findNeedClearSumByAccountId(accountId);
		
		if(sum != null){
			//将需要整理的券设置为失效
			customerInvestmentTicketDao.setNeedClearInvalidByAccountId(accountId);
			
			Integer[] denominations = new Integer[]{100,50,20,10,5};//这里之后要改成从数据库查询
			ArrayList<Integer> clearDenominations = new ArrayList<Integer>();
			for(Integer denomination : denominations){
				int count = (sum / denomination);
				sum = (sum - (denomination*count) );
				
				for(int i = 0; i < count; i++){
					clearDenominations.add(denomination);
				}
			}
			
			if(sum > 0){
				throw new RuntimeException("您的投资券存在异常面值，导致无法合并投资券。");
			}
			
			//发放整理后的优惠券
			customerInvestmentTicketHandler.giveCustomerTickets(Long.parseLong(accountId),ArrayUtils.integerArrayToIntArray(clearDenominations.toArray(new Integer[]{})) , "现金券合并",10);
		}
		
	}
}