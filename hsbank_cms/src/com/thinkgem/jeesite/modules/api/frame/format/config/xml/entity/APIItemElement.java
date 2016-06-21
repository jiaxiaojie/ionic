package com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 万端瑞 on 2016/5/17.
 */
@XmlRootElement(name="api")
public class APIItemElement {
    private DataElement data;
    private String path;

    @XmlAttribute(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlElement(name = "data")
    public DataElement getData() {
        return data;
    }

    public void setData(DataElement data) {
        this.data = data;
    }
}
