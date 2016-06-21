package com.thinkgem.jeesite.common.utils.sms_auth.util;

import com.hsbank.api.util.ApiStatusConstant;

/**
 * API接口响应参数常量
 * @author Administrator
 * 2013-05-14
 */
public class CustomApiStatusConstant extends ApiStatusConstant {
    /**缺少手机号码*/
    public static final String MOBILE_MISSING_CODE = "101";
    public static final String MOBILE_MISSING_TEXT = "mobile missing";
    /**无效的手机号码*/
    public static final String MOBILE_INVALID_CODE = "102";
    public static final String MOBILE_INVALID_TEXT = "mobile invaild";
    
    /**缺少验证码*/
    public static final String AUTH_CODE_MISSING_CODE = "103";
    public static final String AUTH_CODE_MISSING_TEXT = "auth_code missing";
    /**无效的验证码*/
    public static final String AUTH_CODE_INVALID_CODE = "104";
    public static final String AUTH_CODE_INVALID_TEXT = "auth_code invaild";
}
