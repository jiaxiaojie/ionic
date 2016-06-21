package test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;

public class Test {
	
	public static LinkedHashMap<Long,Double> cashTicketBeanToMap(List<CashTicketBean> cashTicketBeans){
		LinkedHashMap<Long,Double> result = new LinkedHashMap<Long,Double>();
		for(CashTicketBean cashTicketBean : cashTicketBeans){
			result.put(cashTicketBean.getTicketId(), cashTicketBean.getDenomination());
		}
		return result;
	}
	
	private static LinkedList<Long> getKeys(LinkedHashMap<Long, Double> resultTickets){
		LinkedList<Long> tickets = new LinkedList<Long>();
		
		if(resultTickets != null){
			for(Long key : resultTickets.keySet()){
				tickets.add(key);
			}
		}
		
		return tickets;
	}


	public static LinkedHashMap<Long, Double> selectTicket(Double parval, LinkedHashMap<Long,Double> tickets){
		return selectTicket( parval,  tickets, true);
	}
	
	public static LinkedList<CashTicketBean> selectTicketReturnCashTicketBeanList(Double parval, LinkedHashMap<Long,Double> tickets){
		LinkedList<CashTicketBean> result = new LinkedList<CashTicketBean>();
		
		LinkedHashMap<Long, Double> selectedTickets = selectTicket( parval, tickets);
		
		for(Long key : selectedTickets.keySet()){
			CashTicketBean cashTicketBean = new CashTicketBean();
			cashTicketBean.setTicketId(key);
			cashTicketBean.setDenomination(selectedTickets.get(key));
			
			result.add(cashTicketBean);
		}
		return result;
	}
	
	public static LinkedList<Long> selectTicketReturnKeyList(Double parval, LinkedHashMap<Long,Double> tickets){
		LinkedHashMap<Long, Double> selectedTickets = selectTicket( parval, tickets);
		return getKeys(selectedTickets);
	}
	
	private static LinkedHashMap<Long, Double> selectTicket(Double parval, LinkedHashMap<Long,Double> tickets,Boolean isRollbackSelect){
		
		/*if(isRollbackSelect == true){
			LinkedHashMap<Double, Double> beforReuslt = selectTicket(parval,tickets,false);
			if(beforReuslt != null){
				return beforReuslt;
			}
		}*/
		
		
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
					LinkedHashMap<Long, Double> tempResultTickets = selectTicket(tempParval,resultTickets, false);
					if(tempResultTickets != null){
						tempResultTickets.put(key,ticket);
						currentResidueParval = 0d;
						resultTickets = tempResultTickets;
						break;
					}else{
						
					}
				}
				
				
			}
			
		}
		
		if(currentResidueParval != 0){
			resultTickets = null;
		}
		
		
		return resultTickets;
	}

	public static void main(String[] args) {
		LinkedHashMap<Long,Double> tickets = new LinkedHashMap<Long,Double>();
		tickets.put(1l,5d);
		tickets.put(2l,5d);
		tickets.put(3l,5d);
		tickets.put(4l,20d);
		tickets.put(5l,10d);
		LinkedList<Long> resultTickets = selectTicketReturnKeyList(25d,tickets);
		
		System.out.println(resultTickets);
		
		System.out.println(tickets);
	}



/*	public static LinkedList<Double> selectTicketSequence(Double parval, LinkedList<Double> tickets){
		//选择的券
		LinkedList<Double> resultTickets = new LinkedList<Double>();
		
		//当前剩余待选面值
		Double currentResidueParval = parval;
		for(Double ticket : tickets){//循环待选券
			//选择当前待选券后剩余待选的面值
			Double selectedResidueParval = currentResidueParval-ticket;
			
			//如果当前选择的券面值小于当前待选面值
			if(selectedResidueParval >= 0){
				resultTickets.add(ticket);
				currentResidueParval -= ticket;
				
				if(currentResidueParval == 0){
					break;
				}
			}
		}
		
		if(currentResidueParval != 0){
			resultTickets = null;
		}
		
		
		return resultTickets;
		
	}*/
}
