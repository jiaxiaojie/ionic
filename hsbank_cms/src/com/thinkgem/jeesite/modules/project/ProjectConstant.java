package com.thinkgem.jeesite.modules.project;

/**
 * P2P常量
 * @author Arthur.Xie
 * 2015-07-31
 */
public class ProjectConstant {
	/**是否需要提示开通第三方账户*/
	public static final String KEY_NEED_THIRD_ACCOUNT_TIP = "need_third_account_tip";
	/**默认风控信息文章Id*/
	public static String DEFAULT_RISK_INFO_ID = "5df72c7559c848fb91edfd344674bd27";
	/**邮箱验证码超时时间（单位：秒）*/
  	public static final long EMAIL_VALIDATE_CODE_TIMEOUT = 300;
	/**字典类型的默认值*/
	public static final String DICT_DEFAULT_VALUE = "0";
	/**一天最多提现次数*/
	public static final int ONEDAY_MAX_WITHDRAW_COUNT = 1;
	/**-------------------------------投资服务费收取策略-------------------------------*/
	/**不收取*/
	public static final String PROJECT_SERVICE_CHARGE_TYPE_SERVICE_ZERO = "0";
	/**优先收取*/
	public static final String PROJECT_SERVICE_CHARGE_TYPE_SERVICE_CHARGE_FIRST = "1";
	/**按比例收取*/
	public static final String PROJECT_SERVICE_CHARGE_TYPE_BY_RATE = "2";
	/**-------------------------------投资方式-------------------------------*/
	/**直投*/
	public static final String PROJECT_INVESTMENT_TYPE_DIRECT = "1";
	/**债权转让*/
	public static final String PROJECT_INVESTMENT_TYPE_ASSIGNMENT = "2";
	/**-------------------------------项目期限类型-------------------------------*/
	/**按日*/
	public static final String PROJECT_DURATION_TYPE_DAILY = "1";
	/**按月*/
	public static final String PROJECT_DURATION_TYPE_MONTHLY = "2";
	/**-------------------------------还款方式---------------------------------------*/
	/**还款方式：等额本息*/
	public static final String REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST = "3";
	/**还款方式：等额本金*/
	public static final String REPAYMNET_METHOD_AVERAGE_CAPITAL = "4";
	/**还款方式：到期还本付息*/
	public static final String REPAYMNET_METHOD_ONE_TIME = "1";
	/**还款方式：先息后本*/
	public static final String REPAYMNET_METHOD_INTEREST_FIRST = "2";
	/**-------------------------------还款类型---------------------------------------*/
	/**正常还款*/
	public static final String PROJECT_REPAYMENT_TYPE_NORMAL = "0";
	/**提前还款*/
	public static final String PROJECT_REPAYMENT_TYPE_EARLY = "1";
	/**逾期还款*/
	public static final String PROJECT_REPAYMENT_TYPE_LATE = "2";
	/**转让还款*/
	public static final String PROJECT_REPAYMENT_TYPE_TRANSFER = "4";
	/**-------------------------------还款状态---------------------------------------*/
	/**已撤销*/
	public static final String PROJECT_REPAY_STATUS_REPEAL = "-1";
	/**预算*/
	public static final String PROJECT_REPAY_STATUS_BUDGET = "0";
	/**已还款*/
	public static final String PROJECT_REPAY_STATUS_ALREADY = "1";
	/**已转让*/
	public static final String PROJECT_REPAY_STATUS_TRANSFER = "2";
	/**已冻结*/
	public static final String PROJECT_REPAY_STATUS_FREEZE = "3";
	/**-------------------------------融资意向状态-------------------------------*/
	/**等待审批*/
	public static final String PROJECT_WILL_STATUS_WAITTING_REVIEW = "0";
	/**审批通过*/
	public static final String PROJECT_WILL_STATUS_REVIEW_PASS = "1";
	/**审批拒绝*/
	public static final String PROJECT_WILL_STATUS_REVIEW_REJECT = "2";
	/**-------------------------------项目状态-----------------------------------*/
	/**取消*/
	public static final String PROJECT_STATUS_CANCEL = "-1";
	/**初始创建*/	
	public static final String PROJECT_STATUS_CREATE = "0";
	/**提交审批*/
	public static final String PROJECT_STATUS_SUBMIT = "1";
	/**审批通过*/
	public static final String PROJECT_STATUS_REVIEW_PASS = "2"; 
 	/**投标中*/
	public static final String PROJECT_STATUS_INVESTMENT = "3";
	/**投标结束*/
	public static final String PROJECT_STATUS_INVESTMENT_FINISHED = "4";
	/**还款中*/
	public static final String PROJECT_STATUS_REPAYMENTING = "5";
	/**还款结束*/
	public static final String PROJECT_STATUS_REPAYMENT_END = "6";
	/**清算结束*/
	public static final String PROJECT_STATUS_END = "7"; 
	/**流标*/
	public static final String PROJECT_STATUS_FAILED="8";
	/**-------------------------------还款计划状态-------------------------------*/
	/**未还款*/
	public static final String PROJECT_REPAY_PLAN_WILL_REPAY = "0";
	/**正常还款*/
	public static final String PROJECT_REPAY_PLAN_NORMAL_REPAY = "1";
	/**提前还款*/
	public static final String PROJECT_REPAY_PLAN_EARLY_REPAY = "2";
	/**逾期还款*/
	public static final String PROJECT_REPAY_PLAN_OVERDUE_REPAY = "3";
	/**-------------------------------债权必须持有天数-------------------------------*/
	/**债权不允许转让*/
	public static final int PROJECT_TRANSFER_CODE_WITHOUT_PERMISSION = -1;
	/**债权必须持有00天以上*/
	public static final int PROJECT_TRANSFER_CODE_0 = 0;
	/**债权必须持有30天以上*/
	public static final int PROJECT_TRANSFER_CODE_30 = 30;
	/**债权必须持有60天以上*/
	public static final int PROJECT_TRANSFER_CODE_60 = 60;
	/**债权必须持有70天以上*/
	public static final int PROJECT_TRANSFER_CODE_90 = 90;
	/**-------------------------------项目投资状态-------------------------------*/
	/**已撤销*/
	public static final String PROJECT_INVESTMENT_STATUS_REPEAL = "-1";
	/**正常*/
	public static final String PROJECT_INVESTMENT_STATUS_NORMAL = "0";
	/**已转让*/
	public static final String PROJECT_INVESTMENT_STATUS_TRANSFER = "1";
	/**冻结中*/
	public static final String PROJECT_INVESTMENT_STATUS_FREEZE = "2";
	/**还款结束*/
	public static final String PROJECT_INVESTMENT_STATUS_END = "3";

