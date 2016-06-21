package com.thinkgem.jeesite.common.loan.repayment_plan.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.thinkgem.jeesite.common.loan.repayment_plan.IRepaymentPlan;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.type.StringUtil;
import com.hsbank.util.constant.DatetimeField;

/**
 * 先息后本
 * <p/>
 * 先息后本：按月付息、到期还本。借款人在借款到期日一次性归还借款本金，利息按月归还。
 * <p/>
 * 计算公式如下：
 * 每期应还利息 = [贷款总额×月利率]
 * <p/>
 * @author Arthur.Xie
 * 2015-07-26
 */
public class InterestFirstDebtService implements IRepaymentPlan {
	/**日志对象*/
    private static Logger _log = Logger.getLogger(InterestFirstDebtService.class);
	
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
		for (int month = 1; month <= totalMonth; month ++) {
			RepaymentPlanItem item = monthlyRepaymentAmount(totalLoan, realMonthlyInterestRate, totalMonth, month, beginDate);
			if (month == totalMonth) {
				//最后一个月
				//当期应还本金 = 整个项目的本金
				item.setPrincipal(totalLoan);
				//当期应还本息 = 当期应还本金 + 当期应还利息
				double principalAndInterest = totalLoan + item.getInterest();
				principalAndInterest = LoanUtil.formatAmount(principalAndInterest);
				item.setPrincipalAndInterest(principalAndInterest);
				//当期剩余本金 = 0
				item.setPrincipalRemaining(0);
			} else {
				//当期应还本金 = 0
				item.setPrincipal(0);
				//当期应还本息 = 当期应还利息
				item.setPrincipalAndInterest(item.getInterest());
				//当期剩余本金 = 整个项目的本金
				item.setPrincipalRemaining(totalLoan);
			}
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
		//累计应还利息 = 贷款额×月利率×贷款期数
		return LoanUtil.formatAmount(totalLoan * realMonthlyInterestRate * totalMonth);
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
		//<4>.当期应还利息
		double monthly_I = LoanUtil.formatAmount(totalLoan * monthlyInterestRate);
		resultValue.setInterest(monthly_I);
		//<5>.应还利息累计：当期应还利息 * 当期期数
		double sumInterest = monthly_I * month;
		sumInterest = LoanUtil.formatAmount(sumInterest);
		resultValue.setSumInterest(sumInterest);
		return resultValue;
	}
}
