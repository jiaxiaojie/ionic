package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class AreaListParams extends BaseParams {
    private String areaId = "1"; //区域ID


    @NotBlank(message = "区域ID不能为空")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
