package com.thinkgem.jeesite.common.loan.repayment_plan;

import com.thinkgem.jeesite.common.loan.repayment_plan.impl.AverageCapital;
import com.thinkgem.jeesite.common.loan.repayment_plan.impl.AverageCapitalPlusInterest;
import com.thinkgem.jeesite.common.loan.repayment_plan.impl.InterestFirstDailyServiceImpl;
import com.thinkgem.jeesite.common.loan.repayment_plan.impl.InterestFirstDebtService;
import com.thinkgem.jeesite.common.loan.repayment_plan.impl.OneTimeDailyServiceImpl;
import com.thinkgem.jeesite.common.loan.repayment_plan.impl.OneTimeDebtService;
import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.hsbank.util.type.StringUtil;

/**
 * 工厂模式: 生成还款计划
 * @author wuyuan.xie
 * CreateDate 2007-09-11
 */
public class RepaymentPlanFactory {
	
	/**
	 * 得到一个工厂接口的实现类的实例
	 * @param repaymentMethod 		还款方式
	 * @return 
	 */
	public static IRepaymentPlan getInstance(String repaymentMethod) {
		IRepaymentPlan instance = null;
		repaymentMethod = StringUtil.dealString(repaymentMethod).toLowerCase();
		if (LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST.equals(repaymentMethod)) {
			instance = new AverageCapitalPlusInterest();
		} else if (LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL.equals(repaymentMethod)) {
			instance = new AverageCapital();
		} else if (LoanConstant.REPAYMNET_METHOD_ONE_TIME.equals(repaymentMethod)) {
			instance = new OneTimeDebtService();
		} else if (LoanConstant.REPAYMNET_METHOD_INTEREST_FIRST.equals(repaymentMethod)) {
			instance = new InterestFirstDebtService();
		}
		return instance;
	}
	
	/**
	 * 按日计息接口实现类
	 * @param repaymentMethod
	 * @return
	 */
	public static RepaymentPlanDailyService getDailyInstance(String repaymentMethod) {
		RepaymentPlanDailyService instance = null;
		repaymentMethod = StringUtil.dealString(repaymentMethod).toLowerCase();
	    if (LoanConstant.REPAYMNET_METHOD_ONE_TIME.equals(repaymentMethod)) {
			instance = new OneTimeDailyServiceImpl();
		} else if (LoanConstant.REPAYMNET_METHOD_INTEREST_FIRST.equals(repaymentMethod)) {
			instance = new InterestFirstDailyServiceImpl();
		}
		return instance;
	}
}
