package com.thinkgem.jeesite.modules.api.frame.util.expression;

import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.expression.Function;
import com.thinkgem.jeesite.modules.api.frame.util.expression.context.FunctionConfig;
import com.thinkgem.jeesite.modules.api.frame.util.expression.context.WAPIContext;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.MethodExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.PathExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.StringExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.path.PathParser;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by 万端瑞 on 2016/6/8.
 * 表达式计算器
 */
public class ExpressionCalculator {
    /**
     * 通过表达式获取数据对象中的数据
     * @param expression
     * @param data
     * @return
     */
    public static Object calculateValue(String expression, Object data){
        //1.解析表达式为表达式对象
        Expression expr = ExpressionParser.parseExpression(expression);

        //2.计算表达式的值
        return calculateValue(expr, data);
    }

    /**
     * 计算表达式的值
     * @param expression
     * @param data
     * @return
     */
    public static Object calculateValue(Expression expression, Object data){
        Object result = null;

        //如果是方法表达式
        if(expression instanceof MethodExpression){
            MethodExpression methodExpression = (MethodExpression)expression;

            //1.计算参数的值
            LinkedList<Object> paramVals = new LinkedList<>();
            LinkedList<Expression> params =  methodExpression.getMethodParams();
            for(Expression param : params){
                Object paramVal = calculateValue(param,data);
                paramVals.add(paramVal);
            }

            //调用方法
            Function function = WAPIContext.getFunctionConfig().getFunction(methodExpression.getMethodName());//WExpressionContext.getFunctionConfig().getFunctions().get(methodExpression.getMethodName());
            result = function.invoke(paramVals.toArray());
        }//如果是字符串表达式
        else if(expression instanceof StringExpression){
            result = ((StringExpression)expression).getValue();
        }//如果是path表达式
        else if(expression instanceof PathExpression){
            result = new PathParser().readValue(data,((PathExpression)expression).getPath());
        }
        return result;
    }

    public static void main(String[] args){
        FunctionConfig functionConfig = WAPIContext.getFunctionConfig();
        //functionConfig.loadFunction(new File("C:\\Users\\pc\\IdeaProjects\\hsbank_cms\\resources\\api\\functionTest.xml"));
        Object val = ExpressionCalculator.calculateValue("formatDateByPattern(parseDate(getDate('yyyy-MM-dd')),'yyyy-MM-dd HH:mm:ss\\,')",null);
        System.out.println(val);
    }

}
