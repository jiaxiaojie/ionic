package com.thinkgem.jeesite.common.ticket.util.bean;

/**
 * 现金券bean
 * @author lzb
 *
 */
public class CashTicketBean {
    private Long ticketId;	//现金券id
	private Double denomination;	//面值
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public Double getDenomination() {
		return denomination;
	}
	public void setDenomination(Double denomination) {
		this.denomination = denomination;
	}
	
	
	

}
