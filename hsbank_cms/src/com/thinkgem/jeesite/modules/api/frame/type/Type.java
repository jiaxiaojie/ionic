package com.thinkgem.jeesite.modules.api.frame.type;

import com.thinkgem.jeesite.common.supcan.common.properties.Express;
import com.thinkgem.jeesite.modules.api.frame.type.xml.TypeElement;
import com.thinkgem.jeesite.modules.api.frame.util.expression.ExpressionParser;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;

/**
 * 作者 万端瑞 on 2016/6/20.
 */
public class Type {
    private String name;
    private Class javaType;
    private Expression expression;



    public Type(String name, Class javaType, Expression expression) {
        this.expression = expression;
        this.javaType = javaType;
        this.name = name;
    }

    public static Type createType(TypeElement typeElement){
        String name = typeElement.getName();
        Expression expression = ExpressionParser.parseExpression(typeElement.getExpression());
        Class clazz = null;
        try {
            if(typeElement.getJavaType()==null){
                throw new RuntimeException("类型"+typeElement.getName()+"对应的java类型不能为Null");
            }
            clazz = Class.forName(typeElement.getJavaType());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("类型"+typeElement.getName()+"对应的java类型"+typeElement.getJavaType()+"没找到");
        }
        Type type = new Type(name,clazz, expression);
        return type;
    }



    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
