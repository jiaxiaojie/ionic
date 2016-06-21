package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class DeleteAddressParams extends BaseParams {

    private String addressId; //收货地址ID

    @NotBlank(message = "地址ID不能为空")
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
