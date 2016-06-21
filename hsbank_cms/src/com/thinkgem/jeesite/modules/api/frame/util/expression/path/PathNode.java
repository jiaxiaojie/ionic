package com.thinkgem.jeesite.modules.api.frame.util.expression.path;

/**
 * Created by 万端瑞 on 2016/6/6.
 */
public class PathNode {
    private String columnName;
    private Integer index;//index为null表示没有索引，index为-1表示索引集合中所有项，index>=0表示索引指定项

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public PathNode(String columnName, Integer index) {
        this.columnName = columnName;
        this.index = index;
    }

    @Override
    public String toString() {
        return "columnName:"+(columnName==null?"null":columnName) + ", index:" + (index==null?"null":index.toString());
    }
}
