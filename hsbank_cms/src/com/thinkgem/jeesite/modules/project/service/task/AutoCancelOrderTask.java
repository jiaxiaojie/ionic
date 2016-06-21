package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.project.service.assignment.AssignmentService;
import com.thinkgem.jeesite.modules.project.service.investment.InvestmentService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * 投资记录超过30分钟未支付，回滚数据
 *
 * @author lizibo
 * @since 2015/08/11
 */
@Component("autoCancelOrderTask")
public class AutoCancelOrderTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private ProjectExecuteSnapshotService executeSnapshotService;
	@Autowired
	LogTimerTaskService logTimerTaskService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private AssignmentService assignmentService;
	
	/**
	 * 每5分钟执行一次
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】autoCancelOrderTask start...");
		//当前时间-30分钟(暂时定为5分钟)
		Date theDate = DateUtils.addMinutes(new Date(), -15);
		String status = "1";
		String remark = "autoCancelOrderTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		//查询投资时间超过30分钟、冻结中的投资记录
		List<ProjectInvestmentRecord> investmentRecordList = projectInvestmentRecordDao.findInvestmentListByOpDtAndStatus(ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE, theDate);
		for(ProjectInvestmentRecord record : investmentRecordList){
			logger.debug("find recordId is " + record.getId());
			try {
				ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(String.valueOf(record.getProjectId()));
				if (record.getTransferProjectId() == null || record.getTransferProjectId() == 0) {
					//投资
					investmentService.afterInvest(projectBaseInfo, record, false);
				} else{
					//债权转让
					//转让项目流水号
					String projectId = String.valueOf(record.getProjectId());
					String transferProjectId = String.valueOf(record.getTransferProjectId());
					ProjectTransferInfo projectTransferInfo = projectTransferInfoService.get(transferProjectId);
					String transferor = projectTransferInfo.getTransferor() != null ? projectTransferInfo.getTransferor().longValue()+"" : "";
					String investmentUserId = record.getInvestmentUserId() != null ? record.getInvestmentUserId().longValue() + "" : "";
					if (investmentUserId.equals(transferor)){
						//投资人=债权转出人，就是剩余债权对应的投资记录
						continue;
					} 
					//原投资记录
					Long investmentRecordId = projectTransferInfo.getInvestmentRecordId();
					ProjectInvestmentRecord pir = projectInvestmentRecordService.get(String.valueOf(investmentRecordId));
					projectTransferInfo.setPir(pir);
					//执行快照
					ProjectExecuteSnapshot pesAssign = executeSnapshotService.getByProjectIdAndTransferId(projectId, transferProjectId);
					projectTransferInfo.setProjectExecuteSnapshot(pesAssign);
					//原始项目信息
					projectTransferInfo.setProjectBaseInfo(projectBaseInfo);
					//根据请求流水号获取投资记录信息
					List<ProjectInvestmentRecord> recordList = projectInvestmentRecordService.findListByThirdPartyOrder(record.getThirdPartyOrder());
					ProjectInvestmentRecord recordAssignment = null;
					ProjectInvestmentRecord recordRemaining = null;
					for(ProjectInvestmentRecord pr : recordList){
						String investmentUserId_pr = pr.getInvestmentUserId().longValue() + "";
						if (investmentUserId_pr.equals(transferor)){
							//投资人=债权转出人，就是剩余债权对应的投资记录
							recordRemaining = pr;
						} else {
							recordAssignment = pr;
						}
					}
					assignmentService.afterAssign(projectTransferInfo, recordAssignment, recordRemaining, false);
				}
			} catch (Exception e) {
				status = "0";
				failBuilder.append("autoCancelOrderTask fail record id is " + record.getId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoCancelOrderTask investment record id is "
						+ record.getId() + " run err mes is "+e.getMessage());
			}
		}
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		//记录日志
		logTimerTaskService.logTimer(investmentRecordList, "autoCancelOrderTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】autoCancelOrderTask end...");
		logger.info("autoCancelOrderTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
