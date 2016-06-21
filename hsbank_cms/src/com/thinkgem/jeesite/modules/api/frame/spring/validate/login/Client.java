package com.thinkgem.jeesite.modules.api.frame.spring.validate.login;

import com.thinkgem.jeesite.modules.api.frame.spring.validate.annotation.AutoValidate;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 万端瑞 on 2016/6/2.
 */
@AutoValidate
public class Client {
    private String client;
    private  OperTerm operTerm;

    public OperTerm getOperTerm() {
        return operTerm;
    }

    @NotBlank(message="client不能为空")
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