	/**-------------------------------合同执行状态-------------------------------*/
	/**正常*/
	public static final String PROJECT_EXECUTE_SNAPSHOT_STATUS_NORMAL = "0";
	/**逾期还款*/
	public static final String PROJECT_EXECUTE_SNAPSHOT_STATUS_OVERDUE = "1";
	/**提前还款*/
	public static final String PROJECT_EXECUTE_SNAPSHOT_STATUS_EARLY = "2";
	/**代偿*/
	public static final String PROJECT_EXECUTE_SNAPSHOT_STATUS_COMPENSATION = "3";
	/**-------------------------------操作终端-------------------------------*/
	/**PC*/
	public static final String OP_TERM_DICT_PC = "0";
	/**ANDROID*/
	public static final String OP_TERM_DICT_ANDROID = "1";
	/**IOS*/
	public static final String OP_TERM_DICT_IOS = "2";
	/**WEIXIN*/
	public static final String OP_TERM_DICT_WEIXIN = "3";
	/**-------------------------------会员优惠卷状态-------------------------------*/
	/**正常*/
	public static final String TICKET_DICT_NORMAL = "0";
	/**已使用*/
	public static final String TICKET_DICT_USED = "1";
	/**过期*/
	public static final String TICKET_DICT_OVER = "2";
	/**-------------------------------账户余额变更方式-------------------------------*/
	/**B2B 网银*/
	public static final String CHANGE_TYPE_BALANCE_NET_B2B_RECHARGE="NET_B2B";
	/**B2C 网银*/
	public static final String CHANGE_TYPE_BALANCE_NET_B2C_RECHARGE="NET_B2C";
	/**一键支付*/
	public static final String CHANGE_TYPE_BALANCE_A_PAY_RECHARGE="A_PAY";
	/**代充值*/
	public static final String CHANGE_TYPE_BALANCE_WH_NO_CARD_RECHARGE="WH_NO_CARD";
	/**快捷充值*/
	public static final String CHANGE_TYPE_BALANCE_SWIFT_RECHARGE="SWIFT";
	/**正常提现，T+1 天到账*/
	public static final String CHANGE_TYPE_BALANCE_NORMAL_WITHDRAW="NORMAL";
	/**加急提现，T+0 当日到账*/
	public static final String CHANGE_TYPE_BALANCE_URGENT_WITHDRAW="URGENT";
	/**投资冻结*/
	public static final String CHANGE_TYPE_BALANCE_INVEST_FREEZE="1";
	/**投资冻结取消*/
	public static final String CHANGE_TYPE_BALANCE_INVEST_CANCEL_FREEZE="2";
	/**投资确认*/
	public static final String CHANGE_TYPE_BALANCE_INVEST_CONFIRM="3";
	/**还款*/
	public static final String CHANGE_TYPE_BALANCE_REPAYMENT="4";
	/**充值获取抵用额*/
	public static final String CHANGE_TYPE_BALANCE_RECHAGE_GET="5";
	/**推荐好友投资返利*/
	public static final String CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT="6";
	/**首次充值送现金*/
	public static final String CHANGE_TYPE_BALANCE_RECHARGE_GIVE_AMOUNT="7";
	/**活动返利*/
	public static final String CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT="8";
	/**赎回活期产品本金*/
	public static final String CHANGE_TYPE_BALANCE_CURRENT_PROJECT_REDEEM_PRINCIPAL = "9";
	/**提取活期产品利息*/
	public static final String CHANGE_TYPE_BALANCE_CURRENT_PROJECT_POLL_INTEREST = "10";
	/**活期产品清盘*/
	public static final String CHANGE_TYPE_BALANCE_CURRENT_PROJECT_WINDING_UP = "11";

