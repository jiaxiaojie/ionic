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
	</head>
	<body>
     <div class="app-body">
        <div class="app-content">
             <div class="yp-main">
                <div class="container">
                    <div class="row hs-margin-top-10">
                        <div class="col-xs-12 text-center">
                            <span class="icon-img-md">
                                <img src="${ctxStatic}/modules/mobile/images/icon/registration_failure.png" />
                            </span>
                        </div>
                    </div>
                </div>
                <div class="container hs-margin-top-10">
                    <div class="row">
                        <div class="col-xs-12 font-size-12 text-success">${description }</div>
                    </div>
                </div>
                <div class="yp-body">
                    <ul class="list-group menu hs-margin-top-10">
                        <li class="list-group-item">
                            <span>真实姓名：</span>
                            <span class="menu-text-right pull-right">${p2p:vagueName(investmentRecord.cb.customerName) }</span>
                        </li>
                        <li class="list-group-item">
                            <span>身份证号：</span>
                            <span class="menu-text-right pull-right">${p2p:vagueCertNum(investmentRecord.cb.certNum) }</span>
                        </li>
                        <li class="list-group-item">
                            <span>投资项目：</span>
                            <span class="menu-text-right pull-right">${fns:getDictLabel(projectBaseInfo.projectTypeCode, 'project_type_dict', '')} ${projectBaseInfo.projectCode }</span>
                        </li>
                        <li class="list-group-item">
                            <span>投资金额：</span>
                            <span class="menu-text-right pull-right">${investmentRecord.amount }元</span>
                        </li>
                        <li class="list-group-item">
                            <span>年化利率：</span>
                            <span class="menu-text-right pull-right"><fmt:formatNumber  value="${projectBaseInfo.annualizedRate * 100 }" pattern="#.##" />%</span>
                        </li>
                        <li class="list-group-item">
                            <span>还款方式：</span>
                            <span class="menu-text-right pull-right">${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</span>
                        </li>
                        <li class="list-group-item">
                            <span>还款日期：</span>
                            <span class="menu-text-right pull-right">
	                            <c:choose>
					         	  <c:when test="${projectBaseInfo.repaymentMode eq '1' && repaymentPlan.size() > 0 }">
					         	      <fmt:formatDate value="${repaymentPlan.get(0).getEndDate() }" pattern="yyyy-MM-dd"/>
					         	  </c:when>
					         	  <c:otherwise>
					         	      <fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate }" pattern="yyyy-MM-dd"/>
					         	  </c:otherwise>
					            </c:choose>
				            </span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        </div>
        <script type="text/javascript">
	        var mobileType = "${mobileType }";
	        if(mobileType == "WECHAT"){
	        	window.location.href = "${callbackUrl}";
	        }else if(mobileType == "ANDROID"){
	        	window.WebJavaScript.investReturn();
	        }else if(mobileType == "IOS"){
	        	window.location.href = "${resultUrl }";
	        }
        </script>
</body>
</html>