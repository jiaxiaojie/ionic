package com.thinkgem.jeesite.modules.project.service.loan;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.thinkgem.jeesite.modules.entity.MessageAlertSetting;
import com.thinkgem.jeesite.modules.message.service.MessageAlertSettingService;
import com.thinkgem.jeesite.modules.sms.utils.SmsUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectDateNodeDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
@Service
public class LoanService implements ILoanService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Autowired
	private ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	private ProjectDateNodeDao projectDateNodeDao;

	@Autowired
	private MessageAlertSettingService messageAlertSettingService;
	@Override
	public boolean isFinished(ProjectBaseInfo projectInfo) {
		return System.currentTimeMillis() > projectInfo.getBiddingDeadline().getTime() || isFull(projectInfo);
	}

	@Override
	public boolean isFull(ProjectBaseInfo projectInfo) {
		//<1>.该项目的投资记录有没有【冻结中】状态 ? 没有满标 : 下一步判断
		List<ProjectInvestmentRecord> list = projectInvestmentRecordDao.findListByProjectId(projectInfo.getProjectId(), ProjectConstant.PROJECT_INVESTMENT_STATUS_FREEZE);
		if (list.size() > 0) {
			return false;
		}
		//<2>.该项目的已募集金额 >= 募集金额 ? 满标 : 没有满标
		if (projectInfo.getPes().getEndFinanceMoney() < projectInfo.getFinanceMoney()) {
			return false;
		}
		return true;
	}
	
	@Override
	public Map<String, Object> finishedHandler(ProjectBaseInfo projectInfo) {
		if (isFinished(projectInfo)) {
			//更新项目状态
			logger.debug("---------" + DateUtils.formatDateTime(new Date())
					+ ":finishedHandler projectId :" + projectInfo.getProjectId() + " start.");
			projectBaseInfoDao.updateProjectToInvestmentFinish(projectInfo.getProjectId());
			//若项目满标或者项目过期则短信提醒风控发标
			messageAlertSettingService.sendSmsFromFengControl(projectInfo.getProjectName());
			//更新幕资结束时间
			projectDateNodeDao.updateEndFundingDt(projectInfo.getProjectId(), new Date());
			logger.debug("---------" + DateUtils.formatDateTime(new Date())
					+ ":finishedHandler projectId :" + projectInfo.getProjectId() + " start.");
		}
		return null;
	}

	@Override
	public Map<String, Object> successHandler() {
		return null;
	}

	@Override
	public Map<String, Object> failedHandler() {
		return null;
	}
}
