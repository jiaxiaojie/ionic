<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="值"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="值"%>
<%@ attribute name="idVal" type="java.lang.String" required="true" description="值"%>
<%@ attribute name="idName" type="java.lang.String" required="true" description="值"%>
<%@ attribute name="checked" type="java.lang.String" required="true" description="是否选中"%>



<input type="hidden" name="${idName }" value="${idVal }"/>
<input type="hidden" id="${idVal }" name="${name}" value="${value}"/>
<input value="${idVal }" type="checkbox"  ${checked?'checked="checked"':''} />