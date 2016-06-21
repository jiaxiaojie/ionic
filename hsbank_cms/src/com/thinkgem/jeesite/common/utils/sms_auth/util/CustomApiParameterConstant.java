package com.thinkgem.jeesite.common.utils.sms_auth.util;

import com.hsbank.api.util.ApiParameterConstant;

/**
 * API消息参数常量
 * @author Administrator
 * 2013-05-14
 */
public class CustomApiParameterConstant extends ApiParameterConstant {
    /** 接收短信验证码的手机号码 */
    public static final String MOBILE = "mobile";
    /** 短信内容 */
    public static final String CONTENT = "content";
    /** 发送优先级: (最低)0 - 9(最高) */
    public static final String PRIORITY = "priority";
    /** 验证码 */
    public static final String AUTH_CODE = "auth_code";
}
