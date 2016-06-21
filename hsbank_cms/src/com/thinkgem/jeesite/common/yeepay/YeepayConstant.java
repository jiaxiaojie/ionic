/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay;

/**
 * @author yangtao
 *
 */
public class YeepayConstant {

	//易宝平台编号
	public static String YEEPAY_PLATFORM_NO="10012467598";
	//易宝浏览器网关地址前缀
	public static String YEEPAY_GATE_URL_PREFIX="https://member.yeepay.com/member/bha/";
	//易宝浏览器网关移动端地址前缀
	public static String YEEPAY_GATE_WIRELESS_URL_PREFIX="https://member.yeepay.com/member/bhawireless/";
	//易宝直接接口地址前缀
	
	public static String YEEPAY_DIRECT_URL_PREFIX="https://member.yeepay.com/member/bhaexter/bhaController";
	
	//payProduct可选值，默认为快捷支付，NET为网银支付
	public static String YEEPAY_PAY_PRODUCT_NET="NET";
	
	//paySwift可选值，快捷支付
	public static String YEEPAY_PAY_SWIFT_UPGRADE = "UPGRADE";
	
	//=====测试环境参数 开始=====
	//网站文件访问前缀
	public static String HSBANK_FILE_ACCESS_PREFIX_URL = "http://139.196.5.141:7007/hsbank/";
	//易宝浏览器返回地址前缀（使用时将端口号修改为相应值）
	public static String YEEPAY_NOTIFI_BASE_URL = "http://139.196.5.141:7007/hsbank/";
	//传给易宝普通产品trenderNo前缀
	public static String YEEPAY_TENDERORDERNO_PREFIX = "test_";
	//传给易宝活期产品trenderNo前缀
	public static String YEEPAY_CURRENT_TENDERORDERNO_PREFIX = "test_current_";
	//易宝后台notify通知地址（使用时将端口号修改为相应值）
	public static String YEEPAY_NOTIFI_BACKSTAGE_BASE_URL = "http://139.196.5.141:7007/hsbank/";
	//=====测试环境参数 结束=====

	/*
	//=====正式环境参数 开始=====
	//网站文件访问前缀
	public static String HSBANK_FILE_ACCESS_PREFIX_URL = "https://www.hsbank360.com/";
	//易宝浏览器返回地址前缀
	public static String YEEPAY_NOTIFI_BASE_URL = "https://www.hsbank360.com/";
	//传给易宝普通产品trenderNo前缀
	public static String YEEPAY_TENDERORDERNO_PREFIX = "";
	//传给易宝活期产品trenderNo前缀
	public static String YEEPAY_CURRENT_TENDERORDERNO_PREFIX = "current_";
	//易宝后台notify通知地址
	public static String YEEPAY_NOTIFI_BACKSTAGE_BASE_URL = "http://112.74.85.169:9508/";
	//=====正式环境参数 结束=====
	*/

	/*
	//=====正式 后台 环境参数 开始=====
	//网站文件访问前缀
	public static String HSBANK_FILE_ACCESS_PREFIX_URL = "https://www.hsbank360.com/";
	//易宝浏览器返回地址前缀
	public static String YEEPAY_NOTIFI_BASE_URL = "http://112.74.85.169:9508/";
	//传给易宝普通产品trenderNo前缀
	public static String YEEPAY_TENDERORDERNO_PREFIX = "";
	//传给易宝活期产品trenderNo前缀
	public static String YEEPAY_CURRENT_TENDERORDERNO_PREFIX = "current_";
	//易宝后台notify通知地址
	public static String YEEPAY_NOTIFI_BACKSTAGE_BASE_URL = "http://112.74.85.169:9508/";
	//=====正式 后台 环境参数 结束=====
	*/

	//易宝加密校验地址前缀
	public static String YEEPAY_SIGN_URL_PREFIX="http://120.25.122.251:6789/sign/servlet/signService";
	//易宝浏览器返回地址前缀(后台)
	public static String YEEPAY_CALLBACK_URL_PREFIX = YEEPAY_NOTIFI_BASE_URL + "a/yeepay/endResult?";
	//易宝浏览器通知地址前缀(后台)
	public static String YEEPAY_NOTIFY_URL_PREFIX = YEEPAY_NOTIFI_BASE_URL + "a/notify/";
	public static String YEEPAY_BACKSTAGE_CALLBACK_URL_PREFIX = YEEPAY_NOTIFI_BACKSTAGE_BASE_URL + "a/yeepay/";
	
