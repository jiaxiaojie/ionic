package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.ProjectPayMoneyService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * 解冻取消转账
 * 一次性任务，不用线上持续跑的
 * @author lizibo
 * @since 2015/08/14
 */
@Component("autoCancelInvestmentTask")
public class AutoCancelInvestmentTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired 
	private ProjectPayMoneyService projectPayMoneyService;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	/**
	 * 每5分钟执行一次
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】autoCancelInvestmentTask start...");
		//查询第三方流水号不为空的投资记录
		List<ProjectInvestmentRecord> investmentRecordList = projectInvestmentRecordDao.findInvestmentListThirdPartyOrderNotEmpty();
		String status = "1";
		String remark = "autoCancelInvestmentTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		for(ProjectInvestmentRecord record : investmentRecordList){
			logger.debug("find recordId is "+record.getId());
			String projectId = String.valueOf(record.getProjectId());
			String thirdPartyOrder = record.getThirdPartyOrder();
			ProjectBaseInfo pbi = projectBaseInfoDao.get(projectId);
			// 获得项目对应的借款人
			Long agentId = pbi.getAgentUser();
			Long customerId = pbi.getBorrowersUser();
			if ((agentId != null) && agentId != 0 && (!agentId.equals(""))
					&& (!agentId.equals("0"))) {
				customerId = agentId;
			}
			CustomerAccount ca = customerAccountDao.get(new Long(customerId));
			String borrowUserNo = ca.getPlatformUserNo();
			String transferProjectId = String.valueOf(record.getTransferProjectId());
			ProjectTransferInfo projectTransferInfo = projectTransferInfoService.get(transferProjectId);
			if(projectTransferInfo == null ){
				projectTransferInfo = new ProjectTransferInfo();
			}
			String transferor = projectTransferInfo.getTransferor() != null ? projectTransferInfo.getTransferor().longValue()+"" : "";
			String investmentUserId = record.getInvestmentUserId() != null ? record.getInvestmentUserId().longValue() + "" : "";
			try {
				//解冻取消转账
				if (investmentUserId.equals(transferor)){
					//投资人=债权转出人，就是剩余债权对应的投资记录
					continue;
				}else{
					projectPayMoneyService.confirmInvestmentRecord(projectId, pbi, borrowUserNo, record, thirdPartyOrder, "CANCEL");
				}
			} catch (Exception e) {
				status = "0";
				failBuilder.append("autoCancelInvestmentTask record id is " + record.getId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoCancelInvestmentTask investment record id is "
						+ record.getId() + " run err mes is "+e.getMessage());
			}
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		//记录日志
		logTimerTaskService.logTimer(investmentRecordList, "autoCancelInvestmentTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】autoCancelInvestmentTask end...");
		logger.info("autoCancelInvestmentTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
