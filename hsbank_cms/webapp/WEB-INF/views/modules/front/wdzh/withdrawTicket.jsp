<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/wdjf_jfgk.css?${version }" rel="stylesheet"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/wdjf_szmx.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			$(function(){
				$("#startDateTime").click(function(){
					WdatePicker({maxDate:$("#endDateTime").val()});
				});
				$("#endDateTime").click(function(){
					WdatePicker({minDate:$("#startDateTime").val()});
				});
			});
			function submit() {
				$("#searchForm").submit();
			}
		</script>
	</head>
	<body>
	
		<div class="statistics withdrawTicket">
			<div class="s_top"></div>
			<div class="s_middle column_1  clearfix">
				<div class="line_one"></div>
				<dl class="one">
					<dt class="">当前提现券张数：</dt>
					<dd class="font_color_red">${customerBalance.freeWithdrawCount }张</dd>
				</dl>
			</div>
			<div class="s_bottom"></div>
		</div>
		
		<div class="div_height_5"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle" style="width:749px;">
			<div class="line_01">
				<span class="span_text_1">提现券张数变更明细</span>
			</div>
			<hr/>
			<div class="div_height_5"></div>
			<div class="line_02">
				<form id="searchForm" type="post" action="${ctxFront }/customer/withdrawTicket">
					<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
					<span class="span_text_1">日期</span>
					<input id="startDateTime"  style="cursor:pointer"  name="startDateTime" value="<fmt:formatDate value="${pageSearchBean.startDateTime}" pattern="yyyy-MM-dd"/>" readonly="true" type="text" maxLength="10" class="input"/>
					<span class="span_text_2">至</span>
					<input id="endDateTime"  style="cursor:pointer"  name="endDateTime" value="<fmt:formatDate value="${pageSearchBean.endDateTime}" pattern="yyyy-MM-dd"/>" readonly="true" type="text" maxLength="10" class="input"/>
					<div id="bt_query" onClick="submit()" class="bt_yellow_85x24 bt_query">查询</div>
				</form>
			</div>
			<hr/>
			<table class="table table-hover" style="width: 739px;">
	            <thead>
	                <tr>
	                    <th width="35%">变更时间</th>
	                    <th width="35%">变更原因</th>
	                    <th width="30%">变更张数</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach items="${page.list}" var="customerFreeWithdrawCountHis">
	                <tr>
	                    <td><fmt:formatDate value="${customerFreeWithdrawCountHis.changeDt }" pattern="yyy-MM-dd HH:mm:ss"/></td>
	                    <td>${fns:getDictLabel(customerFreeWithdrawCountHis.changeTypeCode, "customer_free_withdraw_count_change_type_dict", "")}</td>
	                    <td>${customerFreeWithdrawCountHis.changeVal }</td>
	                </tr>
	                </c:forEach>
	            </tbody>
	        </table>
			
			<!-- 分页开始 -->
	        <div id="page_navigation"><div style="display:inline-block;">${page.toStringFront() }</div></div>
	        <script type="text/javascript">
	        	function page(n,s) {
	        		$("#pageNo").val(n);
	        		$("#searchForm").submit();
	        	}
	        </script>
	        <!-- 分页结束 -->
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
	</body>
</html>