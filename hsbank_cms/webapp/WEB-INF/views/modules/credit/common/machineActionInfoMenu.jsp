<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${creditMachineAccount.creditId}${creditInvestUserInfo.creditMachineAccount.creditId }"/>



<sys:menus 
topMenus="[
{title:'债权基本信息列表',href='${ctx}/credit/creditBaseInfo/',selected=false},
{title:'债权所属台账信息查看',href='',selected=true}]" 

menus="[
{title:'债权所属台账信息',href='${ctx}/credit/creditMachineAccount/list?creditId=${queryValue}',selected=true},
{title:'客户生日提醒',href='${ctx}/credit/creditInvestUserInfo/birthdayRemindList?creditMachineAccount.creditId=${queryValue}'},
{title:'还款提醒',href='${ctx}/credit/creditInvestUserInfo/repaymentList?creditMachineAccount.creditId=${queryValue}'}

]" 

id="machineAccountInfomenus" ></sys:menus>
