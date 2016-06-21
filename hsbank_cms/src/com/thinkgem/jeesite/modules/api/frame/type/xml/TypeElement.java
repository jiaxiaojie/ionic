package com.thinkgem.jeesite.modules.api.frame.type.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 作者 万端瑞 on 2016/6/20.
 */
@XmlRootElement(name="type")
public class TypeElement {
    private String name;
    private String javaType;
    private String expression;

    @XmlElement(name = "expression")
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @XmlElement(name = "javaType")
    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }


    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
