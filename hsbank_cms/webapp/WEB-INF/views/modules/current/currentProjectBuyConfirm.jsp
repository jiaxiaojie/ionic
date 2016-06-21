<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投资购买确认</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(
			function() {
				$("#inputForm").submit();
			}
		);
	</script>
</head>
<body>
<form:form id="inputForm" modelAttribute="currentProjectInfo" action="${yeepayURL }" method="post" class="form-horizontal" >
    <textarea name="req" style="display:none;">${req }</textarea>
    <input type="hidden" name="sign" value="${sign }"/>
    <div style="display:none">
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
    </div>
</form:form>	
</body>
</html>