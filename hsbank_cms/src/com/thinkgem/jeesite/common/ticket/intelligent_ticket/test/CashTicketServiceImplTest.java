package com.thinkgem.jeesite.common.ticket.intelligent_ticket.test;


import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.ticket.intelligent_ticket.impl.CashTicketServiceImpl;
import com.thinkgem.jeesite.common.ticket.util.bean.CashTicketBean;


/**
 * 测试：智能选券
 * @author lzb
 * 2016-04-25
 */
public class CashTicketServiceImplTest {
	
    public static void main(String[] args){

		List<CashTicketBean> newList = new ArrayList<CashTicketBean>();
		
		CashTicketBean bean0 = new CashTicketBean();
        bean0.setTicketId(0L);
        bean0.setDenomination(50.0);
        newList.add(bean0);
		
        CashTicketBean bean1 = new CashTicketBean();
        bean1.setTicketId(1L);
        bean1.setDenomination(50.0);
        newList.add(bean1);
        
        CashTicketBean bean2 = new CashTicketBean();
        bean2.setTicketId(2L);
        bean2.setDenomination(5.0);
        newList.add(bean2);
        
        CashTicketBean bean3 = new CashTicketBean();
        bean3.setTicketId(3L);
        bean3.setDenomination(50.0);
        newList.add(bean3);
        
        CashTicketBean bean4 = new CashTicketBean();
        bean4.setTicketId(4L);
        bean4.setDenomination(10.0);
        newList.add(bean4);
        
        CashTicketBean bean5 = new CashTicketBean();
        bean5.setTicketId(5L);
        bean5.setDenomination(10.0);
        newList.add(bean5);
        
        CashTicketBean bean6 = new CashTicketBean();
        bean6.setTicketId(6L);
        bean6.setDenomination(20.0);
        newList.add(bean6);
        //投资金额
        Double amount = 5000.0;
        CashTicketServiceImpl cashTicket= new CashTicketServiceImpl();
    	List<Long> resultList = cashTicket.getBestTicket(newList, amount);
    	if(resultList.size()==0){
    		System.out.println(new String[]{}.toString());
    	}else{
    		for(int l=0;l<resultList.size();l++){
    			System.out.println(resultList.get(l));
    		}
    	}
    	
	}
    
   
}
