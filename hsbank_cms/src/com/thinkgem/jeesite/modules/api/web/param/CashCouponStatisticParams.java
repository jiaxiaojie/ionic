package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class CashCouponStatisticParams extends BaseParams {
    private String status = "0";

    @NotBlank(message = "现金券状态不能为空")
    @Pattern(regexp = "[012]",message = "现金券状态只能是0，1或2")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
