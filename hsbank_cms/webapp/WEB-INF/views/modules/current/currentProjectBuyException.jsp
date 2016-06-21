<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资购买</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		function confirm() {
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/current/currentProjectInfo/list">产品列表</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="currentProjectInfo" action="${ctx}/current/currentProjectInfo/currentProjectBuyDetail" method="post" class="form-horizontal" >
<form:hidden path="id"/>
    <div>
        	<p><span style="color:red">${description }</span></p>
     </div>
	<div class="control-group">
			<label class="control-label">项目名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化利率：</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<form:input path="startingAmount" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
    <div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="button" value="返回" onclick="confirm()" />
	</div>  	
</form:form>	
</body>
</html>