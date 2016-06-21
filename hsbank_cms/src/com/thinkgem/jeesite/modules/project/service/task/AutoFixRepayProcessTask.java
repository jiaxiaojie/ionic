/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectRepaymentRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentRecordService;
import com.thinkgem.jeesite.modules.project.service.repayment.RepaymentService;

/**
 * @author yangtao
 *
 */
@Component("autoFixRepayProcessTask")
public class AutoFixRepayProcessTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectRepaymentRecordService projectRepaymentRecordService;
	@Autowired
	RepaymentService repaymentService;
	public void job(){
		logger.info("autoFixRepayProcessTask start...");
		Date theDate=DateUtils.addMinutes(new Date(), 30);
		String status=ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE;
		List<ProjectRepaymentRecord> recordList=projectRepaymentRecordService.getOverTimeRepaymentRecord(status, theDate);
		if((recordList==null)||(recordList.size()==0)){
			logger.info("autoFixRepayProcessTask run recordList is null");
		}else{
			for(ProjectRepaymentRecord record:recordList){
				logger.debug("autoFixRepayProcessTask run at "+record.getRecordId()+" begin..");
				repaymentService.fixProcess(record);
				logger.debug("autoFixRepayProcessTask run at "+record.getRecordId()+" end..");
			}
		}
		logger.info("autoFixRepayProcessTask end...");
	}
}
