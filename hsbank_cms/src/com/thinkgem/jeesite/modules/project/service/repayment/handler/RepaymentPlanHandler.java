package com.thinkgem.jeesite.modules.project.service.repayment.handler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;

/**
 * 还款计划处理
 * <p/>
 * 
 * @author wuyuan.xie CreateDate 2015-07-27
 */
@Component("repaymentPlanHandlerForRepay")
public class RepaymentPlanHandler {
	@Autowired
	private ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;

	/**
	 * 获得最近下一期的将要还款记录
	 * 
	 * @param date
	 * @return
	 */
	public ProjectRepaymentSplitRecord getNextRepayRecord(String projectId,
			Date date) {
		List<ProjectRepaymentSplitRecord> list = repaymentSplitRecordDao
				.getNextRepayRecord(projectId, date);
		if ((list == null) || (list.size() == 0)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 获得最近上一期的还款记录
	 * 
	 * @param date
	 * @return
	 */
	public ProjectRepaymentSplitRecord getOldRepayRecord(String projectId,
			Date date) {
		List<ProjectRepaymentSplitRecord> list = repaymentSplitRecordDao
				.getOldRepayRecord(projectId, date);
		if ((list == null) || (list.size() == 0)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 获得尚未归还的本金
	 * 
	 * @param projectId
	 * @param date
	 * @return
	 */
	public double getWillRepayPrincipal(String projectId, Date date) {
		return repaymentSplitRecordDao.getWillRepayPrincipal(projectId, date);
	}

	/**
	 * 获得尚未归还的利息
	 * 
	 * @param projectId
	 * @param date
	 *            下一期的还款日期
	 * @return
	 */
	public double getWillRepayInterest(String projectId, Date date) {
		return repaymentSplitRecordDao.getWillRepayInterest(projectId, date);
	}

	/**
	 * 获得所有未还款的还款记录列表
	 * 
	 * @param project
	 * @param date
	 * @return
	 */
	public List<ProjectRepaymentSplitRecord> getWillRepayList(String projectId,
			Date date) {
		List<ProjectRepaymentSplitRecord> list = repaymentSplitRecordDao
				.getNextRepayRecord(projectId, date);
		return list;
	}

	/**
	 * 新增一条【还款计划】记录到【还款拆分明细表（project_repayment_split_record）】
	 * 
	 * @param project_id
	 *            项目Id
	 * @param investment_record_id
	 *            投资记录Id
	 * @param repayment_user_id
	 *            还款人
	 * @param repayment_account
	 *            还款人账户
	 * @param payee_user_id
	 *            收款人
	 * @param payee_account
	 *            收款人账户
	 * @param money
	 *            实际还款金额（包括应还本金、应还利息）
	 * @param repay_type
	 *            还款类型(正常还款、提前还款、逾期还款、代偿)
	 * @param principal
	 *            应还本金
	 * @param interest
	 *            应还利息
	 * @param sum_interest
	 *            累计应还利息
	 * @param remained_principal
	 *            剩余本金
	 * @param repayment_dt
	 *            还款日期
	 * @param
	 */
	protected void addRepaymentPlan(String project_id,
			String investment_record_id, Long repayment_user_id,
			Long repayment_account, Long payee_account, Double money,
			Double principal, Double interest, Double sum_interest,
			Double remained_principal) {
		ProjectRepaymentSplitRecord repaymentSplitRecord = new ProjectRepaymentSplitRecord();
		repaymentSplitRecord.setProjectId(Long.parseLong(project_id));
		repaymentSplitRecord.setInvestmentRecordId(Long
				.parseLong(investment_record_id));
		repaymentSplitRecord.setRepaymentUserId(repayment_user_id);
		repaymentSplitRecord.setRepaymentAccount(String
				.valueOf(repayment_account));
		repaymentSplitRecord.setPayeeAccount(String.valueOf(payee_account));
		repaymentSplitRecord.setMoney(money);
		repaymentSplitRecord
				.setRepayType(ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL); // 还款类型(正常还款)
		repaymentSplitRecord.setPrincipal(principal);
		repaymentSplitRecord.setInterest(interest);
		repaymentSplitRecord.setSumInterest(sum_interest);
		repaymentSplitRecord.setRemainedPrincipal(remained_principal);
		repaymentSplitRecord.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_BUDGET); // 还款状态(预算)
		repaymentSplitRecord.setCreateDt(new Date());
		repaymentSplitRecordDao.insert(repaymentSplitRecord);
	}

}
