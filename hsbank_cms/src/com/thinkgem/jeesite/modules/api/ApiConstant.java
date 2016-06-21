package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.yeepay.YeepayConstant;

/**
 * P2P常量
 * @author lzb
 * @version 2015-10-16
 */
public class ApiConstant {
	//Token
	public static String TOKEN = "token";
	/**-------------------------------状态编码-------------------------------*/
	/**成功*/
	public static int API_OPERA_SUCCESS = 0;
	/**失败*/
	public static int API_OPERA_FAIL = -1;
	/**缓存失效*/
	public static int API_INVALID_TOKEN = 1;
	/**-------------------------------结果编码-------------------------------*/
	/**成功*/
	public static int API_RESULT_SUCCESS = 0;
	/**失败*/
	public static int API_RESULT_FAIL = 1;
	/**参数校验失败*/
	public static int API_PARAMS_VALID_RESULT_FAIL = 3;

	//移动端token缓存失效时间
	public static int API_MOBILE_CACHE_TOKEN_INVALID_TIME = -21600;
	//WEB网站token缓存失效时间
	public static int API_WEBSITE_CACHE_TOKEN_INVALID_TIME = -30;
	//访问易宝缓存数据失效时间
	public static int API_CACHE_YEEPAY_INVALID_TIME = -15;
	//微信jsSDK签名失效时间
	public static int API_CACHE_WECHAT_JSSDK_INVALID_TIME = -120;
    //状态编码key
	public static String API_STATUS_CODE = "code";
	//状态描述key
	public static String API_STATUS_TEXT = "text";
	//状态描述value
	public static String API_STATUS_TEXT_VALUE = "ok";
	//状态默认值
	public static String API_STATUS_TEXT_VALUE_DEFAULT = null;
	//返回数据
	public static String API_RESP_DATA = "data";
	//返回后台异常提示
	public static String API_EXCEPTION_RESP_TEXT = "系统忙，请稍后再试";
	//返回数据默认值
	public static String API_RESP_DATA_DEFAULT = null;
	public static String YEEPAY_REQ_URL_PREFIX = YeepayConstant.YEEPAY_NOTIFI_BASE_URL + "f/api/yeepay/wireless/";
	//身份证类型为二代身份证
	public static final String ID_CARD_TYPE = "G2_IDCARD";
	//微信端签名APPID/SECRET正式环境
	public static final String API_WECHAT_APPID = "wx6e9088b884c5d53d";
	public static final String API_WECHAT_SECRET = "21f885f022881f754738b87960110e41";
	//微信端签名APPID/SECRET测试环境
	/*public static final String API_WECHAT_APPID = "wx0110c1a747a42c95";
	public static final String API_WECHAT_SECRET = "d4624c36b6795d1d99dcf0547af5443d";*/
	public static final String API_WECHAT_APPID_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+API_WECHAT_APPID+"&secret="+API_WECHAT_SECRET;
	public static final String API_WECHAT_SECRET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
	/**-------------------------------请求易宝业务类型-------------------------------*/
	/**注册*/
	public static String API_YEEPAY_BUSINESS_RES_TOREGISTER = "1";
	/**绑卡*/
	public static String API_YEEPAY_BUSINESS_RES_TOBINDBANKCARD = "2";
	/**充值*/
	public static String API_YEEPAY_BUSINESS_RES_TORECHARGE = "3";
	/**投资*/
	public static String API_YEEPAY_BUSINESS_RES_TOINVEST = "4";
	/**提现*/
	public static String API_YEEPAY_BUSINESS_RES_TOWITHDRAW = "5";
	/**重置交易密码*/
	public static String API_YEEPAY_BUSINESS_RES_TORESETPASSWORD = "6";
	/**重置交易密码*/
	public static String API_YEEPAY_BUSINESS_RES_TORESETMOBILE = "7";
	
	/**-------------------------------操作终端-------------------------------*/
	/**web*/
	public static final String OP_TERM_DICT_WEB = "WEB";
	/**wechat*/
	public static final String OP_TERM_DICT_WECHAT = "WECHAT";
	/**android*/
	public static final String OP_TERM_DICT_ANDROID = "ANDROID";
	/**ios*/
	public static final String OP_TERM_DICT_IOS = "IOS";
	
	/**已注册*/
	public static final String MY_INVITATION_STATUS_REGISTERED = "0";
	/**已投资*/
	public static final String MY_INVITATION_STATUS_INVESTED = "1";
	/**已充值*/
	public static final String MY_INVITATION_STATUS_RECHARGE = "2";
	/**已开通第三方账号*/
	public static final String MY_INVITATION_STATUS_HASOPENTHIRD = "3";
	/**已成交*/
	public static final String MY_INVITATION_STATUS_TRADED = "4";
	
	/**-------------------------------投资排行类型-------------------------------*/
	/**投资排行*/
	public static final String INVESTMENT_STAT_TYPE_GENERAL = "1";
	/**年化排行*/
	public static final String INVESTMENT_STAT_TYPE_THEYEAR = "2";
	
	/**-------------------------------解绑卡状态-------------------------------*/
	/**解绑状态*/
	public static final String UNBINDBANKCARD_STATUS = "UNBIND";
	/**解绑状态描述*/
	public static final String UNBINDBANKCARD_STATUS_DESP = "预约解绑中";
	
	/**-------------------------------是否接收push消息-------------------------------*/
	/**接收*/
	public static final String PUSH_SWITCH_YES = "1";
	/**不接收*/
	public static final String PUSH_SWITCH_NO = "0";

	/**-------------------------------我的活期项目查询条件(类型)-------------------------------*/
	/**收益*/
	public static final String CURRENT_MY_ACCOUNT_SEARCH_TYPE_GET_INTEREST = "1";
	/**投资*/
	public static final String CURRENT_MY_ACCOUNT_SEARCH_TYPE_INVESTMENT = "2";
	/**赎回*/
	public static final String CURRENT_MY_ACCOUNT_SEARCH_TYPE_REDEEM = "3";

	/**-------------------------------我的资金管理-交易记录查询条件(交易类型)-------------------------------*/
	/**全部*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_ALL = "0";
	/**充值*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_RECHARGE = "1";
	/**提现*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_WITHDRAW = "2";
	/**投标*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_INVEST = "3";
	/**回款*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_REPAYMENT = "4";
	/**奖励*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_REWARD = "5";
	/**其他*/
	public static final String CURRENT_MY_TRANSACTION_RECORD_SEARCH_TYPE_OTHER = "6";

}
