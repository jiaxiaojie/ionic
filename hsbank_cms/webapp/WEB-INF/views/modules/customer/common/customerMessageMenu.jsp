<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${modelMenus.accountId}"/>
<c:set var="disableSelect" value="false"/>
<c:if test="${!empty queryValue }">
<c:set var="topMenus" value="{title:'会员列表',href='${ctx}/customer/customerAccountInfo/customerAccountInfoList',selected=false},
{title:'消息设置',href='',selected=true}"/>
<c:set var="disableSelect" value="true"/>
</c:if>
<c:set var="disabledReceiveSmsMessageTimeForm" value="true"/>

<shiro:hasPermission name="message:receiveSmsMessageTime:edit">
<c:set var="disabledReceiveSmsMessageTimeForm" value="false"/>
</shiro:hasPermission>

<!-- {title:'消息',href='${ctx}/message/messageInstance/searchPageList?accountId=${queryValue}'}, -->

<sys:menus 
topMenus="[${topMenus}]" 
menus="[
{title:'消息开关',href='${ctx}/message/receiveMessageSwitch/customerSwitchSettingList?accountId=${queryValue}'},
{title:'短信接收时段设置',href='${ctx}/message/receiveSmsMessageTime/toSettingTime?accountId=${queryValue}&disableSelect=${disableSelect }&disabledForm=${disabledReceiveSmsMessageTimeForm }'}
]" 
id="customerMessageMenu" ></sys:menus>

<!-- 
<c:if test="${empty modelMenus.pageStyle}">
</c:if>
<c:if test="${!empty modelMenus.pageStyle}">
<sys:menus 
topMenus="[
]" 

menus="[
{title:'消息开关',href='${ctx}/message/receiveMessageSwitch/customerSwitchSettingList?pageStyle=my'},
{title:'短信接收时段设置',href='${ctx}/message/receiveSmsMessageTime/toSettingTime?disabledForm=false&pageStyle=my'}
]" 
id="customerMessageMenu" ></sys:menus>
</c:if> -->