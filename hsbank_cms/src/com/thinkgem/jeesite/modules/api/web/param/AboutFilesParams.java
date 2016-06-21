package com.thinkgem.jeesite.modules.api.web.param;


import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by 万端瑞 on 2016/5/26.
 */
public class AboutFilesParams  extends BaseParams {
    private Integer projectId;

    @NotNull(message = "项目ID不能为空")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
