package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class GetEmailCodeParams extends BaseParams {
    private String email; //邮箱

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
