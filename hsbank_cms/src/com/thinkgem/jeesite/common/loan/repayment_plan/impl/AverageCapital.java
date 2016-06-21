package com.thinkgem.jeesite.common.loan.repayment_plan.impl;

import com.hsbank.util.constant.DatetimeField;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.loan.repayment_plan.IRepaymentPlan;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 等额本金
 * <p/>
 * 等额本金：把贷款总额按贷款期数等分，每月偿还同等数额的本金和剩余贷款在该月所产生的利息。每月的还款本金额固定，而利息越来越少。
 * <p/>
 * 计算公式如下：
 * 每期应还本金 = 贷款总额 / 贷款期数
 * 每期应还利息 = 贷款总额×（贷款期数 - 当前期数）×月利率
 * 每期应还本息 =（贷款总额 / 贷款期数）+（贷款总额 - 累计已还本金）×月利率
 * 累计应还利息 =（贷款期数 + 1）×贷款额×月利率 / 2
 * 累计应还本金 = 贷款总额 / 贷款期数×当前期数
 * <p/>
 * 等额本金还款法实际上是等差数列
 * <p/>
 * 【等额本金】相对于【等额本息】需支付的利息比较少，因为贷款占用时间比较少。
 * 【等额本金】相对于【等额本息】刚开始时还款压力较大。
 * <p/>
 * @author Arthur.Xie
 * 2015-07-26
 */
public class AverageCapital implements IRepaymentPlan {
    private static Logger _log = Logger.getLogger(AverageCapital.class);
    
    @Override
	public List<RepaymentPlanItem> generate(double totalLoan, int totalMonth, double annualInterestRate, double discount, String strBeginDate) {
    	Date beginDate = StringUtil.toDate(strBeginDate);
		return generate(totalLoan, totalMonth, annualInterestRate, discount, beginDate);
	}

	@Override
	public List<RepaymentPlanItem> generate(double totalLoan, int totalMonth, double annualInterestRate, double discount, Date beginDate) {
		List<RepaymentPlanItem> resultValue = new ArrayList<RepaymentPlanItem>();
		//实际年利率
		double realAnnualInterestRate = annualInterestRate * discount;
		_log.debug("realAnnualInterestRate = " + realAnnualInterestRate);
		//实际月利率
		double realMonthlyInterestRate = LoanUtil.monthlyInterestRate(realAnnualInterestRate);
		_log.debug("realMonthlyInterestRate = " + realMonthlyInterestRate);
		//每期应还本金：贷款总额 / 贷款期数
		double monthlyPrincipal = LoanUtil.formatAmount(totalLoan / totalMonth);
		//累计应还利息
		double sumInterest = 0L;
		for (int month = 1; month <= totalMonth; month ++) {
			RepaymentPlanItem item = monthlyRepaymentAmount(totalLoan, realMonthlyInterestRate, totalMonth, month, monthlyPrincipal, beginDate);
			if (month == totalMonth) {
				//最后一个月
				//当期应还本金 = 贷款总额 - 每期应还本金 * (贷款期数 - 1)
				double principal = totalLoan - monthlyPrincipal * (totalMonth - 1);
				principal = LoanUtil.formatAmount(principal);
				item.setPrincipal(principal);
				//当期应还本息 = 当期应还本金 + 当期应还利息
				double principalAndInterest = principal + item.getInterest();
				principalAndInterest = LoanUtil.formatAmount(principalAndInterest);
				item.setPrincipalAndInterest(principalAndInterest);
			}
			//累计应还利息
			sumInterest += item.getInterest();;
			sumInterest = LoanUtil.formatAmount(sumInterest);
			item.setSumInterest(sumInterest);
			//-------------------------
			resultValue.add(item);
		}
		return resultValue;
	}
	
	@Override
	public double calculate(double totalLoan, int totalMonth, double annualInterestRate, double discount) {
		//实际年利率
		double realAnnualInterestRate = annualInterestRate * discount;
		_log.debug("realAnnualInterestRate = " + realAnnualInterestRate);
		//实际月利率
		double realMonthlyInterestRate = LoanUtil.monthlyInterestRate(realAnnualInterestRate);
		//累计应还利息 =（贷款期数 + 1）×贷款额×月利率 ÷ 2
		return LoanUtil.formatAmount((totalMonth + 1) * totalLoan * realMonthlyInterestRate / 2);
	}
	
	/**
	 * 计算每月还款金额
	 * <p/>
	 * ROUND_HALF_UP: 遇到.5的情况时往上近似,例: 1.5 -> 2
	 * <p/>
	 * @param totalLoan 					贷款总额，如：10000
	 * @param monthlyInterestRate 			月利率（= 年利率 / 12），如：年利率为12.0%，则月利率为1.0%，输入0.01
	 * @param totalMonth					贷款期数，如：12
	 * @param month 						第几期，如：1
	 * @param monthlyPrincipal				每期应还本金
	 * @param beginDate						开始日期
	 * @return
	 */
	public RepaymentPlanItem monthlyRepaymentAmount(double totalLoan, double monthlyInterestRate, int totalMonth, int month, double monthlyPrincipal, Date beginDate) {
		RepaymentPlanItem resultValue = new RepaymentPlanItem();
		//当前期数
		resultValue.setIndex(month);
		//当期开始日期
		resultValue.setBeginDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, month - 1));
		//当期截止日期
		resultValue.setEndDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, month));
		double monthly_P = monthlyPrincipal;
		if (month == totalMonth) {
			//最后一个月
			//<1>.当期应还本金 = 贷款总额 - 每期应还本金 * (贷款期数 - 1)
			monthly_P = totalLoan - monthlyPrincipal * (totalMonth - 1);
			monthly_P = LoanUtil.formatAmount(monthly_P);
			//<2>.当期剩余本金：0
			double monthly_PR = 0;
			resultValue.setPrincipalRemaining(monthly_PR);
			//<3>.累计应还本金
			double sumPrincipal = LoanUtil.formatAmount(totalLoan);
			resultValue.setSumPrincipal(sumPrincipal);
		} else {
			//<1>.当期应还本金
			resultValue.setPrincipal(monthly_P);
			//<2>.当期剩余本金：贷款总额 - 当前期数 × 每期应还本金
			double monthly_PR = LoanUtil.formatAmount(totalLoan - month * monthly_P);
			resultValue.setPrincipalRemaining(monthly_PR);
			//<3>.累计应还本金
			double sumPrincipal = LoanUtil.formatAmount(totalLoan - monthly_PR);
			resultValue.setSumPrincipal(sumPrincipal);
		}
		//当期应还利息：(贷款总额 - (当前期数 - 1) × 当期应还本金)×月利率
		double monthly_I = LoanUtil.formatAmount((totalLoan - (month - 1) * monthly_P) * monthlyInterestRate);
		resultValue.setInterest(monthly_I);
		//当期应还本息
		double monthly_P_I = monthly_P + monthly_I;
		monthly_P_I = LoanUtil.formatAmount(monthly_P_I);
		resultValue.setPrincipalAndInterest(monthly_P_I);
		return resultValue;
	}
}
