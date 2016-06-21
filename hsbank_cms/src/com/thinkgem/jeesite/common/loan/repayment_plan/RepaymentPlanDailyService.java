package com.thinkgem.jeesite.common.loan.repayment_plan;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;

/**
 * 还款计划接口（按日计息）
 * @author lzb
 * CreateDate 2016-03-31
 */
public interface RepaymentPlanDailyService {	
	
	/**
	 * 生成还款计划（按日计息）
	 * @param interestItems				加息券列表
	 * @param durationType				项目期限类型
	 * @param totalLoan					投资金额		
	 * @param totalDuration				项目期限（日/月）
	 * @param annualInterestRate		年利率
	 * @param discount					年利率折扣
	 * @param beginDate					投标截止日期
	 * @param beginInterestDate			计息开始日期
	 * @return
	 */
	public List<RepaymentPlanItem> generate(List<IncreaseInterestItem> interestItems, String durationType, double totalLoan, int totalDuration, double annualInterestRate, double discount, Date beginDate, Date beginInterestDate);
	
	/**
	 * 计算预期利息
	 * @param interestItems				加息券列表
	 * @param durationType				项目期限类型
	 * @param totalLoan					投资金额		
	 * @param totalDuration				项目期限（日/月）
	 * @param annualInterestRate		年利率
	 * @param discount					年利率折扣
	 * @param beginDate					投标截止日期
	 * @param beginInterestDate			计息开始日期
	 * @return
	 */
	public double calculate(List<IncreaseInterestItem> interestItems, String durationType, double totalLoan, int totalDuration, double annualInterestRate, double discount, Date beginDate, Date beginInterestDate);
}
