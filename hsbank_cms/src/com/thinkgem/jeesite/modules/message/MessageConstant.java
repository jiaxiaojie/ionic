package com.thinkgem.jeesite.modules.message;

import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;

public class MessageConstant {
	/**信鸽 android推送目标应用 id*/
	public static final long XINGE_ANDROID_ACCESSID = 2100181904L;
	/**信鸽 android推送密钥*/
	public static final String XINGE_ANDROID_SECRETKEY = "cde89593c624beb65187ea24d65f9393";
	/**信鸽 ios推送目标应用 id*/
	public static final long XINGE_IOS_ACCESSID = 2200181905L;
	/**信鸽 ios推送密钥*/
	public static final String XINGE_IOS_SECRETKEY = "bcdc1e7f8dd838aa3aa4064828bdb9f6";
//	/**信鸽 测试推送环境（ios有效）*/
	// public static final int IOS_ENVIRONMENT = XingeApp.IOSENV_DEV;
	/**信鸽 正式推送环境（ios有效）*/
	public static final int IOS_ENVIRONMENT = XingeApp.IOSENV_PROD;
	/**--------------------------------信鸽 允许推送时间段----------------------------------**/
	public static final TimeInterval XINGE_ACCEPTTIME = new TimeInterval(9, 0, 20, 0);
	
	
	/**系统设置消息开关（认定account_id为此值的设置为系统设置）*/
	public static final Long SYSTEM_MESSAGE_SWITCH_ACCOUNT_ID = 0L;
	/**系统消息开关默认值*/
	public static final String SYSTEM_MESSAGE_SWITCH_DEFAULT_VALUE = "0";
	
	/**--------------------------------消息发送渠道----------------------------------**/
	/**web*/
	public static final String MESSAGE_CHANNEL_WEB = "0";
	/**微信*/
	public static final String MESSAGE_CHANNEL_WECHAT = "1";
	/**android*/
	public static final String MESSAGE_CHANNEL_ANDROID = "2";
	/**ios*/
	public static final String MESSAGE_CHANNEL_IOS = "3";
	/**短信*/
	public static final String MESSAGE_CHANNEL_SMS = "4";
	/**邮件*/
	public static final String MESSAGE_CHANNEL_EMAIL = "5";
	
	/**----------------------------------消息状态-----------------------------------**/
	/**失效*/
	public static final String MESSAGE_STATUS_EXPIRE = "-2";
	/**已删除*/
	public static final String MESSAGE_STATUS_DELETE = "-1";
	/**创建*/
	public static final String MESSAGE_STATUS_CREATE = "0";
	/**已发送*/
	public static final String MESSAGE_STATUS_SEND = "1";
	/**已读*/
	public static final String MESSAGE_STATUS_READ = "2";
	
	/**----------------------------自定义消息接收用户类型-------------------------------**/
	/**上传文件*/
	public static final String CUSTOM_MESSAGE_RECEIVER_TYPE_UPLOAD_FILE = "0";
	/**特定用户*/
	public static final String CUSTOM_MESSAGE_RECEIVER_TYPE_APPOINT = "1";
	
	/**------------------------------自定义消息状态----------------------------------**/
	/**审批不通过*/
	public static final String CUSTOM_MESSAGE_STAUTS_REVIEW_FAILED = "-1";
	/**创建*/
	public static final String CUSTOM_MESSAGE_STAUTS_CREATE = "0";
	/**提交审批*/
	public static final String CUSTOM_MESSAGE_STAUTS_SUBMIT_REVIEW = "1";
	/**审批通过*/
	public static final String CUSTOM_MESSAGE_STAUTS_REVIEW_PASS = "2";
	/**执行结束*/
	public static final String CUSTOM_MESSAGE_STAUTS_FINISHED = "3";
	
	/**--------------------------------是否接收消息----------------------------------**/
	/**接收*/
	public static final String IS_RECEIVE_MESSAGE_YES = "0";
	/**不接收*/
	public static final String IS_RECEIVE_MESSAGE_NO = "1";
	
	/**-------------------------------消息来源类型----------------------------------**/
	/**规则产生*/
	public static final String MESSAGE_FROM_TYPE_RULE = "0";
	/**自定义产生*/
	public static final String MESSAGE_FROM_TYPE_CUSTOM = "1";
	
