/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing;

/**
 * @author yangtao
 *
 */
public class MarketConstant {
	/**-------------------------------用户行为-------------------------------**/
	/**登录*/
	public static final String  CUSTOMER_BEHAVIOR_LOGIN="1010";
	/**注册*/
	public static final String  CUSTOMER_BEHAVIOR_REGISTER="1020";
	/**签到*/
	public static final String  CUSTOMER_BEHAVIOR_SIGN="1030";
	/**邀请*/
	public static final String  CUSTOMER_BEHAVIOR_INVITE="1040";
	/**充值*/
	public static final String  CUSTOMER_BEHAVIOR_RECHARGE="1050";
	/**开通第三方账号*/
	public static final String  CUSTOMER_BEHAVIOR_OPEN_THIRD_PARTY="1060";
	/**绑卡*/
	public static final String  CUSTOMER_BEHAVIOR_BIND_BANK_CARD="1070";
	/**提现*/
	public static final String  CUSTOMER_BEHAVIOR_WITHDRAW="1080";
	/**投资--投标*/
	public static final String  CUSTOMER_BEHAVIOR_INVESTMENT_TENDER="1090";
	/**投资--债权转让*/
	public static final String  CUSTOMER_BEHAVIOR_INVESTMENT_CREDIT_ASSIGNMENT="1100";
	/**融资申请*/
	public static final String  CUSTOMER_BEHAVIOR_LOAN_MONRY="1110";
	/**抽奖*/
	public static final String  CUSTOMER_BEHAVIOR_LUCK_DRAW="1120";
	/**投标--完成*/
	public static final String  CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER="1130";
	
	/**-------------------------------活动状态-------------------------------**/
	/**被拒绝*/
	public static final String  MARKETING_ACTIVITY_STATUS_REFUSE="-1";
	/**创建*/
	public static final String  MARKETING_ACTIVITY_STATUS_CREATE="0";
	/**审批通过*/
	public static final String  MARKETING_ACTIVITY_STATUS_PASS="1";
	/**执行结束*/
	public static final String  MARKETING_ACTIVITY_STATUS_OVER="2";
	/**归档*/
	public static final String  MARKETING_ACTIVITY_STATUS_ARCHIVING="3";
	
	
	/**-------------------------------通用入参名称-------------------------------**/
	/**关联会员编号*/
	public static final String CUSTOMER_ACCOUNT_PARA="accountId";
	/**关联渠道编号*/
	public static final String CHANNEL_PARA="channelId";
	/**活动编号*/
	public static final String ACTICITY_PARA="acticityId";
	/**行为编号*/
	public static final String BEHAVIOR_PARA="behaviorCode";
	/**handler*/
	public static final String HANDLER_PARA="marketingActivityHandler";
	/**
	 * 功能码
	 */
	public static final String FUNC_PARA="functionCode";
	/**金额*/
	public static final String AMOUNT_PARA="amount";
	/**项目流水号*/
	public static final String PROJECT_ID_PARA="projectId";
	/**投资记录流水号*/
	public static final String RECORD_ID_PARA="recordId";
	/**推荐人（邀请人）手机号。如：a邀请b，则a为邀请人，b为被推荐人（被邀请人）*/
	public static final String INVITER_MOBILE_PARA = "inviterMobile";
	/**-------------------------------用户行为-------------------------------**/
	
	/**-------------------------------活动操作结果-------------------------------**/
	/**失败*/
	public static final String  MARKETING_ACTIVITY_RESULT_CODE_FAIL="0";
	/**成功*/
	public static final String  MARKETING_ACTIVITY_RESULT_CODE_SUCCESS="1";
	
	/**-------------------------------大转盘奖品实例状态-------------------------------**/
	/**正常*/
	public static final String WHEEL_PRIZE_INSTANCE_STATUS_NORMAL = "0";
	/**锁定*/
	public static final String WHEEL_PRIZE_INSTANCE_STATUS_LOCK = "1";
	/**已被抽中*/
	public static final String WHEEL_PRIZE_INSTANCE_STATUS_GOT = "2";
	
	/**-------------------------------大转盘中奖纪录状态-------------------------------**/
	/**中奖*/
	public static final String WHEEL_GET_PRIZE_RECORD_STATUS_GET = "0";
	/**待赠送*/
	public static final String WHEEL_GET_PRIZE_RECORD_STATUS_GIVING = "1";
	/**已赠送*/
	public static final String WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN = "2";
	/**失效*/
	public static final String WHEEL_GET_PRIZE_RECORD_STATUS_INVALID = "-1";
	
