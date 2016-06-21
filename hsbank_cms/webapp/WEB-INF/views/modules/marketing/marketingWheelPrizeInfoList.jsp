<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大转盘中奖记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#makePrizeInstance").click(function() {
				top.$.jBox.confirm("生成奖励实例后不可更改且不能添加奖品，确认奖品添加结束并生成实例吗？", "提示", makePrizeInstance);
			});
		});
		function makePrizeInstance(v, h, f) {
			if(v == "ok") {
				$("#makePrizeInstance").hide();
				$.post("${ctx}/marketing/marketingWheelPrizeInfo/makePrizeInstance?activityId=${marketingWheelPrizeInfo.activityId}", {}, function(hasSuccess) {
					if(hasSuccess) {
						top.$.jBox.tip("生成成功!");
					} else {
						top.$.jBox.tip("生成失败!");
					}
					window.location.reload();
				});
			}
		}
		function setDefaultPrize(id) {
			$.get("${ctx}/marketing/marketingWheelPrizeInfo/setDefaultPrize?id=" + id, {}, function(result) {
				$("#searchForm").submit();
			});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/marketing/marketingWheelPrizeInfo/">大转盘奖品列表</a></li>
		<c:if test="${!hasMadePrizeInstance }">
		<shiro:hasPermission name="marketing:marketingWheelPrizeInfo:edit"><li><a href="${ctx}/marketing/marketingWheelPrizeInfo/form">大转盘奖品添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="marketingWheelPrizeInfo" action="${ctx}/marketing/marketingWheelPrizeInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:select path="activityId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${activityList}" itemLabel="name" itemValue="acticityId" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${canMadePrizeInstance }">
			<li class="btns"><input id="makePrizeInstance" class="btn btn-danger" type="button" value="生成奖品实例"/></li>
			</c:if>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>奖品名称</th>
				<th>奖品标签</th>
				<th>奖品类型</th>
				<th>奖品值</th>
				<th>奖品数量</th>
				<th>是否是默认奖品</th>
				<shiro:hasPermission name="marketing:marketingWheelPrizeInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketingWheelPrizeInfo">
			<tr>
				<td><a href="${ctx}/marketing/marketingWheelPrizeInfo/form?id=${marketingWheelPrizeInfo.id}">
					${marketingWheelPrizeInfo.name}
				</a></td>
				<td>
					${marketingWheelPrizeInfo.label}
				</td>
				<td>
					${fns:getDictLabel(marketingWheelPrizeInfo.type, 'marketing_award_type_dict', '')}
				</td>
				<td>
					${marketingWheelPrizeInfo.value}
				</td>
				<td>
					${marketingWheelPrizeInfo.number}
				</td>
				<td>
					<c:choose>
						<c:when test="${marketingWheelPrizeInfo.isDefault eq '1' }">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<shiro:hasPermission name="marketing:marketingWheelPrizeInfo:edit"><td>
					<c:if test="${canMadePrizeInstance }">
    				<a href="${ctx}/marketing/marketingWheelPrizeInfo/form?id=${marketingWheelPrizeInfo.id}">修改</a>
    				</c:if>
    				<a href="javascript:void(0);" onclick="setDefaultPrize(${marketingWheelPrizeInfo.id})">设置为默认奖品</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>