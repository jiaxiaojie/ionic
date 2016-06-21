package test;

/**
 * 
 */

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryCpTranscationRespA {
	@XmlAttribute(name = "platformNo")
	private String platformNo; //商户编号
	private String description;
    private String code;
    @XmlElementWrapper(name = "records")  
    @XmlElement(name = "record")  
	private ArrayList<QueryCpTranscationItemA> records;
	public ArrayList<QueryCpTranscationItemA> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<QueryCpTranscationItemA> records) {
		this.records = records;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
