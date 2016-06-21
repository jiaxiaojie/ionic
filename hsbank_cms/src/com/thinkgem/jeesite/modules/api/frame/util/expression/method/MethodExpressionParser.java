package com.thinkgem.jeesite.modules.api.frame.util.expression.method;

import com.thinkgem.jeesite.modules.api.frame.util.Stack;
import com.thinkgem.jeesite.modules.api.frame.util.expression.ExpressionUtils;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.MethodExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.PathExpression;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.StringExpression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 万端瑞 on 2016/6/12.
 * 方法调用的表达式解析器
 * TODO 方法中字符串参数目前只支持连续1个\，如果多个可能会出错，之后改进
 */
public class MethodExpressionParser {
    private Stack<Object> nodeStack = new Stack<Object>();

    /**
     * 解析表达式为对象
     * @param methodExpression
     * @return
     */
    public MethodExpression toParseMethod(String methodExpression){
        //1.分解节点
        LinkedList<String> nodeList = new LinkedList<>();
        nodeList = disassembleMethodNode(methodExpression);

        for(String methodNode : nodeList){
            if(")".equals(methodNode)){
                MethodExpression methodExpr = popMethodForNodeStack();
                nodeStack.push(methodExpr);
            }else if(!",".equals(methodNode)){
                nodeStack.push(methodNode);
            }
        }
        return (MethodExpression) nodeStack.pop();
    }

    /**
     * 从节点Stack中弹出方法
     * @return
     */
    private MethodExpression popMethodForNodeStack() {
        //1.弹出参数
        LinkedList<Object> params = new LinkedList<>();
        Boolean isPop = true;
        while (isPop){
            Object node = nodeStack.pop();
            if(!"(".equals(node)){
                params.add(node);
            }else{
                isPop = false;
            }
        }

        //2.弹出方法名
        String methodName = nodeStack.pop().toString();

        //3.构造返回值
        MethodExpression method = new MethodExpression();
        LinkedList<Expression> expressions = new LinkedList<>();
        for(Object param : params){
            if(param instanceof MethodExpression){
                expressions.add((MethodExpression)param);

            }//如果是字符串节点
            else if(param instanceof String && ExpressionUtils.isStringExpression((String)param)){
                StringExpression stringExp = new StringExpression();
                String paramStr = (String)param;
                stringExp.setStringExpression(paramStr);
                expressions.add(stringExp);
            }else{//否则认为是path节点
                PathExpression pathExp = new PathExpression();
                pathExp.setPath((String)param);
                expressions.add(pathExp);
            }
        }
        Collections.reverse(expressions);
        method.setMethodParams(expressions);
        method.setMethodName(methodName);
        return method;
    }




    /**
     * 分割节点
     * @param methodExpression
     * @return
     */
    private LinkedList<String> disassembleMethodNode(String methodExpression) {
        LinkedList<String> result = new LinkedList<>();
        char[] methodExpressionChar = methodExpression.toCharArray();

        Integer prevMethodBeginIndex = 0;
        for(int i = 0; i < methodExpressionChar.length; i++){
            if(((methodExpressionChar[i] == '('
                    || methodExpressionChar[i] == ')'
                    || methodExpressionChar[i] == ',') &&  methodExpressionChar[i-1] != '\\')
                    || i==methodExpressionChar.length){
                if(prevMethodBeginIndex < i){
                    result.add(methodExpression.substring(prevMethodBeginIndex,i).trim());
                }
                result.add(methodExpression.substring(i,i+1).trim());
                prevMethodBeginIndex = i+1;
            }
        }
        return result;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        //new ExpressionParser().parseMethodExpression("hi()");
        //String[] splitResult = expression.split("(?<!\\\\)\\.");
        //Object r2 = new ExpressionParser().parsePathExpression("mthod(ab.a,'\,').") ;
        Object r = new MethodExpressionParser().toParseMethod("word(hello(hello.word,;hehe('hah\\,a'),true),'123')");
        //System.out.println("".equals("'123'".replaceAll("^'\\S*'$","")));
        //LinkedList<String> result = new ExpressionParser().disassembleMethodNode("word(hello(hello.word,hehe('haha'),true),'123')");
        System.out.println( r);
    }
}
