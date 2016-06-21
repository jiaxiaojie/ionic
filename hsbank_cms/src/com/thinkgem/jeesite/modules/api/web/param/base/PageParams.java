package com.thinkgem.jeesite.modules.api.web.param.base;

import javax.validation.constraints.Min;

/**
 * Created by 万端瑞 on 2016/5/26.1
 */
public class PageParams extends BaseParams {//@RequestParam(required=false,defaultValue="10")，@RequestParam(required=false,defaultValue="1")
    private Integer pageSize = 10;
    private Integer pageNumber = 1;


    @Min(value=1,message = "pageSize必须大于等于1")
    public Integer getPageSize() {
        return pageSize;
    }


    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Min(value=1,message = "pageNumber必须大于等于1")
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