	/**大转盘token失效时间，单位：分钟*/
	public static final int WHEEL_TOKEN_TIME_OUT = 30;
	
	/**---------------------------------活动现金奖励记录状态-----------------------------**/
	/**取消*/
	public static final String MARKETING_MONEY_AWARD_STATUS_CANCEL = "-1";
	/**待赠送*/
	public static final String MARKETING_MONEY_AWARD_STATUS_GIVING = "0";
	/**已赠送*/
	public static final String MARKETING_MONEY_AWARD_STATUS_GIVEN = "2";
	
	/**---------------------------------是否是活动默认奖品-----------------------------**/
	/**否*/
	public static final String IS_DEFAULT_PRIZE_NOT = "0";
	/**是*/
	public static final String IS_DEFAULT_PRIZE_YES = "1";
	
	/**-----------------------------------比赛队伍----------------------------------**/
	/**红队*/
	public static final String MATCH_SIDE_RED = "red";
	/**蓝队*/
	public static final String MATCH_SIDE_BLUE = "blue";
	
	/**-----------------------------------选择队伍初始值----------------------------------**/
	//比赛未结束
	public static final int MATCH_NOT_OVER = 0;
	//红队胜
	public static final int RES_WIN = 1;
	//蓝队胜
	public static final int BLUE_WIN = 2;
	/**-----------------------------------1016参与竞猜扣除和送花生豆----------------------------------**/
	
	public static final String  MATCH_GUESSINGS_1016_POST_INTEGRAL_REMARK = "竞猜";
	public static final String  MATCH_GUESSINGS_SECSSES_1016_GIVE_INTEGRAL_REMARK = "竞猜奖励";
	
	/**-----------------------------------1016参与竞猜扣除和送花生豆常量----------------------------------**/
	/**参加竞猜扣除100花生豆*/
	public static final int  MATCH_GUESSINGS_1016_POST_INTEGRAL_100 = -100;
	/**猜对奖励1000花生豆*/
	public static final int  MATCH_GUESSINGS_1016_GIVE_INTEGRAL_1000 = 1000;
	
	

	/**-------------------------------营销活动中使用到的常量-------------------------------**/
	/**activity 1000:用户注册，送5元现金券面值*/
	public static final int[] ACTIVITY_1000_REGISTER_GIVE_INVESTMENT_TICKET_DENOMINATIONS = {5};
	/**activity 1000:用户注册，送5元现金券说明*/
	public static final String ACTIVITY_1000_REGISTER_GIVE_INVESTMENT_TICKET_REMARK = "注册成功送现金券";
	/**activity 1000:用户开通第三方账号，送10元现金券面值*/
	public static final int[] ACTIVITY_1000_OPENTHIRDPARTY_GIVE_INVESTMENT_TICKET_DENOMINATIONS = {10};
	/**activity 1000:用户开通第三方账号，送10元现金券说明*/
	public static final String ACTIVITY_1000_OPENTHIRDPARTY_GIVE_INVESTMENT_TICKET_REMARK = "开通托管账户送现金券";
	/**activity 1000:首次充值，送10元现金券面值*/
	public static int[] ACTIVITY_1000_FIRST_RECHARGE_GIVE_INVESTMENT_TICKET_DENOMINATIONS = {10};
	/**activity 1000:首次充值，送10元现金券说明*/
	public static final String ACTIVITY_1000_FIRST_RECHARGE_GIVE_INVESTMENT_TICKET_REMARK = "首次充值送现金券";
	/**activity 1000:首次投资，送200元现金券面值*/
	public static final int[] ACTIVITY_1000_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_DENOMINATIONS = {100,50,20,20,10};
	/**activity 1000:首次投资，送200元现金券说明*/
	public static final String ACTIVITY_1000_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_REMARK = "首次投资送现金券";
	/**activity 1000:首次投资放款，送2.88元现金金额*/
	public static final double ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_MONEY_AMOUNT = 2.88;
	/**activity 1000:首次投资放款，送8元现金说明*/
	public static final String ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_MONEY_REMARK = "完成首次投资送现金";
	/**activity 1000:首次投资放款，送10次免费提现次数值*/
	public static final int ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_FREE_WITHDRAW_COUNT_VALUE = 10;
	/**activity 1000:首次投资放款，送10次免费提现次数说明*/
	public static final String ACTIVITY_1000_FIRST_INVESTMENTTENDEROVER_GIVE_FREE_WITHDRAW_COUNT_REMARK = "完成首次投资赠送提现券";

