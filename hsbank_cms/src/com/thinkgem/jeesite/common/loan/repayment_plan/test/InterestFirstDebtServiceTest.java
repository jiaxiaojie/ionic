package com.thinkgem.jeesite.common.loan.repayment_plan.test;

import java.util.List;

import com.thinkgem.jeesite.common.loan.repayment_plan.RepaymentPlanFactory;
import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.hsbank.util.type.DatetimeUtil;

/**
 * 测试：先息后本
 * @author Arthur.Xie
 * 2015-07-27
 */
public class InterestFirstDebtServiceTest {
	public static void main(String[] args) {
		//贷款总额
		double totalLoan = 10000.00;
		//年利率
		double annualInterestRate = 0.12;
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalMonth = 12;
		//开始日期
		String strBeginDate = "2015-08-03";
		//生成还款计划
		List<RepaymentPlanItem> repaymentPlan = RepaymentPlanFactory.getInstance(LoanConstant.REPAYMNET_METHOD_INTEREST_FIRST).generate(totalLoan, totalMonth, annualInterestRate, discount, strBeginDate);
		System.out.println("第 N 期,开始日期,截止日期,应还本息,应还本金,应还利息,剩余本金,累计应还本金,累计应还利息");
		for (RepaymentPlanItem item : repaymentPlan) {
			System.out.println(item.getIndex() + "," 
					+ DatetimeUtil.dateToString(item.getBeginDate()) + "," 
					+ DatetimeUtil.dateToString(item.getEndDate()) + "," 
					+ item.getPrincipalAndInterest() + "," 
					+ item.getPrincipal() + "," 
					+ item.getInterest() + "," 
					+ item.getPrincipalRemaining() + "," 
					+ item.getSumPrincipal() + "," 
					+ item.getSumInterest());
		}
		System.out.println("累计应还利息 = " + RepaymentPlanFactory.getInstance(LoanConstant.REPAYMNET_METHOD_INTEREST_FIRST).calculate(totalLoan, totalMonth, annualInterestRate, discount));
	}
}
