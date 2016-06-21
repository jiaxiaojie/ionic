package com.thinkgem.jeesite.modules.customer.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.service.CustomerRateTicketService;

/**
 * @author ydt 自动将过期会员加息券状态设置为已过期
 */
@Component
public class AutoExpiredCustomerRateTicketTask {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerRateTicketService customerRateTicketService;
	
	/**
	 * 每天0点5分执行
	 */
	public void job() {
		Date startDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTime) + "】autoExpiredCustomerRateTicketTask start...");
		customerRateTicketService.expiredTicket();
		Date endDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTiem) + "】autoExpiredCustomerRateTicketTask end...");
		logger.info("autoExpiredCustomerRateTicketTask total time consuming：【" + (endDateTiem.getTime() - startDateTime.getTime()) / 1000 + "s】");
	}
}
