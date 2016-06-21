package com.thinkgem.jeesite.modules.credit.task;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.credit.service.CreditBaseInfoService;

@Component
public class ChangeCreditStatusTask {
	@Autowired
	private CreditBaseInfoService creditBaseInfoService;
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 债权结束时间超过后，将债权状态改为募集结束 每日00:00:01运行
	 */
	public void job() {
		logger.info("=====changeCreditStatusTask start=====date:"+DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		creditBaseInfoService.updateStatusByCurrentDate();
		logger.info("=====changeCreditStatusTask end=====date:"+DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
	}
}
