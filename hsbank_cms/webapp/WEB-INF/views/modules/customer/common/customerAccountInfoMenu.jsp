<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${model.accountId}${repaymentSplitRecord.payeeUserId}"/>



<sys:menus 
topMenus="[
{title:'会员列表',href='${ctx}/customer/customerAccountInfo/customerAccountInfoList',selected=false},
{title:'会员详细信息',href='${ctx}/customer/customerAccountInfo/customerAccountInfo?pageType=customerAccountInfoMenu&accountId=${queryValue}',selected=true}]"

menus="[
{title:'账号信息',href='${ctx}/customer/customerAccountInfo/customerAccountInfo?accountId=${queryValue}',selected=true},
{title:'基本信息',href='${ctx}/customer/customerAccountInfo/baseInfo?accountId=${queryValue}'},
{title:'汇总信息',href='${ctx}/customer/customerAccountInfo/balanceInfo?accountId=${queryValue}'},
{title:'现金券清单',href='${ctx}/customer/customerAccountInfo/investmentTicketInfo?accountId=${queryValue}'},
{title:'余额流水',href='${ctx}/customer/customerAccountInfo/customerBalanceHisInfo?accountId=${queryValue}'},
{title:'提现次数流水',href='${ctx}/customer/customerAccountInfo/customerFreeWithdrawCountHisInfo?accountId=${queryValue}'},
{title:'绑卡信息',href='${ctx}/customer/customerAccountInfo/customerBankCardInfo?accountId=${queryValue}'},
{title:'投资信息',href='${ctx}/customer/customerAccountInfo/projectInvestmentRecordInfo?investmentUserId=${queryValue}'},
{title:'提现记录',href='${ctx}/customer/customerAccountInfo/customerWithdrawHisInfo?accountId=${queryValue}'},
{title:'花生豆流水',href='${ctx}/customer/customerAccountInfo/customerIntegralDetailInfo?accountId=${queryValue}'},
{title:'邀请好友流水',href='${ctx}/customer/customerAccountInfo/customerFriendsInfo?accountId=${queryValue}'},
{title:'还款信息',href='${ctx}/customer/customerAccountInfo/repaymentSplitRecordInfo?payeeUserId=${queryValue}'}
]"
id="customerAccountInfoMenu" ></sys:menus>



