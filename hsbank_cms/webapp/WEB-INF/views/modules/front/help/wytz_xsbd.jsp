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
		<div class="right_title"><span>新手必读</span></div>
     
     	<div class="content">
             <div class="panel-group" id="accordion">
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse0" class="collapsed"><em class="icon_arrow_right"></em>产品介绍<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse0" class="panel-collapse collapse">
                   <div class="panel-body">
                   	<dl>
                     	<dt>花生金服的投资标的有哪些？</dt>
                         <dd>花生金服投资标的有：车辆抵押贷款、房屋抵押贷款、典当质押贷款、其他债权转让、量化对冲、个人信用贷。</dd>
                     </dl>
                     <dl>
                     	<dt>花生金融的融资方式：</dt>
                         <dd>服务平台+线下投资理财客户</dd>
                     </dl>
                   </div>
                 </div>
               </div>
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse1" class="collapsed"><em class="icon_arrow_right"></em>理财收益有多少？理财期限有多久？<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse1" class="panel-collapse collapse">
                   <div class="panel-body">
                     <p>我们为投资用户提供多样化的投资项目，具体收益和理财期限信息见各个投资项目详情。</p>
                   </div>
                 </div>
               </div>
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse3" class="collapsed"><em class="icon_arrow_right"></em>理财是否有额外费用？<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse3" class="panel-collapse collapse">
                   <div class="panel-body">
                   	<p><span class="label label-info">理财过程中可能产生的费用如下：</span></p>
                     <div class="div_height_10"></div>
                     <dl>
                     	<dt>充值费用：</dt>
                         <dd>第三方支付平台将收取您充值金额的0.18%作为转账费用。目前由花生金服补贴。</dd>
                     </dl>
                     <dl>
                     	<dt>提现费用：</dt>
                         <dd>第三方平台将收取每笔2元的提现费用，用户可以使用花生金服赠送的免费提现服务进行提现。每位注册用户首次平台将提供10次免费提现服务，限制每账号每天提现一次。</dd>
                         <dd><span class="label label-important">注：</span> 充值/提现时由第三方支付收取的支付费用，花生金服不收取任何费用哦。</dd>
                     </dl>
                     <dl>
                     	<dt>债权转让费用：</dt>
                         <dd>平台向转出人和购买人同时收取债权转让管理费。</dd>
                         <dd><span class="label label-info">转让管理费金额：</span> 成交金额*转让管理费率。转让管理费按转让债权金额的0.2%收取，债权转让管理费在成交后直接从成交金额中扣除，不成交不收费。</dd>
                     </dl>
                   </div>
                 </div>
               </div>
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse4" class="collapsed"><em class="icon_arrow_right"></em>如何获取和使用现金券、花生豆？<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse4" class="panel-collapse collapse">
                   <div class="panel-body">
                   	<p><span class="label label-info">现金券获取：</span> 目前新人注册、首次投资，邀请好友都可以获得现金券，更多活动，敬请关注花生金服平台的活动通告。</p>
                     <div class="div_height_10"></div>
                   	<p><span class="label label-info">现金券使用：</span> 现金券可用于投资项目时抵扣相应的金额。</p>
                     <div class="div_height_10"></div>
                   	<p><span class="label label-info">花生豆获取：</span> 每日签到、投资可获得相应比例的花生豆。</p>
                     <div class="div_height_10"></div>
                   	<p><span class="label label-info">花生豆使用：</span> 花生豆可用于花生乐园兑换奖品。花生乐园即将上线，敬请期待。</p>
                     <div class="div_height_10"></div>
                   	<p><span class="label label-important">注：</span> 每日签到可获得10花生豆，每投资1元可获得1花生豆。</p>
                     <div class="div_height_10"></div>
                   </div>
                 </div>
               </div>
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse5" class="collapsed"><em class="icon_arrow_right"></em>提现多久到账？<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse5" class="panel-collapse collapse">
                   <div class="panel-body">
                     <p>资金将在申请提现后1-2个工作日内到达您指定的银行账户。</p>
                   </div>
                 </div>
               </div>
               <div class="panel panel-default">
                 <div class="panel-heading">
                   <h4 class="panel-title">
                     <a data-toggle="collapse" onclick="collapseClick(this)" data-parent="#accordion" href="#collapse6" class="collapsed"><em class="icon_arrow_right"></em>平台计息方式？<i class="icon_plus"></i></a>
                   </h4>
                 </div>
                 <div id="collapse6" class="panel-collapse collapse">
                   <div class="panel-body">
                     <dl>
                     	<dt>等额本息：</dt>
                         <dd>等额本息还款法是一种被广泛采用的还款方式。在还款期内，每月偿还同等数额的借款(包括本金和利息)。融资人每月还款额中的本金比重逐月递增、利息比重逐月递减。</dd>
                         <dd><span class="label label-info">具体计算公式如下：</span></dd>
                         <dd><strong>P=(A∙b%∙(1+b%)^n)/((1+b%)^n-1)</strong></dd>
                         <dd>P: 每月还款额<br>A: 借款本金<br>b: 月利率<br>n: 还款总期数</dd>
                         <dd>因计算中存在四舍五入，最后一期还款金额与之前略有不同。</dd>
                     </dl>
                     <dl>
                     	<dt>等额本金：</dt>
                     	 <dd><strong>P=A÷n+（A-C）∙b%</strong></dd>
                         <dd>P: 每月还款额<br>C: 已归还本机累计额<br>A: 借款本金<br>b: 月利率<br>n: 还款总期数</dd>
                         <dd>因计算中存在四舍五入，最后一期还款金额与之前略有不同。</dd>
                     </dl>
                     <dl>
                     	<dt>到期还本付息：</dt>
                         <dd>利息=本金×[时期（年或月数）×年或月利率+零头天数×日利率]</dd>
                         <dd><span class="label label-important">注：</span> 月利率=年利率/12，日利率=年利率/360，期利率=年利率×每期月数/12。</dd>	
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