	/**activity 1001:被推荐人开通第三方账号，送25元现金券面值*/
	public static final int[] ACTIVITY_1001_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS = {10, 10, 5};
	/**activity 1001:被推荐人开通第三方账号，送25元现金券说明*/
	public static final String ACTIVITY_1001_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK = "好友开通第三方账号送现金券";
	/**activity 1001:被推荐人首次投资放款，送2.88元现金金额*/
	public static final double ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_MONEY_AMOUNT = 2.88;
	/**activity 1001:被推荐人首次投资放款，送2.88元现金说明*/
	public static final String ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_MONEY_REMARK = "好友完成首次投资送现金";
	/**activity 1001:被推荐人首次投资放款，千分之一投资金额现金比率*/
	public static final Double ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_MONEY_RATE = 0.001;
	/**activity 1001:被推荐人首次投资放款，千分之一投资金额现金说明*/
	public static final String ACTIVITY_1001_FIRST_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_MONEY_REMARK = "好友完成首次投资送投资额千分之一现金";
	/**activity 1001:第5位被推荐人投资放款，送200元现金券面值*/
	public static final int[] ACTIVITY_1001_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS = {100, 50, 20, 20, 10};
	/**activity 1001:第5位被推荐人投资放款，送200元现金券说明*/
	public static final String ACTIVITY_1001_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK = "满5位好友完成投资送现金券";
	
	/**activity 1003：投资送花生豆*/
	public static final String ACTIVITY_1003_INVESTMENTTENDEROVER_GIVE_INTEGRAL_REMARK = "投资送花生豆";

	/**activity 1005：大转盘中奖说明*/
	public static final String ACTIVITY_1005_WHEEL_GIVE_PRIZE_REMARK = "大转盘抽奖奖励";
	
	/**activity 1006：首次登录客户端，送20元现金券面值*/
	public static final int[] ACTIVITY_1006_FIRST_LOGIN_CLIENT_GIVE_INVESTMENT_TICKET_DENOMINATIONS = {10, 10};
	/**activity 1006：首次登录客户端，送20元现金券说明*/
	public static final String ACTIVITY_1006_FIRST_LOGIN_CLIENT_GIVE_INVESTMENT_TICKET_REMARK = "首次登录客户端送现金券";
	
	/**activity 1007：双旦送推荐人现金奖励说明*/
	public static final String ACTIVITY_1007_GIVE_RECOMMENDER_MONEY_REMARK = "双旦好友投资送现金";
	
	/**activity 1008:活动期间首次投资，送5元现金券面值*/
	public static final int[] ACTIVITY_1008_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_DENOMINATIONS = {5};
	/**activity 1008:活动期间首次投资，送5元现金说明*/
	public static final String ACTIVITY_1008_FIRST_INVESTMENTTENDER_GIVE_INVESTMENT_TICKET_REMARK = "一月份首次投资送现金券";
	
	/**activity 1009:一次抽奖机会的投资额度*/
	//单笔投资每满1000元即可获得1次抽奖机会（投资后立即获得抽奖机会），单次取整计算获得抽奖机会的次数，例如：投资1900，获得1次抽奖机会；投资2100，获得2次抽奖机会
	public static final int ACTIVITY_1009_REQUIRE_INVESTMENT_AMOUNT = 1000;
	/**activity 1009：大转盘中奖说明*/
	public static final String ACTIVITY_1009_WHEEL_GIVE_PRIZE_REMARK = "春节大转盘抽奖奖励";

	/**activity 1010:一次抽奖机会的投资额度*/
	//单笔投资每满1000元即可获得1次抽奖机会（投资后立即获得抽奖机会），单次取整计算获得抽奖机会的次数，例如：投资1900，获得1次抽奖机会；投资2100，获得2次抽奖机会
	public static final int ACTIVITY_1010_REQUIRE_INVESTMENT_AMOUNT = 1000;
	
	/**activity 1011：女性领取专享礼包说明*/
	public static final String ACTIVITY_1011_WHEEL_GIVE_PRIZE_REMARK = "女神专享礼包";
	/**activity 1011：女性领取专享礼包,送50元现金券（20+10+10+5+5）(1张提现券+)*/
	public static final int[] ACTIVITY_1011_WOMAN_BAG_TICKET_DENOMINATIONS = {20,10,10,5,5};
	/**activity 1011：女性领取专享礼包,送1张提现券*/
	public static final int ACTIVITY_1011_WOMAN_BAG_WITHDRAW_TICKET_COUNT = 1;
	/**activity 1011：女性领取专享礼包,送1000颗花生豆*/
	public static final int ACTIVITY_1011_WOMAN_BAG_PEANUT_BEAN_COUNT = 1000;
	public static final String HANDLER_PARA_VAL_WOMAN_BAG="marketActivity1011Handler";
	
