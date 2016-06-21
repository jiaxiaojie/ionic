package com.thinkgem.jeesite.modules.ApiAutoTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;

/**
 * Created by liuguoqing on 2016/5/23.
 */
public class UrlConstant {
    /**
     * hsbankWeb.index 首页、登录、注册
     */
    //登录
    public final String INDEX_LOGIN = "/index/login";
    //注册
    public final String INDEX_REGISTER = "/index/register";
    //登录/注册--获取验证码
    public final String INDEX_VERIFY_CODE = "/index/getVerifyCode";
    //忘记密码时，重置密码
    public final String INDEX_RESET_PASSWORD = "/index/resetPassword";
    //最新公告
    public final String INDEX_NOTICE_LIST = "/index/getNoticePageList";
    //最新公告详情
    public final String INDEX_NOTICE_DETAILS = "/index/getNoticeDetails";
    //累计募集
    public final String INDEX_COLLECT_TOTAL = "/index/collectTotal";
    //媒体报道
    public final String INDEX_NEWS_LIST = "/index/getNewsPageList";
    //媒体报道详情
    public final String INDEX_NEWS_DETAILS = "/index/getNewsDetails";
    //最新投资
    public final String INDEX_LATEST_INVEST = "/index/getLatestInvestPageList";
    //今日排行
    public final String INDEX_TODAY_RANKING = "/index/todayRanking";
    //广告位信息
    public final String INDEX_AD_POSITION_INFO = "/index/getAdPositionInfo";

    /**
     * hsbankWeb.current 活期理财
     */
    // 活期理财(活花生)详情
    public final String CURRENT_DETAILS = "/current/currentDetails";

    /**
     * hsbankWeb.regular 定期理财
     */
    //新手专享(新花生)
    public final String REGULAR_NOVICE_PROJECT_DETAILS = "/regular/noviceProjectDetails";
    //定期项目-统计信息
    public final String REGULAR_REGULAR_STATISTIC_INFO = "/regular/getRegularStatisticInfo";
    //项目的还款计划
    public final String REGULAR_PROJECT_PAYMENT_PLAN = "/regular/projectPaymentPlan";
    //确认投资-还款计划
    public final String REGULAR_INVEST_REPAYMENT_PLAN = "/regular/InvestRepaymentPlanParams";
    //投资列表
    public final String REGULAR_INVESTMENT_RECORDS = "/regular/investmentRecords";
    //定期项目-分页列表
    public final String REGULAR_PAGE_LIST = "/regular/pageList";
    //获取项目的相关文件
    public final String REGULAR_ABOUT_FILES = "/regular/aboutFiles";
    //定期项目-可投资数量
    public final String REGULAR_INVEST_COUNT = "/regular/getInvestCount";

    /**
     * hsbankWeb.credi 债权转让
     */
    //债权转让-分页列表
    public final String CREDIT_PAGE_LIST = "/credit/pageList";
    //债权转让-项目详情
    public final String CREDIT_DETAILS = "/credit/details";

    /**
     * hsbankWeb.public 私人订制
     */
    //私人订制-分页列表
    public final String PRIVATE_PAGE_LIST = "/private/getPageList";
    //私人订制-项目详情
    public final String PRIVATE_DETAILS = "/private/getPrivateDetails";
    //私人订制-立即预约
    public final String PRIVATE_RESERVATION = "/private/reservation";

    /**
     * hsbankWeb.myAccount 我的账号
     */
    //我的账号-我的信息
    public final String MY_ACCOUNT_INFO = "/myAccount/myInfo";
    //我的账号-账户资产
    public final String MY_ACCOUNT_ASSETS = "/myAccount/myAssets";
    //我的账号-账户收益
    public final String MY_ACCOUNT_PROFIT = "/myAccount/myProfit";
    //我的账号-投资走势 FIXME 暂不开发
    // public final String MY_ACCOUNT_INVESTMENT_TREND = "/myAccount/investmentTrend";
    //"我的账号-投资失败的冻结资金
    public final String MY_ACCOUNT_FROZENAMOUNT_INVEST_FAIL = "/myAccount/frozenAmountByInvestFail";
    //我的账号-签到
    public final String MY_ACCOUNT_SIGN = "/myAccount/sign";

    /**
     * hsbankWeb.myCapitalManage 资金管理
     */
    //资金管理-交易记录
    public final String MY_CAPITAL_MANAGE_RECORD = "/myCapitalManage/transactionRecord";

