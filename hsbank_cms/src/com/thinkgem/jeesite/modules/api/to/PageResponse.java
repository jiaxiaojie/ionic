package com.thinkgem.jeesite.modules.api.to;

import java.util.List;

/**
 * 分页输出参数对象
 * @author liuguoqing
 */
public class PageResponse<T>{
    /* 记录总数 */
    private long count;
    /* 记录列表 */
    private List<T> resultList;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageResponse{");
        sb.append("count=").append(count);
        sb.append(", resultList=").append(resultList);
        sb.append('}');
        return sb.toString();
    }
}