	/**activity 1010：活动分享说明*/
	public static final String ACTIVITY_1010_SHARE_REMARK = "摇钱树活动分享";
	
	
	public static final String HANDLER_PARA_VAL_APRIL_SPRING_OUTING="marketActivity1012Handler";
	/**
	 *  4月春游季 领取奖励类型
	 */
	//现金
	public static final String RECEIVE_TYPE_MONEY="money";
	//花生豆
	public static final String RECEIVE_TYPE_PEANUT_BEAN="peanutBean";
	

	/**activity 1013:被推荐人开通第三方账号，送25元现金券面值*/
	public static final int[] ACTIVITY_1013_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS = {10, 10, 5};
	/**activity 1013:被推荐人开通第三方账号，送25元现金券说明*/
	public static final String ACTIVITY_1013_OPENTHIRDPARTY_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK = "好友开通第三方账号送现金券";
	/**activity 1013:第5位被推荐人投资放款，送200元现金券面值*/
	public static final int[] ACTIVITY_1013_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_DENOMINATIONS = {100, 50, 20, 20, 10};
	/**activity 1013:第5位被推荐人投资放款，送200元现金券说明*/
	public static final String ACTIVITY_1013_FIFTH_INVESTMENTTENDEROVER_GIVE_RECOMMENDER_INVESTMENT_TICKET_REMARK = "满5位好友完成投资送现金券";
	
	/**activity 1014：送推荐人现金奖励说明*/
	public static final String ACTIVITY_1014_GIVE_RECOMMENDER_MONEY_REMARK = "好友投资送现金";

	/**activity 1015：参赛现金券奖励（相应排名活得相对应的奖励）*/
	public static final int ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_DENOMINATIONS = 10;
	/**activity 1015：参赛现金券奖励说明*/
	public static final String ACTIVITY_1015_INVESTMENT_JOIN_MATCH_AWARD_TICKET_REMARK = "拔河擂台赛参与奖";
	/**activity 1015：每日排行榜人员可获得的现金券奖励（相应排名活得相对应的奖励）*/
	public static final int[][] ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_DENOMINATIONS = {
		{50, 50},{50, 20, 10},{50, 10},{20},{20},{20},{20},{20},{20},{20}
	};
	/**activity 1015：每日排行榜人员可获得的现金券奖励说明*/
	public static final String ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_REMARK = "大力士奖";
	/**activity 1015：拔河擂台赛奖励说明*/
	public static final String ACTIVITY_1015_INVESTMENT_MATCH_AWARD_REMARK = "拔河擂台赛奖励";
	
	/**activity 1017：裘老板婚礼，用户扫码注册，送1800元现金券，包含的现金券类型，每种类型送十张*/
	public static final int[] ACTIVITY_1017_QIU_LAO_BAN_MARRY_TICKET_DENOMINATIONS = {100,100,100,100,100,100,100,100,100,100,
																					50,50,50,50,50,50,50,50,50,50,
																					20,20,20,20,20,20,20,20,20,20,
																					10,10,10,10,10,10,10,10,10,10};
	public static final String ACTIVITY_1017_QIU_LAO_BAN_MARRY_REMARK = "裘老板婚礼赠送";
	/**
	 * 来源渠道，对终端的再度区分
	 */
	public static final String SOURCE_CHANNEL_PARA = "source_channel_para";
	
	public static final String CUSTOMER_BLANCE_ALIGNMENT_REMARK = "数据对齐";

	/**activity 1018：一呼百医奖励说明*/
	public static final String ACTIVITY_1018_AWARD_REMARK = "一呼百医活动奖励";

	/**activity 1019：单笔投资金额限制*/
	public static final int[] ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT = {100000,50000,30000,10000,5000,1000};

	/**activity 1021:欧洲杯活动，刮奖奖励说明*/
	public static final String ACTIVITY_1021_EUROPEAN_CUP_SCRATCH_REWARD_REMARK = "欧洲杯刮奖奖励";

	/**activity 1020:欧洲杯活动，最佳射手榜奖励说明*/
	public static final String ACTIVITY_1020_EUROPEAN_CUP_TOP10SCORER_REWARD_REMARK = "欧洲杯最佳射手奖励";

	/**activity 1020:欧洲杯活动，最佳射手榜奖励金额*/
	public static final int[] ACTIVITY_1020_EUROPEAN_CUP_TOP10SCORER_REWARD_AMOUNT = {58,28,18,8,8,8,6,6,6,6};

	/**activity 1020：活动分享说明*/
	public static final String ACTIVITY_1020_SHARE_REMARK = "欧洲杯活动分享";
}
