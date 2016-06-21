package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class CashCouponParams extends PageParams {
    private String status = "0"; //状态

    @NotBlank(message = "状态不能为空")
    @Pattern(regexp = "[012]",message = "现金券状态只能是0，1或2")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
