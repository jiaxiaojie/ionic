/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toCpTransaction;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yangtao
 *
 */
public class ToCpTransactionRepaymentExtend {
	public String tenderOrderNo;// 项目编号

	public String getTenderOrderNo() {
		return tenderOrderNo;
	}

	public void setTenderOrderNo(String tenderOrderNo) {
		this.tenderOrderNo = tenderOrderNo;
	}

	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<property name=\"tenderOrderNo\" value=\"")
				.append(tenderOrderNo).append("\"/>");
		return sb.toString();
	}
	public Map<String,String> toMap(){
		Map<String,String> ret=new HashMap<String,String>();
		ret.put("tenderOrderNo", tenderOrderNo);
		return ret;
	}
}
