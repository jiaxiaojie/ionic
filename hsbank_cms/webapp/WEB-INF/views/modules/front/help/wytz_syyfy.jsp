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
     	
         <div class="right_title"><span>收益与费用</span></div>
     
     	<div class="content">
             <div class="panel-group" id="accordion">
             
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>购买债权转让收益<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse1" class="panel-collapse collapse">
                   <div class="panel-body">
                     <p>年利率区间7%—24%，具体收益由您所投资的转让债权利率确定。</p>
                   </div>
                 </div>
               </div>
               
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse2" class="collapsed"><em class="icon_arrow_right"></em>等额本息借款的收益该如何计算<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse2" class="panel-collapse collapse">
                   <div class="panel-body">
                     <dl>
                         <dd>等额本息还款法是一种被广泛采用的还款方式。在还款期内，每月偿还同等数额的借款(包括本金和利息)。融资人每月还款额中的本金比重逐月递增、利息比重逐月递减。</dd>
                         <dd><span class="label label-info">具体计算公式如下：</span></dd>
                         <dd>P: 每月还款额<br>A: 借款本金<br>b: 月利率<br>n: 还款总期数</dd>
                         <dd>因计算中存在四舍五入，最后一期还款金额与之前略有不同。</dd>
                     </dl>
                   </div>
                 </div>
               </div>
               
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>充值提现费用<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse3" class="panel-collapse collapse">
                   <div class="panel-body">
                   	<dl>
                     	<dt>充值费用：</dt>
                         <dd>第三方支付平台将收取您充值金额的0.18%作为转账费用。目前由花生金服补贴。</dd>
                     </dl>
                   	<dl>
                     	<dt>提现费用：</dt>
                         <dd>第三方平台将收取每笔2元的提现费用，用户可以使用花生金服赠送的免费提现服务进行提现。每位注册用户首次平台将提供10次免费提现服务，限制每账号每天提现一次。</dd>
                     </dl>
                     <dd><span class="label label-important">注：</span> 充值/提现时由第三方支付收取的支付费用，花生金服不收取任何费用哦。</dd>
                   </div>
                 </div>
               </div>
               
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>债权转让管理费<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse4" class="panel-collapse collapse">
                   <div class="panel-body">
                     <dl>
                     	<dd>债权转让的费用为转让管理费，平台向转出人和购买人同时收取。</dd>
                     	<dd><span class="label label-info">转让管理费金额：</span>成交金额*转让管理费率。转让管理费率在目前的运营中按0.1%收取，具体金额以债权转让页面显示为准。债权转让管理费在成交后直接从成交金额中扣除，不成交平台不向用户收取转让管理费。</dd>
                     </dl>
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