	/**-------------------------------会员优惠劵使用状态-------------------------------*/
	/**充值赠送*/
	public static final String CHANGE_TYPE_TICKET_RECHARGE="0";
	/**投资使用*/
	public static final String CHANGE_TYPE_TICKET_INVEST="1";
	/**-------------------------------是否已经开通第三方账号-------------------------------*/
	/**已开通*/
	public static final String HASOPENED = "1";
	/**未开通*/
	public static final String UNOPENED = "0";
	/**-------------------------------债权转让状态-------------------------------*/
	/**正在转让*/
	public static final String PROJECT_TRANSFER_STATUS_RUNNING="0";
	/**全部转让结束*/
	public static final String PROJECT_TRANSFER_STATUS_ALLEND="1";
	/**部分转让结束*/
	public static final String PROJECT_TRANSFER_STATUS_SOMEEND="2";
	/**转让失败*/
	public static final String PROJECT_TRANSFER_STATUS_FAIL="3";
	/**项目逾期引起的下线*/
	public static final String PROJECT_TRANSFER_STATUS_OVERDUE="4";
	/**用户主动取消下线*/
	public static final String PROJECT_TRANSFER_STATUS_CANCEL="5";
	
	/**-------------------------------债权转让服务费收取方式-------------------------------*/
	/**上家负责*/
	public static final String PROJECT_TRANSFER_SERVICE_CHAGE_TYPE_UP_ALL="1";
	/**下家负责*/
	public static final String PROJECT_TRANSFER_SERVICE_CHAGE_TYPE_DOWN_ALL="2";
	/**上下架对半*/
	public static final String PROJECT_TRANSFER_SERVICE_CHAGE_TYPE_UP_DOWN_HALF="3";
	
			
	/**-------------------------------不正常还款罚息比例-------------------------------*/
	/**提前还款罚息比例*/
	public static  double PROJECT_EARLY_REPAY_DEFAULT_PAYMENT_RATIO = 0.02;
	/**逾期还款日罚息比例*/
	public static  double PROJECT_OVERDUE_REPAY_DEFAULT_PAYMENT_RATIO = 0.0005;
	
	/**-------------------------------个人信用贷-------------------------------*/
	/**个人信用贷年化利率*/
	public static  double PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE = 0.132;
	/**个人信用贷服务费费率*/
	public static  double PROJECT_PERSONAL_CREDIT_LOAN_SERVICE_RATE = 0.03;
	/**个人信用贷最高额度*/
	public static  double PROJECT_PERSONAL_CREDIT_LOAN_MAX_VALUE = 1000000;
	/**个人信用贷缺省期数*/
	public static  int PROJECT_PERSONAL_CREDIT_LOAN_MAX_TERMS = 24;
	
