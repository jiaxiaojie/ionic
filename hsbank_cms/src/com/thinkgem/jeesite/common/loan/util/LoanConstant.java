package com.thinkgem.jeesite.common.loan.util;

/**
 * 借贷相关常量类
 * @author Arthur.Xie
 * 2015-07-26
 */
public class LoanConstant {
	/**还款方式：等额本息*/
	public static final String REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST = "average_capital_plus_interest";
	/**还款方式：等额本金*/
	public static final String REPAYMNET_METHOD_AVERAGE_CAPITAL = "average_capital";
	/**还款方式：到期还本付息*/
	public static final String REPAYMNET_METHOD_ONE_TIME = "one_time";
	/**还款方式：先息后本*/
	public static final String REPAYMNET_METHOD_INTEREST_FIRST = "interest_first";
	
	/**转让债权*/
	public static final String LOAN_ASSIGNMENT = "loan_assignment";
	/**剩余债权*/
	public static final String LOAN_REMAINING = "loan_remaining";
}