    /**
     * hsbankWeb.myInvestment 我的投资
     */
    //我的投资-活期理财(统计信息)
    public final String MY_INVESTMENT_CURRENT_STATISTIC = "/myInvestment/currentStatistic";
    //我的投资-活期理财(项目分页列表)
    public final String MY_INVESTMENT_CURRENT_PAGE_LIST = "/myInvestment/currentPageList";
    //我的投资-活期理财(活期项目信息)
    public final String MY_INVESTMENT_CURRENT_PROJECT_INFO = "/myInvestment/currentProjectInfo";
    //我的投资-活期理财(每日信息分页列表)
    public final String MY_INVESTMENT_CURRENT_DAILY_LIST = "/myInvestment/currentDailyPageList";
    //我的投资-定期理财
    public final String MY_INVESTMENT_REGULAR = "/myInvestment/regular";

    /**
     * hsbankWeb.accountManage 账户设置
     */
    //账户设置-个人信息
    public final String ACCOUNT_MANAGE_PERSONAL_INFOR = "/accountManage/personalInfor";
    //账户设置-银行卡信息
    public final String ACCOUNT_MANAGE_BANK_INFOR = "/accountManage/bankInfor";
    //账户设置-银行卡解绑结果
    public final String ACCOUNT_MANAGE_UNBIND_BANK_CARD_RESULT = "/accountManage/unbindBankCardResult";
    //账户设置-绑定邮箱(获取邮箱验证码)
    public final String ACCOUNT_MANAGE_EMAIL_CODE = "/accountManage/getEmailCode";
    //账户设置-绑定邮箱(激活邮箱)
    public final String ACCOUNT_MANAGE_ACTIVATE_EMAIL = "/accountManage/activateEmail";
    //账户设置-绑定邮箱(修改邮箱)
    public final String ACCOUNT_MANAGE_UPDATE_EMAIL = "/accountManage/activateEmail";

    /**
     * hsbankWeb.myCoupons 我的卡券
     */
    //我的卡券-现金券统计
    public final String MY_COUPONS_CASH_COUPON_STATISTIC="/myCoupons/cashCouponStatistic";
    //我的卡券-现金券
    public final String MY_COUPONS_CASH_COUPON="/myCoupons/cashCoupon";
    //我的卡券-提现券
    public final String MY_COUPONS_WITHDRAW_COUPON="/myCoupons/withdrawCoupon";

    /**
     * hsbankWeb.myMessage 我的消息
     */
    //我的消息-统计信息
    public final String MY_MESSAGE_STATISTIC="/myMessage/messageStatistic";
    //我的消息
    public final String MY_MESSAGE_PAGE_LIST="/myMessage/messagePageList";
    //消息更新(标记已读或删除)
    public final String MY_MESSAGE_UPDATE_MESSAGE="/myMessage/updateMessage";
    //获取消息设置信息
    public final String MY_MESSAGE_SETTING_INFO="/myMessage/settingInfo";
    //消息设置
    public final String MY_MESSAGE_SETTING="/myMessage/messageSetting";

    /**
     * hsbankWeb.integralMall 花生乐园
     */
    // 花生乐园-参与记录
    public final String INTEGRAL_MALL_ORDER_LIST = "/integralMall/buyOrderList";
    // 花生乐园-商品分页列表
    public final String INTEGRAL_MALL_PRODUCT_LIST = "/integralMall/productPageList";

    /**
     * hsbankWeb.myPeanut 我的花生
     */
    //我的花生-花生豆分页列表
    public final String MY_PEANUT_PAGE_LIST="/myPeanut/myPeanutPageList";
    //我的花生-兑换记录(订单)分页列表
    public final String MY_PEANUT_ORDER_PAGE_LIST="/myPeanut/orderPageList";
    //我的花生-兑换记录(订单)详情
    public final String MY_PEANUT_ORDER_DETAILS="/myPeanut/orderDetails";

    /**
     * hsbankWeb.myAddress 我的收货地址
     */
    //我的收货地址-分页列表
    public final String MY_ADDRESS_PAGE_LIST="/myAddress/pageList";
    //我的收货地址-新增/编辑地址
    public final String MY_ADDRESS_MERGE_ADDRESS="/myAddress/mergeAddress";
    //我的收货地址-删除地址
    public final String MY_ADDRESS_DELETE_ADDRESS="/myAddress/deleteAddress";

}
