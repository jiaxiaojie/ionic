package com.thinkgem.jeesite.modules.api.base;

/**
 * Description
 *
 * @author pc
 * @version 2016-05-17
 */
public class BusinessConstant {
    //验证码
    public static final String VALIDATE_CODE = "validateCode";
    //验证码宽
    public static final int w = 70;
    //验证码高
    public static final int h = 26;

    public static final String SUCCESS="success";
    //私人订制状态 状态('状态( 0 草稿 1 审核 2 通过 3 不通过 )',
    public static final String PERSONAL_APPROVED="1";
    public static final String PERSONAL_PASS="2";
    public static final String PERSONAL_NOPASS="3";
    public static final String PERSONAL_DRAFT="0";
}
