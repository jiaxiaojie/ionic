package com.thinkgem.jeesite.modules.api.frame.util.expression;



import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.util.expression.method.MethodExpressionParser;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.MethodExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.PathExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.StringExpression;

/**
 * Created by 万端瑞 on 2016/6/8.
 * 表达式解析器
 */
public class ExpressionParser {




    /**
     * 解析表达式到表达式对象,目前只支持方法表达式
     * @param expression
     * @return
     */
    public static Expression parseExpression(String expression){
        Expression expr = null;
        if(ExpressionUtils.isStringExpression(expression)){
            StringExpression stringExp = new StringExpression();
            stringExp.setValue(expression.substring(1,expression.length()-1));
            expr = stringExp;
        }else if(ExpressionUtils.isMethodExpression(expression)){
            expr = new MethodExpressionParser().toParseMethod(expression);
        }else{
            PathExpression pathExp = new PathExpression();
            pathExp.setPath(expression);
            expr = pathExp;
        }
        return expr;
    }











    /**
     *
     * @param args
     */
    public static void main(String[] args){
        //new ExpressionParser().parseMethodExpression("hi()");
        //String[] splitResult = expression.split("(?<!\\\\)\\.");
        //Object r2 = new ExpressionParser().parsePathExpression("mthod(ab.a,'\,').") ;
       //Object r = new ExpressionParser().testParseMethod("word(hello(hello.word,hehe('hah\\,a'),true),'123')");
        //System.out.println("".equals("'123'".replaceAll("^'\\S*'$","")));
        //LinkedList<String> result = new ExpressionParser().disassembleMethodNode("word(hello(hello.word,hehe('haha'),true),'123')");
        //System.out.println( result);
    }

}
