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
			$(document).ready(
				function() {
					$("#rechargeForm").submit();
				}
			);
		</script>
	</head>
	<body>
	    <div class="app-body" style="display: none;">
        <div class="app-content">
             <div class="yp-main">
                 <div class="yp-body">
                     <ul class="list-group menu hs-margin-top-10">
                         <li class="list-group-item">
                             <span>充值金额(元)</span>
                             <span class="menu-text-right pull-right text-success"><fmt:formatNumber value="${amount }" pattern="#0.00"/></span>
                         </li>
                     </ul>
                 </div>
                 <div class="container">
                     <div class="row">
                         <div class="col-xs-12 font-size-12 text-success">充值手续费按充值金额的0.18%由第三方支付平台收取。目前该手续费由花生金服承担</div>
                     </div>
                 </div>
                 <form id="rechargeForm" action="${rechargeUrl }" method="post">
		        	<textarea name="req" style="display:none;">${req }</textarea>
		        	<input type="hidden" name="sign" value="${sign }"/>
		        </form>
                 <div class="yp-btn">
                     <div class="container hs-margin-top-10">
                         <div class="row">
                             <div class="col-xs-12">
                                 <a href="javascript:void(0);" class="btn btn-danger btn-block btn-md" onclick="toRecharge()">确认</a>
                             </div>
                         </div>
                     </div>
                 </div>
             </div>
        </div>
        </div>
	</body>
</html>