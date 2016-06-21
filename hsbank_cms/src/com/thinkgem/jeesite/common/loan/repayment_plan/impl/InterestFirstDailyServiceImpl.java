package com.thinkgem.jeesite.common.loan.repayment_plan.impl;

import com.hsbank.util.constant.DatetimeField;
import com.hsbank.util.type.DatetimeUtil;
import com.thinkgem.jeesite.common.loan.repayment_plan.RepaymentPlanDailyService;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 先息后本（按日计息）
 * <p/>
 * 先息后本：按月付息、到期还本。借款人在借款到期日一次性归还借款本金，利息按月归还。
 * <p/>
 * 计算公式如下：
 * 每期应还利息 = [贷款总额 × 日利率 × 每期天数]
 * <p/>
 * @author lzb
 * 2016-03-31
 */
public class InterestFirstDailyServiceImpl implements RepaymentPlanDailyService {
	/**日志对象*/
    private static Logger _log = Logger.getLogger(InterestFirstDailyServiceImpl.class);
	
    @Override
	public List<RepaymentPlanItem> generate(
			List<IncreaseInterestItem> interestItems, String durationType,
			double totalLoan, int totalDuration, double annualInterestRate,
			double discount, Date beginDate, Date beginInterestDate) {
    	List<RepaymentPlanItem> resultValue = new ArrayList<RepaymentPlanItem>();
		//实际年利率
		double realAnnualInterestRate = annualInterestRate * discount;
		//实际日利率
		double realDailyInterestRate = LoanUtil.dailyInterestRate(realAnnualInterestRate);
		_log.debug("realDailyInterestRate = " + realDailyInterestRate);
		double sumInterest =0D;
		//预期总利息
		double totalInterest = this.calculate(interestItems, durationType,totalLoan,totalDuration,annualInterestRate, discount, beginDate, beginInterestDate);
		Map<String,Object> rateTicketMap = new HashMap<String,Object>();
		for (int month = 1; month <= totalDuration; month ++) {
			RepaymentPlanItem item = monthlyRepaymentAmount(interestItems, rateTicketMap, totalLoan, realDailyInterestRate, totalDuration, month, beginDate, beginInterestDate);
			if (month == totalDuration) {
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
			sumInterest += LoanUtil.formatAmount(item.getInterest());
			//剩余应还利息
			double interestRemaining = LoanUtil.formatAmount(totalInterest - sumInterest);
			//剩余应还本息(本金 + 利息)
            double principalAndInterestRemaining  = LoanUtil.formatAmount(item.getPrincipalRemaining() + interestRemaining);
			item.setPrincipalAndInterestRemaining(principalAndInterestRemaining);
			item.setSumInterest(LoanUtil.formatAmount(sumInterest));
			resultValue.add(item);
		}
		return resultValue;
	}
    
    @Override
	public double calculate(List<IncreaseInterestItem> interestItems,
			String durationType, double totalLoan, int totalDuration,
			double annualInterestRate, double discount, Date beginDate, Date beginInterestDate) {
    	//<1>.实际年利率
		double realAnnualInterestRate = annualInterestRate * discount;
		//<2>.实际日利率
		double realDailyInterestRate = LoanUtil.dailyInterestRate(realAnnualInterestRate);
		double sumInterest =0D;
		Map<String,Object> rateTicketMap = new HashMap<String,Object>();
		for (int month = 1; month <= totalDuration; month ++) {
			RepaymentPlanItem item = monthlyRepaymentAmount(interestItems, rateTicketMap, totalLoan, realDailyInterestRate, totalDuration, month, beginDate, beginInterestDate);
			sumInterest += LoanUtil.formatAmount(item.getInterest());
		}
		return sumInterest;
	}
	
	/**
	 * 计算每月还款金额
	 * <p/>
	 * <p/>
	 * @param totalLoan 					贷款总额，如：10000
	 * @param realDailyInterestRate 		日利率（= 年利率 / 365）
	 * @param totalMonth					贷款期数，如：12
	 * @param month 						第几期，如：1
	 * @param beginDate						开始日期
	 * @return
	 */
	public RepaymentPlanItem monthlyRepaymentAmount(List<IncreaseInterestItem> interestItems, Map<String,Object> rateTicketMap, double totalLoan, double realDailyInterestRate, int totalMonth, int month, Date beginDate, Date beginInterestDate) {
		RepaymentPlanItem resultValue = new RepaymentPlanItem();
		//<1>.当前期数
		resultValue.setIndex(month);
		//<2>.当期开始日期
		resultValue.setBeginDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, month - 1));
		//<3>.当期截止日期
		resultValue.setEndDate(DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, month));
		//<4>.当期计息开始日期
		Date monthlyBeginDate = month > 1 ? resultValue.getBeginDate() : beginInterestDate;
		monthlyBeginDate = DateUtils.dateFormate(monthlyBeginDate);
		//<5>.当期期限
		int monthlyDuration = (int) DateUtils.getDistanceOfTwoDate(monthlyBeginDate, resultValue.getEndDate());
		//<6>.当期应还利息
		double monthly_I = LoanUtil.formatAmount(totalLoan * realDailyInterestRate * monthlyDuration);
		//<7>.加息券利息
		double monthly_RI = getRateTicketInterest(interestItems, rateTicketMap, monthlyDuration, totalLoan);
		monthly_I = LoanUtil.formatAmount(monthly_I + monthly_RI);
		resultValue.setInterest(monthly_I);
		resultValue.setRateTicketInterest(monthly_RI);
		return resultValue;
	}

	/**
	 * 计算加息券利息
	 * @param interestItems
	 * @param monthlyDuration
	 * @param totalLoan
	 * @return
	 */
	public static double getRateTicketInterest(List<IncreaseInterestItem> interestItems, Map<String,Object> rateTicketMap, int monthlyDuration, double totalLoan){
		double rateTicketInterest = 0D;
		for(IncreaseInterestItem interestItem : interestItems){
			int usedDuration = rateTicketMap.get(interestItem.getInterestId()) !=null ? (int)rateTicketMap.get(interestItem.getInterestId()) : 0;
			Double interestRate = interestItem.getInterestRate();
			//<1>.实际加息券日利率
			double realDailyInterestRate = LoanUtil.dailyInterestRate(interestRate);
			//<2>.剩余加息期限
			int surplusDuration = interestItem.getInterestDuration() - usedDuration;
			//<3>.加息期限 = 剩余加息期限 > 本期期限 ? 本期期限 : 剩余加息期限
			int interestDuration = surplusDuration > monthlyDuration ? monthlyDuration : surplusDuration;
			usedDuration += interestDuration;
			rateTicketMap.put(interestItem.getInterestId(), usedDuration);
			//<4>.加息金额 = 加息券上限额 > 投资金额 ? 投资金额 : 加息券上限额
			Double interestAmount = interestItem.getMaxAmount().compareTo(totalLoan) > 0 ? totalLoan : interestItem.getMaxAmount();
			rateTicketInterest += LoanUtil.formatAmount(interestAmount * realDailyInterestRate * interestDuration);
		}
		return LoanUtil.formatAmount(rateTicketInterest);
	}

	

	
}