	//易宝前端浏览器callback地址前缀
	public static String YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX = YEEPAY_NOTIFI_BASE_URL + "yeepay/gateWay/callback/";
	//易宝前端浏览器notify地址前缀
	public static String YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX = YEEPAY_NOTIFI_BASE_URL + "yeepay/gateWay/notify/";
	//易宝直连notify地址前缀
	public static String YEEPAY_DIRECT_NOTIFY_URL_PREFIX = YEEPAY_NOTIFI_BASE_URL + "yeepay/direct/notify/";
	//易宝前端浏览器移动端callback地址前缀
	public static String YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX = YEEPAY_NOTIFI_BASE_URL + "yeepay/wireless/callback/";
	
	//费率模式：收取商户手续费
	public static String YEEPAY_FEE_MODE_PLATFORM = "PLATFORM";
	//费率模式：收取用户手续费
	public static String YEEPAY_FEE_MODE_USER = "USER";
	
	//提现模式：正常提现，T+1 天到账
	public static String YEEPAY_WITHDRAW_TYPE_NORMAL = "NORMAL";
	//提现模式：加急提现，T+0 当日到账
	public static String YEEPAY_WITHDRAW_TYPE_URGENT = "URGENT";
	
	/**业务类型：TENDER  投标*/
	public static final String BIZ_TYPE_TENDER = "TENDER";
	/**业务类型：REPAYMENT  还款*/
	public static final String BIZ_TYPE_REPAYMENT = "REPAYMENT";
	/**业务类型：CREDIT_ASSIGNMENT  债权转让*/
	public static final String BIZ_TYPE_CREDIT_ASSIGNMENT = "CREDIT_ASSIGNMENT";
	/**业务类型：TRANSFER  转账*/
	public static final String BIZ_TYPE_TRANSFER = "TRANSFER";
	/**业务类型：COMMISSION  分润，仅在资金转账明细中使用*/
	public static final String BIZ_TYPE_COMMISSION = "COMMISSION";
	
	//交互接口编码约定
	//接口文档2.1章节 注册
	public static String PROJECT_INTERFACE_GATE_WAY_TOREGISTER_REQ="2001001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOREGISTER_CALLBACK="2001002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOREGISTER_NOTIFY="2001003";
	public static String PROJECT_INTERFACE_GATE_WAY_TOREGISTER_ACTION="toRegister";
	//接口文档2.2章节 充值
	public static String PROJECT_INTERFACE_GATE_WAY_TORECHARGE_REQ="2002001";
	public static String PROJECT_INTERFACE_GATE_WAY_TORECHARGE_CALLBACK="2002002";
	public static String PROJECT_INTERFACE_GATE_WAY_TORECHARGE_NOTIFY="2002003";
	public static String PROJECT_INTERFACE_GATE_WAY_TORECHARGE_ACTION="toRecharge";
	//接口文档2.3章节 提现
	public static String PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_REQ="2003001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_CALLBACK="2003002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_NOTIFY="2003003";
	public static String PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_ACTION="toWithdraw";
	//接口文档2.4章节 绑卡
	public static String PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ="2004001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_CALLBACK="2004002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_NOTIFY="2004003";
	public static String PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_ACTION="toBindBankCard";
	//接口文档2.5章节 取消绑卡
	public static String PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_REQ="2005001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_CALLBACK="2005002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_NOTIFY="2005003";
	public static String PROJECT_INTERFACE_GATE_WAY_TOUNBINDBANKCARD_ACTION="toUnbindBankCard";
	//接口文档2.6章节 企业用户注册
	public static String PROJECT_INTERFACE_GATE_WAY_TOENTERPRISEREGISTER_REQ="2006001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOENTERPRISEREGISTER_CALLBACK="2006002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOENTERPRISEREGISTER_NOTIFY="2006003";	
	//接口文档2.7章节 转账授权
	//投标
	public static String PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ="20070A1";
	//还款
	public static String PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_REPAYMENT_REQ="20070B1";
	//债权转让
	public static String PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CREDIT_ASSIGNMENT_REQ="20070C1";
	//资金转出
	public static String PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TRANSFER_REQ="20070D1";
	
