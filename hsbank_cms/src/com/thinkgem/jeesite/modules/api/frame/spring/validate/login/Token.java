package com.thinkgem.jeesite.modules.api.frame.spring.validate.login;

import com.thinkgem.jeesite.modules.api.frame.spring.validate.annotation.AutoValidate;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 万端瑞 on 2016/5/25.
 */
@AutoValidate
public class Token extends Client {
    private String token;
    private Long accountId;

    @NotBlank(message="token不能为空")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getAccountId() {
        return accountId;
    }


}
