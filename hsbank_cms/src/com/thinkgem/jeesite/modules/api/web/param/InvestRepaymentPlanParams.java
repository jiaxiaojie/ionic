package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by 万端瑞 on 2016/5/26.1
 */
public class InvestRepaymentPlanParams extends PageParams {
    private String projectId; //项目ID
    private Double amount; //投资金额

    @NotNull(message = "项目ID不能为空")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Min(value=1,message = "amount必须大于等于1")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
