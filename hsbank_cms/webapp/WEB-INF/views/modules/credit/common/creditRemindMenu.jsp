<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${creditMachineAccount.creditId}${creditInvestUserInfo.creditMachineAccount.creditId }"/>



<sys:menus 
topMenus="[
]" 

menus="[

{title:'客户生日提醒',href='${ctx}/credit/creditInvestUserInfo/birthdayRemindList?creditMachineAccount.creditId=${queryValue}'},
{title:'还款提醒',href='${ctx}/credit/creditInvestUserInfo/repaymentList?creditMachineAccount.creditId=${queryValue}'}

]" 

id="creditRemindMenu" ></sys:menus>
