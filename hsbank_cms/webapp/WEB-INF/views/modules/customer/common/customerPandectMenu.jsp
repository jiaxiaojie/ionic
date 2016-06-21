<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${currentAccountSummary.accountId}${currentProjectHoldInfo.accountId}${currentAccountPrincipalChangeHis.accountId}${currentAccountInterestChangeHis.accountId}${currentProjectRedemptionApply.accountId}"/>



<sys:menus 
topMenus="[
{title:'会员列表',href='${ctx}/customer/customerAccountInfo/customerAccountInfoList',selected=false},
{title:'活期投资',href='${ctx}/current/currentAccountSummary/form?pageType=accountMenus&accountId=${queryValue}&queryFieldName=accountId',selected=true}]" 

menus="[
{title:'活期账户总览',href='${ctx}/current/currentAccountSummary/form?accountId=${queryValue}',selected=true},
{title:'持有明细',href='${ctx}/current/currentProjectHoldInfo/list?accountId=${queryValue}'},
{title:'交易流水',href='${ctx}/current/currentAccountPrincipalChangeHis/list?accountId=${queryValue}'},
{title:'利息流水',href='${ctx}/current/currentAccountInterestChangeHis/list?accountId=${queryValue}'},
{title:'赎回流水',href='${ctx}/current/currentProjectRedemptionApply/list?accountId=${queryValue}'}]" 

id="accountMenus" ></sys:menus>



