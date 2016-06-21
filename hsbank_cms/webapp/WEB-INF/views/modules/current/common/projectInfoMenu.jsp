<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${currentProjectInfo.id}${currentProjectExecuteSnapshot.projectId}${currentAccountPrincipalChangeHis.projectId}${currentProjectRedemptionApply.projectId}${currentAccountInterestChangeHis.projectId}${currentProjectHoldInfo.projectId}"/>


<sys:menus 
topMenus="[
{title:'活期项目信息列表',href='${ctx}/current/currentProjectInfo/',selected=false},
{title:'活期项目详细信息查看',href='',selected=true}]" 

menus="[
{title:'活期项目基本信息',href='${ctx}/current/currentProjectInfo/form?id=${queryValue}',selected=true},
{title:'投资快照',href='${ctx}/current/currentProjectExecuteSnapshot/form?projectId=${queryValue}'},
{title:'交易流水',href='${ctx}/current/currentAccountPrincipalChangeHis/list?projectId=${queryValue}'},
{title:'赎回流水',href='${ctx}/current/currentProjectRedemptionApply/list?projectId=${queryValue}'},
{title:'利息流水',href='${ctx}/current/currentAccountInterestChangeHis/list?projectId=${queryValue}'},
{title:'持有明细',href='${ctx}/current/currentProjectHoldInfo/list?projectId=${queryValue}'}]" 

id="menus" ></sys:menus>
