<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="my_account"/>
		<link href="${ctxStatic}/modules/front/css/wdzh/wdjf_jfgk.css?${version }" rel="stylesheet"/>
		<title></title>
	</head>
	<body>
	
		<div class="statistics">
			<div class="s_top"></div>
			<div class="s_middle column_3  clearfix">
				<dl class="one border_right">
					<dt class=""><strong>可用花生豆</strong></dt>
					<dd class="font_color_red"><strong>${customerIntegralSnapshot.integralBalance }颗</strong></dd>
				</dl>
				<dl class="two border_right">
					<dt class="">本月获得</dt>
					<dd class="font_color_red">${empty getIntegralCurrentMonth ? 0 : getIntegralCurrentMonth }颗</dd>
				</dl>
				<dl class="three">
					<dt class="">累计获得</dt>
					<dd class="font_color_red">${empty getTotalIntegral ? 0 : getTotalIntegral }颗</dd>
				</dl>
			</div>
			<div class="s_bottom"></div>
		</div>
	
		<div class="div_height_5"></div>
		<div class="bg_789_top"></div>
		<div id="content_center" class="bg_789_middle">
			<div class="div_height_15"></div>
			<div class="list_area">
				<div class="line_01">
					<span class="span_text_1 font_color_brown">最近花生豆变化</span>
					<span class="span_text_2"><a href="${ctxFront }/customer/integral/detail" class="a_underline">详情</a></span>
				</div>
				<table class="table table-hover" style="width: 749px; margin-left: 20px;">
		            <thead>
		                <tr>
		                    <th width="40%">时间</th>
		                    <th width="35%">操作</th>
		                    <th width="25%">花生豆变化</th>
		                </tr>
		            </thead>
		            <tbody>
						<c:forEach items="${customerIntegralHisList}" var="customerIntegralHis" varStatus="status">
						<c:if test="${status.index < 10 }">
		                <tr>
		                    <td><fmt:formatDate value="${customerIntegralHis.opDt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                    <td>${fns:getDictLabel(customerIntegralHis.changeType, "customer_integral_change_type_dict"	,"")}</td>
		                    <td>${customerIntegralHis.changeVal }</td>
		                </tr>
		                </c:if>
		                </c:forEach>
		            </tbody>
		        </table>
			</div>
			<div class="div_height_50"></div>
			<div class="bottom"></div>
		</div>
		<div class="bg_789_bottom"></div>
	</body>
</html>