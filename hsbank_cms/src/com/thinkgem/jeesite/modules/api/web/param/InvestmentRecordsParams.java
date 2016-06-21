package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;

import javax.validation.constraints.NotNull;

/**
 * Created by 万端瑞 on 2016/5/26.1
 */
public class InvestmentRecordsParams extends PageParams {
    private Long projectId; //项目ID

    @NotNull(message = "项目ID不能为空")
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
