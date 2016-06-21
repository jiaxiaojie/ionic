package com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by 万端瑞 on 2016/5/18.
 */
@XmlRootElement(name="function")
public class FunctionElement {
    private String clazz;
    private String method;
    private List<String> paramTypes;
    private String returnType;

    @XmlElement(name = "class")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @XmlElement(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @XmlElementWrapper(name="params")
    @XmlElement(name = "type")
    public List<String> getParamTypes() {
        return paramTypes;
    }


    public void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }
    @XmlElement(name = "return")
    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }



}
