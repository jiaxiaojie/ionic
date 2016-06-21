package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;

/**
 * @author ydt 到期自动设置会员投资券状态为已过期
 */
@Component
public class AutoChangeCustomerInvestmentTicketStatusToExpired {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	
	/**
	 * 每天0点5分执行
	 */
	public void job() {
		Date startDateTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startDateTime) + "】autoChangeCustomerInvestmentTicketStatusToExpired start...");
		customerInvestmentTicketService.autoChangeStatusToExpired();
		Date endDateTiem = new Date();
		logger.info("【" + DateUtils.formatDateTime(endDateTiem) + "】autoChangeCustomerInvestmentTicketStatusToExpired end...");
		logger.info("autoChangeCustomerInvestmentTicketStatusToExpired total time consuming：【" + (endDateTiem.getTime() - startDateTime.getTime()) / 1000 + "s】");
	}
}
