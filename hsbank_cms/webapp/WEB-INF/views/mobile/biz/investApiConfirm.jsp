<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html ng-app="hsWechat" ui-prevent-touchmove-defaults>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
	    <base href="/" />
	    <meta name="apple-mobile-web-app-capable" content="yes" />
	    <meta name="apple-mobile-web-app-status-bar-style" content="yes" />
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/mobile/css/app-base.css" />
	    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
		<title></title>
		<script type="text/javascript">
			function toSubmit() {
				$("#investForm").submit();
			}
		</script>
	</head>
	<body>
	<div class="app-body" style="display: none;">
        <div class="app-content">
             <div class="yp-main">
                <div class="yp-body">
                    <ul class="list-group menu hs-margin-top-10">
                        <li class="list-group-item">
                            <span>投资项目：</span>
                            <span class="menu-text-right pull-right">${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')} ${projectBaseInfo.projectCode }</span>
                        </li>
                        <li class="list-group-item">
                            <span>年利率：</span>
                            <span class="menu-text-right pull-right"><fmt:formatNumber value="${projectBaseInfo.annualizedRate }" type="percent" maxFractionDigits="1" /></span>
                        </li>
                        <li class="list-group-item">
                            <span>项目周期：</span>
                            <span class="menu-text-right pull-right">${projectBaseInfo.projectDuration }个月</span>
                        </li>
                        <li class="list-group-item">
                            <span>投资金额：</span>
                            <span class="menu-text-right pull-right"><fmt:formatNumber value="${amount }" pattern="#0.00"/>元</span>
                        </li>
                        <li class="list-group-item">
                            <span>应付金额：</span>
                            <span class="menu-text-right pull-right"><fmt:formatNumber value="${actualAmount }" pattern="#0.00"/>元</span>
                        </li>
                    </ul>
                </div>
                <form id="investForm" action="${yeepayURL }" method="post">
		        	<textarea name="req" style="display:none;">${req }</textarea>
		        	<input type="hidden" name="sign" value="${sign }"/>
		        </form>
                <div class="yp-btn hs-margin-top-10">
                    <div class="container">
                        <div class="row">
                            <div class="col-xs-12">
                                <a href="javascript:void(0);" class="btn btn-danger btn-block btn-md" onclick="toSubmit()">立即支付</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <script type="text/javascript">
           $("#investForm").submit();
		</script>
</body>
</html>