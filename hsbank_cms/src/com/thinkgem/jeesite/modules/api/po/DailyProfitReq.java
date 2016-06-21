package com.thinkgem.jeesite.modules.api.po;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lzb on 2016/5/27.
 */
public class DailyProfitReq {
    private String projectId;   //项目id
    private String type;        //类型（1:收益,2:投资,3:赎回(利息的提取和本金的赎回)）

    @NotBlank(message="项目id不能为空")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    @NotBlank(message="类型不能为空")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
