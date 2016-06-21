package com.thinkgem.jeesite.common.ticket.util;

import java.util.*;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.project.ProjectConfig;

/**
 * 现金券工具类
 * 2016-04-28
 *
 */
public class TicketUtils {

	/**
	 * 选择组合现金券
	 * @param parval
	 * @param tickets
	 * @return
	 */
	public static LinkedList<Long> chooseGroupTickets(Double parval, LinkedHashMap<Long,Double> tickets){
		LinkedHashMap<Long, Double> selectedTickets = groupTickets( parval, tickets,true);
		return getKeys(selectedTickets);
	}

	/**
	 * 选择组合现金券map
	 * @param parval
	 * @param tickets
     * @return
     */
	public static Map<String, Object> chooseGroupTicketsMap(Double parval, LinkedHashMap<Long,Double> tickets){
		LinkedHashMap<Long, Double> selectedTickets = groupTickets( parval, tickets,true);
		return getKeysValue(selectedTickets);
	}
	
	/**
	 * 组合现金券
	 * @param parval
	 * @param tickets
	 * @param isRollbackSelect
	 * @return
	 */
	private static LinkedHashMap<Long, Double> groupTickets(Double parval, LinkedHashMap<Long,Double> tickets,Boolean isRollbackSelect){
		//选择的券
		LinkedHashMap<Long, Double> resultTickets = new LinkedHashMap<Long, Double>();
		//当前剩余待选面值
		Double currentResidueParval = parval;
		for(Long key : tickets.keySet()){//循环待选券
			Double ticket = tickets.get(key);
			//选择当前待选券后剩余待选的面值
			Double selectedResidueParval = currentResidueParval-ticket;
			//如果当前选择的券面值小于当前待选面值
			if(selectedResidueParval >= 0){
				resultTickets.put(key,ticket);
				currentResidueParval -= ticket;
				if(currentResidueParval == 0){
					break;
				}
			}
			else if(isRollbackSelect){
				Double tempParval = parval-ticket;
				if(tempParval >= 0){
					LinkedHashMap<Long, Double> tempResultTickets = groupTickets(tempParval,resultTickets, false);
					if(tempResultTickets != null){
						tempResultTickets.put(key,ticket);
						currentResidueParval = 0d;
						resultTickets = tempResultTickets;
						break;
					}
				}
			}
			
		}
		if(currentResidueParval != 0){
			resultTickets = null;
		}
		return resultTickets;
	}
	
	/**
	 * 现金券bean to Map
	 * @param cashTicketBeans
	 * @return
	 */
	public static LinkedHashMap<Long,Double> cashTicketBeanToMap(List<CashTicketBean> cashTicketBeans){
		LinkedHashMap<Long,Double> result = new LinkedHashMap<Long,Double>();
		for(CashTicketBean cashTicketBean : cashTicketBeans){
			result.put(cashTicketBean.getTicketId(), cashTicketBean.getDenomination());
		}
		return result;
	}
	
	/**
	 * 组合list tickets
	 * @param resultTickets
	 * @return
	 */
	private static LinkedList<Long> getKeys(LinkedHashMap<Long, Double> resultTickets){
		LinkedList<Long> tickets = new LinkedList<Long>();
		
		if(resultTickets != null){
			for(Long key : resultTickets.keySet()){
				tickets.add(key);
			}
		}
		
		return tickets;
	}


	/**
	 * 组合现金券ids、amount
	 * @param resultTickets
	 * @return
     */
	private static  Map<String, Object> getKeysValue(LinkedHashMap<Long, Double> resultTickets){
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedList<Long> tickets = new LinkedList<Long>();
		Double amount = 0.0;
		if(resultTickets != null){
			for(Long key : resultTickets.keySet()){
				tickets.add(key);
				amount +=resultTickets.get(key);
			}
		}
		map.put("ticketIds",tickets);
		map.put("amount",amount);
		return map;
	}

	/**
	 * 本次投资折合现金券对应金额
	 * @param amount
	 * @return
     */
	public static Double getUsableAmount(Double amount){
        //本次投资可用券金额
		amount = NumberUtils.formatNotRoundWithScale(amount * ProjectConfig.getInstance().getTicketAmountRate(),0);
		//折合现金券对应金额
		amount = NumberUtils.formatNotRoundWithScale((amount - amount % 5),0);
		return amount;
	}

}
