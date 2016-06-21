package com.thinkgem.jeesite.modules.project.service.util.handler;

import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.loan.repayment_plan.RepaymentPlanFactory;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentPlanDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentSplitRecordDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 还款计划处理
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
@Component("repaymentPlanHandlerUtil")
public class RepaymentPlanHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectRepaymentPlanDao projectRepaymentPlanDao;
	@Autowired
	private ProjectRepaymentSplitRecordDao repaymentSplitRecordDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private RateTicketHandler rateTicketHandler;
	
	/**
	 * 生成还款计划：生成【融资项目】的还款计划
	 * @return
	 */
	public void generateForProject(ProjectBaseInfo projectInfo) {
		//还款方式
		String repaymentMethod = ProjectUtil.getRepaymentMethod(projectInfo.getRepaymentMode());
		//贷款总额
		double totalLoan = projectInfo.getFinanceMoney();
		//年利率
		double annualInterestRate = projectInfo.getAnnualizedRate();
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalDuration = projectInfo.getProjectDuration().intValue();
		//项目期限类型
		String durationType = StringUtil.dealString(projectInfo.getDurationType());
		//投标截止日期
		Date beginDate = DateUtils.dateFormate(projectInfo.getBiddingDeadline());
		//计息日期
		Date beginInterestDate = DateUtils.dateFormate(beginDate);
		List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
		List<RepaymentPlanItem> repaymentPlan = RepaymentPlanFactory.getDailyInstance(repaymentMethod)
				.generate(interestItems, durationType, totalLoan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate);
		for (RepaymentPlanItem item : repaymentPlan) {
			String project_id = projectInfo.getProjectId();
			Date plan_date = item.getEndDate();
			Double plan_money = item.getPrincipalAndInterest();
			Double principal = item.getPrincipal();
			Double interest = item.getInterest();
			Double rateTicketInterest = item.getRateTicketInterest();
			Double remaining_principal = item.getPrincipalRemaining();
			Double remaining_principal_interest = item.getPrincipalAndInterestRemaining();
			// 新增还款计划
			this.addRepaymentPlanForProject(project_id, plan_date, plan_money, principal, interest, rateTicketInterest, remaining_principal, remaining_principal_interest);
		} 
	}
	
	/**
	 * 生成还款计划：生成【单条投资记录】的还款计划
	 * @return
	 */
	public boolean generateForInvestment(ProjectBaseInfo projectInfo, ProjectInvestmentRecord investmentRecord) {
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":generateForInvestment start...");
		//还款方式
		String repaymentMethod = ProjectUtil.getRepaymentMethod(projectInfo.getRepaymentMode());
		//贷款总额
		double totalLoan = investmentRecord.getAmount();
		//年利率
		double annualInterestRate = projectInfo.getAnnualizedRate();
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalDuration = projectInfo.getProjectDuration().intValue();
		//项目期限类型
		String durationType = StringUtil.dealString(projectInfo.getDurationType());
		//投标截止日期
		Date beginDate = DateUtils.dateFormate(projectInfo.getBiddingDeadline());
		//计息日期
		Date beginInterestDate = DateUtils.dateFormate(investmentRecord.getOpDt());
		//加息券列表
		List<IncreaseInterestItem> interestItems = rateTicketHandler.getInterestItems(investmentRecord.getRateTicketIds());
		List<RepaymentPlanItem> repaymentPlan = RepaymentPlanFactory.getDailyInstance(repaymentMethod).generate(interestItems, 
				durationType, totalLoan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate);
		//融资代理人
		Long agentId = projectInfo.getAgentUser();
		//还款人
		Long repayment_user_id = projectInfo.getBorrowersUser();
		if ((agentId != null) && agentId != 0 && (!agentId.equals(""))
				&& (!agentId.equals("0"))) {
			repayment_user_id = agentId;
		}
		//还款人账号
		String repayment_account = customerAccountDao.get(repayment_user_id).getPlatformUserNo();
		logger.info("---------repaymentPlan size:" + repaymentPlan != null ? repaymentPlan.size() : 0);
		Date tempDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(tempDateTime)
				+ ":start repaymentPlan addRepaymentPlanForInvestment... ");
		for (RepaymentPlanItem item : repaymentPlan) {
			String project_id = projectInfo.getProjectId();
			String investment_record_id = investmentRecord.getId();
			//收款人
			Long payee_user_id = investmentRecord.getInvestmentUserId();
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
			this.addRepaymentPlanForInvestment(project_id, investment_record_id, repayment_user_id, repayment_account, 
					payee_user_id,payee_account, money, principal, interest, rateTicketInterest, sum_interest, remained_principal, repayment_dt);		
		} 
		Date endDateTime = new Date();
		logger.info("---------" + DateUtils.formatDateTime(endDateTime)
				+ ":all repaymentPlan addRepaymentPlanForInvestment total time consuming: "
				+ (endDateTime.getTime() - tempDateTime.getTime()) / 1000);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ":generateForInvestment end...");
		return false;
	}
	
	/**
	 * 生成还款计划：生成【单条投资记录】的还款计划，但是不入库
	 * @return
	 */
	public List<RepaymentPlanItem> generateForInvestment(ProjectBaseInfo projectInfo, Double loan, String rateTicketIds) {
		//还款方式
		String repaymentMethod = ProjectUtil.getRepaymentMethod(projectInfo.getRepaymentMode());
		//年利率
		double annualInterestRate = projectInfo.getAnnualizedRate();
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalDuration = projectInfo.getProjectDuration().intValue();
		//项目期限类型
		String durationType = StringUtil.dealString(projectInfo.getDurationType());
		//投标截止日期
		Date beginDate = DateUtils.dateFormate(projectInfo.getBiddingDeadline());
		//计息日期
		Date beginInterestDate = DateUtils.dateFormate(new Date());
		//加息券列表
		List<IncreaseInterestItem> interestItems = rateTicketHandler.getInterestItems(rateTicketIds);
		return RepaymentPlanFactory.getDailyInstance(repaymentMethod).generate(interestItems,
				durationType, loan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate);
	}

	/**
	 * 生成还款计划分页列表：生成【单条投资记录】的还款计划，但是不入库
	 * @param projectInfo
	 * @param loan
	 * @param rateTicketIds
	 * @param pageSize
	 * @param pageNumber
     * @return
     */
	public Page<RepaymentPlanItem> generateForInvestment(ProjectBaseInfo projectInfo, Double loan, String rateTicketIds,Integer pageSize,Integer pageNumber){


		List<RepaymentPlanItem> repaymentPlanItems = generateForInvestment(projectInfo, loan, "");

		List<RepaymentPlanItem> result = new LinkedList<RepaymentPlanItem>();
		for(int i = pageSize*(pageNumber-1);i < pageSize*pageNumber;i++){
			try{
				result.add(repaymentPlanItems.get(i));
			}
			catch (IndexOutOfBoundsException e){
				break;
			}
		}
		return new Page<RepaymentPlanItem>(pageNumber, pageSize, repaymentPlanItems.size(), result);
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
	 * @param rateTicketInterest        加息券利息
	 * @param sum_interest   			累计应还利息
	 * @param remained_principal   		剩余本金
	 * @param repayment_dt         		还款日期
	 * @param             		
	 */
	private void addRepaymentPlanForInvestment(String project_id,
			String investment_record_id, Long repayment_user_id,
			String repayment_account, Long payee_user_id,String payee_account, Double money,
			Double principal, Double interest,Double rateTicketInterest, Double sum_interest,
			Double remained_principal, Date repayment_dt) {
		ProjectRepaymentSplitRecord repaymentSplitRecord = new ProjectRepaymentSplitRecord();
		repaymentSplitRecord.setProjectId(Long.parseLong(project_id));
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
	
	/**
	 * 新增一条【还款计划】记录：针对【融资项目】
	 * 新增一条【还款计划】记录到【还款计划表（project_repayment_plan）】
	 * @param project_id           			项目Id
	 * @param plan_date 					还款日期
	 * @param plan_money    				还款金额（包括应还本金、应还利息）
	 * @param principal            			应还本金
	 * @param interest             			应还利息
	 * @param remaining_principal   		剩余本金
	 * @param             		
	 */
	private void addRepaymentPlanForProject(String project_id, Date plan_date, Double plan_money, 
			Double principal, Double interest, Double rateTicketInterest, Double remaining_principal, Double remaining_principal_interest) {
		ProjectRepaymentPlan projectRepaymentPlan = new ProjectRepaymentPlan();
		projectRepaymentPlan.setProjectId(Long.parseLong(project_id));
		projectRepaymentPlan.setPlanDate(plan_date);
		projectRepaymentPlan.setPlanMoney(String.valueOf(plan_money));
		projectRepaymentPlan.setPrincipal(String.valueOf(principal));
		projectRepaymentPlan.setInterest(String.valueOf(interest));
		projectRepaymentPlan.setRateTicketInterest(rateTicketInterest);
		projectRepaymentPlan.setRemainingPrincipal(String.valueOf(remaining_principal));
		projectRepaymentPlan.setRemainingPrincipalInterest(String.valueOf(remaining_principal_interest));
		projectRepaymentPlan.setStatus(ProjectConstant.PROJECT_REPAY_PLAN_WILL_REPAY);
		projectRepaymentPlanDao.insert(projectRepaymentPlan);
	}
}
