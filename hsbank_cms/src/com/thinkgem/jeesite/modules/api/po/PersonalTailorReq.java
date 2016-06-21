package com.thinkgem.jeesite.modules.api.po;

/**
 * Description
 *  私人定制参数
 * @author pc
 * @version 2016-05-19
 */
public class PersonalTailorReq {

    private String client;
    private Integer projectId;
    private Integer flag;//记录总数标记(0:不统计,1:统计)
    private Integer pageSize;//页容量
    private Integer pageNumber;//页码

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getPageSize() {
        if(pageSize==null){
            return 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        if(pageNumber==null){
            return 1;
        }
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "PersonalTailorReq{" +
                "client='" + client + '\'' +
                ", projectId=" + projectId +
                ", flag=" + flag +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
