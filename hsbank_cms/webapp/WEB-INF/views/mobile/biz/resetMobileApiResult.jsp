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
              <c:choose>
		    	<c:when test="${isSuccess }">
                <div class="container hs-margin-top-10">
                    <div class="row">
                        <div class="col-xs-12 font-size-12 text-success">修改手机号成功！</div>
                    </div>
                </div>
               </c:when>
               <c:otherwise>
                    <div class="container hs-margin-top-10">
                    <div class="row">
                        <div class="col-xs-12 font-size-12 text-success">修改手机号失败！</div>
                    </div>
                </div>
               </c:otherwise>
               </c:choose>
            </div>
        </div>
        </div>
        <script type="text/javascript">
	        var mobileType = "${mobileType }";
	        if(mobileType == "WECHAT" || mobileType == "WEB"){
	        	window.location.href = "${callbackUrl}";
	        }else if(mobileType == "ANDROID"){
	        	window.WebJavaScript.resetTransPwdReturn();
	        }else if(mobileType == "IOS"){
	        	window.location.href = "${resultUrl }";
	        }
        </script>
	</body>
</html>