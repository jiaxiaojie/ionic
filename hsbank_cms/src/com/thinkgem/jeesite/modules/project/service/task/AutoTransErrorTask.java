package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.ProjectTransErrorRecordService;

/**
 * 异常交易
 *
 * @author lzb
 * @since 2016/03/02
 */
@Component("autoTransErrorTask")
public class AutoTransErrorTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	ProjectTransErrorRecordService projectTransErrorRecordService;
	@Autowired
	ProjectInvestmentRecordDao projectInvestmentRecordDao;
	
	
	/**
	 * 每天晚上2:30点执行一次
	 */
	public void job() {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】autoTransErrorTask start...");
		String status = "1";
		String remark = "autoTransErrorTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		Date beginDate = org.apache.commons.lang3.time.DateUtils.addDays(new Date(), -5);
		beginDate = DateUtils.dateFormate(beginDate);
		Date endDate = DateUtils.dateFormate(new Date());
		//删除5前的异常交易记录
		projectTransErrorRecordService.deleteBeforeDaysErrorRecord(10);
		List<ProjectInvestmentRecord> list = projectInvestmentRecordDao.findListByTheDateAndStatus(ProjectConstant.PROJECT_REPAY_STATUS_REPEAL, beginDate,endDate);
		for(ProjectInvestmentRecord record : list){
			try {
				logger.debug("find the accountId is "+record.getInvestmentUserId()+",thirdPartyOrder is "+ record.getThirdPartyOrder());
				projectTransErrorRecordService.initTransErrorRecord(record);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				status = "0";
				failBuilder.append("autoTransErrorTask fail record id is " + record.getInvestmentUserId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoTransErrorTask investment record id is "
						+ record.getInvestmentUserId() + " run err mes is "+e.getMessage());
			}
		}
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		Date endTime = new Date();
		logTimerTaskService.logTimer(list, "autoTransErrorTask", startTime, endTime, status, remark);
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】autoTransErrorTask end...");
		logger.info("autoTransErrorTask total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
	}
	

}
