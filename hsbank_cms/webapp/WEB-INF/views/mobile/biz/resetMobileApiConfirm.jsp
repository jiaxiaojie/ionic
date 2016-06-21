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
			function toResetTransPwd() {
				$("#resetMobileForm").submit();
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
                            <span>姓名：</span>
                            <span class="menu-text-right pull-right">${customerBase.customerName }</span>
                        </li>
                        <li class="list-group-item">
                            <span>身份证号：</span>
                            <span class="menu-text-right pull-right">${customerBase.certNum }</span>
                        </li>
                        <li class="list-group-item">
                            <span>手机号：</span>
                            <span class="menu-text-right pull-right">${customerBase.mobile }</span>
                        </li>
                    </ul>
                </div>
                <form id="resetMobileForm" action="${resetMobileUrl }" method="post">
		        	<textarea name="req" style="display:none;">${req }</textarea>
		        	<input type="hidden" name="sign" value="${sign }"/>
		        </form>
                <div class="yp-btn hs-margin-top-10">
                    <div class="container">
                        <div class="row">
                            <div class="col-xs-12">
                                <a href="javascript:void(0);" class="btn btn-danger btn-block btn-md" onclick="toResetTransPwd()">前往修改</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <script type="text/javascript">
           $("#resetMobileForm").submit();
		</script>
	</body>
</html>