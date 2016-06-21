package com.thinkgem.jeesite.modules.current;

public class CurrentProjectConstant {
	/**---------------------------活期项目状态------------------------*/
	/**取消*/
	public static final String CURRENT_PROJECT_STATUS_CANCEL = "-1";
	/**创建*/
	public static final String CURRENT_PROJECT_STATUS_CREATED = "0";
	/**提交审批*/
	public static final String CURRENT_PROJECT_STATUS_REVIEWING = "1";
	/**审批通过*/
	public static final String CURRENT_PROJECT_STATUS_REVIEW_PASS = "2";
	/**投标中*/
	public static final String CURRENT_PROJECT_STATUS_TENDERING = "3";
	/**投标结束*/
	public static final String CURRENT_PROJECT_STATUS_TENDER_OVER = "4";
	/**已清盘*/
	public static final String CURRENT_PROJECT_STATUS_WINDING_UPED = "5";

	/**---------------------------活期产品公告状态------------------------*/
	/**不启用*/
	public static final String CURRENT_PROJECT_NOTICE_STATUS_DISABLE = "0";
	/**启用*/
	public static final String CURRENT_PROJECT_NOTICE_STATUS_ENABLE = "1";

	/**---------------------------活期项目清盘状态------------------------*/
	/**无申请*/
	public static final String CURRENT_PROJECT_WINDING_UP_STATUS_NO_APPLY = "0";
	/**提交审批*/
	public static final String CURRENT_PROJECT_WINDING_UP_STATUS_REVIEWING = "1";
	/**审批通过*/
	public static final String CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS = "2";
	/**完成清盘*/
	public static final String CURRENT_PROJECT_WINDING_UP_STATUS_WINDING_UPED = "3";

	/**---------------------------活期产品审批结果------------------------*/
	/**不通过*/
	public static final String CURRENT_PROJECT_REVIEW_RESULT_UN_PASS = "0";
	/**通过*/
	public static final String CURRENT_PROJECT_REVIEW_RESULT_PASS = "1";
	
	/**---------------------------活期产品持有状态------------------------*/
	/**正常*/
	public static final String CURRENT_PROJECT_HOLD_STATUS_NORMAL = "0";
	/**已清盘*/
	public static final String CURRENT_PROJECT_HOLD_STATUS_WINDING_UPED = "1";

	/**---------------------------活期产品赎回申请状态------------------------*/
	/**申请失败*/
	public static final String CURRENT_PROJECT_REDEMPTION_APPLY_STATUS_FAILED = "-1";
	/**申请中*/
	public static final String CURRENT_PROJECT_REDEMPTION_APPLY_DOING = "0";
	/**申请通过*/
	public static final String CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS = "1";
	/**已赎回*/
	public static final String CURRENT_PROJECT_REDEMPTION_APPLY_REDEEMED = "2";

	/**---------------------------活期账户本金变更类型------------------------*/
	/**投资*/
	public static final String CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT = "0";
	/**赎回*/
	public static final String CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_REDEEM = "1";
	/**清盘*/
	public static final String CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_WINDING_UP = "2";
	
	/**---------------------------活期账户本金变更状态------------------------*/
	/**已撤销*/
	public static final String CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_CANCEL = "-1";
	/**正常*/
	public static final String CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL = "0";
	/**冻结中*/
	public static final String CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE = "1";

	/**---------------------------活期账户利息变更类型------------------------*/
	/**收息*/
	public static final String CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST = "0";
	/**赎回*/
	public static final String CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_REDEEM = "1";
	/**清盘*/
	public static final String CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_WINDING_UP = "2";

	/**---------------------------是否是工作日------------------------*/
	/**否*/
	public static final String IS_WORKDAY_NO = "0";
	/**是*/
	public static final String IS_WORKDAY_YES = "1";

	/**---------------------------最大持有金额------------------------*/
	public static final double MAX_INVESTMENT_MONEY = 100000;
	/**---------------------------单日最大赎回金额------------------------*/
	public static final double ONE_DAY_MAX_REDEEM_MONEY = 50000;
	/**-----赎回到账时间分割点（工作日时间此时间点前赎回当日到账，否则次日到账）-----*/
	public static final String REDEEM_SEPARATE_TIME = "15:00:00";
	
	/**-------------------------一年的天数---------------------------*/
	public static final int DAYS_IN_YEAR = 365;

	/**-------------------------本金变化原因---------------------------*/
	public static final String PRINCIPAL_CHANGE_REASON_REDEEM = "赎回活期产品本金";
	public static final String PRINCIPAL_CHANGE_REASON_WINDING_UP = "活期产品清盘";
	/**-------------------------利息变化原因---------------------------*/
	public static final String INTEREST_CHANGE_REASON_POLL = "提取活期产品收益";
	public static final String INTEREST_CHANGE_REASON_WINDING_UP = "活期产品清盘";
	
	/**---------------------------yes or no----------------------------*/
	/**否*/
	public static final String NO = "0";
	/**是*/
	public static final String YES = "1";
	
	/**--------------------活期赎回信息记录原因-----------------------*/
	public static final String CURRENT_APPLY_AMOUNT_BZ = "融资人可用于赎回余额不足!";
	public static final String CURRENT_APPLY_FINISH = "活期产品赎回完成!";
	
	/**--------------------活期赎回状态-----------------------*/
	public static final String CURRENT_APPLY_DEFOULT = "0";
	public static final String CURRENT_APPLY_SCESSES = "1";

	/**--------------------每万元日收益额度-----------------------*/
	public static final double ONE_DAY_TEN_THOUSAND_PROFIT = 10000;
	
	
	
	
}
