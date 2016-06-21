package com.thinkgem.jeesite.modules.api.frame.util.expression.obj;

import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;

/**
 * Created by 万端瑞 on 2016/6/8.
 */
public class PathExpression implements Expression {
    //private Expression headNodeExpression;
    private String path;

    /*public Expression getHeadNodeExpression() {
        return headNodeExpression;
    }

    public void setHeadNodeExpression(Expression headNodeExpression) {
        this.headNodeExpression = headNodeExpression;
    }*/

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
