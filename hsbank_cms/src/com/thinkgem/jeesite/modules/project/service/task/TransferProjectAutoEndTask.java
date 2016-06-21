/**
 * 
 */
package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransferInfoDao;
import com.hsbank.util.type.DatetimeUtil;

/**
 * @author yangtao
 *
 */
@Component("transferProjectAutoEndTask")
public class TransferProjectAutoEndTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectTransferInfoDao projectTransferInfoDao;
	@Autowired
	ProjectInvestmentRecordDao  projectInvestmentRecordDao;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	
	/**
	 * 每半小时执行一次
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】transferProjectAutoEndTask start...");
		Date today=new Date();
		//获得所有已经到时间但还在执行的转让列表
		List<ProjectTransferInfo> transferList= projectTransferInfoDao.getRuningTransferList(today);
		String status = "1";
		String remark = "transferProjectAutoEndTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		for(ProjectTransferInfo item:transferList){
			logger.debug("find transferProjectId is " + item.getTransferProjectId());
			double remainderCreditor=new Double(item.getRemainderCreditor()).doubleValue();
			ProjectInvestmentRecord pir=projectInvestmentRecordDao.get(item.getInvestmentRecordId().longValue()+"");
			//根据剩余债权设置此转让的状态，部分转让/全部转让/没有任何转让/异常
			if(remainderCreditor>0){
				item.setStatus(ProjectConstant.PROJECT_TRANSFER_STATUS_SOMEEND);
			}else if(remainderCreditor==0){
				item.setStatus(ProjectConstant.PROJECT_TRANSFER_STATUS_ALLEND);
			}else if(remainderCreditor==pir.getAmount().doubleValue()){
				item.setStatus(ProjectConstant.PROJECT_TRANSFER_STATUS_FAIL);
			}else {
				logger.info("transferProjectAutoEndTask transfer project id is="+pir.getTransferProjectId()+" 状态异常,需要查证");
				item.setStatus(ProjectConstant.PROJECT_TRANSFER_STATUS_FAIL);
			}
			item.setId(item.getTransferProjectId().longValue()+"");
			item.setCloseDate(new Date());
			try {
				projectTransferInfoDao.update(item);
			} catch (Exception e) {
				status = "0";
				failBuilder.append("transferProjectAutoEndTask project id is " + item.getProjectId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("transferProjectAutoEndTask project id is "
						+ item.getProjectId() + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		logTimerTaskService.logTimer(transferList, "transferProjectAutoEndTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】transferProjectAutoEndTask end...");
		logger.info("transferProjectAutoEndTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
