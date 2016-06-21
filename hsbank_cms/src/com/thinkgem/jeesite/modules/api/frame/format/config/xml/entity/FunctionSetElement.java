package com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by 万端瑞 on 2016/5/18.
 */
@XmlRootElement(name="functions")
public class FunctionSetElement {
    private List<FunctionElement> functionItems;

    @XmlElement(name = "function")
    public List<FunctionElement> getFunctionItems() {
        return functionItems;
    }

    public void setFunctionItems(List<FunctionElement> functionItems) {
        this.functionItems = functionItems;
    }

    public static void main(String[] arags){
        Object obj = JaxbMapper.fromXml("<functions >\n" +
                "\n" +
                "    <function>\n" +
                "        <method>com.thinkgem.jeesite.modules.sys.utils.DictUtils.getDictLabel</method>\n" +
                "        <params>\n" +
                "            <type>java.lang.String</type>\n" +
                "            <type>java.lang.String</type>\n" +
                "            <type>java.lang.String</type>\n" +
                "        </params>\n" +
                "        <return>java.lang.String</return>\n" +
                "    </function>\n" +
                "\n" +
                "</functions>",FunctionSetElement.class);
        System.out.println();
    }
}
