package com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 万端瑞 on 2016/5/17.
 */
@XmlRootElement(name="data")
public class DataElement {
    private String format;

    @XmlElement(name = "foramt")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
