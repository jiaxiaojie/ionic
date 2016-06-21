<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="queryValue" value="${projectDateNode.queryProjectId}${projectTransferInfo.queryProjectId}${projectRepaymentRecord.queryProjectId}${projectInvestmentRecord.queryProjectId}${projectExecuteSnapshot.queryProjectId}${projectRepaymentSplitRecord.queryProjectId}${projectRepaymentPlan.queryProjectId}${projectBaseInfo.id}${projectReviewRecord.queryProjectId}"/>


<sys:menus 
topMenus="[
{title:'借贷产品列表',href='${ctx}/project/projectBaseInfo/${empty projectBaseInfo.backPath?'querylist':projectBaseInfo.backPath}',selected=false},
{title:'借贷产品信息',href='#',selected=true}]" 

menus="[
{title:'合同基本信息',href='${ctx}/project/projectBaseInfo/form?id=${ queryValue}',selected=true},
{title:'审批记录列表',href='${ctx}/project/projectReviewRecord/list?queryProjectId=${ queryValue}'},
{title:'还款计划列表',href='${ctx}/project/projectRepaymentPlan/list?queryProjectId=${ queryValue}'},
{title:'还款明细',href='${ctx}/project/projectRepaymentSplitRecord/projectRepaymentSplitRecordInfoList?queryProjectId=${ queryValue}'},
{title:'执行快照',href='${ctx}/project/projectExecuteSnapshot/view?queryProjectId=${ queryValue}'},
{title:'投资记录列表',href='${ctx}/project/projectInvestmentRecord/list?queryProjectId=${ queryValue}'},
{title:'还款记录列表',href='${ctx}/project/projectRepaymentRecord/list?queryProjectId=${ queryValue}'},
{title:'转让记录列表',href='${ctx}/project/projectTransferInfo/list?queryProjectId=${ queryValue}'},
{title:'数据节点',href='${ctx}/project/projectBaseInfo/projectDateNodeDetail?queryProjectId=${ queryValue}'}

]" 

id="jdProjectInfoMenus" ></sys:menus>

