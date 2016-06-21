package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;

/**
 * Created by 万端瑞 on 2016/5/27.
 */
public class ProductPageListParams extends PageParams {
    private String type; //商品类型(1:优惠券,2:商品实物)
    private Integer flag = 0; //记录总数标记(0:不统计,1:统计)(default: 0)

    @NotNull(message = "商品类型不能为空")
    @Pattern(regexp = "[12]",message = "商品类型只能是(1:优惠券,2:商品实物)")
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    @NotNull(message = "记录总数标记不能为空")
    @Min(value=0,message = "记录总数标记只能为(0:不统计,1:统计)（默认为0）")
    @Max(value=1,message = "记录总数标记只能为(0:不统计,1:统计)（默认为0）")
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