	/**投资转让费率*/
	public static  double PROJECT_TRANSFER_SERVICE_RATE=0.002;

	/**前端分页页容量*/
	public static int FRONT_PAGE_SIZE = 12;

	/**-------------------------------重置密码方式-------------------------------*/
	/**重置密码：邮箱*/
	public static final String CUSTOMER_CHANGE_PASSWORD_BY_EMAIL_RESET = "0";
	/**重置密码：短信*/
	public static final String CUSTOMER_CHANGE_PASSWORD_BY_MOBILE_RESET = "1";
	/**修改密码*/
	public static final String CUSTOMER_CHANGE_PASSWORD_BY_NORMAL_CHANGE = "2";
	
	/**-------------------------------重置密码状态-------------------------------*/
	/**成功*/
	public static final String CUSTOMER_RESET_PASSWORD_STATUS_SUCCESS = "0";
	/**失败*/
	public static final String CUSTOMER_RESET_PASSWORD_STATUS_FAIL = "1";
	
	/**----------------------------发送（短信、邮件）验证码的业务类型------------------------*/
	/**重置密码*/
	public static final String SEND_VALIDATE_CODE_SERVICE_TYPE_RESET_PASSWORD = "0";
	/**修改密码*/
	public static final String SEND_VALIDATE_CODE_SERVICE_TYPE_CHANGE_PASSWORD = "1";
	/**注册新用户*/
	public static final String SEND_VALIDATE_CODE_SERVICE_TYPE_REGISTER = "2";
	/**修改邮箱*/
	public static final String SEND_VALIDATE_CODE_SERVICE_TYPE_CHANGE_EMAIL = "3";
	
	/**-------------------------------是否通过认证-------------------------------*/
	/**否*/
	public static final String UNAUTHED = "0";
	/**是*/
	public static final String HAS_AUTHED = "1";
	
	/**-------------------------------会员可免费提现次数-------------------------------*/
	/**变更类型：活动赠送*/
	public static final String FREE_WITHDRAW_COUNT_CHANGE_TYPE_ACTIVITY_GIVE = "0";
	/**变更类型：提现使用*/
	public static final String FREE_WITHDRAW_COUNT_CHANGE_TYPE_WITHDRAW_USE = "1";
	/**变更类型：花生豆兑换*/
	public static final String FREE_WITHDRAW_COUNT_CHANGE_TYPE_PEANUT_EXCHANGE = "2";
	
	
	/**-------------------------------会员信用认证状态-------------------------------*/
	/**认证失败*/
	public static final String CUSTOMER_CREDIT_AUTH_STATUS_FAILED = "-1";
	/**未认证*/
	public static final String CUSTOMER_CREDIT_AUTH_STATUS_UNCOMMIT = "0";
	/**审核中*/
	public static final String CUSTOMER_CREDIT_AUTH_STATUS_AUTHING = "1";
	/**已认证*/
	public static final String CUSTOMER_CREDIT_AUTH_STATUS_AUTHED = "2";
	
	/**----------------------------------会员花生豆变更类型-------------------------------*/
	/**签到*/
	public static final String CUSTOMER_INTEGRAL_CHANGE_TYPE_SIGN = "0";
	/**消费*/
	public static final String CUSTOMER_INTEGRAL_CHANGE_TYPE_EXPENSE = "1";
	/**活动奖励*/
	public static final String CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY = "2";
	/**取消消费返还*/
	public static final String CUSTOMER_INTEGRAL_CHANGE_TYPE_CANCEL_EXPENSE="3";
	
	/**----------------------------------签到获得的积分值-------------------------------*/
	/**首次签到赠送花生豆数量*/
	public static final int CUSTOMER_INTEGRAL_CHANGE_VALUE_SIGN_FIRST = 10;
	/**累计签到赠送花生豆数量递增值*/
	public static final int CUSTOMER_INTEGRAL_CHANGE_VALUE_SIGN_INCREMENT = 0;
	/**签到赠送花生豆数量最大值*/
	public static final int CUSTOMER_INTEGRAL_CHANGE_VALUE_SIGN_MAX = 100;

