package com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by 万端瑞 on 2016/5/17.
 */
@XmlRootElement(name="apis")
public class APISetElement {
    private String basePath;
    private List<APIItemElement> apiItems;

    @XmlElement(name = "api")
    public List<APIItemElement> getApiItems() {
        return apiItems;
    }

    public void setApiItems(List<APIItemElement> apiItems) {
        this.apiItems = apiItems;
    }

    @XmlAttribute(name = "basePath")
    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public static void main(String[] arags){
        Object obj = JaxbMapper.fromXml("<apis basePath = \"${frontPath}/api/regular\">\n" +
                "    <api path=\"aboutFiles\">\n" +
                "        <data>\n" +
                "            <foramt>\n" +
                "                [\n" +
                "                {\n" +
                "                fileType:{\n" +
                "                description:\"文件类型(1:营业执照,2:贸易合同,3:借款合同,4:央行登记信息,5:实地考察照片,6:发票,7:物流签收单据,8:其他资料)\",\n" +
                "                type:\"int\",\n" +
                "                optional:\"FALSE\"       },\n" +
                "                fileTypeName:{\n" +
                "                description:\"文件类型名称\",\n" +
                "                type:\"String\",\n" +
                "                optional:\"FALSE\"       },\n" +
                "                resultList:[\n" +
                "                {\n" +
                "                description:\"文件图片url(绝对路径)\",\n" +
                "                type:\"String\",\n" +
                "                optional:\"FALSE\"         }\n" +
                "                ]\n" +
                "                }\n" +
                "                ]\n" +
                "            </foramt>\n" +
                "        </data>\n" +
                "    </api>\n" +
                "\n" +
                "    <api path=\"repaymentPlan\">\n" +
                "        <data>\n" +
                "            <foramt>\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t  count:{\n" +
                "\t\t\t\t\t description:\"记录总数\",\n" +
                "\t\t\t\t\t type:\"int\",\n" +
                "\t\t\t\t\t optional:\"FALSE\"     },\n" +
                "\t\t\t\t  resultList:[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t  planDate:{\n" +
                "\t\t\t\t\t\t description:\"还款日期\",\n" +
                "\t\t\t\t\t\t type:\"string\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  planMoney:{\n" +
                "\t\t\t\t\t\t description:\"还款金额\",\n" +
                "\t\t\t\t\t\t type:\"number\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  principal:{\n" +
                "\t\t\t\t\t\t description:\"应还本金\",\n" +
                "\t\t\t\t\t\t type:\"number\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  interest:{\n" +
                "\t\t\t\t\t\t description:\"应还利息\",\n" +
                "\t\t\t\t\t\t type:\"number\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  remainingPrincipal:{\n" +
                "\t\t\t\t\t\t description:\"剩余应还本金\",\n" +
                "\t\t\t\t\t\t type:\"number\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  status:{\n" +
                "\t\t\t\t\t\t description:\"状态\",\n" +
                "\t\t\t\t\t\t type:\"string\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  statusName:{\n" +
                "\t\t\t\t\t\t description:\"状态名称\",\n" +
                "\t\t\t\t\t\t type:\"String\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"         },\n" +
                "\t\t\t\t\t  residualInterest:{\n" +
                "\t\t\t\t\t\t description:\"剩余本息\",\n" +
                "\t\t\t\t\t\t type:\"number\",\n" +
                "\t\t\t\t\t\t optional:\"FALSE\"\n" +
                "                         dataColumn:\"remainingPrincipal\"\n" +
                "                        }\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t  ]\n" +
                "\t\t\t\t}\n" +
                "            </foramt>\n" +
                "        </data>\n" +
                "    </api>\n" +
                "\n" +
                "</apis>",APISetElement.class);
        System.out.println();
    }
}