	public static String PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CALLBACK="2007002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_NOTIFY="2007003";	
	//接口文档2.8章节 自动投标授权
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOTRANSFER_REQ="2008001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOTRANSFER_CALLBACK="2008002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOTRANSFER_NOTIFY="2008003";
	//接口文档2.9章节 自动还款授权
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_REQ="2009001";
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_CALLBACK="2009002";
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_NOTIFY="2009003";
	public static String PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_ACTION="toAuthorizeAutoRepayment";
	//接口文档2.10章节 重置密码
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_REQ="2010001";
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_CALLBACK="2010002";
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_NOTIFY="2010003";
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_ACTION="toResetPassword";
	//接口文档2.11章节 修改手机号
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_REQ="2011001";
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_CALLBACK="2011002";
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_NOTIFY="2011003";
	public static String PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_ACTION="toResetMobile";
	
	//接口文档3.1章节 账户查询
	public static String PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_REQ="300101";
	public static String PROJECT_INTERFACE_DIRECT_ACCOUNT_INFO_SERVICE="ACCOUNT_INFO";
	
	
	
	//接口文档3.2章节 资金冻结
	public static String PROJECT_INTERFACE_DIRECT_FREEZE_REQ="300201";
	public static String PROJECT_INTERFACE_DIRECT_FREEZE_SERVICE="FREEZE";
	//接口文档3.3章节 资金解冻
	public static String PROJECT_INTERFACE_DIRECT_UNFREEZE_REQ="300301";
	public static String PROJECT_INTERFACE_DIRECT_UNFREEZE_SERVICE="UNFREEZE";
	//接口文档3.4章节 直接转账
	public static String PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_REQ="300401";
	public static String PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_NOTIFY="300402";
	public static String PROJECT_INTERFACE_DIRECT_DIRECT_TRANSACTION_SERVICE="DIRECT_TRANSACTION";
	//接口文档3.5章节 自动转账授权
	//正常还款
	public static String PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NORMAL_REQ="3005A1";
	//提前还款
	public static String PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_EARLY_REQ="3005B1";
	//逾期还款
	public static String PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_OVERDUE_REQ="3005C1";
	public static String PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_NOTIFY="300502";
	public static String PROJECT_INTERFACE_DIRECT_AUTO_TRANSACTION_SERVICE="AUTO_TRANSACTION";
	//接口文档3.6章节 单笔业务查询
	public static String PROJECT_INTERFACE_DIRECT_QUERY_REQ="300601";
	public static String PROJECT_INTERFACE_DIRECT_QUERY_SERVICE="QUERY";
	//接口文档3.7章节 转账确认
	public static String PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_REQ="300701";
	public static String PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_NOTIFY="300702";
	public static String PROJECT_INTERFACE_DIRECT_COMPLETE_TRANSACTION_SERVICE="COMPLETE_TRANSACTION";
	//接口文档3.8章节 取消自动投标授权
	public static String PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_TRANSFER_REQ="300801";
	public static String PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_TRANSFER_SERVICE="CANCEL_AUTHORIZE_AUTO_TRANSFER";
	//接口文档3.9章节 取消自动还款授权
	public static String PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_REQ="300901";
	public static String PROJECT_INTERFACE_DIRECT_CANCEL_AUTHORIZE_AUTO_REPAYMENT_SERVICE="CANCEL_AUTHORIZE_AUTO_REPAYMENT";
	//接口文档3.10章节 代扣充值
	public static String PROJECT_INTERFACE_DIRECT_WHDEBITNOCARD_RECHARGE_REQ="301001";
	public static String PROJECT_INTERFACE_DIRECT_WHDEBITNOCARD_RECHARGE_NOTIFY="301002";
	public static String PROJECT_INTERFACE_DIRECT_WHDEBITNOCARD_RECHARGE_SERVICE="WHDEBITNOCARD_RECHARGE";
	//接口文档3.11章节 平台信息
	public static String PROJECT_INTERFACE_DIRECT_PLATFORM_INFO_REQ="301101";
	public static String PROJECT_INTERFACE_DIRECT_PLATFORM_INFO_NOTIFY="301102";
	public static String PROJECT_INTERFACE_DIRECT_PLATFORM_INFO_SERVICE="PLATFORM_INFO";
	//接口文档3.12章节 项目（标的）查询
	public static String PROJECT_INTERFACE_DIRECT_PROJECT_QUERY_REQ="301201";
	public static String PROJECT_INTERFACE_DIRECT_PROJECT_QUERY_SERVICE="PROJECT_QUERY";
	//接口文档3.13 解绑
	public static String PROJECT_INTERFACE_DIRECT_UNBIND_BANK_CARD_REQ="301301";
	public static String PROJECT_INTERFACE_DIRECT_UNBIND_BANK_CARD_SERVICE="UNBIND_CARD";
	
	
}
