package com.thinkgem.jeesite.modules.api.web.param;


import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class RegularParams extends PageParams {
    private String type; //项目类型(0:全部,1:抵押,2:个人信用贷,3:商圈贷,4:质押,5:融资租赁,6:资管计划)
    private String status; //项目状态(1:投标中,2:持有中,3:已结束,4:转出中)

    @NotBlank(message = "项目类型不能为空")
    @Pattern(regexp = "[01234567]",message = "项目类型只能为以下值：(0:全部,1:抵押,2:个人信用贷,3:商圈贷,4:质押,5:融资租赁,6:资管计划,7:供应链)")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    @NotBlank(message = "项目状态不能为空")
    @Pattern(regexp = "[1234]",message = "项目状态只能为以下值：(1:投标中,2:持有中,3:已结束,4:转出中)")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
