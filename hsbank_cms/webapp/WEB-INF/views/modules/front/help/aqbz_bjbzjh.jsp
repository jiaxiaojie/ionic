<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="decorator" content="help_center"/>
	<link href="${ctxStatic}/modules/front/css/help_center.css?${version }" rel="stylesheet"/>
	<title></title>
</head>
<body>
<div id="content_bottom" class="help_content">
<div class="help_main">
	<div class="right_title"><span>花生本金保障计划</span></div>
   	<div class="content">
           <div class="panel-group" id="accordion">
           	<div class="panel panel-default">
                   <div class="panel-heading">
                     <h4 class="panel-title">
                       <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>在花生金服投资能否保障本金安全？<i class="icon_plus"></i></a>
                     </h4>
                   </div>
                   <div id="collapse1" class="panel-collapse collapse">
                     <div class="panel-body">
                       <p>为保障投资人的出借安全，花生金服设有适用于全体理财用户的本金保障计划、以及严谨完善的风控体系。</p>
                     </div>
                   </div>
               </div>
           	<div class="panel panel-default">
                   <div class="panel-heading">
                     <h4 class="panel-title">
                       <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>什么是花生保障计划<i class="icon_plus"></i></a>
                     </h4>
                   </div>
                   <div id="collapse2" class="panel-collapse collapse">
                     <div class="panel-body">
                       <p>“花生保障计划”是上海富定金融信息服务股份有限公司(下称“花生金服”，运营www.hsbank360.com网站（下称“平台”）为保护平台全体投资人的共同权益而建立的信用风险共担机制。<a href="" class="btn_text_gold">查看详情>></a></p>
                       <p><strong>平台承诺只选择最优质的债权项目：</strong></p>
                       <p><span class="label label-info">抵押标的：</span> 借款人以抵押物（房产、车产、保值物品）作为担保在和花生金服平台发布借款项目的模式。平台所有借款和信贷平台所有债权均提前公示详细信息，保证债权透明，不存在虚假债权。具备强制执行效力。即使借款人违约，出借人仍可保障本金、收益的安全。</p>
                       <p>1、项目均由花生金服的专业风控团队对项目借款人通过线下严格审核，确保该项目已办理相关手续，债权来源合规、合法。进行多个第三方机构负责对借款人的抵押物、借款人资质以及是否涉及法律纠纷和诉讼进行专业的综合评估。平台也会根据相关提供的信息对借款人进行家访调查，审查是否属实，提醒借款人归息日期及金额。若借款人逾期未能还款，则进行电话及上门催收工作。</p>
                       <p>2、风险保证金：风险准备金是花生金服所设立的用于保障出借人利益，在出借人资金无法顺利回款的情况下弥补出借人损失的资金，是避免出借人利益损失的一项强有力的措施。平台根据每月新增待收金额，提取一定比例的金额放入“风险准备金”账户。
平台承诺风险准备金专款专用，仅用于弥补出借人的资金损失。如果发生项目违约，将在该项目到期日之后的3个工作日内启动平台风险保证金收购该项目债权。该计划适用于花生金服所有产品、标的。详见<a href="" class="btn_text_gold">《花生金服风险备用金账户-产品偿付规则明细表》</a>
                       </p>
                       <p>3、花生金服平台所有产品、标的，和投资权益均由国内顶尖律师事务所提供法律咨询支持服务，确保项目合规、合法。<a href="" class="btn_text_gold">查看详情>></a></p>
                       <p></p>
                     </div>
                   </div>
               </div> 
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	function collapseClick(element) {
		if($(element).hasClass("collapsed")) {
			$(element).find("i").removeClass("icon_plus").addClass("icon_minus");
		}else {
			$(element).find("i").removeClass("icon_minus").addClass("icon_plus");
		}
	}
</script>
</body>
</html>
