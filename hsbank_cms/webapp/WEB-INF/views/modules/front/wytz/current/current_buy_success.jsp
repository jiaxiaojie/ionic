<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_tzcg.css?${version }" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/front/css/wdzh/zjgl_ktzh.css?${version }" />
		<title></title>
	</head>
	<body>
<!--以下内容放入“<div class="juanzhou_middle"></div>”里面-->
<div class="content980">

	<div class="tzcg">
    	<div class="tzcg_1"></div>
        
        <div class="tzcg_2">
        	<div class="buy_list"><b>真实姓名</b><p>${p2p:vagueName(cHis.cb.customerName) }</p></div>
        	<div class="buy_list"><b>身份证号</b><p>${p2p:vagueCertNum(cHis.cb.certNum) }</p></div>
        	<div class="buy_list"><b>投资项目</b><p><span class="blue-text">${p2p:abbrev(cInfo.name,100)}</span></p></div>
        	<div class="buy_list"><b>投资金额</b><p><span><fmt:formatNumber value="${cHis.changeValue }" pattern="#0.00"/></span>元</p></div>
        	<div class="buy_list"><b>年化利率</b><p><fmt:formatNumber  value="${cInfo.rate * 100 }" pattern="#.##" />%</p></div>
        </div>
        
        <div class="btn_group_one" style="margin-bottom:80px;">
            <!--<a href="javascript:;" class="btn_brown_158x31">下载合同</a>-->
            <a href="${ctxFront}/currentAccount/myCurrentPeanut" class="btn_blue_158x31">查看我的活花生</a>
        </div>
        
        <div class="bottom-grain"></div>
    </div>

</div>
</body>
</html>