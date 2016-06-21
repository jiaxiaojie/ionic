package com.thinkgem.jeesite.modules.customer.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.dao.InvestmentTicketTypeDao;

/**
 * 
 * @author ydt
 *
 */
@Component
public class CustomerInvestmentTicketHandler {
	@Autowired
	private CustomerInvestmentTicketDao customerInvestmentTicketDao;
	@Autowired
	private InvestmentTicketTypeDao investmentTicketTypeDao;
	
	/**
	 * 赠送用户投资券
	 * @param accountId
	 * @param denominations
	 * @param remark
	 */
	public void giveCustomerTickets(long accountId, int[] denominations, String remark) {
		giveCustomerTickets( accountId,  denominations,  remark, null);
	}
	
	
	public void giveCustomerTickets(long accountId, int[] denominations, String remark,Integer termOfValidity) {
		for(int denomination : denominations) {
			InvestmentTicketType investmentTicketType = investmentTicketTypeDao.getByDenomination(denomination);
			if(investmentTicketType == null) {
				throw new ServiceException("can not find investmentTicketType by denomination:" + denomination);
			}
			CustomerInvestmentTicket customerInvestmentTicket = new CustomerInvestmentTicket();
			customerInvestmentTicket.setAccountId(accountId);
			customerInvestmentTicket.setTicketTypeId(Long.parseLong(investmentTicketType.getId()));
			customerInvestmentTicket.setGetDt(new Date());
			customerInvestmentTicket.setGetRemark(remark);
			
			Integer resultTermOfValidity =  ( termOfValidity==null?investmentTicketType.getTermOfValidity():termOfValidity );
			
			customerInvestmentTicket.setInvalidDt(DateUtils.dateAddDay(DateUtils.dateFormate(new Date()), resultTermOfValidity));
			customerInvestmentTicket.setStatus(ProjectConstant.CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL);
			customerInvestmentTicketDao.insert(customerInvestmentTicket);
		}
	}
}
