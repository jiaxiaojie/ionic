package com.thinkgem.jeesite.modules.project.service.util.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerInvestmentTicketDao;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.StringUtil;

/**
 * 投资券处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("ticketHandler")
public class TicketHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerInvestmentTicketDao customerInvestmentTicketDao;
	
	/**
	 * 参数校验
	 * ------------------------------
	 * 对每一张券都要做如下检查
	 * a.券是不是自己的
	 * b.券状态是不是可用状态
	 * c.投资卷金额  <= 本次投资最大用卷金额
	 * 任何一条没有满足，都要抛出异常，终止操作
	 * ------------------------------
	 * @param accountId 		用户账户Id
	 * @param ticketIds 		投资券的Id，多张券以","分割
	 * @return
	 */
	public void check(String isNewUser, Long accountId, String ticketIds, Double amount, Double ticketAmount) {
		ticketIds = StringUtil.dealString(ticketIds);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":check ticketIds:" + ticketIds + " start...");
		if (!"".equals(ticketIds)) {
			logger.debug("Have chosen ticketIds:" + ticketIds);
			//判断是否新手项目
			if("0".equals(isNewUser)){
				throw new ServiceException("新手项目不能用券！");
			}
			//得到当前账户可用优惠券列表
			List<CustomerInvestmentTicket> ticketList = customerInvestmentTicketDao.findCanUseListByAccountId(accountId);
			logger.debug("CanUseTicketList size:" + ticketList != null ? ticketList.size() : 0);
			Map<String, Object> ticketIdMap = new HashMap<String, Object>();
			Map<String, Object> selectedMap  = new HashMap<String, Object>();
			for (CustomerInvestmentTicket item : ticketList) {
				ticketIdMap.put(item.getId(), item.getId());
				selectedMap.put(item.getId(), item);
			}
			List<CustomerInvestmentTicket> selectedList = new ArrayList<CustomerInvestmentTicket>();
			String[] ticketArray = ticketIds.split(",");
			for (String ticketId : ticketArray) {
				//判断指定优惠券是否属于该账户
				if(!ticketIdMap.containsKey(ticketId)){
					throw new ServiceException("此现金劵【" + ticketId + "】不属于账户【" + accountId + "】");
				}else{
					selectedList.add((CustomerInvestmentTicket)selectedMap.get(ticketId));
				}
			}
			//本次投资最大用券金额
			Double maxTicketAmount = amount * ProjectConfig.getInstance().getTicketAmountRate();
			Double totalDenomination = 0.0;
			for(CustomerInvestmentTicket selectedTicket : selectedList){
				totalDenomination += selectedTicket.getInvestmentTicketType().getDenomination().doubleValue();
			}
			if(totalDenomination.compareTo(LoanUtil.formatAmount(maxTicketAmount)) > 0){
				throw new ServiceException("选择现金券金额【" + totalDenomination + "】大于本次投资最大用券金额【" + maxTicketAmount + "】");
			}
			if(ticketAmount !=null){
				if(ticketAmount.compareTo(totalDenomination) !=0){
					throw new ServiceException("用券金额【" + ticketAmount + "】不等于选券金额【" + totalDenomination + "】");
				}
			}
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":check ticketIds:" + ticketIds + " end...");
	}
	
	/**
	 * 数据入库：更新相应券的状态为已使用
	 * ------------------------------
	 * @param accountId 		用户账户Id
	 * @param ticketIds 		投资券的Id，多张券以","分割
	 * @return
	 */
	public void updateStatus(Long accountId, String ticketIds) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":update ticketIds:" + ticketIds + " start...");
		ticketIds = StringUtil.dealString(ticketIds);
		String[] ticketArray = ticketIds.split(",");
		for (String ticketId : ticketArray) {
			logger.debug("the ticketId:"+ ticketId);
			//更新相应券的状态为已使用
			customerInvestmentTicketDao.updateStatus(ticketId,ProjectConstant.TICKET_DICT_USED, new Date());
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":update ticketIds:" + ticketIds + " end...");
	}
}
