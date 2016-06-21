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
			function toBindBankCard() {
				$("#bindBankCardForm").submit();
			}
		</script>
	</head>
	<body>
	    <div class="app-body" style="display: none;">
        <div class="app-content">
             <div class="yp-main">
                <c:choose>
				<c:when test="${cardStatus eq 'UNBIND'}">
				    <div class="icon_card_area"><i class="icon_card"></i></div>
	                <div class="container hs-margin-top-10">
	                    <div class="row">
	                        <div class="col-xs-12 font-size-12 text-success"><c:choose><c:when test="${hasOpenThirdAccount }">您还未在易宝支付绑定银行卡</c:when><c:otherwise>您还未开通易宝支付账号，请先开通</c:otherwise></c:choose></div>
	                    </div>
	                </div>
	                <form id="bindBankCardForm" action="${bindBankCardUrl }" method="post">
			        	<textarea name="req" style="display:none;">${req }</textarea>
			        	<input type="hidden" name="sign" value="${sign }"/>
			        </form>
			        <div class="yp-btn hs-margin-top-10">
                    <div class="container">
                        <div class="row">
                            <div class="col-xs-12">
                                <c:choose><c:when test="${hasOpenThirdAccount }"><a href="javascript:void(0);" class="btn btn-danger btn-block btn-md" onClick="toBindBankCard()">立即绑定</a></c:when></c:choose>
                            </div>
                        </div>
                    </div>
                </div>
                </c:when>
                </c:choose>
                <c:if test="${cardStatus eq 'VERIFIED' }">
                    <div class="container hs-margin-top-10">
	                    <div class="row">
	                        <div class="col-xs-12 font-size-12 text-success">您已在易宝支付绑定银行卡</div>
	                    </div>
	                </div>
                </c:if>
				<c:if test="${cardStatus eq 'VERIFYING' }">
				     <div class="container hs-margin-top-10">
	                    <div class="row">
	                        <div class="col-xs-12 font-size-12 text-success">您绑定的银行卡正在认证中……</div>
	                    </div>
	                </div>
				</c:if>
            </div>
        </div>
        </div>
        <script type="text/javascript">
           $("#bindBankCardForm").submit();
		</script>
	</body>
</html>