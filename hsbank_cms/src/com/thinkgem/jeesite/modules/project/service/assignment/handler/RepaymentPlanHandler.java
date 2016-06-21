package com.thinkgem.jeesite.modules.project.service.assignment.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.loan.credit_assignment.CreditAssignment;
import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import com.thinkgem.jeesite.modules.project.service.util.handler.RateTicketHandler;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.type.StringUtil;
import com.hsbank.util.constant.DatetimeField;

/**
 * 还款计划处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("assignmentRepaymentPlanHandler")
public class RepaymentPlanHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;
	
	/**
	 * 检查：当前日期是否在距下一个还款日期 N 天内，是返回true，否则返回false
	 * @return
	 */
	public boolean checkPlanEndDate(String investmentRecordId){
		//得到指定投资记录待收款列表
		List<ProjectRepaymentSplitRecord> willRepaymentList = repaymentSplitRecordDao.getRepaymentListByInvestmentRecord(investmentRecordId, ProjectConstant.PROJECT_REPAY_STATUS_BUDGET);
		Date tempDate = DatetimeUtil.getDate(DatetimeField.DAY, ProjectConfig.getInstance().getDayCountToNextRepay());
		for (ProjectRepaymentSplitRecord item : willRepaymentList) {
			return tempDate.getTime() > item.getRepaymentDt().getTime();
		}
		return false;
	}
	
	/**
	 * 处理器
	 * <1>.生成还款计划
	 * @return
	 */
	public boolean handler(ProjectTransferInfo projectTransferInfo, ProjectInvestmentRecord recordAssignment, ProjectInvestmentRecord recordRemaining) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":Assign generate repayment plan start...");
		//转让总金额
		double totalLoan = projectTransferInfo.getTransferPrice();
		//购买债权额度
		double assignmentLoan = recordAssignment.getAmount();
		//剩余额度
		double remainingLoan = recordRemaining.getAmount();
		//转让日期
		Date assignmentDate = new Date();
		//加息券列表
		List<IncreaseInterestItem> interestItems = rateTicketHandler.getInterestItems(recordAssignment.getRateTicketIds());
		//原标的待还款计划列表
		List<ProjectRepaymentSplitRecord> willRepaymentList = repaymentSplitRecordDao.getRepaymentListByInvestmentRecord(projectTransferInfo.getPir().getId(), ProjectConstant.PROJECT_REPAY_STATUS_BUDGET);
		List<RepaymentPlanItem> oldRepaymentPlan = getRepaymentListByInvestmentRecord(willRepaymentList);
		Map<String, List<RepaymentPlanItem>> repaymentPlan = CreditAssignment.getInstance().generate(totalLoan,  assignmentLoan,  interestItems, remainingLoan,  assignmentDate, oldRepaymentPlan);
		//<1>.转让债权的还款计划
		List<RepaymentPlanItem> repaymentPlanAssignment = repaymentPlan.get(LoanConstant.LOAN_ASSIGNMENT);
		Long project_id = projectTransferInfo.getProjectId();
		//融资代理人
		Long agentId = projectTransferInfo.getProjectBaseInfo().getAgentUser();
		//还款人
		Long repayment_user_id = projectTransferInfo.getProjectBaseInfo().getBorrowersUser();
		if ((agentId != null) && agentId.longValue() != 0 && (!agentId.equals(""))
				&& (!agentId.equals("0"))) {
			repayment_user_id = agentId;
		}
		//还款人账号(用户平台标示)
		String repayment_account = customerAccountDao.get(repayment_user_id).getPlatformUserNo();
		logger.info("---------repaymentPlanAssignment size:" + repaymentPlanAssignment != null ? repaymentPlanAssignment.size() : 0);
		Date tempDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(tempDateTime)
				+ ":start repaymentPlanAssignment addRepaymentPlan... ");
		for (RepaymentPlanItem item : repaymentPlanAssignment) {
			String investment_record_id = recordAssignment.getId();
			//收款人
			Long payee_user_id = recordAssignment.getInvestmentUserId();
			//收款人账号
			String payee_account = customerAccountDao.get(payee_user_id).getPlatformUserNo();
			Double money = item.getPrincipalAndInterest();
			Double principal = item.getPrincipal();
			Double interest = item.getInterest();
			Double rateTicketInterest = item.getRateTicketInterest();
			Double sum_interest = item.getSumInterest();
			Double remained_principal = item.getPrincipalRemaining();
			Date repayment_dt = item.getEndDate();
			// 新增还款计划
			this.addRepaymentPlan(project_id, investment_record_id, repayment_user_id, repayment_account, 
					payee_user_id,payee_account, money, principal, interest, rateTicketInterest, sum_interest, remained_principal, repayment_dt);		
		} 
		Date endDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(endDateTime)
				+ ":all repaymentPlanAssignment addRepaymentPlan total time consuming: "
				+ (endDateTime.getTime() - tempDateTime.getTime()) / 1000);
		//<2>.剩余债权的还款计划
		List<RepaymentPlanItem> repaymentPlanRemaining = repaymentPlan.get(LoanConstant.LOAN_REMAINING);
		logger.info("---------repaymentPlanRemaining size:" + repaymentPlanRemaining != null ? repaymentPlanRemaining.size() : 0);
		Date tempRemainingDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(tempRemainingDateTime)
				+ ":start repaymentPlanRemaining addRepaymentPlan... ");
		for (RepaymentPlanItem item : repaymentPlanRemaining) {
			String investment_record_id = recordRemaining.getId();
			//收款人
			Long payee_user_id = recordRemaining.getInvestmentUserId();
			//收款人账号
			String payee_account = customerAccountDao.get(payee_user_id).getPlatformUserNo();
			Double money = item.getPrincipalAndInterest();
			Double principal = item.getPrincipal();
			Double interest = item.getInterest();
			Double rateTicketInterest = item.getRateTicketInterest();
			Double sum_interest = item.getSumInterest();
			Double remained_principal = item.getPrincipalRemaining();
			Date repayment_dt = item.getEndDate();
			// 新增还款计划
			this.addRepaymentPlan(project_id, investment_record_id, repayment_user_id, repayment_account, 
					payee_user_id,payee_account, money, principal, interest, rateTicketInterest, sum_interest, remained_principal, repayment_dt);		
		} 
		Date endRemainingDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(endRemainingDateTime)
				+ ":all repaymentPlanRemaining addRepaymentPlan total time consuming: "
				+ (endRemainingDateTime.getTime() - tempRemainingDateTime.getTime()) / 1000);
		//<3>.将原标的的待还款计划状态更新为【已转让】
		logger.info("---------willRepaymentList size:" + willRepaymentList != null ? willRepaymentList.size() : 0);
		for (ProjectRepaymentSplitRecord record : willRepaymentList) {
			logger.debug("Original subject to repayment plan recordId:" + record.getSplitRecordId());
			repaymentSplitRecordDao.updateStatusById(String.valueOf(record.getSplitRecordId()), ProjectConstant.PROJECT_REPAY_STATUS_TRANSFER);
		}
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":Assign generate repayment plan end...");
		return false;
	}
	
	/**
	 * 得到指定投资记录的指定状态的收款列表
	 * @param investmentRecordId
	 * @param status
	 * @return
	 */
	private List<RepaymentPlanItem> getRepaymentListByInvestmentRecord(List<ProjectRepaymentSplitRecord> recordList ) {
		List<RepaymentPlanItem> resultValue = new ArrayList<RepaymentPlanItem>();
		for (ProjectRepaymentSplitRecord record : recordList) {
			RepaymentPlanItem item = new RepaymentPlanItem();
			String repaymentMode = record.getProjectBaseInfo().getRepaymentMode();
			String durationType = StringUtil.dealString(record.getProjectBaseInfo().getDurationType());
			int projectDuration = record.getProjectBaseInfo().getProjectDuration().intValue();
			if(ProjectConstant.PROJECT_DURATION_TYPE_DAILY.equals(durationType)){//项目期限类型 按日
				item.setBeginDate(DatetimeUtil.getDate(record.getRepaymentDt(), DatetimeField.DAY,  - projectDuration));
			}else{
				int num = ProjectConstant.REPAYMNET_METHOD_INTEREST_FIRST.equals(repaymentMode) ? 1 : projectDuration;
				//开始日期
				item.setBeginDate(DatetimeUtil.getDate(record.getRepaymentDt(), DatetimeField.MONTH,  - num));
			}
			//截止日期
			item.setEndDate(record.getRepaymentDt());
			//应还本金+利息: Principal and Interest*/
			item.setPrincipalAndInterest(LoanUtil.formatAmount(record.getPrincipal() + record.getInterest()));
			//应还本金*/
			item.setPrincipal(record.getPrincipal());
			//剩余应还本金: Principal Remaining*/
			item.setPrincipalRemaining(record.getRemainedPrincipal());
			//应还利息*/
			item.setInterest(LoanUtil.formatAmount(record.getInterest()));
			//加息券利息
			double rateTicketInterest = record.getRateTicketInterest() !=null ? record.getRateTicketInterest() : 0.0;
			item.setRateTicketInterest(rateTicketInterest);
			resultValue.add(item);
		}
		return resultValue;
	}
	
	
	
	/**
	 * 新增一条【还款计划】记录到【还款拆分明细表（project_repayment_split_record）】
	 * @param project_id           		项目Id
	 * @param investment_record_id 		投资记录Id
	 * @param repayment_user_id    		还款人
	 * @param repayment_account    		还款人账户
	 * @param payee_user_id        		收款人
	 * @param payee_account        		收款人账户
	 * @param money                		实际还款金额（包括应还本金、应还利息）
	 * @param repay_type           		还款类型(正常还款、提前还款、逾期还款、代偿)
	 * @param principal            		应还本金
	 * @param interest             		应还利息
	 * @param sum_interest   			累计应还利息
	 * @param remained_principal   		剩余本金
	 * @param repayment_dt         		还款日期
	 * @param             		
	 */
	private void addRepaymentPlan(Long project_id,
			String investment_record_id, Long repayment_user_id,
			String repayment_account, Long payee_user_id,String payee_account, Double money,
			Double principal, Double interest, Double rateTicketInterest, Double sum_interest,
			Double remained_principal, Date repayment_dt) {
		ProjectRepaymentSplitRecord repaymentSplitRecord = new ProjectRepaymentSplitRecord();
		repaymentSplitRecord.setProjectId(project_id);
		repaymentSplitRecord.setInvestmentRecordId(Long.parseLong(investment_record_id));
		repaymentSplitRecord.setRepaymentUserId(repayment_user_id);
		repaymentSplitRecord.setRepaymentAccount(repayment_account);
		repaymentSplitRecord.setPayeeUserId(payee_user_id);
		repaymentSplitRecord.setPayeeAccount(payee_account);		
		repaymentSplitRecord.setMoney(money);
		repaymentSplitRecord.setPrincipal(principal);
		repaymentSplitRecord.setInterest(interest);
		repaymentSplitRecord.setRateTicketInterest(rateTicketInterest);
		repaymentSplitRecord.setSumInterest(sum_interest);
		repaymentSplitRecord.setRemainedPrincipal(remained_principal);
		repaymentSplitRecord.setRepaymentDt(repayment_dt);
		repaymentSplitRecord.setRepayType(ProjectConstant.PROJECT_REPAYMENT_TYPE_NORMAL); // 还款类型(正常还款)
		repaymentSplitRecord.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_BUDGET); // 还款状态(预算)
		repaymentSplitRecord.setCreateDt(new Date());
		repaymentSplitRecordDao.insert(repaymentSplitRecord);
	}
}
