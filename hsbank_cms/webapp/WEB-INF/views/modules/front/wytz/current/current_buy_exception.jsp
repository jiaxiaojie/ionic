<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_tzsb.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title></title>
	</head>
	<body>

<!--以下内容放入“<div class="juanzhou_middle"></div>”里面-->
<div class="content980">

	<div class="tzcg">
    	<div class="tzsb_1">
        	<p><em class="red-text">*&nbsp;</em><span style="color:red">${description }</span></p>
        </div>
        
        <div class="tzcg_2">
        	<div class="buy_list"><b>投资项目</b><p><span class="blue-text">${p2p:abbrev(cInfo.name,100)}</span></p></div>
        	<div class="buy_list"><b>投资金额</b><p><span><fmt:formatNumber value="${amount }" pattern="#0.00"/></span>元</p></div>
        	<div class="buy_list"><b>年化利率</b><p><fmt:formatNumber  value="${cInfo.rate * 100 }" pattern="#.##" />%</p></div>
        </div>
        
        <div class="btn_brown_158x38" style="margin-bottom:80px;"><a href="${ctxFront }/current/currentProjectDetail?id=${cInfo.id }">返回项目详情页</a></div>
        
        <div class="bottom-grain"></div>
    </div>

</div>
</body>
</html>