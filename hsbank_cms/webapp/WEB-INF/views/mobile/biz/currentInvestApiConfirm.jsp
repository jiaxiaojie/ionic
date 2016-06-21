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
	</head>
	<body>
	<div class="app-body" style="display: none;">
        <div class="app-content">
             <div class="yp-main">
                <div class="yp-body">
                    <ul class="list-group menu hs-margin-top-10">
                        <li class="list-group-item">
                            <span>投资项目：</span>
                            <span class="menu-text-right pull-right">${p2p:abbrev(cInfo.name, 100) }</span>
                        </li>
                        <li class="list-group-item">
                            <span>年化利率：</span>
                            <span class="menu-text-right pull-right"><fmt:formatNumber value="${cInfo.rate }" type="percent" maxFractionDigits="1" /></span>
                        </li>
                        <li class="list-group-item">
                            <span>投资金额：</span>
                            <span class="menu-text-right pull-right"><fmt:formatNumber value="${amount }" pattern="#0.00"/>元</span>
                        </li>
                    </ul>
                </div>
                <form id="investForm" action="${yeepayURL }" method="post">
		        	<textarea name="req" style="display:none;">${req }</textarea>
		        	<input type="hidden" name="sign" value="${sign }"/>
		        </form>
            </div>
        </div>
        </div>
        <script type="text/javascript">
           $(document).ready(
   				function() {
   					$("#investForm").submit();
   				}
   			);
		</script>
</body>
</html>