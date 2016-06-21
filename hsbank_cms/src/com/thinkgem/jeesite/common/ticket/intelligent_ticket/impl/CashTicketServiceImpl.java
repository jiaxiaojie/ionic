package com.thinkgem.jeesite.common.ticket.intelligent_ticket.impl;

import java.util.*;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.ticket.intelligent_ticket.CashTicketService;
import com.thinkgem.jeesite.common.ticket.util.TicketConstant;
import com.thinkgem.jeesite.common.ticket.util.TicketUtils;
import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.hsbank.util.type.NumberUtil;
import org.apache.commons.collections.map.HashedMap;

/**
 * 智能选券
 * <p/>
 * 选券原则（一）时间优先，金额次之 将可用券按照失效期优先，金额从大到小的顺序排列；相同失效日期下，金额较大的优先排列；
 * <p/>
 * 选券原则（二）金额优先，时间次之 将可用券按照金额优先，失效期递增的顺序排列；相同金额下，失效早的券优先排列；
 * <p/>
 * @author lzb
 * 2016-04-25
 */
public class CashTicketServiceImpl implements CashTicketService {
	private static boolean is_go = true;

	@Override
	public List<Long> getBestTicket(List<CashTicketBean> list, Double amount) {
		List<Long> bestTickets = new LinkedList<Long>();
		//本次投资折合现金券对应金额
		amount = TicketUtils.getUsableAmount(amount);
		//是否选券 = 现金券列表 > 0  && (折合现金券额度 >= 最小现金券面额) ? 选券 : 不选券
		if(list != null && list.size() > 0 && amount.compareTo(TicketConstant.INTELLIGENT_MIN_TICKET_AMOUNT) >= 0){
			Double sumTicketAmount = sumTicketAmount(list);
			//选券金额 >= 可用现金券总额 ? 全券返回  : 组合最佳选券
			bestTickets = amount.compareTo(sumTicketAmount) >= 0 ? getTicketIds(list)
					: TicketUtils.chooseGroupTickets(amount,TicketUtils.cashTicketBeanToMap(list));
			//如果，根据当前可用券额度，没有选到组合券  ? 临近原则选券 : 返回组合券
			bestTickets = nearGroupTicket(list, amount, bestTickets);
		}
		return bestTickets;
	}




	@Override
	public Map<String, Object> getBestTicketMap(List<CashTicketBean> list, Double amount) {
		Map<String, Object> bestTicketsMap = new HashMap<String, Object>();
		//本次投资折合现金券对应金额
		amount = TicketUtils.getUsableAmount(amount);
		//是否选券 = 现金券列表 > 0  && (折合现金券额度 >= 最小现金券面额) ? 选券 : 不选券
		if(list != null && list.size() > 0 && amount.compareTo(TicketConstant.INTELLIGENT_MIN_TICKET_AMOUNT) >= 0){
			Double sumTicketAmount = sumTicketAmount(list);
			//选券金额 >= 可用现金券总额 ? 全券返回  : 组合最佳选券
			bestTicketsMap = amount.compareTo(sumTicketAmount) >= 0 ? getCashTicketMap(list)
					: TicketUtils.chooseGroupTicketsMap(amount,TicketUtils.cashTicketBeanToMap(list));
			//如果，根据当前可用券额度，没有选到组合券  ? 临近原则选券 : 返回组合券
			bestTicketsMap = nearGroupTicketMap(list, amount, bestTicketsMap);
		}
		return bestTicketsMap;
	}


	/**
	 * 临近原则选券（根据可用券额度）
	 * @param list
	 * @param bestTickets
	 * @return
	 */
	public static List<Long> nearGroupTicket(List<CashTicketBean> list, Double temAmount,List<Long> bestTickets){
		if(bestTickets.size() <= 0){
			temAmount = temAmount - TicketConstant.INTELLIGENT_MIN_TICKET_AMOUNT;
			if(temAmount.compareTo(TicketConstant.INTELLIGENT_MIN_TICKET_AMOUNT) < 0){
				return new ArrayList<Long>();
			}
			bestTickets =TicketUtils.chooseGroupTickets(temAmount,TicketUtils.cashTicketBeanToMap(list));
			bestTickets = bestTickets.size() <= 0 ? nearGroupTicket(list, temAmount, bestTickets) : bestTickets;
		}
		return bestTickets;
	}

	/**
	 * 临近原则选券map（根据可用券额度）
	 * @param list
	 * @param temAmount
	 * @param bestTicketsMap
     * @return
     */
	public static Map<String, Object> nearGroupTicketMap(List<CashTicketBean> list, Double temAmount,Map<String, Object> bestTicketsMap){
		if(bestTicketsMap.size() <= 0){
			temAmount = temAmount - TicketConstant.INTELLIGENT_MIN_TICKET_AMOUNT;
			if(temAmount.compareTo(TicketConstant.INTELLIGENT_MIN_TICKET_AMOUNT) < 0){
				return new HashMap<String, Object>();
			}
			bestTicketsMap =TicketUtils.chooseGroupTicketsMap(temAmount,TicketUtils.cashTicketBeanToMap(list));
			bestTicketsMap = bestTicketsMap.size() <= 0 ? nearGroupTicketMap(list, temAmount, bestTicketsMap) : bestTicketsMap;
		}
		return bestTicketsMap;
	}

