<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="decorator" content="front_with_juanzhou"/>
		<link href="${ctxStatic}/modules/front/css/wytz/project_tzsb.css?${version }" rel="stylesheet"/>
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
        	<div class="buy_list"><b>投资项目</b><p><span class="blue-text">${p2p:abbrev(projectBaseInfo.projectName,100)}</span></p></div>
        	<div class="buy_list"><b>投资金额</b><p><span>${amount }</span>元</p></div>
        	<div class="buy_list"><b>年化利率</b><p><fmt:formatNumber  value="${projectBaseInfo.annualizedRate * 100 }" pattern="#.##" />%</p></div>
        	<div class="buy_list"><b>还款方式</b><p>${fns:getDictLabel(projectBaseInfo.repaymentMode, 'project_repayment_mode_dict', '')}</p></div>
        	<div class="buy_list"><b>还款日期</b><p><fmt:formatDate value="${projectBaseInfo.plannedRepaymentDate }" pattern="yyyy-MM-dd"/></p></div>
        </div>
        
        <div class="btn_brown_158x38" style="margin-bottom:80px;"><a href="${ctxFront }/wytz">返回我要投资</a></div>
    </div>

</div>
</body>
</html>