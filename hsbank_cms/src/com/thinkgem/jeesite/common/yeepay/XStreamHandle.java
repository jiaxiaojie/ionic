/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.DefaultMapper;

public class XStreamHandle {
	static Logger  logger = Logger.getLogger(XStreamHandle.class);

    @SuppressWarnings("deprecation")
	public static String toXml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.registerConverter(new MapCustomConverter(new DefaultMapper(XmlOutputFormatter.class.getClassLoader()))); 
        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
        /*
         // 以压缩的方式输出XML
         StringWriter sw = new StringWriter();
         xstream.marshal(obj, new CompactWriter(sw));
         return sw.toString();
         */
        // 以格式化的方式输出XML
        String xml= xstream.toXML(obj);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
		Matcher m = p.matcher(xml);
		xml = m.replaceAll("");
		return xml;
    }
    
    @SuppressWarnings("deprecation")
	public static <T> T toBean(String xmlStr, Class<T> cls) {
    	logger.info("str--"+xmlStr);
        XStream xstream = new XStream(new DomDriver());
        xstream.registerConverter(new MapCustomConverter(new DefaultMapper(XmlOutputFormatter.class.getClassLoader()))); 
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
    
  
}