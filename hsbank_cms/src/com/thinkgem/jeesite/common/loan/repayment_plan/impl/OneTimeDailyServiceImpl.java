package com.thinkgem.jeesite.common.loan.repayment_plan.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.thinkgem.jeesite.common.loan.repayment_plan.RepaymentPlanDailyService;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.DatetimeUtil;
import com.hsbank.util.constant.DatetimeField;

/**
 * 到期还本付息（按日计息）
 * <p/>
 * 到期还本付息：就是借款人借到钱之后，期间不用归还任何资金，到期之后一次性归还本金和利息。
 * <p/>
 * 计算公式如下：
 * <1>.本息总额：		到期还本付息额=贷款总额 ×[年利率（%）÷ 365] × 贷款期（日）
 * 其中：日利率=年利率÷365
 * <p/>
 * 【到期还本付息】简单，适合短期借贷。
 * <p/>
 * @author lzb
 * 2016-03-31
 */
public class OneTimeDailyServiceImpl implements RepaymentPlanDailyService {
	/**日志对象*/
    private static Logger _log = Logger.getLogger(OneTimeDailyServiceImpl.class);
   

	@Override
	public List<RepaymentPlanItem> generate(
			List<IncreaseInterestItem> interestItems, String durationType, double totalLoan,
			int totalDuration, double annualInterestRate, double discount,
			Date beginDate, Date beginInterestDate) {
		List<RepaymentPlanItem> resultValue = new ArrayList<RepaymentPlanItem>();
		//实际年利率
		double realAnnualInterestRate = annualInterestRate * discount;
		//实际日利率
		double realDailyInterestRate = LoanUtil.dailyInterestRate(realAnnualInterestRate);
		_log.debug("realDailyInterestRate = " + realDailyInterestRate);
		RepaymentPlanItem item = new RepaymentPlanItem();
		//<1>.当前期数
		item.setIndex(1);
		//<2>.当期开始日期
		item.setBeginDate(beginDate);
		Date endDate = DatetimeUtil.getDate(beginDate, DatetimeField.DAY, totalDuration);
		//<3>.项目期限(按月)
		if(ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY.equals(durationType)){
			endDate = DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, totalDuration);
		}
		//<4>.实际计息期限
		int realDuration = (int) DateUtils.getDistanceOfTwoDate(beginInterestDate, endDate);
		_log.debug("realDuration = " + realDuration);
		//<5>.当期截止日期
		item.setEndDate(endDate);
		//<6>.到期一次应还利息 = 贷款总额×日利率（‰）× 计息期限（日）
		double interest = LoanUtil.formatAmount(totalLoan * realDailyInterestRate * realDuration);
		_log.debug("interest = " + interest);
		//<7>.加息券利息
		double rateTicketInterest = getRateTicketInterest(interestItems, realDuration, totalLoan);
		_log.debug("rateTicketInterest = " + rateTicketInterest);
		interest = LoanUtil.formatAmount(interest + rateTicketInterest);
		item.setInterest(interest);
		item.setRateTicketInterest(rateTicketInterest);
		//<8>.到期一次应还本息
		double principalAndInterest = totalLoan + interest;
		principalAndInterest = LoanUtil.formatAmount(principalAndInterest);
		item.setPrincipalAndInterest(principalAndInterest);
		//<9>.到期一次应还本金
		double principal = LoanUtil.formatAmount(totalLoan);
		item.setPrincipal(principal);
		//<10>.到期剩余本金
		item.setPrincipalRemaining(0);
		//<11>.到期剩余本息
		item.setPrincipalAndInterestRemaining(0);
		//<12>.累计应还本金
		item.setSumPrincipal(totalLoan);
		//<13>.累计应还利息
		item.setSumInterest(interest);
		//----------------
		resultValue.add(item);
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
		Date endDate = DatetimeUtil.getDate(beginDate, DatetimeField.DAY, totalDuration);
		//<3>.项目期限(按月)
		if(ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY.equals(durationType)){
			endDate = DatetimeUtil.getDate(beginDate, DatetimeField.MONTH, totalDuration);
		}
		//<4>.实际计息期限
		int realDuration = (int) DateUtils.getDistanceOfTwoDate(beginInterestDate, endDate);
		realDuration = ProjectConstant.PROJECT_DURATION_TYPE_DAILY.equals(durationType) ? realDuration + 1 : realDuration;
		//<5>.到期一次应还利息 = 贷款总额×日利率（‰）× 计息期限（日）
		double interest = LoanUtil.formatAmount(totalLoan * realDailyInterestRate * realDuration);
		//<6>.加息券利息
		double rateTicketInterest = getRateTicketInterest(interestItems, realDuration, totalLoan);
		interest = LoanUtil.formatAmount(interest + rateTicketInterest);
		return interest;
	}
	
	
	/**
	 * 计算加息券利息
	 * @param interestItems
	 * @param realDuration
	 * @param totalLoan
	 * @return
	 */
	public static double getRateTicketInterest(List<IncreaseInterestItem> interestItems, int realDuration, double totalLoan){
		double rateTicketInterest = 0D;
		for(IncreaseInterestItem interestItem : interestItems){
			Double interestRate = interestItem.getInterestRate();
			//<1>.实际加息券日利率
			double realDailyInterestRate = LoanUtil.dailyInterestRate(interestRate);
			//<2>.加息期限 = 加息券期限 > 实际计息期限 ? 实际计息期限 : 加息券期限
			int interestDuration = interestItem.getInterestDuration() > realDuration ? realDuration : interestItem.getInterestDuration();
			//<3>.加息金额 = 加息券上限额 > 投资金额 ? 投资金额 : 加息券上限额
			Double interestAmount = interestItem.getMaxAmount().compareTo(totalLoan) > 0 ? totalLoan : interestItem.getMaxAmount();
			rateTicketInterest += LoanUtil.formatAmount(interestAmount * realDailyInterestRate * interestDuration);
		}
		return LoanUtil.formatAmount(rateTicketInterest);
	}
	
}
