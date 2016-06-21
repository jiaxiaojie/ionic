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
 * 到期还本付息
 * <p/>
 * 到期还本付息：就是借款人借到钱之后，期间不用归还任何资金，到期之后一次性归还本金和利息。
 * <p/>
 * 计算公式如下：
 * <1>.贷款期为一年的：		到期还本付息额=贷款总额×[1+年利率（%）]
 * <2>.贷款期不到一年：		到期还本付息额=贷款总额×[1+月利率（‰）×贷款期（月）]
 * 其中：月利率=年利率÷12
 * <p/>
 * 【到期还本付息】简单，适合短期借贷。
 * <p/>
 * @author Arthur.Xie
 * 2015-07-26
 */
public class OneTimeDebtService implements IRepaymentPlan {
	/**日志对象*/
    private static Logger _log = Logger.getLogger(OneTimeDebtService.class);
    
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
		RepaymentPlanItem item = new RepaymentPlanItem();
		//<1>.当前期数
		item.setIndex(1);
		//<2>.当期开始日期
		item.setBeginDate(beginDate);
		//<3>.当期截止日期
		item.setEndDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, 1));
		//<4>.到期一次应还利息 = 贷款总额×月利率（‰）×贷款期数（月）
		double interest = LoanUtil.formatAmount(totalLoan * realMonthlyInterestRate * totalMonth);
		item.setInterest(interest);
		//<5>.到期一次应还本息
		double principalAndInterest = totalLoan + interest;
		principalAndInterest = LoanUtil.formatAmount(principalAndInterest);
		item.setPrincipalAndInterest(principalAndInterest);
		//<6>.到期一次应还本金
		double principal = LoanUtil.formatAmount(totalLoan);
		item.setPrincipal(principal);
		//<7>.到期剩余本金
		item.setPrincipalRemaining(0);
		//<8>.累计应还本金
		item.setSumPrincipal(totalLoan);
		//<9>.累计应还利息
		item.setSumInterest(interest);
		//----------------
		resultValue.add(item);
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
}
