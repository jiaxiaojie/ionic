package com.thinkgem.jeesite.common.loan.credit_assignment;

import com.hsbank.util.type.DatetimeUtil;
import com.thinkgem.jeesite.common.loan.repayment_plan.impl.InterestFirstDailyServiceImpl;
import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;

import java.util.*;

/**
 * 债权转让: 根据【最原始标的】的还款计划，生成两个新的还款计划
 * <p/>
 * 【最原始标的】: 比如有一个总额是【10000元的标的】，第一次转让了2000元时，生成了一个【8000元的标的】和一个【2000元】的标的。
 * 【8000元的标的】再转让3000元时，【10000元的标的】就是最原始标的，转让债权为【3000元】，剩余债权为【5000元】
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
public class CreditAssignment implements ICreditAssignment {
	/**单例*/
	private static CreditAssignment instance = null;
	
	/**私有构造函数*/
	private CreditAssignment() {
	}
	
	/**得到单例*/
	public static CreditAssignment getInstance() {
        return instance == null ? instance = new CreditAssignment() : instance;
    }

	@Override
	public Map<String, List<RepaymentPlanItem>> generate(double totalLoan, double assignmentLoan, List<IncreaseInterestItem> interestItems, double remainingLoan, Date assignmentDate, List<RepaymentPlanItem> repaymentPlan) {
		Map<String, List<RepaymentPlanItem>> resultValue = new HashMap<String, List<RepaymentPlanItem>>();
		//当前期数
		int index = 0;
		//累计应还利息
		double sumInterest_assignment = 0L;
		double sumInterest_remaining = 0L;
		//累计应还本金
		double sumPrincipal_assignment = 0L;
		double sumPrincipal_remaining = 0L;
		if (totalLoan > 0 && assignmentLoan > 0 && totalLoan >= assignmentLoan) {
			//转让债权_还款计划
			List<RepaymentPlanItem> assignmentRepaymentPlan = new ArrayList<RepaymentPlanItem>();
			//剩余债权_还款计划
			List<RepaymentPlanItem> remainingRepaymentPlan = new ArrayList<RepaymentPlanItem>();
			double rateTicketInterest = 0D;
			Map<String,Object> rateTicketMap = new HashMap<String,Object>();
			for (RepaymentPlanItem item : repaymentPlan) {
				RepaymentPlanItem assignmentItem = getNewItem(item);
				RepaymentPlanItem remainingItem = getNewItem(item);
				Date beginDate = item.getBeginDate();
				Date endDate = item.getEndDate();
				if (assignmentDate.getTime() >=beginDate.getTime() && assignmentDate.getTime() <= endDate.getTime()) {
					//在转让区间：当期收益按天、再按额度拆分
					//<1>.期数
					index = 1;
					assignmentItem.setIndex(index);
					remainingItem.setIndex(index);
					//<2>.天数
					//当期还款区间天数
					long dayCount = DatetimeUtil.dayInterval(beginDate, endDate);
					//未产生利息的天数
					long dayCountRemaining = DatetimeUtil.dayInterval(assignmentDate, endDate);
					//<3>.应还利息
					//当期应还利息
					double interest = LoanUtil.formatAmount(item.getInterest() - item.getRateTicketInterest()) ;
					//当期加息券利息
					rateTicketInterest = InterestFirstDailyServiceImpl.getRateTicketInterest(interestItems, rateTicketMap, (int)dayCountRemaining, assignmentLoan);
					if (interest > 0) {
						//未产生的利息
						double interestRemaining = interest * ((double)dayCountRemaining / (double)dayCount);
						assignmentItem.setInterest(LoanUtil.formatAmount(interestRemaining * (assignmentLoan / totalLoan) + rateTicketInterest));
						remainingItem.setInterest(LoanUtil.formatAmount(item.getInterest() - assignmentItem.getInterest()));
					}
					assignmentItem.setRateTicketInterest(rateTicketInterest);
					sumInterest_assignment = assignmentItem.getInterest();
					sumInterest_remaining = remainingItem.getInterest();
				} else {
					//不在转让区：当期收益按额度拆分
					//<1>.期数
					index += 1;
					assignmentItem.setIndex(index);
					remainingItem.setIndex(index);
					//当期还款区间天数
					long dayCount = DatetimeUtil.dayInterval(beginDate, endDate);
					//<2>.应还利息
					double interest = LoanUtil.formatAmount(item.getInterest() - item.getRateTicketInterest()) ;
					//当期加息券利息
					rateTicketInterest = InterestFirstDailyServiceImpl.getRateTicketInterest(interestItems, rateTicketMap, (int)dayCount, assignmentLoan);
					if (interest > 0) {
						assignmentItem.setInterest(LoanUtil.formatAmount(interest * (assignmentLoan / totalLoan) + rateTicketInterest));
						remainingItem.setInterest(LoanUtil.formatAmount(interest * (remainingLoan / totalLoan)));
					}
					assignmentItem.setRateTicketInterest(rateTicketInterest);
					sumInterest_assignment += assignmentItem.getInterest();
					sumInterest_remaining += remainingItem.getInterest();
				}
				//应还本金
				double principal = item.getPrincipal();
				if (principal > 0) {
					assignmentItem.setPrincipal(LoanUtil.formatAmount(principal * (assignmentLoan / totalLoan)));
					remainingItem.setPrincipal(LoanUtil.formatAmount(principal * (remainingLoan / totalLoan)));
				}
				//应还本息
				assignmentItem.setPrincipalAndInterest(LoanUtil.formatAmount(assignmentItem.getPrincipal() + assignmentItem.getInterest()));
				remainingItem.setPrincipalAndInterest(LoanUtil.formatAmount(remainingItem.getPrincipal() + remainingItem.getInterest()));
				//--------------------------------------------
				//累计应还利息
				assignmentItem.setSumInterest(LoanUtil.formatAmount(sumInterest_assignment));
				remainingItem.setSumInterest(LoanUtil.formatAmount(sumInterest_remaining));
				//累计应还本金
				sumPrincipal_assignment += assignmentItem.getPrincipal();
				sumPrincipal_remaining += remainingItem.getPrincipal();
				assignmentItem.setSumPrincipal(LoanUtil.formatAmount(sumPrincipal_assignment));
				remainingItem.setSumPrincipal(LoanUtil.formatAmount(sumPrincipal_remaining));
				//剩余应还本金
				assignmentItem.setPrincipalRemaining(LoanUtil.formatAmount(assignmentLoan - sumPrincipal_assignment));
				remainingItem.setPrincipalRemaining(LoanUtil.formatAmount(remainingLoan - sumPrincipal_remaining));
				//--------------------------------------------
				assignmentRepaymentPlan.add(assignmentItem);
				remainingRepaymentPlan.add(remainingItem);
			}
			resultValue.put(LoanConstant.LOAN_ASSIGNMENT, assignmentRepaymentPlan);
			resultValue.put(LoanConstant.LOAN_REMAINING, remainingRepaymentPlan);
		}
		return resultValue;
	}
	
	/**
	 * 得到一个新的还款记录
	 * @param oldItem
	 * @return
	 */
	private RepaymentPlanItem getNewItem(RepaymentPlanItem oldItem) {
		RepaymentPlanItem resultValue = new RepaymentPlanItem();
		resultValue.setBeginDate(oldItem.getBeginDate());
		resultValue.setEndDate(oldItem.getEndDate());
		return resultValue;
	}
}