	/**----------------------------------连续签到天数-------------------------------*/
	/**连续签到第20天*/
	public static final int CUSTOMER_CONSECUTIVE_SIGN_20_DAYS = 20;
	/**连续签到第30天*/
	public static final int CUSTOMER_CONSECUTIVE_SIGN_30_DAYS = 30;

    /**----------------------------------连续签到赠送现金券-------------------------------*/
	/**连续签到第20天，可获得5元现金券*/
	public static final int[] CUSTOMER_CONSECUTIVE_SIGN_20_DAY_GIVE_INVESTMENT_TICKET = {5};
	/**连续签到第30天，可获得10元现金券*/
	public static final int[] CUSTOMER_CONSECUTIVE_SIGN_30_DAY_GIVE_INVESTMENT_TICKET = {10};
	
	/**----------------------------------会员投资券状态-------------------------------*/
	/**正常*/
	public static final String CUSTOMER_INVESTMENT_TICKET_STATUS_NORMAL = "0";
	/**已使用*/
	public static final String CUSTOMER_INVESTMENT_TICKET_STATUS_USED = "1";
	/**过期*/
	public static final String CUSTOMER_INVESTMENT_TICKET_STATUS_EXPIRED = "2";
	
	/**---------------------------------注册礼包------------------------------------*/
	/**礼包名称*/
	public static final String REGISTER_GIFT_BAG_NAME = "注册大礼包";
	/**赠送投资券id*/
	public static final long[] REGISTER_GIVE_INVESTMENT_TICKET_IDS = {1,1,2};	//5元券 5元券 10元券
	/**赠送给推荐人投资券id*/
	public static final long REGISTER_GIVE_TO_RECOMMEND_INVESTMENT_TICKET_ID = 3;	//20元券
	
	/**--------------------------------会员账号状态------------------------------------*/
	/**注销*/
	public static final String CUSTOMER_ACCOUNT_STATUS_DELETED = "-1";
	/**正常*/
	public static final String CUSTOMER_ACCOUNT_STATUS_NORMAL = "0";
	/**锁定*/
	public static final String CUSTOMER_ACCOUNT_STATUS_LOCKED = "1";
	/**等待重置*/
	public static final String CUSTOMER_ACCOUNT_STATUS_WAITING_RESET = "2";
	
	/**--------------------------------最大投资金额---------------------------------*/
	/**最大投资金额*/
	public static final long PROJECT_MAX_AMOUNT_DEFAULT = 10000;
	
	/**--------------------------------推荐人类型-----------------------------------*/
	/**无推荐人*/
	public static final String RECOMMENDER_TYPE_NONE = "0";
	/**未注册用户*/
	public static final String RECOMMENDER_TYPE_UNREGISTER = "1";
	/**普通用户*/
	public static final String RECOMMENDER_TYPE_NORMAL = "2";
	/**员工*/
	public static final String RECOMMENDER_TYPE_EMPLOYEE = "3";
	
	/**------------------------性别----------------------*/
	/**男*/
	public static final String SEX_MALE = "1";
	/**女*/
	public static final String SEX_FEMALE = "2";

	/**--------------------用户地址状态------------------------*/
	/**正常*/
	public static final String CUSTOMER_ADDRESS_STATUS_NORMAL = "0";
	/**已删除*/
	public static final String CUSTOMER_ADDRESS_STATUS_DELETED = "-1";
	
	/**--------------------用户地址是否为默认地址------------------------*/
	/**是*/
	public static final String CUSTOMER_ADDRESS_IS_DEFAULT_YES = "1";
	/**否*/
	public static final String CUSTOMER_ADDRESS_IS_DEFAULT_NOT = "0";
	
	/**-----------------------花生乐园订单状态--------------*/
	/**取消*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_CANCEL = "-1";
	/**下单*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_ORDER = "0";
	/**确认*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_CONFIRM = "1";
	/**已发货*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_SENDED = "2";
	/**已收货*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_RECEIVED = "3";
	/**订单完成*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_FINISH = "4";
	/**删除*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_DELETE = "5";
	
	/**-----------------------营销活动奖励产品类型--------------*/
	/**投资券*/
	public static final String MARKETING_AWARD_TYPE_INVESTMENT_TICKET = "1";
	/**现金*/
	public static final String MARKETING_AWARD_TYPE_MONEY = "2";
	/**花生豆*/
	public static final String MARKETING_AWARD_TYPE_INTEGRAL = "3";
	/**免费提现次数*/
	public static final String MARKETING_AWARD_TYPE_FREE_WITHDRAW_COUNT = "4";
	/**实物*/
	public static final String MARKETING_AWARD_TYPE_OBJECT = "5";
	
	
	
