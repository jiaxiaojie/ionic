package com.thinkgem.jeesite.common.loan.repayment_plan.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.loan.repayment_plan.RepaymentPlanFactory;
import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.hsbank.util.type.DatetimeUtil;

/**
 * 测试：到期还本付息(按日计息)
 * @author lzb
 * 2016-04-01
 */
public class OneTimeDailyServiceImplTest {
	public static void main(String[] args) {
		//贷款总额
		double totalLoan = 10000.00;
		//年利率
		double annualInterestRate = 0.12;
		//年利率折扣
		double discount = 1.0;
		//贷款期数(日/月)
		int totalDuration = 14;
		String durationType = ProjectConstant.PROJECT_DURATION_TYPE_DAILY;
		//String durationType = ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY;
		//投标截止日期
		Date beginDate = DateUtils.parseDate("2016-03-15");
		//计息日期
		Date beginInterestDate = DateUtils.parseDate("2016-03-13");
		List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
		IncreaseInterestItem interestItem = new IncreaseInterestItem();
		interestItem.setInterestRate(0.005);
		interestItem.setInterestDuration(90);
		interestItem.setMaxAmount(50000.0);
		
		IncreaseInterestItem interestItem1 = new IncreaseInterestItem();
		interestItem1.setInterestRate(0.01);
		interestItem1.setInterestDuration(6);
		interestItem1.setMaxAmount(1000.0);
		interestItems.add(interestItem);
		interestItems.add(interestItem1);
		
		//生成还款计划
		List<RepaymentPlanItem> repaymentPlan = RepaymentPlanFactory.getDailyInstance(LoanConstant.REPAYMNET_METHOD_ONE_TIME).generate(interestItems, durationType, totalLoan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate);
		System.out.println("第 N 期,投标截止日期,还款日期,应还本息,应还本金,应还利息,加息券利息,剩余本金,累计应还本金,累计应还利息");
		for (RepaymentPlanItem item : repaymentPlan) {
			System.out.println(item.getIndex() + "," 
					+ DatetimeUtil.dateToString(item.getBeginDate()) + "," 
					+ DatetimeUtil.dateToString(item.getEndDate()) + "," 
					+ item.getPrincipalAndInterest() + "," 
					+ item.getPrincipal() + "," 
					+ item.getInterest() + "," 
					+ item.getRateTicketInterest() + "," 
					+ item.getPrincipalRemaining() + "," 
					+ item.getSumPrincipal() + "," 
					+ item.getSumInterest());
		}
		System.out.println("累计应还利息 = " + RepaymentPlanFactory.getDailyInstance(LoanConstant.REPAYMNET_METHOD_ONE_TIME).calculate(interestItems, durationType, totalLoan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate));
	}
}
