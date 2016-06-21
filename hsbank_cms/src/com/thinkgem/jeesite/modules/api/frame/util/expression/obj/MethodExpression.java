package com.thinkgem.jeesite.modules.api.frame.util.expression.obj;

import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 万端瑞 on 2016/6/8.
 */
public class MethodExpression implements Expression {
    private String methodName;
    private LinkedList<Expression> methodParams;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public LinkedList<Expression> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(LinkedList<Expression> methodParams) {
        this.methodParams = methodParams;
    }
}
