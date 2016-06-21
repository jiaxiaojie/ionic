package com.thinkgem.jeesite.modules.project.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBlanceAlignmentService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.service.util.handler.AlignmentBlanceHandler;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 账户余额对齐
 *
 * @author lizibo
 * @since 2015/11/03
 */
@Component("autoAlignmentBlanceTask")
public class AutoAlignmentBlanceTask {
	Logger logger = Logger.getLogger(this.getClass());
	//线程数据量
	public static int THREAD_SIZE = 1000;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBlanceAlignmentService customerBlanceAlignmentService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	/**
	 * 每天晚上2点执行一次
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoAlignmentBlanceTask start...");
		//删除5天前的数据
		customerBlanceAlignmentService.deleteSomeDaysAgoData(5);
		//查询待对账的用户数据
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("statusCode", "0");
		map.put("hasOpenThirdAccount", "1");
		List<CustomerAccount> cList = customerAccountService.findAlignmentList(map);
		List<CustomerAccount> accountList = new ArrayList<CustomerAccount>();
		int count = 0;
		for(CustomerAccount account : cList){
			if(StringUtils.isNotBlank(account.getPlatformUserNo())) {
				count++;
				accountList.add(account);
				if(count % THREAD_SIZE == 0 || count == cList.size()){
					AlignmentBlanceHandler alignmentBlanceHandler = new AlignmentBlanceHandler
							(accountList,yeepayCommonHandler,customerBlanceAlignmentService,logTimerTaskService);
					Thread t = new Thread(alignmentBlanceHandler);
					t.start();
					accountList = new ArrayList<CustomerAccount>();
				}
				
			}
		}
		Date endTime = new Date();
		logger.info("autoAlignmentBlanceTask total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
	}
	

}
