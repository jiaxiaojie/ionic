package com.thinkgem.jeesite.modules.api.po;

/**
 * Description
 * 登录参数
 * @author pc
 * @version 2016-05-16
 */
public class LoginReq{

    private String client;
    private String mobile;
    private String password;
    private String smsCode;
    private String verifyCode;

    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "client='" + client + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