	/**-----------------------花生乐园产品类型--------------*/
	/**现金券*/
	public static final String MARKETING_PRODUCT_TYPE_INVESTMENT_TICKET = "2";
	/**现金*/
	public static final String MARKETING_PRODUCT_TYPE_MONEY = "3";
	/**免费提现次数*/
	public static final String MARKETING_PRODUCT_TYPE_FREE_WITHDRAW_COUNT = "4";
	/**实物*/
	public static final String MARKETING_PRODUCT_TYPE_OBJECT = "1";
	
	/**-------------------------投资券类型状态----------------------*/
	/**正常*/
	public static final String INVESTMENT_TICKET_TYPE_STATUS_NORMAL = "0";
	/**不可用*/
	public static final String INVESTMENT_TICKET_TYPE_STATUS_DISABLE = "-1";
	
	/**------------------------花生乐园商品类型----------------------*/
	/**投资券产品类别名称*/
	public static final String INTEGRAL_MALL_PRODUCT_TYPE_TICKET_NAME = "投资券";
	
	/**-----------------同一身份证号最多能拥有账号的个数---------------*/
	public static final int IDCARDNO_MAX_HAVE_ACCOUNT_NUMBER = 3;
	
	/**-------------------------------轮播图状态-------------------------------**/
	/**拒绝*/
	public static final String  CAROUSEL_INFO_STATUS_REFUSE="-1";
	/**创建*/
	public static final String  CAROUSEL_INFO_STATUS_CREATE="0";
	/**审批通过*/
	public static final String  CAROUSEL_INFO_STATUS_PASS="1"; 

	/**-------------------------------轮播图类型-------------------------------**/
	/**活动*/
	public static final String  PHOTO_TYPE_ACTIVITY="1"; 
	/**项目*/
	public static final String  PHOTO_TYPE_PRO="2";

	/**-------------------------------活动奖励来源类型-------------------------------**/
	/**自身*/
	public static final String MARKET_AWARD_CAUSE_TYPE_SELF = "0";
	/**好友投资*/
	public static final String MARKET_AWARD_CAUSE_TYPE_FRIEND_INVESTMENT = "1";
	/**好友其它行为*/
	public static final String MARKET_AWARD_CAUSE_TYPE_FRIEND_OTHER_BEHAVIOR = "2";
	/**好友其它行为*/
	public static final String MARKET_AWARD_CAUSE_TYPE_FRIEND_GET = "3";
	
	/**-----------------------投资排行类型--------------*/
	/**日*/
	public static final String INVESTMENT_RANK_TYPE_DAY = "0";
	/**周*/
	public static final String INVESTMENT_RANK_TYPE_WEEK = "1";
	/**月*/
	public static final String INVESTMENT_RANK_TYPE_MONTH = "2";
	/**年*/
	public static final String INVESTMENT_RANK_TYPE_YEAR = "3";
	/**一月活动投资排行*/
	public static final String INVESTMENT_RANK_TYPE_JANUARY = "4";
	
	/**----------------------客户留言类型---------------*/
	/**亨沃美国房地产投资*/
	public static final String CUSTOMER_LEAVE_MESSAGE_TYPE_HANWORLD_REALTY = "1";
	
	/**----------------------客户留言状态---------------*/
	/**提交*/
	public static final String CUSTOMER_LEAVE_MESSAGE_STATUS_SUBMIT = "0";
	
	/**-------------------------------银行卡信息状态-------------------------------**/
	/**正常*/
	public static final String BANK_INFO_STATUS_NORMAL ="1";
	/**无效*/
	public static final String BANK_INFO_STATUS_INVALID ="0";
	
	/**-------------------------------汇率类型-------------------------------**/
	/**货币基金*/
	public static final String EXCHANGE_RATE_TYPE_MONETARY ="1";
	/**银行活期*/
	public static final String EXCHANGE_RATE_TYPE_BANKCURRENT ="2";
}
