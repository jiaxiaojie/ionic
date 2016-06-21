package com.thinkgem.jeesite.common.ticket.intelligent_ticket;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;

/**
 * 智能选券
 * @author lzb
 * CreateDate 2016-04-25
 */
public interface CashTicketService {

	/**
	 * 获取最佳组合现金券ids
	 * @param list
	 * @param amount
     * @return
     */
	public List<Long> getBestTicket(List<CashTicketBean> list, Double amount);

	/**
	 * 获取最佳组合现金券map
	 * @param list
	 * @param amount
     * @return
     */
	public Map<String, Object> getBestTicketMap(List<CashTicketBean> list, Double amount);

}
