/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.query;

import java.util.List;

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
public class QueryWithdrawResp {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
    public String description;
	public String code;
	@XmlElementWrapper(name = "records")  
    @XmlElement(name = "record") 
	public List<QueryWithdrawItem> records;
	
	public List<QueryWithdrawItem> getRecords() {
		return records;
	}
	public void setRecords(List<QueryWithdrawItem> records) {
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
