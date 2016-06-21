package com.thinkgem.jeesite.modules.integral;

public class IntegralConstant {
	/**-------------------------------产品状态-----------------------------------*/
	/**取消*/
	public static final String INTEGRAL_PROJECT_STATUS_CANCEL = "-1";
	/**初始创建*/	
	public static final String INTEGRAL_PROJECT_STATUS_CREATE = "0";
	/**审批通过*/
	public static final String INTEGRAL_PROJECT_STATUS_REVIEW_PASS = "1"; 
	/**------------------状态（规格参数、价格策略、用户地址）------------------------------*/
	/**正常*/
	public static final String INTEGRAL_MALL_STATUS_NORMAL = "1";
	/**无效*/
	public static final String INTEGRAL_MALL_STATUS_INVALID = "0";
	/**-------------------------------订单状态-----------------------------------*/
	/**取消*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_CANCEL = "-1";  
	/**下单*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_SUBMIT = "0";
	/**确认*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_PAID = "1";
	/**已发货*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_DELIVER = "2";
	/**已收货*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_RECEIPT = "3";
	/**订单完成*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_OVER = "4";
	/**删除*/
	public static final String INTEGRAL_MALL_ORDER_STATUS_DELETE = "5";
	/**-------------------------------产品价格类型-----------------------------------*/
	/**标新价*/
	public static final String INTEGRAL_MALL_PRICE_TYPE_NEW = "1";
	/**折扣*/
	public static final String INTEGRAL_MALL_PRICE_TYPE_DISCOUNT = "1";
	/**-------------------------------标签类型-----------------------------------*/
	/**右上角*/
	public static final String INTEGRAL_MALL_HOT_TYPE_RIGHT = "1";
	/**左上角*/
	public static final String INTEGRAL_MALL_HOT_TYPE_LEFT = "2";
	
}