	/**
	 * 获取可用现金券ids
	 * @param list
	 * @return
	 */
	public List<Long> getTicketIds(List<CashTicketBean> list){
		LinkedList<Long> ticketList = new LinkedList<Long>();
		for(int i=0 ;i < list.size(); i++){
			ticketList.add(list.get(i).getTicketId());
		}
		return ticketList;
	}

	/**
	 * 获取可用现金券map
	 * @param list
	 * @return
     */
	public Map<String, Object> getCashTicketMap(List<CashTicketBean> list){
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedList<Long> ticketList = new LinkedList<Long>();
		Double amount = 0.0;
		for(int i=0 ;i < list.size(); i++){
			ticketList.add(list.get(i).getTicketId());
			amount +=list.get(i).getDenomination();
		}
		map.put("ticketIds",ticketList);
		map.put("amount",amount);
        return  map;
	}
	
	/**
	 * 截取掉最后一个字符
	 * @param str
	 * @return
	 */
	public static String deleteLastChar(String str){
		str = str.length() > 0 ? str.substring(0,str.length() - 1) : str;
		return str;
	}
	
	/**
	 * 获取可用现金券总额
	 * @param list
	 * @return
	 */
	public Double sumTicketAmount(List<CashTicketBean> list){
		Double sumAmount = 0.0;
		for(int i=0 ; i<list.size() ; i++){
			CashTicketBean bean = list.get(i);
			sumAmount += bean.getDenomination();
		}
		return sumAmount;
	}

	/**
	 * 组合最佳现金券
	 * @param list
	 * @param results
	 * @param amount
	 * @return
	 */
	public static List<Long> bestGroupTicket(List<CashTicketBean> list,List<Long> results,Double amount){
		int listSize = list.size();
		label:
		for(int i=0;i<listSize;i++){//最外层循环
			//list中的每个元素都做一遍首元素
			Double first_money = list.get(i).getDenomination();
			long first_ids = list.get(i).getTicketId();
			if(first_money.compareTo(amount) == 0){
				results.add(first_ids);
				break label;
			}
			for(int j=i+1;j<listSize;j++){//中循环  分别以首元素之外的元素做组合，如 ：ab{abc abcd}   ac{acd}  ad 
				String second_ids = first_ids + "," + list.get(j).getTicketId();
				Double second_money = LoanUtil.formatAmount(first_money + list.get(j).getDenomination());
				if(second_money.compareTo(amount) == 0){
					results.add(NumberUtil.toLong(second_ids,0L));
					break label;
				}
				int k = j+1;
				int tmp = 0;
				while(k <listSize){
					for(tmp=k;tmp<listSize;tmp++){
						String third_ids = getTicketIds(list, k, tmp);
						Double third_amount = getSumMoney(list, k, tmp);
						if(LoanUtil.formatAmount(second_money + third_amount).compareTo(amount) ==0){
							results.add(NumberUtil.toLong((second_ids +"," + third_ids),0L));
							break label;
						}
					}
					k++;
				}
			}
		}
		return results;
	}
	
    /**
     * 统计现金券ids
     * @param list
     * @param s
     * @param e
     * @return
     */
	public static String getTicketIds(List<CashTicketBean> list,int start,int end){
		String str = "";
		for(int i=start ; i<=end ; i++){
			str += list.get(i).getTicketId() + ",";
		}
		str = deleteLastChar(str);
		return str;
	}
	
	/**
	 * 计算总金额
	 * @param list
	 * @param s
	 * @param e
	 * @return
	 */
	public static Double getSumMoney(List<CashTicketBean> list,int s,int e){
		Double sumMoney = 0.0;
		for(int i=s;i<=e;i++){
			sumMoney += list.get(i).getDenomination();
		}
		return sumMoney;
	}
	
	public static List<String> chooseTicket(List<CashTicketBean> list,List<String> results,Double amount){
		return groupTicket(list,"",0.0,results,amount);
	}
	
	/**
	 * 
	 * 组合现金券
	 * @param list		可用现金券列表
	 * @param em		组合元素
	 * @param results	返回结果
	 * @return
	 */
	public static List<String> groupTicket(List<CashTicketBean> list,String em,Double emAmount,List<String> results,Double amount)
    {
		if(is_go == false){
			return results;
		}
		if(results.size()<=0){
			if(StringUtils.isNotBlank(em) && emAmount.compareTo(0.0) > 0){
				if(emAmount.compareTo(amount)==0){
					results.add(em.substring(1,em.length()));
					is_go = false;
					list = new ArrayList<CashTicketBean>();
				}
			}
			for(int i=0;i<list.size();i++)
		    {
				List<CashTicketBean> second = new ArrayList<CashTicketBean>(list);
			    CashTicketBean  bean = second.get(i);
			    String atr = "," + bean.getTicketId();
			    Double temAmount =emAmount  + bean.getDenomination();
			    if(temAmount.compareTo(amount) > 0){
			    	break;
			    }
			    second.remove(i);
			    second = results.size() > 0 ? new ArrayList<CashTicketBean>() : second;
			    //递归组合现金券
			    if(results.size() > 0){
			    	return groupTicket(second,em + atr,temAmount,results,amount);
			    }else{
			    	groupTicket(second,em + atr,temAmount,results,amount);
			    }
		    }
		}
	    return results;
    }

}