	/**------------------------------消息产生规则状态--------------------------------**/
	/**审批不通过*/
	public static final String MESSAGE_RULE_STATUS_REVIEW_FAILED = "-1";
	/**创建*/
	public static final String MESSAGE_RULE_STATUS_CREATE = "0";
	/**审批通过*/
	public static final String MESSAGE_RULE_STATUS_REVIEW_PASS = "1";
	/**执行结束*/
	public static final String MESSAGE_RULE_STATUS_FINISHED = "2";
	
	/**----------------------------------消息类型----------------------------------**/
	/**系统消息*/
	public static final String MESSAGE_TYPE_SYSTEM = "0";
	/**用户消息*/
	public static final String MESSAGE_TYPE_ACCOUNT = "1";
	
	/**---------------------------------行为编码-----------------------------------**/
	/**注册*/
	public static final String BEHAVIOR_REGISTER = "1010";
	/**登录*/
	public static final String BEHAVIOR_LOGIN = "1020";
	/**签到*/
	public static final String BEHAVIOR_SIGN = "1030";
	/**开通第三方账号*/
	public static final String BEHAVIOR_OPEN_THIRD_ACCOUNT = "1040";
	/**绑卡*/
	public static final String BEHAVIOR_BIND_BANK_CARD = "1050";
	/**重置密码*/
	public static final String BEHAVIOR_RESET_PASSWORD = "1060";
	/**修改密码*/
	public static final String BEHAVIOR_CHANGE_PASSWORD = "1070";
	/**充值*/
	public static final String BEHAVIOR_RECHARGE = "1080";
	/**提现*/
	public static final String BEHAVIOR_WITHDRAW = "1090";
	/**投标*/
	public static final String BEHAVIOR_INVESTMENT_TENDER = "1100";
	/**投标结束*/
	public static final String BEHAVIOR_INVESTMENT_TENDER_OVER = "1110";
	/**还款*/
	public static final String BEHAVIOR_TENDER_REPAY = "1120";
	/**投资债权*/
	public static final String BEHAVIOR_INVESTMENT_CREDITOR = "1130";
	/**投资活期*/
	public static final String BEHAVIOR_INVESTMENT_CURRENT = "1140";
	/**申请赎回本金*/
	public static final String BEHAVIOR_REDEEM_PRINCIPAL_APPLY = "1150";
	/**赎回本金审批通过*/
	public static final String BEHAVIOR_REDEEM_PRINCIPAL_PASS = "1160";
	/**赎回本金申请失败*/
	public static final String BEHAVIOR_REDEEM_PRINCIPAL_FAILED = "1170";
	/**赎回本金完成*/
	public static final String BEHAVIOR_REDEEM_PRINCIPAL_REDEEMED = "1180";
	/**活期收息*/
	public static final String BEHAVIOR_GET_INTEREST = "1190";
	/**活期提取利息*/
	public static final String BEHAVIOR_POLL_INTEREST = "1200";
	/**抽奖*/
	public static final String BEHAVIOR_LUCK_DRAW = "1210";
	
	/**-------------------------------默认短信发送时间段-------------------------------**/
	/**短信发送时间段：开始时间*/
	public static final String SMS_MESSAGE_START_TIME = "09:00:00";
	/**短信发送时间段：结束时间*/
	public static final String SMS_MESSAGE_END_TIME = "20:00:00";
	
	/**---------------------------------消息变量------------------------------------**/
	public static final String[] MESSAGE_VARIABLE = {"#accountId#", "#customerName#",
		"#mobile#", "#amount#", "#date#", "#rank#", "#count#", "#params#"};
	
	/**---------------------------------通用入参名称---------------------------------**/
	/**会员编号*/
	public static final String PARA_ACCOUNT_ID = "accountId";
	/**金额*/
	public static final String PARA_AMOUNT = "amount";
	/**行为编号*/
	public static final String PARA_BEHAVIOR_CODE = "behaviorCode";
	/**操作终端*/
	public static final String PARA_OP_TERM = "opTerm";
	/**消息产生规则*/
	public static final String PARA_MESSAGE_CREATE_RULE = "messageCreateRule";
	/**时间*/
	public static final String PARA_DATE = "date";
	/**参数*/
	public static final String PARA_PARAMS = "params";
	/**数量*/
	public static final String PARA_COUNT = "count";
}
