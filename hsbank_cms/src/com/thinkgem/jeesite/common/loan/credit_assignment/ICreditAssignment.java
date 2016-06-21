package com.thinkgem.jeesite.common.loan.credit_assignment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;

/**
 * 债权转让接口
 * <p/>
 * 【最原始标的】: 比如有一个总额是【10000元的标的】，第一次转让了1000元时，生成了一个【9000元的标的】和一个【1000元】的标的。
 * 【9000元的标的】再转让2000元时，【10000元的标的】就是最原始标的，转让债权为【2000元】，剩余债权为【7000元】
 * <p/>
 * @author wuyuan.xie
 * CreateDate 2015-07-27
 */
public interface ICreditAssignment {	
	/**
	 * 根据【最原始标的】的还款计划，生成两个新的还款计划
	 * @param totalLoan 					贷款总额，如：10000
	 * @param assignmentLoan				转让债权，如：1000
	 * @param interestItems					加息券列表
	 * @param remainingLoan					剩余债权，如：7000
	 * @param assignmentDate				转让日期
	 * @param repaymentPlan					原还款计划
	 * @return
	 */
	public Map<String, List<RepaymentPlanItem>> generate(double totalLoan, double assignmentLoan, List<IncreaseInterestItem> interestItems, double remainingLoan, Date assignmentDate, List<RepaymentPlanItem> repaymentPlan);
}
