package com.thinkgem.jeesite.modules.api.frame.type.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 作者 万端瑞 on 2016/6/20.
 */
@XmlRootElement(name="types")
public class TypeSetElement {

    private List<TypeElement> typeElements;

    @XmlElement(name = "type")
    public List<TypeElement> getTypeElements() {
        return typeElements;
    }

    public void setTypeElements(List<TypeElement> typeElements) {
        this.typeElements = typeElements;
    }
}
