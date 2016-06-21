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
 * 等额本息
 * <p/>
 * 等额本息：把本金总额与利息总额相加，然后平均分摊到还款期限的每个月中。每个月的还款额是固定的，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 * <p/>
 * 计算公式如下：
 * 每期应还本息 = [贷款总额×月利率×（1+月利率）^贷款期数]÷[（1+月利率）^贷款期数-1]
 * <p/>
 * 等额本息还款法实际上是等比数列
 * <p/>
 * 【等额本息】相对于【等额本金】需支付的利息比较多，因为贷款占用时间比较多。
 * 【等额本息】相对于【等额本金】刚开始时还款压力较小。
 * <p/>
 * <p/>
 * @author Arthur.Xie
 * 2015-07-26
 */
public class AverageCapitalPlusInterest implements IRepaymentPlan {
	/**日志对象*/
    private static Logger _log = Logger.getLogger(AverageCapitalPlusInterest.class);
	
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
		//剩余本金
		double principalRemaining = totalLoan;
		//累计应还利息
		double sumInterest = 0L;
		//累计应还本金
		double sumPrincipal = 0L;
		for (int month = 1; month <= totalMonth; month ++) {
			RepaymentPlanItem item = monthlyRepaymentAmount(totalLoan, realMonthlyInterestRate, totalMonth, month, beginDate);
			if (month == totalMonth) {
				//最后一个月
				//当期应还本金 = 上期剩余本金
				item.setPrincipal(principalRemaining);
				//当期应还本息 = 当期应还本金 + 当期应还利息
				double principalAndInterest = principalRemaining + item.getInterest();
				principalAndInterest = LoanUtil.formatAmount(principalAndInterest);
				item.setPrincipalAndInterest(principalAndInterest);
				//当期剩余本金 = 0
				principalRemaining = 0;
				item.setPrincipalRemaining(principalRemaining);
			} else {
				//当期剩余本金
				principalRemaining -= item.getPrincipal();
				principalRemaining = LoanUtil.formatAmount(principalRemaining);
				item.setPrincipalRemaining(principalRemaining);
			}
			//累计应还利息
			sumInterest += item.getInterest();
			sumInterest = LoanUtil.formatAmount(sumInterest);
			item.setSumInterest(sumInterest);
			//累计应还本金
			sumPrincipal = totalLoan - principalRemaining;
			sumPrincipal = LoanUtil.formatAmount(sumPrincipal);
			item.setSumPrincipal(sumPrincipal);
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
		//累计应还利息 = 贷款总额×贷款期数×月利率×（1＋月利率）^贷款期数÷（（1＋月利率）^贷款期数－1〕－贷款总额 
		return LoanUtil.formatAmount(totalLoan * totalMonth * realMonthlyInterestRate * Math.pow(1 + realMonthlyInterestRate, totalMonth) / (Math.pow(1 + realMonthlyInterestRate, totalMonth) - 1) - totalLoan);
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
	 * @param beginDate						开始日期
	 * @return
	 */
	public RepaymentPlanItem monthlyRepaymentAmount(double totalLoan, double monthlyInterestRate, int totalMonth, int month, Date beginDate) {
		RepaymentPlanItem resultValue = new RepaymentPlanItem();
		//<1>.当前期数
		resultValue.setIndex(month);
		//<2>.当期开始日期
		resultValue.setBeginDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, month - 1));
		//<3>.当期截止日期
		resultValue.setEndDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, month));
		//<4>.当期应还本息：[贷款总额×月利率×（1+月利率）^贷款期数]÷[（1+月利率）^贷款期数-1]
		double monthly_P_I = LoanUtil.formatAmount(totalLoan * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalMonth) / (Math.pow(1 + monthlyInterestRate, totalMonth) - 1));
		resultValue.setPrincipalAndInterest(monthly_P_I);
		//<5>.当期应还利息
		double monthly_I = LoanUtil.formatAmount(totalLoan * monthlyInterestRate * (Math.pow((1 + monthlyInterestRate), totalMonth) - Math.pow((1 + monthlyInterestRate), (month - 1))) / (Math.pow((1 + monthlyInterestRate), totalMonth) - 1));
		resultValue.setInterest(monthly_I);
		//<6>.当期应还本金（= 当期应还本金和利息 - 当期应还利息）
		double monthly_P = monthly_P_I - monthly_I;
		resultValue.setPrincipal(monthly_P);
		return resultValue;
	}
}
