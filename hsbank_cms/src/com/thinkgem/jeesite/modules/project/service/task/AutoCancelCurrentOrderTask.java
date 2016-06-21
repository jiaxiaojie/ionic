package com.thinkgem.jeesite.modules.project.service.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.service.investment.CurrentInvestmentServiceImpl;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.log.service.LogTimerTaskService;
import com.hsbank.util.type.DatetimeUtil;

/**
 * 活期产品：投资记录超过30分钟未支付，回滚数据
 *
 * @author lizibo
 * @since 2015/12/14
 */
@Component("autoCancelCurrentOrderTask")
public class AutoCancelCurrentOrderTask {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentInvestmentServiceImpl currentInvestmentServiceImpl;
	@Autowired
	private LogTimerTaskService logTimerTaskService;
	
	
	/**
	 * 每5分钟执行一次
	 */
	public void job() {
		long start = System.currentTimeMillis();
		Date beginDt = DatetimeUtil.getCurrentDate();
		String startTime = DatetimeUtil.datetimeToString(beginDt);
		logger.info("【" + startTime + "】autoCancelCurrentOrderTask start...");
		//当前时间-15分钟
		Date theDate = DateUtils.addMinutes(new Date(), -15);
		String status = "1";
		String remark = "autoCancelCurrentOrderTask result is OK";
		StringBuilder failBuilder = new StringBuilder();
		//查询投资时间超过15分钟、冻结中的投资记录
		List<CurrentAccountPrincipalChangeHis> cHisList = currentAccountPrincipalChangeHisDao.getPrincipalChangeHisListByTypeAndStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE, theDate);
		for(CurrentAccountPrincipalChangeHis cHis : cHisList){
			logger.debug("find current recordId is " + cHis.getId());
			try {
				CurrentProjectInfo cInfo = currentProjectInfoService.get(String.valueOf(cHis.getProjectId()));
				//回滚数据
				currentInvestmentServiceImpl.alterCurrentInvest(cInfo, cHis, false);
			} catch (Exception e) {
				status = "0";
				failBuilder.append("autoCancelCurrentOrderTask fail record id is " + cHis.getId() + " run err mes is :" + e.getMessage()).append(";");
				logger.error("autoCancelCurrentOrderTask investment record id is "
						+ cHis.getId() + " run err mes is "+e.getMessage());
			}
		}
		if(failBuilder.length() > 0){
			remark = failBuilder.toString();
		}
		Date endDt = DatetimeUtil.getCurrentDate();
		//记录日志
		logTimerTaskService.logTimer(cHisList, "autoCancelCurrentOrderTask", beginDt, endDt, status, remark);
		String endTime = DatetimeUtil.datetimeToString(endDt);
        long end = System.currentTimeMillis();
		logger.info("【" + endTime + "】autoCancelCurrentOrderTask end...");
		logger.info("autoCancelCurrentOrderTask total time consuming：【" + (end - start) / 1000 + "s】");
	}
}
