package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 万端瑞 on 2016/5/27.1
 */
public class UpdateEmailParams extends BaseParams {
    private String newEmail;
    private String emailCode;
    private String password;

    @NotBlank(message = "新邮箱不能为空")
    @Email(message = "新邮箱格式不正确")
    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    @NotBlank(message = "邮箱验证码不能为空")
    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    @NotBlank(message = "密码不能为空")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
