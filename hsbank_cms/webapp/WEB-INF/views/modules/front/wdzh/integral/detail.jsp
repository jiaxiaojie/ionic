<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/wdjf_szmx.css?${version }" rel="stylesheet"/>
		<title></title>
		<script type="text/javascript">
			function submit() {
				$("#searchForm").submit();
			}
			$(function(){
				$("#startDateTime").click(function(){
					WdatePicker({maxDate:$("#endDateTime").val()});
				});
				$("#endDateTime").click(function(){
					WdatePicker({minDate:$("#startDateTime").val()});
				});
			});
		</script>
	</head>
	<body>
		<div class="div_height_10"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="line_01">
				<span class="span_text_1">我的花生豆</span>
			</div>
			<hr/>
			<div class="div_height_5"></div>
			<div class="line_02">
				<form id="searchForm" method="post" action="${ctxFront }/customer/integral/detail">
					<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo }"/>
					<span class="span_text_1">类型</span>
					<select name="changeTypeCode" class="select">
						<c:choose>
							<c:when test="${changeTypeCode eq '1' }">
						<option value="">所有</option>
						<option value="1" selected="selected">获取</option>
						<option value="2">消费</option>
							</c:when>
							<c:when test="${changeTypeCode eq '2' }">
						<option value="">所有</option>
						<option value="1">获取</option>
						<option value="2" selected="selected">消费</option>
							</c:when>
							<c:otherwise>
						<option value="" selected="selected">所有</option>
						<option value="1">获取</option>
						<option value="2">消费</option>
							</c:otherwise>
						</c:choose>
					</select>
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
	                    <th width="35%">时间</th>
	                    <th width="35%">操作</th>
	                    <th width="30%">花生豆变化</th>
	                </tr>
	            </thead>
	            <tbody>
					<c:forEach items="${page.list}" var="customerIntegralHis">
	                <tr>
	                    <td><fmt:formatDate value="${customerIntegralHis.opDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                    <td>${fns:getDictLabel(customerIntegralHis.changeType, "customer_integral_change_type_dict"	,"")}</td>
	                    <td>${customerIntegralHis.changeVal }</td>
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