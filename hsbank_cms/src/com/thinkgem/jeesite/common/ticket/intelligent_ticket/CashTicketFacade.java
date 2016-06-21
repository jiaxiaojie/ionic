package com.thinkgem.jeesite.common.ticket.intelligent_ticket;

import com.thinkgem.jeesite.common.ticket.intelligent_ticket.impl.CashTicketServiceImpl;

public class CashTicketFacade {

	/**
	 * 
	 * @return
	 */
	public static CashTicketService getCashTicketService(){
		CashTicketService cashTicketService = new CashTicketServiceImpl();
		return cashTicketService;
	}
}
