package com.thinkgem.jeesite.modules.project.service.util.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.customer.dao.CustomerRateTicketDao;
import com.thinkgem.jeesite.modules.entity.CustomerRateTicket;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 加息券处理
 * <p/>
 * @author lzb
 * CreateDate 2016-04-05
 */
@Component("rateTicketHandler")
public class RateTicketHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerRateTicketDao customerRateTicketDao;
	
	/**
	 * 加息券校验
	 * @param canUseRateTicket
	 * @param accountId
	 * @param rateTicketIds
	 */
	public void check(String canUseRateTicket, Long accountId, String rateTicketIds){
		if(StringUtils.isNotBlank(rateTicketIds)){
			//判断项目是否可用加息券
			if(ProjectConstant.DICT_DEFAULT_VALUE.equals(canUseRateTicket)){
				throw new ServiceException("此项目不能使用加息券！");
			}
			//当前账户可使用加息券列表
			List<CustomerRateTicket> raList = customerRateTicketDao.getListByAccountId(accountId, ProjectConstant.TICKET_DICT_NORMAL);
			Map<String,Object> rateTicketMap = new HashMap<String,Object>();
			for(CustomerRateTicket rateTicket : raList){
				rateTicketMap.put(rateTicket.getId(), rateTicket.getId());
			}
			String[] ticketArray = rateTicketIds.split(",");
			for(String ticketId : ticketArray){
				//判断指定优惠券是否属于该账户
				if(!rateTicketMap.containsKey(ticketId)){
					throw new ServiceException("此加息券【" + ticketId + "】不属于账户【" + accountId + "】");
				}
			}
		}
	}
	
	/**
	 * 组装加息券列表
	 * @param rateTicketIds
	 * @return
	 */
	public List<IncreaseInterestItem> getInterestItems(String rateTicketIds){
		List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
		if(StringUtils.isNotBlank(rateTicketIds)){
			List<CustomerRateTicket> rateTicketList = customerRateTicketDao.getListByRateTicketIds(rateTicketIds);
			for(CustomerRateTicket rateTicket: rateTicketList){
				IncreaseInterestItem item  = new IncreaseInterestItem();
				item.setInterestId(rateTicket.getId());
				item.setInterestDuration(rateTicket.getRateTicketType().getRateDuration());
				item.setInterestRate(rateTicket.getRateTicketType().getRate());
				item.setMaxAmount(rateTicket.getRateTicketType().getMaxAmount());
				interestItems.add(item);
			}
		}
		return interestItems;
	}
	
	/**
	 * 更新新加息券信息
	 * @param rateTicketIds
	 * @param status
	 * @param useRemark
	 * @param useProjectId
	 * @param useDt
	 */
	public void updateRateTicket(String rateTicketIds,String status, String useRemark, Long useProjectId, Date useDt){
		if(StringUtils.isNotBlank(rateTicketIds)){
			String[] ticketArray = rateTicketIds.split(",");
			for(String rateTicket : ticketArray){
				logger.debug("the rateTicket id:"+ rateTicket);
				CustomerRateTicket customerRateTicket = customerRateTicketDao.get(rateTicket);
				customerRateTicket = customerRateTicket != null ? customerRateTicket : new CustomerRateTicket();
				customerRateTicket.setStatus(status);
				customerRateTicket.setUseRemark(useRemark);
				customerRateTicket.setUseProjectId(useProjectId);
				customerRateTicket.setUseDt(useDt);
				customerRateTicketDao.update(customerRateTicket);
			}
		}
	}
}
