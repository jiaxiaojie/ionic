package com.thinkgem.jeesite.modules.project.service.assignment.handler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectTransferInfoDao;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.constant.DatetimeField;

/**
 * 项目转让处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("projectTransferHandler")
public class ProjectTransferHandler {
	@Autowired
	private ProjectTransferInfoDao transferInfoDao;
	@Autowired
	private ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;
	
	/**
	 * 新增：生成债权转让合同
	 * @param projectInfo
	 * @param investmentRecord
	 */
	public void addTransferInfo(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord) {
		ProjectTransferInfo entity = new ProjectTransferInfo();
		entity.setProjectId(investmentRecord.getProjectId());
		entity.setParentTransferProjectId(investmentRecord.getTransferProjectId());
		entity.setInvestmentRecordId(NumberUtil.toLong(investmentRecord.getId(), 0L));
		entity.setProjectEndDate(projectInfo.getEndPlannedRepaymentDate());
		entity.setTransferor(investmentRecord.getInvestmentUserId());
		entity.setCreateDate(new Date());
		entity.setDiscountDate(DatetimeUtil.getDate(DatetimeField.HOUR_OF_DAY, ProjectConfig.getInstance().getMaxAssignmentHours()));
		//entity.setServiceChargeType(serviceChargeType);
		entity.setStatus(ProjectConstant.PROJECT_TRANSFER_STATUS_RUNNING);
		//entity.setTransferName(transferName);
		//---------------------------------------
		//得到指定投资记录待收款列表
		List<ProjectRepaymentSplitRecord> willRepaymentList = repaymentSplitRecordDao.getRepaymentListByInvestmentRecord(investmentRecord.getId(), ProjectConstant.PROJECT_REPAY_STATUS_BUDGET);
		for (ProjectRepaymentSplitRecord item : willRepaymentList) {
			if (item.getRepaymentDt().getTime() > System.currentTimeMillis()) {
				//下一个还款日期
				entity.setNextRepaymentDate(item.getRepaymentDt());
				//转让价格 = 剩余本金
				entity.setTransferPrice(item.getRemainedPrincipal());
				//剩余债权
				entity.setRemainderCreditor(item.getRemainedPrincipal());
			}
		}
		//----------------------------------------
		transferInfoDao.insert(entity);
	}
}
